package uk.ac.cardiff.c21048228.mycommute.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentHomeBinding;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainService;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ConstraintLayout commuteLayout = binding.homeCommuteLayout;
        commuteLayout.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", 0);
        if((sharedPreferences.getBoolean("isCommuteSetup", false))){
            commuteLayout.setVisibility(View.VISIBLE);
            // TODO: populate view with commute details
            ArrayList<TrainService> services = new ArrayList<>();

            // Test Code, need to add actual API call
            services.add(new TrainService("9", "12:30", "Cardiff Central", "barry", TrainStatus.ON_TIME));
            services.add(new TrainService("8", "12:30", "Cardiff Central", "merthyr", TrainStatus.ON_TIME));
            services.add(new TrainService("3", "12:30", "Cardiff Central", "queen steet", TrainStatus.ON_TIME));

            Commute commute = new Commute("Cardiff Central", "Barry", services, true);
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            CommuteFragment commuteFragment = new CommuteFragment(commute);
            fragmentTransaction.replace(R.id.homeCommuteLayout, commuteFragment);
            fragmentTransaction.commit();

        } else{
            final TextView textView = binding.tvSetup;
            homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}