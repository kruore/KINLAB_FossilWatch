package com.example.k_pt_watch;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class StartActivity extends Activity {

    //public static final int sub = 1001;

    int targetDistance = 0;
    int residuaryDistance = 0;
    int residuaryCount= 0;


    /**Scene 00**/
    private TextView distanceChecker;
    private EditText distanceEdit;
    private Button CheckDistance;

    /**Scene 01**/
    private TextView distanceChecker2;
    private TextView distancePiecer;
    private TextView mDistancePiecerStartButton;

    /**Scene 03**/
    private TextView mDistancePiecerEdit;
    private Button mDistancePiecerButton;
    private TextView mDistancePiecerText;

    /**Scene 04**/
    private TextView mResiduaryDistanceText;
    private Button mResiduaryDistanceButton;
    private TextView mResiduaryDistanceText02;
    private TextView mResiduaryDistanceText03;
    private TextView mResiduaryDistanceText04;
    private EditText mResiduaryDistance;
    private EditText mResiduarySpeed;

    private int[] distance;
    private int[] separatePart;
    private int separate_distance_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        permissionRequest(this);
        /**Scene 00**/
        distanceEdit = findViewById(R.id.distance);
        CheckDistance = (Button) findViewById(R.id.checkDistance);
        distanceChecker =findViewById(R.id.distanceChecker);

        /**Scene 01**/
        distanceChecker2 = findViewById(R.id.distanceChecker2);
        distancePiecer = findViewById(R.id.distancePiecer);
        mDistancePiecerStartButton = findViewById(R.id.distancePiecerStartButton);

        /**Scene 03**/
        mDistancePiecerEdit = findViewById(R.id.distancePiecerEdit);
        mDistancePiecerButton = findViewById(R.id.distancePiecerButton);
        mDistancePiecerText = findViewById(R.id.distancePiecer01);

        /**Scene 04**/
        mResiduaryDistanceText = findViewById(R.id.residuaryPart);
        mResiduaryDistanceButton = findViewById(R.id.checkDistance03);
        mResiduaryDistanceText02 = findViewById(R.id.residuaryDistance);
        mResiduaryDistanceText03 = findViewById(R.id.Text000);
        mResiduaryDistanceText04 = findViewById(R.id.Text001);
        mResiduaryDistance = findViewById(R.id.distance02);
        mResiduarySpeed = findViewById(R.id.residuaryspeed);

        OnClickViewChanger(0);

        mResiduarySpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                distance[separate_distance_counter]=Integer.parseInt(mResiduarySpeed.getText().toString());
                mResiduarySpeed.clearComposingText();
            }
        });

        mResiduaryDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                distance[separate_distance_counter]=Integer.parseInt(mResiduaryDistance.getText().toString());
                mResiduaryDistance.clearComposingText();
            }
        });

        mDistancePiecerEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                residuaryCount = Integer.parseInt(mDistancePiecerEdit.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CheckDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "거리 = "+distanceEdit.getText(), Toast.LENGTH_SHORT).show();
                OnClickViewChanger(1);
                /**Activity data transfer**/
