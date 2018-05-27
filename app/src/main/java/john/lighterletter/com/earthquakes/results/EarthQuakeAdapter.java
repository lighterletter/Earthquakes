package john.lighterletter.com.earthquakes.results;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import john.lighterletter.com.earthquakes.model.EarthquakeEvent;

class EarthQuakeAdapter extends RecyclerView.Adapter<EarthQuakeViewHolder> {
    private List<EarthquakeEvent> events;

    public EarthQuakeAdapter(List<EarthquakeEvent> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EarthQuakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EarthQuakeViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthQuakeViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
