package uk.ac.cardiff.c21048228.mycommute.ui.settings;

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

import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentTimeSelectorBinding;

public class TimeSelectorFragment extends Fragment {
    FragmentTimeSelectorBinding binding;
    String timeTarget;

    public TimeSelectorFragment(String timeTarget) {
        this.timeTarget = timeTarget;
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
        if (timeTarget.equals("homeTime")) {
            tvTitle.setText(R.string.homeTimeTitle);
        } else if (timeTarget.equals("workTime")) {
            tvTitle.setText(R.string.workTimeTitle);
        }
        timePicker.setIs24HourView(true);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save time to shared prefs
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
                switch (timeTarget) {
                    case "homeTime":
                        //save home time
                        sharedPreferences.edit().putString("homeTime", String.valueOf(timePicker.getHour()) + String.valueOf(timePicker.getMinute())).apply();
                        break;
                    case "workTime":
                        //save work time
                        sharedPreferences.edit().putString("workTime", String.valueOf(timePicker.getHour()) + String.valueOf(timePicker.getMinute())).apply();
                        break;
                }
                //return to settings fragment
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
