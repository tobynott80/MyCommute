package uk.ac.cardiff.c21048228.mycommute.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
}
