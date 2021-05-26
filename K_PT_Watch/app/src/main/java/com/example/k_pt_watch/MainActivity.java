package com.example.k_pt_watch;


//cd C:\Users\epsel\AppData\Local\Android\Sdk\platform-tools
//  adb forward tcp:4444 localabstract:/adb-hub
////    adb connect 127.0.0.1:4444

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;

public class MainActivity extends WearableActivity implements SensorEventListener, LocationListener {

    public LocationManager locationManager;
    public LocationListener locationListener;

    SensorLog sl1;

    public TextView textView01;
    public TextView textView02;

    double mLat = 0.0;
    double mLng = 0.0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        textView01 = (TextView) findViewById(R.id.textView);
        textView02 = (TextView) findViewById(R.id.textView2);
        if (!hasGps()) {
            Log.d("GPS 동작하지 않음", "This hardware doesn't have GPS.");
            // Fall back to functionality that doesn't use location or
            // warn the user that location function isn't available.
        }
        if (hasGps()) {
            Log.d("GPS 동작", "This hardware have GPS.");
            DisplayLocation();
        }

    }

    private boolean hasGps() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = 0.0;
        double lng = 0.0;

        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            mLat = lat;
            mLng = lng;
            String msg = String.format("(%.3f, %.3f)", mLat, mLng);
            textView01.setText(msg);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressLint("MissingPermission")
    private void DisplayLocation() {
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLongitude();
            double lat = lastKnownLocation.getLatitude();

            mLat = lat;
            mLng = lng;
            //Log.d("DBG", "longtitude=" + lng + ", latitude=" + lat);
            //String msg  = String.format("(%.6f, %.6f)", lng, lat);//"longtitude=" + lng + ", latitude=" + lat;
            String msg = String.format("%.6f, %.6f", mLat, mLng);
            //mTextViewLocation.setText(msg);
            textView01.setText(msg);
            Dbg.out(msg);

            //tvGpsLatitude.setText(":: " + Double.toString(lat ));
            //tvGpsLongitude.setText((":: " + Double.toString(lng)));
        }
//    private void UpdateUI() {
//        //String buf = sl1.GetLatestValues(false);
//        String buf;
//        buf = sl1.GetLatestAcc(false);
//        textView02.setText(buf);
//    }

    }
}


