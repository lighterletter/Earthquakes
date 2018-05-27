package john.lighterletter.com.earthquakes.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import john.lighterletter.com.earthquakes.model.EarthquakeEvent;

public class JsonUtil {
    private static final String TAG = JsonUtil.class.getSimpleName();

    /**
     * This parser is modeled after response from api version: 1.5.8
     *
     * @param json json response from server
     * @return list of earthquake events
     */
    public static List<EarthquakeEvent> parseEarthQuakeEvents(String json) {
        List<EarthquakeEvent> list = new ArrayList<>();

        try {
            JSONObject response = new JSONObject(json);
            JSONArray features = response.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject root = features.getJSONObject(i);
                JSONObject properties = root.getJSONObject("properties");

                EarthquakeEvent event = new EarthquakeEvent();

                Object magnitude = properties.get("mag");
                double eventMagnitude = 0.0;
                if (!magnitude.toString().equals("null")) { // in some instances we can get a null value for this field.
                    eventMagnitude = (Double) magnitude;
                }

                event.setMagnitude(eventMagnitude);
                event.setLocation(properties.getString("place"));
                event.setUrl(properties.getString("url"));
                event.setDate(properties.getLong("time"));

                list.add(event);
            }

        } catch (JSONException e) {
            Log.e(TAG, "parseEarthQuakeEvents: ", e);
        }

        return list;
    }
}
