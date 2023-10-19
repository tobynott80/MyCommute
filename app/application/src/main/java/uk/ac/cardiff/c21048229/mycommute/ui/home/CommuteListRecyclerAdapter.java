package uk.ac.cardiff.c21048229.mycommute.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.cardiff.c21048229.mycommute.R;
import uk.ac.cardiff.c21048229.mycommute.ui.timetable.TrainService;
import uk.ac.cardiff.c21048229.mycommute.ui.timetable.TrainStatus;

public class CommuteListRecyclerAdapter extends RecyclerView.Adapter<CommuteListRecyclerAdapter.ViewHolder>{
    private ArrayList<TrainService> services;

    public CommuteListRecyclerAdapter(ArrayList<TrainService> services) {
        this.services = services;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView platformNumber;
        private TextView departureTime;
        private TextView origin;
        private TextView destination;
        private TextView status;
        public ViewHolder(final View view) {
            super(view);
            platformNumber = view.findViewById(R.id.tvPlatformNumber);
            departureTime = view.findViewById(R.id.tvDeparture);
            origin = view.findViewById(R.id.tvOrigin);
            destination = view.findViewById(R.id.tvDestination);
            status = view.findViewById(R.id.tvStatus);
        }
    }

    @NonNull
    @Override
    public CommuteListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_service_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommuteListRecyclerAdapter.ViewHolder holder, int position) {
        String platform = services.get(position).getPlatform();
        String departureTime = services.get(position).getDepartureTime();
        String origin = services.get(position).getOrigin();
        String destination = services.get(position).getDestination();
        TrainStatus status = services.get(position).getStatus();
        holder.platformNumber.setText(platform);
        holder.departureTime.setText(departureTime);
        holder.origin.setText(origin);
        holder.destination.setText(destination);
        if (status == TrainStatus.ON_TIME) {
            holder.status.setText(R.string.on_time);
            holder.status.setTextColor(Color.parseColor("#4CAF50"));
        } else if (status == TrainStatus.DELAYED) {
            holder.status.setText(R.string.delayed);
            holder.status.setTextColor(Color.parseColor("#FFC107"));
        } else if (status == TrainStatus.CANCELLED) {
            holder.status.setText(R.string.cancelled);
            holder.status.setTextColor(Color.parseColor("#F44336"));
        } else if (status == TrainStatus.NO_INFORMATION) {
            holder.status.setText(R.string.no_information);
            holder.status.setTextColor(Color.parseColor("#FF000000"));
        }
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
}