//public class MainActivity extends WearableActivity implements LocationListener, SensorEventListener {
//
//
//    //Connect Button
//    Button m_GPSConnect;
//
//    String myAndroidDeviceId;
//
//    // GPS Position
//    TextView m_GPSDataX;
//    TextView m_GPSDataY;
//
//    //Location Manager = Controlled the LocationPos Detection
//    private LocationManager locationManager;
//
//
//    //Providers
//    private List<String> listProviders;
//
////    //SensorLog
////    private SensorManager mSensorManager;
////
//    SensorLog sl1 ;
//
//    // GPS Position
//    double mLat = 0.0;
//    double mLng = 0.0;
//
//    public String getUniqueID(){
//        String  myAndroidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//        return  myAndroidDeviceId;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //Check Permission.
//        checkAndRequestPermissions(this);
//
//        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        Log.d("로케이션매니저",locationManager.toString()); //android.location.LocationManager@
//
//        myAndroidDeviceId = getUniqueID();
//        myAndroidDeviceId = myAndroidDeviceId.substring(myAndroidDeviceId.length()-5, myAndroidDeviceId.length());
//
//        setAmbientEnabled();
//
//        Button button01 = (Button) findViewById(R.id.Button01);
//        button01.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // active button
//            }
//        });
//        UpdateUI();
//        DisplayLocation();
//
//
//    }
//    private void UpdateUI() {
//        //String buf = sl1.GetLatestValues(false);
//        String buf;
//        buf = sl1.GetLatestAcc(false);
//        m_GPSDataY.setText(buf);
//    }
//
//        @SuppressLint("MissingPermission")
//    private void DisplayLocation()
//    {
//       Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        if (lastKnownLocation != null) {
//            double lng = lastKnownLocation.getLongitude();
//            double lat = lastKnownLocation.getLatitude();
//
//            mLat = lat;
//            mLng = lng;
//            //Log.d("DBG", "longtitude=" + lng + ", latitude=" + lat);
//            //String msg  = String.format("(%.6f, %.6f)", lng, lat);//"longtitude=" + lng + ", latitude=" + lat;
//            String msg  = String.format("C%02d (%.6f, %.6f)", mLat, mLng);
//            //mTextViewLocation.setText(msg);
//            m_GPSDataX.setText(msg);
//            Log.d("X,Y",msg);
//
//            //tvGpsLatitude.setText(":: " + Double.toString(lat ));
//            //tvGpsLongitude.setText((":: " + Double.toString(lng)));
//        }
//        if (lastKnownLocation == null) {
//            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if (lastKnownLocation != null) {
//                double lng = lastKnownLocation.getLongitude();
//                double lat = lastKnownLocation.getLatitude();
//                //Log.d(TAG, "longtitude=" + lng + ", latitude=" + lat);
//               // Dbg.out("longtitude=" + lng + ", latitude=" + lat);
//                Log.d("X,Y를 못찾았을 때","위치를 못찾았음");
//
//            }
//        }
//        if (lastKnownLocation == null) {
//            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//            if (lastKnownLocation != null) {
//                double lng = lastKnownLocation.getLongitude();
//                double lat = lastKnownLocation.getLatitude();
//                Log.d("X,Y를 못찾았을 때","위치를 못찾았음");
//            }
//        }
//
//        listProviders = locationManager.getAllProviders();
//        boolean [] isEnabled = new boolean[3];
//        for(int i=0; i<listProviders.size();i++) {
//            if(listProviders.get(i).equals(LocationManager.GPS_PROVIDER)) {
//                isEnabled[0] = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//            } else if(listProviders.get(i).equals(LocationManager.NETWORK_PROVIDER)) {
//                isEnabled[1] = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
//            } else if(listProviders.get(i).equals(LocationManager.PASSIVE_PROVIDER)) {
//                isEnabled[2] = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
//
//                locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
//            }
//
//        }
//
//        String buf= String.format("Location %b %b %b", isEnabled[0], isEnabled[1], isEnabled[2]);
//        Log.d("위치는",buf);
//
//        //String msg  = String.format("C%02d (%.6f, %.6f)", class_selected, mLat, mLng);
//        //mTextViewLocation.setText(msg);
//        //mTextView4.setText(msg);
//
//        //mTextViewLocation.setText(buf);
//
//    }
//
//
//
//
//
//
//    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
//
//    // Permission Check if permission denied try Granted
//    private boolean checkAndRequestPermissions(Activity activity) {
//
//        int locationPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
//        int locationPermission2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
//        int storagePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        List<String> listPermissionsNeeded = new ArrayList<>();
//
//        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
//            Log.d("퍼미션","로케이션 찾기");
//        }
//        if (locationPermission2 != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//        }
//        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
//            Log.d("퍼미션","퍼미션이 없음");
//            return false;
//        }
//        Log.d("퍼미션","퍼미션은 다 허용됨");
//        return true;
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//        Log.d("위치는","찾긴하나?");
//        double lat = 0.0;
//        double lng = 0.0;
//
//        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
//            lat = location.getLatitude();
//            lng = location.getLongitude();
//
//            mLat = lat;
//            mLng = lng;
//
//            //String msg = String.format("(%.6f, %.6f)", lng, lat);//"longtitude=" + lng + ", latitude=" + lat;
//            String msg = String.format("(%.6f, %.6f)", mLat, mLng);
//            m_GPSDataX.setText(msg);
//
//            //Double.toString(latitude )
//            //tvGpsLatitude.setText(": " + Double.toString(latitude ));
//            //tvGpsLongitude.setText((": " + Double.toString(longitude)));
//            //Log.d(TAG + " GPS : ", Double.toString(latitude )+ '/' + Double.toString(longitude));
//        }
//        if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
//            lat = location.getLatitude();
//            lng = location.getLongitude();
//
//            mLat = lat;
//            mLng = lng;
//
//            //String msg = String.format("(%.6f, %.6f)", lng, lat);//"longtitude=" + lng + ", latitude=" + lat;
//            String msg = String.format("(%.6f, %.6f)", mLat, mLng);
//            m_GPSDataX.setText(msg);
//
//
//        }
//
//        if(location.getProvider().equals(LocationManager.PASSIVE_PROVIDER)) {
//            lat = location.getLatitude();
//            lng = location.getLongitude();
//
//            mLat = lat;
//            mLng = lng;
//
//            //String msg = String.format("(%.6f, %.6f)", lng, lat);//"longtitude=" + lng + ", latitude=" + lat;
//            String msg = String.format("(%.6f, %.6f)", mLat, mLng);
//            m_GPSDataX.setText(msg);
//
//        }
//
//    }
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
//
//}
