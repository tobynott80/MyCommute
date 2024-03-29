package uk.ac.cardiff.c21048229.mycommute.ui.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

import uk.ac.cardiff.c21048229.mycommute.R;
import uk.ac.cardiff.c21048229.mycommute.databinding.FragmentSettingsBinding;
import uk.ac.cardiff.c21048229.mycommute.notification.DailyNotificationReceiver;
import uk.ac.cardiff.c21048229.mycommute.notification.NotificationHelper;
import uk.ac.cardiff.c21048229.mycommute.ui.welcome.WelcomeActivity;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private Calendar homeNotificationTime;
    private Calendar workNotificationTime;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btnSetHomeDeparture = binding.btnHomeDeparture;
        Button btnSetHomeArrival = binding.btnHomeArrival;
        Button btnSetWorkDeparture = binding.btnWorkDeparture;
        Button btnSetWorkArrival = binding.btnWorkArrival;
        Button btnHomeCTime = binding.btnHomeCTime;
        Button btnWorkCTime = binding.btnWorkCTime;
        SwitchMaterial switchNotification = binding.swNotification;
        Button btnHCtime = binding.btnHCtime;
        Button btnWCtime = binding.btnWCtime;
        TextView welcomePageButton = binding.tvWelcomeButton;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        // Set button text based on shared preferences
        if(sharedPreferences.getString("homeDepartureName", "Departure").equals("Departure")){
            btnSetHomeDeparture.setText(getString(R.string.departure));
        } else {
            btnSetHomeDeparture.setText(sharedPreferences.getString("homeDepartureName", "Departure"));
        }
        if(sharedPreferences.getString("homeArrivalName", "Arrival").equals("Arrival")){
            btnSetHomeArrival.setText(getString(R.string.arrival));
        } else {
            btnSetHomeArrival.setText(sharedPreferences.getString("homeArrivalName", "Arrival"));
        }
        if(sharedPreferences.getString("workDepartureName", "Departure").equals("Departure")){
            btnSetWorkDeparture.setText(getString(R.string.departure));
        } else {
            btnSetWorkDeparture.setText(sharedPreferences.getString("workDepartureName", "Departure"));
        }
        if(sharedPreferences.getString("workArrivalName", "Arrival").equals("Arrival")){
            btnSetWorkArrival.setText(getString(R.string.arrival));
        } else {
            btnSetWorkArrival.setText(sharedPreferences.getString("workArrivalName", "Arrival"));
        }

        if(sharedPreferences.getString("homeCTime", "Home").equals("Home")){
            btnHCtime.setText(getString(R.string.time_plain));
        }else{
            String homeCtime = sharedPreferences.getString("homeCTime", "Home");
            btnHCtime.setText(String.format(getString(R.string.time), homeCtime.substring(0,2), homeCtime.substring(2)));
        }

        if(sharedPreferences.getString("workCTime", "Work").equals("Work")){
            btnWCtime.setText(getString(R.string.time_plain));
        }else{
            String workCtime = sharedPreferences.getString("workCTime", "Work");
            btnWCtime.setText(String.format(getString(R.string.time), (workCtime.substring(0,2)), (workCtime.substring(2))));
        }

        switchNotification.setChecked(sharedPreferences.getBoolean("notification", false));

        if (sharedPreferences.getString("homeTime", "Home").equals("Home")) {
            btnHomeCTime.setText(R.string.home);
        } else {
            String homeTime = sharedPreferences.getString("homeTime", "Home");
            btnHomeCTime.setText(String.format(getResources().getString(R.string.time), (homeTime.substring(0, 2)), (homeTime.substring(2))));
            homeNotificationTime = Calendar.getInstance();
            homeNotificationTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(homeTime.substring(0, 2)));
            homeNotificationTime.set(Calendar.MINUTE, Integer.parseInt(homeTime.substring(2)));
            homeNotificationTime.set(Calendar.SECOND, 0);
        }
        if (sharedPreferences.getString("workTime", "Work").equals("Work")) {
            btnWorkCTime.setText(R.string.work);
        } else {
            String workTime = sharedPreferences.getString("workTime", "Work");
            btnWorkCTime.setText(String.format(getResources().getString(R.string.time), (workTime.substring(0, 2)), (workTime.substring(2))));
            workNotificationTime = Calendar.getInstance();
            workNotificationTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(workTime.substring(0, 2)));
            workNotificationTime.set(Calendar.MINUTE, Integer.parseInt(workTime.substring(2)));
            workNotificationTime.set(Calendar.SECOND, 0);
        }
        setDailyNotification(sharedPreferences.getBoolean("notification", false), homeNotificationTime, workNotificationTime);

        btnSetHomeDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open location selector fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StationSelectorFragment stationSelectorFragment =  StationSelectorFragment.newInstance("homeDeparture");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, stationSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        btnSetHomeArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open location selector fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StationSelectorFragment stationSelectorFragment = StationSelectorFragment.newInstance("homeArrival");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, stationSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        btnSetWorkDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open location selector fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StationSelectorFragment stationSelectorFragment = StationSelectorFragment.newInstance("workDeparture");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, stationSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        btnSetWorkArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open location selector fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StationSelectorFragment stationSelectorFragment = StationSelectorFragment.newInstance("workArrival");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, stationSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        btnHomeCTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open time selector frag
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TimeSelectorFragment timeSelectorFragment = TimeSelectorFragment.newInstance("homeTime");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, timeSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        btnWorkCTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open time selector frag
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TimeSelectorFragment timeSelectorFragment = TimeSelectorFragment.newInstance("workTime");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, timeSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        btnHCtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TimeSelectorFragment timeSelectorFragment = TimeSelectorFragment.newInstance("homeCTime");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, timeSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        btnWCtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TimeSelectorFragment timeSelectorFragment = TimeSelectorFragment.newInstance("workCTime");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, timeSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        welcomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open welcome activity
                Intent intent = new Intent(getContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b && homeNotificationTime == null && workNotificationTime == null){
                    Toast.makeText(getContext(), getString(R.string.no_time_set), Toast.LENGTH_SHORT).show();
                    switchNotification.setChecked(false);
                } else {
                    setDailyNotification(b, homeNotificationTime, workNotificationTime);
                    if(b){
                        NotificationHelper.showNotification(getContext(), "MyCommute", getString(R.string.sample_notification));
                    }
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notification", b);
                editor.apply();
            }

        });

        return root;
    }
    private void setDailyNotification(boolean isEnabled, Calendar homeNotificationTime, Calendar workNotificationTime) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent homeIntent = new Intent(getContext(), DailyNotificationReceiver.class);
        Intent workIntent = new Intent(getContext(), DailyNotificationReceiver.class);
        homeIntent.setAction("homeNotification");
        workIntent.setAction("workNotification");
        PendingIntent homePendingIntent = PendingIntent.getBroadcast(getContext(), 0, homeIntent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent workPendingIntent = PendingIntent.getBroadcast(getContext(), 1, workIntent, PendingIntent.FLAG_IMMUTABLE);


        if (isEnabled) {
            if (homeNotificationTime != null) {
                // Schedule daily notification for home commute
                Calendar now = Calendar.getInstance();
                if (now.after(homeNotificationTime)) {
                    homeNotificationTime.add(Calendar.DAY_OF_MONTH, 1);
                }
                now.set(Calendar.SECOND,00);
                now.set(Calendar.MILLISECOND,00);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, homeNotificationTime.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, homePendingIntent);
            }

            if (workNotificationTime != null) {
                // Schedule daily notification for work commute
                Calendar now = Calendar.getInstance();
                if (now.after(workNotificationTime)) {
                    workNotificationTime.add(Calendar.DAY_OF_MONTH, 1);
                }
                now.set(Calendar.SECOND,00);
                now.set(Calendar.MILLISECOND,00);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, workNotificationTime.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, workPendingIntent);
            }
        } else {
            // Cancel daily notifications
            if (homePendingIntent != null) {
                alarmManager.cancel(homePendingIntent);
            }

            if (workPendingIntent != null) {
                alarmManager.cancel(workPendingIntent);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}