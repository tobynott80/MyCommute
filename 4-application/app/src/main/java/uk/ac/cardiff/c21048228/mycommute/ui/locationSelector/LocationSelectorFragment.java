package uk.ac.cardiff.c21048228.mycommute.ui.locationSelector;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentLocationSelectorBinding;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TimetableFragment;

public class LocationSelectorFragment extends Fragment{

    private FragmentLocationSelectorBinding binding;

    private String StationType;
    private ArrayList<Station> stationArrayList;
    private RecyclerView recyclerView;
    private recyclerAdapter adapter;


    public LocationSelectorFragment(String stationType){
        this.StationType = stationType;
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocationSelectorViewModel locationSelectorViewModel =
                new ViewModelProvider(this).get(LocationSelectorViewModel.class);

        binding = FragmentLocationSelectorBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.rvStations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);


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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    @Override
    @SuppressWarnings("deprecation")
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                ArrayList<Station> filteredList = new ArrayList<>();
                for(Station s: stationArrayList){
                    if (s.getStationName().toLowerCase().contains(query.toLowerCase())){
                        filteredList.add(s);
                    }
                }
                adapter.filterList(filteredList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the list based on the search query
                ArrayList<Station> filteredList = new ArrayList<>();
                for(Station s: stationArrayList){
                    if (s.getStationName().toLowerCase().contains(newText.toLowerCase())){
                        filteredList.add(s);
                    }
                }
                adapter.filterList(filteredList);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }





}
