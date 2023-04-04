package uk.ac.cardiff.c21048228.mycommute.ui.locationSelector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.cardiff.c21048228.mycommute.R;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ViewHolder> {

    private ArrayList<Station> stations;
    private Station selectedStation;


    public recyclerAdapter(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView stationName;
        private TextView stationCRS;

        public ViewHolder(final View view) {
            super(view);
            stationName = view.findViewById(R.id.textStationName);
            stationCRS = view.findViewById(R.id.textStationCRS);
        }
    }

    //method for filtering our recyclerview items
    public void filterList(ArrayList<Station> filterList){
        stations = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public recyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.ViewHolder holder, int position) {
        String stationName = stations.get(position).getStationName();
        String stationCRS = stations.get(position).getStationCRS();
        holder.stationName.setText(stationName);
        holder.stationCRS.setText(stationCRS);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedStation = new Station(stationName, stationCRS);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stations.size();
    }




}
