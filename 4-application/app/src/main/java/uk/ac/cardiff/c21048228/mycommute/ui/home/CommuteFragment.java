package uk.ac.cardiff.c21048228.mycommute.ui.home;

import static uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus.DELAYED;
import static uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus.ON_TIME;

import android.graphics.Color;
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

import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentCommuteHomeBinding;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainService;
import uk.ac.cardiff.c21048228.mycommute.ui.home.CommuteListRecyclerAdapter;

public class CommuteFragment extends Fragment {
    FragmentCommuteHomeBinding binding;
    Commute commute;

    public CommuteFragment(Commute commute) {
        this.commute = commute;
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
                binding.tvRouteDetails.setText(String.format("%s to %s", commute.Destination, commute.Arrival));

                ArrayList<TrainService> remainingServices = commute.services;
                RecyclerView recyclerView = binding.rvCommuteServices;
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                CommuteListRecyclerAdapter adapter = new CommuteListRecyclerAdapter(remainingServices);
                recyclerView.setAdapter(adapter);

        } else {
            binding.tvRouteDetails.setText("No services found");
        }
    }

}
