package john.lighterletter.com.earthquakes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import john.lighterletter.com.earthquakes.networking.InitialRequestTask;

public class MainActivity extends AppCompatActivity implements InitialRequestTask.ResponseDelegate {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new InitialRequestTask(this).execute("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2018-05-01&eventtype=earthquake&limit=20");
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, "onResponse: " + response);
    }
}
