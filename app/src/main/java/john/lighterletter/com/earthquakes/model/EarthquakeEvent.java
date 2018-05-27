package john.lighterletter.com.earthquakes.model;

public class EarthquakeEvent {

    private String url;
    private String location;
    private String magnitude;
    private long date;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
