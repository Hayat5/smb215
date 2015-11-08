package Firstdroid.Tutorial.Gps;

import android.R.layout;
import android.app.Activity;

import android.content.Context;

import android.location.Location;
import com.parse.Parse;

import android.location.LocationListener;

import android.location.LocationManager;

import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.List;

public class MainActivity extends Activity {

    public double latitude;
    public double longitude;

    /**
     * Called when the activity is first created.
     */
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);


        /* Use the LocationManager class to obtain GPS locations */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener mlocListener = new MyLocationListener();

        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

        Location mlocation = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

// Enable Local Datastore.
        Parse.enableLocalDatastore(getBaseContext());

        Parse.initialize(getBaseContext(), "nvlOlG2xjqcTOptsoQtEV3GceVjoSItfbcYB9DXn", "kAnL0yvvgk9wzRqfceBVtJZAGrIMcqnVYC42eNIg");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
        query.whereEqualTo("lastLocation", 1);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> nameList, ParseException e) {
                if (e == null) {
                    for (ParseObject nameObj : nameList) {
                        nameObj.put("lastLocation", 0);
                        nameObj.saveInBackground();
                    }
                } else {
                    Log.d("Post retrieval", "Error: " + e.getMessage());
                }
            }
        });

        ParseObject location = new ParseObject("Location");

        location.put("lat", mlocation.getLatitude());
        location.put("long", mlocation.getLongitude());
        location.put("lastLocation", 1);
        location.saveInBackground();

    }


    /* Class My Location Listener */
    public class MyLocationListener implements LocationListener {

        @Override

        public void onLocationChanged(Location loc) {

// latitude = loc.getLatitude();
//longitude = loc.getLongitude();
//String Text = "My current location is: " +
// "Latitude = " + latitude +
// "Longitude = " + longitude;
// Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
// Parse.enableLocalDatastore(getBaseContext());
// Parse.initialize(getBaseContext(), "nvlOlG2xjqcTOptsoQtEV3GceVjoSItfbcYB9DXn", "kAnL0yvvgk9wzRqfceBVtJZAGrIMcqnVYC42eNIg");
        }

        @Override

        public void onProviderDisabled(String provider) {

            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();

        }

        @Override

        public void onProviderEnabled(String provider) {

            Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();

        }

        @Override

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }
}/* End of Class MyLocationListener */
