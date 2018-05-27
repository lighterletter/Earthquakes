package john.lighterletter.com.earthquakes.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import john.lighterletter.com.earthquakes.R;
import john.lighterletter.com.earthquakes.model.EarthquakeEvent;
import john.lighterletter.com.earthquakes.utils.JsonUtil;

public class ResultsActivity extends AppCompatActivity {
    public static final String TAG = ResultsActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String jsonResponse = getIntent().getExtras().getString("response");
        List<EarthquakeEvent> events = JsonUtil.parseEarthQuakeEvents(jsonResponse);

        RecyclerView recyclerView = findViewById(R.id.results_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new EarthQuakeAdapter(events));

    }
}
