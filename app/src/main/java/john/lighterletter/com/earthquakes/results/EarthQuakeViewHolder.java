package john.lighterletter.com.earthquakes.results;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import john.lighterletter.com.earthquakes.R;
import john.lighterletter.com.earthquakes.model.EarthquakeEvent;

class EarthQuakeViewHolder extends RecyclerView.ViewHolder {
    public EarthQuakeViewHolder(ViewGroup parent) {
        super(inflateView(parent));
    }

    private static View inflateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_holder, parent, false);
    }

    public void bind(EarthquakeEvent earthquakeEvent) {

        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(earthquakeEvent.getDate());
        SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        String date = dateformat.format(cl.getTime());

        ((AppCompatTextView) itemView.findViewById(R.id.location_text_view)).setText(earthquakeEvent.getLocation());
        ((AppCompatTextView) itemView.findViewById(R.id.magnitude_text_view)).setText(String.format("Magnitude: %s", String.valueOf(earthquakeEvent.getMagnitude())));
        ((AppCompatTextView) itemView.findViewById(R.id.date_text_view)).setText(String.format("Date: %s", date));
        ((AppCompatButton) itemView.findViewById(R.id.more_info_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
