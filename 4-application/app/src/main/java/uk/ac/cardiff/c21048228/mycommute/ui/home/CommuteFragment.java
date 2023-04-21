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
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainServiceRecyclerAdapter;

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
        // Set an empty adapter for the RecyclerView
        RecyclerView recyclerView = binding.rvCommuteHome;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TrainServiceRecyclerAdapter(new ArrayList<>()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (commute.isValid) {
            if (commute.services.size() > 2) {
                // Set route detail
                binding.tvRouteDetails.setText(String.format("%s to %s", commute.Destination, commute.Arrival));
                // Set first departure UI
                binding.tvDestination1.setText(commute.services.get(0).getDestination());
                binding.tvOrigin1.setText(commute.services.get(0).getOrigin());
                binding.tvPlatformNo1.setText(commute.services.get(0).getPlatform());
                binding.tvTime1.setText(commute.services.get(0).getDepartureTime());
                if(commute.services.get(0).getStatus().equals(ON_TIME)){
                    binding.tvStatus1.setText(R.string.on_time);
                    binding.tvStatus1.setTextColor(Color.parseColor("#4CAF50"));
                } else if (commute.services.get(0).getStatus().equals(DELAYED)){
                    binding.tvStatus1.setText(R.string.delayed);
                    binding.tvStatus1.setTextColor(Color.parseColor("#FFC107"));
                } else {
                    binding.tvStatus1.setText(R.string.cancelled);
                    binding.tvStatus1.setTextColor(Color.parseColor("#F44336"));
                }
                ArrayList<TrainService> remainingServices = commute.services;
                remainingServices.remove(0);
                RecyclerView recyclerView = binding.rvCommuteHome;
                TrainServiceRecyclerAdapter adapter = new TrainServiceRecyclerAdapter(remainingServices);
                recyclerView.setAdapter(adapter);




//                // Set second departure UI
//                binding.tvDestination2.setText(commute.services.get(1).getDestination());
//                binding.tvOrigin2.setText(commute.services.get(1).getOrigin());
//                binding.tvPlatformNo2.setText("Platform " + commute.services.get(1).getPlatform());
//                binding.tvTime2.setText(commute.services.get(1).getDepartureTime());
//                if(commute.services.get(1).getStatus().equals(ON_TIME)){
//                    binding.tvOrigin2.setTextColor(Color.parseColor("#4CAF50"));
//                } else if (commute.services.get(1).getStatus().equals(DELAYED)){
//                    binding.tvOrigin2.setTextColor(Color.parseColor("#FFC107"));
//                } else {
//                    binding.tvOrigin2.setTextColor(Color.parseColor("#F44336"));
//                }
//
//                // Set third departure UI
//                binding.tvDestination3.setText(commute.services.get(2).getDestination());
//                binding.tvOrigin3.setText(commute.services.get(2).getOrigin());
//                binding.tvPlatformNo3.setText("Platform " + commute.services.get(2).getPlatform());
//                binding.tvTime3.setText(commute.services.get(2).getDepartureTime());
//                if(commute.services.get(2).getStatus().equals(ON_TIME)){
//                    binding.tvOrigin3.setTextColor(Color.parseColor("#4CAF50"));
//                } else if (commute.services.get(2).getStatus().equals(DELAYED)){
//                    binding.tvOrigin3.setTextColor(Color.parseColor("#FFC107"));
//                } else {
//                    binding.tvOrigin3.setTextColor(Color.parseColor("#F44336"));
//                }
            }
        } else {
            binding.tvRouteDetails.setText("No services found");
        }
    }
}
