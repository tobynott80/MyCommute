package uk.ac.cardiff.c21048228.mycommute.ui.locationSelector;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentLocationSelectorBinding;

public class LocationSelectorFragment extends Fragment {

    private FragmentLocationSelectorBinding binding;

    private ArrayList<Station> stationArrayList;
    private RecyclerView recyclerView;
    private recyclerAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocationSelectorViewModel locationSelectorViewModel =
                new ViewModelProvider(this).get(LocationSelectorViewModel.class);

        binding = FragmentLocationSelectorBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.rvStations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        stationArrayList = new ArrayList<>();
        //populate the array list with the stations from stations.xml
        XmlResourceParser parser = getResources().getXml(R.xml.stations);
        try{
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("station")) {
                String name = parser.getAttributeValue(null, "name");
                String crs = parser.getAttributeValue(null, "crs");
                Station station = new Station(name, crs);
                stationArrayList.add(station);
            }
        }}catch (Exception e){
            e.printStackTrace();
        }
        adapter = new recyclerAdapter(stationArrayList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