//                Intent intent = new Intent(getApplicationContext(),DistanceChecker.class);
//                String a = distanceEdit.getText().toString();
//                intent.putExtra("CheckDistance", a);
//                try{
//                    startActivity(intent);
//                }
//                catch (ActivityNotFoundException e) {
//                    Log.d("INTENT","Doesn't Moved");
//                }
//                setContentView(R.layout.activitay_distance_check);
            }
        });
        mDistancePiecerStartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OnClickViewChanger(2);
            }
        });
        mDistancePiecerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OnClickViewChanger(3);
            }
        });
        mResiduaryDistanceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OnClickViewChanger(4);
            }
        });
    }
    private void InsertData_DataInserter()
    {
        int j = residuaryCount;
        distance = new int[residuaryCount];
        separatePart = new int[residuaryCount];
    }
    private void setResiduaryActiveate(int i)
    {
        if(i<distance.length)
        {
            mResiduaryDistanceText.setText("구간 "+(i+1));
            Log.d("로그","구간"+(i+1));
            separate_distance_counter++;
        }
        else
        {
            Log.d("로그","separate Part exception");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            int[] distanceArray;
            distanceArray = distance;
            intent.putExtra("distance", distanceArray);
            startActivity(intent);
            try{
                startActivity(intent);
                finish();

            }
            catch (ActivityNotFoundException e) {
                Log.d("INTENT","Doesn't Moved");
            }
            return;
        }
    }

    public void permissionRequest(Activity activity) {
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

    private void OnClickViewChanger(int index)
    {
        switch (index)
        {
            case 0 :
                distanceEdit.setVisibility(View.VISIBLE);
                distanceChecker.setVisibility(View.VISIBLE);
                CheckDistance.setVisibility(View.VISIBLE);

                distanceChecker2.setVisibility(View.INVISIBLE);
                distancePiecer.setVisibility(View.INVISIBLE);
                mDistancePiecerStartButton.setVisibility(View.INVISIBLE);

                mDistancePiecerButton.setVisibility(View.INVISIBLE);
                mDistancePiecerEdit.setVisibility(View.INVISIBLE);
                mDistancePiecerText.setVisibility(View.INVISIBLE);

                mResiduaryDistanceText.setVisibility(View.INVISIBLE);
                mResiduaryDistanceButton.setVisibility(View.INVISIBLE);
                mResiduaryDistanceText02.setVisibility(View.INVISIBLE);
                mResiduaryDistanceText03.setVisibility(View.INVISIBLE);
                mResiduaryDistanceText04.setVisibility(View.INVISIBLE);
                mResiduaryDistance.setVisibility(View.INVISIBLE);
                mResiduarySpeed.setVisibility(View.INVISIBLE);
                break;


            case 1 :
                distanceEdit.setVisibility(View.INVISIBLE);
                distanceChecker.setVisibility(View.INVISIBLE);
                CheckDistance.setVisibility(View.INVISIBLE);

                distanceChecker2.setVisibility(View.VISIBLE);

                distanceChecker2.setText("목표는 "+distanceEdit.getText()+" / KM");
                targetDistance =  Integer.parseInt(distanceEdit.getText().toString());
                residuaryDistance = targetDistance;
                distancePiecer.setVisibility(View.VISIBLE);
                mDistancePiecerStartButton.setVisibility(View.VISIBLE);

                mDistancePiecerButton.setVisibility(View.INVISIBLE);
                mDistancePiecerEdit.setVisibility(View.INVISIBLE);
                mDistancePiecerText.setVisibility(View.INVISIBLE);

                mResiduaryDistanceText.setVisibility(View.INVISIBLE);
                mResiduaryDistanceButton.setVisibility(View.INVISIBLE);
                mResiduaryDistanceText02.setVisibility(View.INVISIBLE);
                mResiduaryDistanceText03.setVisibility(View.INVISIBLE);
                mResiduaryDistanceText04.setVisibility(View.INVISIBLE);
                mResiduaryDistance.setVisibility(View.INVISIBLE);
                mResiduarySpeed.setVisibility(View.INVISIBLE);

                break;
            case 2 :
                distanceEdit.setVisibility(View.INVISIBLE);
                distanceChecker.setVisibility(View.INVISIBLE);
                CheckDistance.setVisibility(View.INVISIBLE);

                distanceChecker2.setVisibility(View.INVISIBLE);
                distancePiecer.setVisibility(View.INVISIBLE);
                mDistancePiecerStartButton.setVisibility(View.INVISIBLE);

                mDistancePiecerButton.setVisibility(View.VISIBLE);
                mDistancePiecerEdit.setVisibility(View.VISIBLE);
                mDistancePiecerText.setVisibility(View.VISIBLE);
                mResiduaryDistanceText.setVisibility(View.INVISIBLE);
                mResiduaryDistanceButton.setVisibility(View.INVISIBLE);
                mResiduaryDistanceText02.setVisibility(View.INVISIBLE);
                mResiduaryDistanceText03.setVisibility(View.INVISIBLE);
                mResiduaryDistanceText04.setVisibility(View.INVISIBLE);
                mResiduaryDistance.setVisibility(View.INVISIBLE);
                mResiduarySpeed.setVisibility(View.INVISIBLE);

                break;
            case 3 :


                mDistancePiecerButton.setVisibility(View.INVISIBLE);
                mDistancePiecerEdit.setVisibility(View.INVISIBLE);
                mDistancePiecerText.setVisibility(View.INVISIBLE);

                mResiduaryDistanceText.setVisibility(View.VISIBLE);
                mResiduaryDistanceButton.setVisibility(View.VISIBLE);
                mResiduaryDistanceText02.setVisibility(View.VISIBLE);
                mResiduaryDistanceText03.setVisibility(View.VISIBLE);
                mResiduaryDistanceText04.setVisibility(View.VISIBLE);
                mResiduaryDistance.setVisibility(View.VISIBLE);
                mResiduarySpeed.setVisibility(View.VISIBLE);
                InsertData_DataInserter();
                setResiduaryActiveate(separate_distance_counter);
                break;
            case 4:
                setResiduaryActiveate(separate_distance_counter);
                break;
            case 5:

                break;
        }
    }
}