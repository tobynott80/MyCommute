package uk.ac.cardiff.c21048228.mycommute.ui.welcome;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentStationSelectorBinding;
import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.Station;
import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.StationListRecyclerAdapter;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TimetableViewModel;

public class StationSelectorFragment extends Fragment implements StationListRecyclerAdapter.ClickListener{
    private FragmentStationSelectorBinding binding;

    private String stationType;
    private ArrayList<Station> stationArrayList;
    private RecyclerView recyclerView;
    private StationListRecyclerAdapter adapter;

    public StationSelectorFragment(String stationType){
        this.stationType = stationType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStationSelectorBinding.inflate(inflater, container, false);
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
        adapter = new StationListRecyclerAdapter(stationArrayList, this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
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
    public void onItemClick(String stationName, String stationCRS) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        if (stationType.equals("homeDeparture")){
            sharedPreferences.edit().putString("homeDepartureName", stationName).apply();
            sharedPreferences.edit().putString("homeDepartureCRS", stationCRS).apply();
        } else if (stationType.equals("homeArrival")){
            sharedPreferences.edit().putString("homeArrivalName", stationName).apply();
            sharedPreferences.edit().putString("homeArrivalCRS", stationCRS).apply();
        } else if (stationType.equals("workDeparture")){
            sharedPreferences.edit().putString("workDepartureName", stationName).apply();
            sharedPreferences.edit().putString("workDepartureCRS", stationCRS).apply();
        } else if (stationType.equals("workArrival")){
            sharedPreferences.edit().putString("workArrivalName", stationName).apply();
            sharedPreferences.edit().putString("workArrivalCRS", stationCRS).apply();
        }

        getParentFragmentManager().popBackStack();
    }
}
