package uk.ac.cardiff.c21048228.mycommute.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentCommuteHomeBinding;

public class CommuteFragment extends Fragment {
    FragmentCommuteHomeBinding binding;
    Commute commute;

    public CommuteFragment(Commute commute) {
        this.commute = commute;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCommuteHomeBinding.bind(view);
        binding.tvRouteDetails.setText("Test");
        if(commute.isValid){
            if (commute.services.size()>2){
                binding.tvRouteDetails.setText(String.format("%s to %s", commute.Arrival, commute.Destination));
            }
        }
    }
}
