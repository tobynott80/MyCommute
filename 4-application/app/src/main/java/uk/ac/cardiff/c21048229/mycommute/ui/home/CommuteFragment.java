package uk.ac.cardiff.c21048229.mycommute.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.cardiff.c21048229.mycommute.databinding.FragmentCommuteHomeBinding;
import uk.ac.cardiff.c21048229.mycommute.ui.timetable.TrainService;

public class CommuteFragment extends Fragment {
    FragmentCommuteHomeBinding binding;
    Commute commute;
    String departureTime;

    public CommuteFragment(Commute commute, String departureTime) {
        this.commute = commute;
        this.departureTime = departureTime;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommuteHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (commute.isValid) {
            // Set route detail
            binding.tvRouteDetails.setText(String.format("%s to %s from %s:%s", commute.Destination, commute.Arrival, departureTime.substring(0, 2), departureTime.substring(2)));

            ArrayList<TrainService> remainingServices = commute.services;
            RecyclerView recyclerView = binding.rvCommuteServices;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            CommuteListRecyclerAdapter adapter = new CommuteListRecyclerAdapter(remainingServices);
            recyclerView.setAdapter(adapter);

        } else {
            binding.tvRouteDetails.setText("No services found");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Reset binding
        binding = null;
        // Reset commute
        commute = null;
        // Cancel async tasks

    }


}
