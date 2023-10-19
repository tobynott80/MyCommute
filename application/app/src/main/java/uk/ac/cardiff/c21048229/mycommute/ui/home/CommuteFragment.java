package uk.ac.cardiff.c21048229.mycommute.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.cardiff.c21048229.mycommute.R;
import uk.ac.cardiff.c21048229.mycommute.databinding.FragmentCommuteHomeBinding;
import uk.ac.cardiff.c21048229.mycommute.ui.timetable.TrainService;

public class CommuteFragment extends Fragment {
    FragmentCommuteHomeBinding binding;
    Commute commute;
    String commuteType;
    String departureTime;

    public CommuteFragment() {

    }

    public static CommuteFragment newInstance(String commuteType) {
        CommuteFragment fragment = new CommuteFragment();
        Bundle args = new Bundle();
        args.putString("commuteType", commuteType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            commuteType = getArguments().getString("commuteType");
        }
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
        HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        if (commuteType.equals("Home")) {
            commute = homeViewModel.getHomeCommute().getValue();
            departureTime = homeViewModel.getHomeDepartureTime().getValue();
            homeViewModel.getHomeCommute().observe(getViewLifecycleOwner(), new Observer<Commute>() {
                @Override
                public void onChanged(Commute homeCommute) {
                    commute = homeCommute;
                }
            });
            homeViewModel.getHomeDepartureTime().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String dTime) {
                    departureTime = dTime;
                }
            });
        } else {
            commute = homeViewModel.getWorkCommute().getValue();
            departureTime = homeViewModel.getWorkDepartureTime().getValue();
            homeViewModel.getWorkCommute().observe(getViewLifecycleOwner(), new Observer<Commute>() {
                @Override
                public void onChanged(Commute workCommute) {
                    commute = workCommute;
                }
            });
            homeViewModel.getWorkDepartureTime().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String dTime) {
                    departureTime = dTime;
                }
            });
        }
        updateCommuteUI();
    }

    private void updateCommuteUI() {
        if (commute != null && commute.isValid) {
            // Set route detail
            binding.tvRouteDetails.setText(String.format("%s to %s from %s:%s", commute.Destination, commute.Arrival, departureTime.substring(0, 2), departureTime.substring(2)));

            ArrayList<TrainService> remainingServices = commute.services;
            RecyclerView recyclerView = binding.rvCommuteServices;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            CommuteListRecyclerAdapter adapter = new CommuteListRecyclerAdapter(remainingServices);
            recyclerView.setAdapter(adapter);

        } else {
            binding.tvRouteDetails.setText(R.string.no_services_found);
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
