package john.lighterletter.com.earthquakes.networking;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class InitialRequestTask extends AsyncTask<String, String, String> {
    private static final String TAG = InitialRequestTask.class.getSimpleName();
    private ResponseDelegate delegate;

    public InitialRequestTask(ResponseDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        InputStream inputStream = null;
        HttpsURLConnection urlConnection = null;
        String result = "";

        try {

            URL url = new URL(strings[0]);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200) {

                Log.d(TAG, "response OK: " + statusCode + "");
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                result = convertInputStreamToString(inputStream);

            }
        } catch (Exception e) {
            Log.e("doInBackground", e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.onResponse(result);
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuilder builder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }

        inputStream.close();
        return builder.toString();
    }

    public interface ResponseDelegate {
        void onResponse(String response);
    }
}
