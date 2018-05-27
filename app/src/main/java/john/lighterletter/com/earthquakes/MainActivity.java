package john.lighterletter.com.earthquakes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import john.lighterletter.com.earthquakes.networking.InitialRequestTask;
import john.lighterletter.com.earthquakes.results.ResultsActivity;

public class MainActivity extends AppCompatActivity implements
        InitialRequestTask.ResponseDelegate,
        AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final HashMap<String, String> orderMap = new HashMap<String, String>() {{
        put("Latest First", "orderby=time");
        put("Oldest First", "orderby=time-asc");
        put("Strongest First", "orderby=magnitude");
        put("Weakest First", "orderby=magnitude-asc");
    }};

    private String orderSelected = "";
    private EditText numberOfResultsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeSpinner();
        numberOfResultsEditText = findViewById(R.id.number_or_results_et);
        findViewById(R.id.request_button).setOnClickListener(this);

        if (savedInstanceState == null) {

        }
    }

    private void initializeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.order_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.api_order_by,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        String numOfResultsString = numberOfResultsEditText.getText().toString();

        if (orderSelected.isEmpty() || numOfResultsString.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            int numOfResults = Integer.parseInt(numOfResultsString);
            String orderBy = orderMap.get(orderSelected);
            String url = String.format(getString(R.string.api_request), getDate30DaysAgo(), numOfResults, orderBy);
            Log.d(TAG, "onCreate: url: " + url);
            new InitialRequestTask(this).execute(url);
        }
    }

    private String getDate30DaysAgo() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        c.add(Calendar.DAY_OF_MONTH, -30);
        return dateformat.format(c.getTime());
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        orderSelected = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
    } //blank on purpose

    @Override
    public void onResponse(String jsonResponse) {
        Intent resultsPage = new Intent(this, ResultsActivity.class);
        resultsPage.putExtra("response", jsonResponse);
        startActivity(resultsPage);
    }
}
