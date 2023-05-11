package uk.ac.cardiff.c21048229.mycommute.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import uk.ac.cardiff.c21048229.mycommute.R;
import uk.ac.cardiff.c21048229.mycommute.databinding.FragmentTimeSelectorBinding;

public class TimeSelectorFragment extends Fragment {
    FragmentTimeSelectorBinding binding;
    String timeTarget;

//    public TimeSelectorFragment(String timeTarget) {
//        this.timeTarget = timeTarget;
//    }

    public TimeSelectorFragment() {
        // Required empty public constructor
    }

    public static TimeSelectorFragment newInstance(String timeTarget) {
        TimeSelectorFragment fragment = new TimeSelectorFragment();
        Bundle args = new Bundle();
        args.putString("timeTarget", timeTarget);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            timeTarget = getArguments().getString("timeTarget");
        }

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTimeSelectorBinding.inflate(inflater, container, false);
        View view =  binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnSubmit = binding.btnSumbit;
        Button btnCancel = binding.btnCancel;
        TimePicker timePicker = binding.timePicker;
        TextView tvTitle = binding.tvTitle;
        if (timeTarget.equals("homeTime") || timeTarget.equals("homeCTime")) {
            tvTitle.setText(R.string.homeTimeTitle);
        } else if (timeTarget.equals("workTime") || timeTarget.equals("workCTime")) {
            tvTitle.setText(R.string.workTimeTitle);
        }
        timePicker.setIs24HourView(true);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the selected time to shared preferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String timeString = String.format(Locale.US, "%02d%02d", hour, minute);
                sharedPreferences.edit().putString(timeTarget, timeString).apply();

                // Return to settings fragment
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to settings fragment
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }
}
