package uk.ac.cardiff.c21048228.mycommute.ui.locationSelector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentLocationSelectorBinding;

public class LocationSelectorFragment extends Fragment {

    private FragmentLocationSelectorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocationSelectorViewModel locationSelectorViewModel =
                new ViewModelProvider(this).get(LocationSelectorViewModel.class);

        binding = FragmentLocationSelectorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
