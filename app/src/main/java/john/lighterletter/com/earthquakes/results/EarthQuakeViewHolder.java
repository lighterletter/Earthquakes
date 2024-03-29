package john.lighterletter.com.earthquakes.results;

import android.content.Intent;
import android.net.Uri;
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
    EarthQuakeViewHolder(ViewGroup parent) {
        super(inflateView(parent));
    }

    private static View inflateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_holder, parent, false);
    }

    public void bind(final EarthquakeEvent earthquakeEvent) {
        String location = earthquakeEvent.getLocation();
        String magnitude = String.format(itemView.getContext().getString(R.string.magnitude_viewholder_message), earthquakeEvent.getMagnitude());
        String date = String.format(itemView.getContext().getString(R.string.date_viewholder_message), getDateFromEvent(earthquakeEvent.getDate()));

        ((AppCompatTextView) itemView.findViewById(R.id.location_text_view)).setText(location);
        ((AppCompatTextView) itemView.findViewById(R.id.magnitude_text_view)).setText(magnitude);
        ((AppCompatTextView) itemView.findViewById(R.id.date_text_view)).setText(date);
        (itemView.findViewById(R.id.more_info_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquakeEvent.getUrl()));
                itemView.getContext().startActivity(browserIntent);
            }
        });
    }

    private String getDateFromEvent(long date) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        return dateFormat.format(cl.getTime());
    }
}
