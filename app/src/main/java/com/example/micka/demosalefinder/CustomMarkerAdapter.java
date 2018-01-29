package com.example.micka.demosalefinder;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Creates customized info window for Map activity
 * and connects marker and info window together.
 */

public class CustomMarkerAdapter implements GoogleMap.InfoWindowAdapter  {

    private static final String LOG_TAG = AccountActivity.class.getSimpleName();
    private final View mWindow;
    private Context context;
    public String title;

    /**
     * Public constructor which is called from
     * MapAcitivity to convert customized XML info window
     * into a view which appears in MapActivity.
     * @param context
     */
    public CustomMarkerAdapter(Context context) {
        Log.d(LOG_TAG, "CustomMarkerAdapter()");

        this.context = context;
        mWindow = LayoutInflater.from(MainActivity.getAppContext()).inflate(R.layout.custom_marker, null);
    }

    /**
     * Interface method which is called first when
     * a marker is tapped. Calls setContentInMarker
     * and returns the updated info window. If null,
     * it will call getInfoContents().
     * @param marker
     * @return
     */
    @Override
    public View getInfoWindow(Marker marker) {
        setContentInMarker(marker, mWindow);
        return mWindow;
    }

    /**
     * Interface method which is called if getInfoWindow
     * returns null. If this method return null, a
     * default info window will be generetad and shown
     * in Map acitivty.
     * @param marker
     * @return
     */
    @Override
    public View getInfoContents(Marker marker) {
        setContentInMarker(marker, mWindow);
        return mWindow;
    }

    /*
     * Sets the title and snippet for the info window.
     */
    private void setContentInMarker(Marker marker, View view) {
        Log.d(LOG_TAG, "setContentInMarker()");

        title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title);

        if(!title.equals("")) {
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.snippet);

        if(!snippet.equals("")) {
            tvSnippet.setText(snippet);
        }
    }

    /**
     * Is called from Map activity and returns
     * title of info window content.
     * @return
     */
    public String getInfoTitle() {
        return title;
    }
}
