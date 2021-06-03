package com.example.k_pt_watch;




import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.wearable.Wearable;

import java.text.DecimalFormat;


/**
 * 2021 . 05. 20  Made By Lee Sang Jun
 * K_ PT HeartRate and GPS Tracking
 */

//cd C:\Users\epsel\AppData\Local\Android\Sdk\platform-tools
//  adb forward tcp:4444 localabstract:/adb-hub
//    adb connect 127.0.0.1:4444
public class MainActivity extends WearableActivity implements SensorEventListener
{

    private double distance = 0;
    private Location beforeLocation = null;
    private float mySpeed = 0;
    private float maxSpeed =0;

    private Button button;
    private TextView GPSLocation;

    private LocationManager lm;`

    private TextView HeartBeat;
    private TextView HeartBeatMax;
    private TextView HeartBeatMin;
    private TextView KnowSpeed;
    private TextView MaxSpeed;
    private int maxValue = 0;
    private int minValue = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    @SuppressLint("MissingPermission")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KnowSpeed = findViewById(R.id.speed);
        MaxSpeed = findViewById(R.id.speed2);
        HeartBeat = findViewById(R.id.heartbeat);
        HeartBeatMax = findViewById(R.id.heartbeatmax);
        HeartBeatMin = findViewById(R.id.heartbeatmin);
        GPSLocation = findViewById(R.id.GPS);
        HeartBeat.setText("RESTART APP");
        HeartBeatMax.setVisibility(View.INVISIBLE);
        HeartBeatMin.setVisibility(View.INVISIBLE);

        permissionRequest(this);
        readSensor();
        // Enables Always-on

        /**
         2021 . 05. 28  Made By Lee Sang Jun
         GPS Tracking , Location Mapping
         *
         * */

        if (!hasGps()) {
            Log.w(TAG, "This hardware doesn't have GPS! The Application will finish.");
        } else {
            Log.w("TAG", "아니 동작하자나");
        }
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onStart() {
        super.onStart();
    }


    /**
     * GPS
     **/
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            /** Distance **/
            if(beforeLocation == null){
                beforeLocation = location;
            }else{
                distance += beforeLocation.distanceTo(location);
                beforeLocation = location;
            }

            /** Speed **/
            mySpeed = location.getSpeed();
            DecimalFormat formatter = new DecimalFormat("0.##");
            String msg = formatter.format(mySpeed);
            KnowSpeed.setText(msg);
            if(maxSpeed<mySpeed)
            {
                maxSpeed=mySpeed;
                String msg2 = formatter.format(maxSpeed);
                MaxSpeed.setText(msg);
            }
            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();
            GPSLocation.setText("Distance:"+formatter.format(distance)+"/m");
//
//            GPSLocation.setText("위치정보 : " + provider + "\n" +
//                    "위도 : " + longitude + "\n" +
//                    "경도 : " + latitude + "\n" +
//                    "고도  : " + altitude);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @SuppressLint("MissingPermission")
        public void onProviderEnabled(String provider) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
        }

        public void onProviderDisabled(String provider) {
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume()
    {
        super.onResume();
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( MainActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }

//            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            String provider = location.getProvider();
//            double longitude = location.getLongitude();
//            double latitude = location.getLatitude();
//            double altitude = location.getAltitude();

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000,
                    5,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    5000,
                    5,
                    gpsLocationListener);
    }
    /**
     * Heart Rate
     **/

    private void readSensor() {
        SensorManager mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            if ((int) event.values[0] == 0) {
                //Heartrate is 0. This means sensor is available but reading cannot be made.
                //Caused either sensor starting up or watch has been removed from writs.
                //Do not log minimum value and display text "NO PULSE"
                HeartBeat.setText("NO PULSE");
                return;
            }
            String msg = "" + (int) event.values[0];
            if (HeartBeatMax.getVisibility() == View.INVISIBLE && (int) event.values[0] != 0) {
                HeartBeatMax.setVisibility(View.VISIBLE);
                HeartBeatMin.setVisibility(View.VISIBLE);
                maxValue = (int) event.values[0];
                minValue = (int) event.values[0];
            }
            if ((int) event.values[0] > maxValue) maxValue = (int) event.values[0];
            if ((int) event.values[0] < minValue) minValue = (int) event.values[0];
            HeartBeat.setText(msg + " bpm");
            HeartBeatMax.setText("MAX: " + maxValue + " bpm");
            HeartBeatMin.setText("MIN: " + minValue + " bpm");
            //Log.d(TAG, msg);
        }

    }
    //Handle button presses
    @SuppressLint("MissingPermission")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getRepeatCount() == 0) {
            if (keyCode == KeyEvent.KEYCODE_STEM_1) {
                final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( MainActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            0 );
                }
                else{
                    GPSLocation.setText("GPS Start");

                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                }
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_STEM_2) {
                event.startTracking();
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_STEM_3) {
                event.startTracking();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //Key를 오래 누를 경우에 동작하는 기능
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_STEM_1) {

            return true;
        } else if (keyCode == KeyEvent.KEYCODE_STEM_2) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_STEM_3) {

        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void permissionRequest(Activity activity) {
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if(permissionCheck1 == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET},1);

        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permissionCheck2 == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},1);

        int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck3 == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
    int locationPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int locationPermission2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        //TODO: Negative answer not handled. Positive answer requires restart.
        if (checkSelfPermission(Manifest.permission.BODY_SENSORS)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.BODY_SENSORS}, 1);
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationPermission);
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, locationPermission2);
        }
        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
    }
    /**
     * UTILITY
     **/

    private boolean hasGps() {

        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);

    }
}


