package com.example.k_pt_watch;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.Locale;
import java.util.List;

public class MainActivity extends WearableActivity {


    //GPS 트래커
    private GpsTracker gpsTracker;


    private Button m_GPSConnect;
    private TextView m_GPSDataX;
    private TextView m_GPSDataY;
    private TextView m_walkData;
    private TextView m_GPSConnectChecker;
    private TextView mTextView6;

    private static final int PERMISSION_REQUEST_READ_BODY_SENSORS = 1;
    private static final int REQUEST_PHONE_PERMISSION = 1;
    private static int REQUEST_ACCESS_FINE_LOCATION = 1000;
    public static final String EXTRA_PROMPT_PERMISSION_FROM_PHONE = "com.example.android.wearable.runtimepermissions.extra.PROMPT_PERMISSION_FROM_PHONE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        m_GPSDataX = (TextView) findViewById(R.id.textView);
        m_GPSConnect = (Button) findViewById(R.id.Button01);
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
                gpsTracker = new GpsTracker(MainActivity.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getCurrentAddress(latitude, longitude);
                m_GPSDataX.setText(address);
            }
        };
        m_GPSConnect.setOnClickListener(Listener);

        setAmbientEnabled();



    }
    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }




    public void DbgToast(String message) {
        Toast toast = null;
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void requestPermission()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){ //포그라운드 위치 권한 확인

            //위치 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            DbgToast("Done");
        }
        else
        {
            DbgToast("Permission");
        }

        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);


        if(permissionCheck == PackageManager.PERMISSION_DENIED){ //백그라운드 위치 권한 확인

            //위치 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 0);
            DbgToast("Done");
        }
        else
        {
            DbgToast("Permission");
        }
    }


}

//
//    private boolean hasGps() {
//        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
//    }

