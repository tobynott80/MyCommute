package uk.ac.cardiff.c21048228.mycommute.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentSettingsBinding;
import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.LocationSelectorFragment;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        btnSetHomeDeparture.setText(sharedPreferences.getString("homeDepartureName", "Departure"));
        btnSetHomeArrival.setText(sharedPreferences.getString("homeArrivalName", "Arrival"));
        btnSetWorkDeparture.setText(sharedPreferences.getString("workDepartureName", "Departure"));
        btnSetWorkArrival.setText(sharedPreferences.getString("workArrivalName", "Arrival"));

        btnSetHomeDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open location selector fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StationSelectorFragment stationSelectorFragment = new StationSelectorFragment("homeDeparture");
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
                StationSelectorFragment stationSelectorFragment = new StationSelectorFragment("homeArrival");
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
                StationSelectorFragment stationSelectorFragment = new StationSelectorFragment("workDeparture");
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
                StationSelectorFragment stationSelectorFragment = new StationSelectorFragment("workArrival");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, stationSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}