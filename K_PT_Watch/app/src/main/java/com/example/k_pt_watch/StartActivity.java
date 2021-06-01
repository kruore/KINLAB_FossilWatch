package com.example.k_pt_watch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {

    //public static final int sub = 1001;

    int targetDistance = 0;
    int residuaryDistance = 0;


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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

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
     //   mResiduaryDistanceText = findViewById(R.id.residuaryDistance);


        OnClickViewChanger(0);

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
                break;
            case 3 :
                distanceEdit.setVisibility(View.INVISIBLE);
                distanceChecker.setVisibility(View.INVISIBLE);
                CheckDistance.setVisibility(View.INVISIBLE);

                distanceChecker2.setVisibility(View.INVISIBLE);
                distancePiecer.setVisibility(View.INVISIBLE);
                mDistancePiecerStartButton.setVisibility(View.INVISIBLE);

                mDistancePiecerButton.setVisibility(View.INVISIBLE);
                mDistancePiecerEdit.setVisibility(View.INVISIBLE);
                mDistancePiecerText.setVisibility(View.INVISIBLE);
                break;

            case 4:
              //  mResiduaryDistanceText.setText("잔여 거리 : "+residuaryDistance + " /Km");

                break;
        }
    }
}