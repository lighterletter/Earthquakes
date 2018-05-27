package john.lighterletter.com.earthquakes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import john.lighterletter.com.earthquakes.networking.InitialRequestTask;

public class MainActivity extends AppCompatActivity implements InitialRequestTask.ResponseDelegate {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            c.add(Calendar.DAY_OF_MONTH, -30);
            String subtracted = dateformat.format(c.getTime());
            String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + subtracted + "&eventtype=earthquake&limit=20";
            Log.d(TAG, "onCreate: url: " + url);
            new InitialRequestTask(this).execute(url);

        }
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, "onResponse: " + response);
    }
}
