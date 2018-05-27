package john.lighterletter.com.earthquakes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import john.lighterletter.com.earthquakes.networking.APIRequestTask;
import john.lighterletter.com.earthquakes.results.ResultsActivity;

public class MainActivity extends AppCompatActivity implements
        APIRequestTask.ResponseDelegate,
        AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String orderSelected = "";
    private EditText numberOfResultsEditText;
    //static map keys must match values at: R.array.api_order_by
    private static final HashMap<String, String> orderMap = new HashMap<String, String>() {{
        put("Latest First", "orderby=time");
        put("Oldest First", "orderby=time-asc");
        put("Strongest First", "orderby=magnitude");
        put("Weakest First", "orderby=magnitude-asc");
    }};

    //region Override methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeSpinner();
        numberOfResultsEditText = findViewById(R.id.number_or_results_et);
        findViewById(R.id.make_api_request_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String numOfResultsString = numberOfResultsEditText.getText().toString();

        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.prompt_no_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        if (orderSelected.isEmpty() || numOfResultsString.isEmpty()) {
            Toast.makeText(this, R.string.prompt_invalid_fields, Toast.LENGTH_LONG).show();
        } else {

            int numOfResults = Integer.parseInt(numOfResultsString);
            if (numOfResults > 200) {
                Toast.makeText(this, "Please limit requests to 200 items to ensure good performance", Toast.LENGTH_SHORT).show();
                return;
            }
            displayLoadingUI();
            String orderBy = orderMap.get(orderSelected);
            String url = String.format(getString(R.string.api_request), getDate30DaysAgo(), numOfResults, orderBy);
            Log.d(TAG, "onCreate: url: " + url); // for debugging ease
            new APIRequestTask(this).execute(url);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreInitialUI();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        orderSelected = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    } //blank on purpose

    @Override
    public void onResponse(String jsonResponse) {
        if (!jsonResponse.isEmpty()) {
            Intent resultsPage = new Intent(this, ResultsActivity.class);
            resultsPage.putExtra(getString(R.string.json_response_key), jsonResponse);
            startActivity(resultsPage);
        } else {
            Toast.makeText(this, R.string.prompt_parsing_error, Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    //region Private methods
    private void initializeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.order_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.api_order_by,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void displayLoadingUI() {
        findViewById(R.id.make_api_request_button).setVisibility(View.INVISIBLE);
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    private void restoreInitialUI() {
        findViewById(R.id.make_api_request_button).setVisibility(View.VISIBLE);
        findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
    }

    private String getDate30DaysAgo() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        c.add(Calendar.DAY_OF_MONTH, -30);
        return dateFormat.format(c.getTime());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    //endregion
}
