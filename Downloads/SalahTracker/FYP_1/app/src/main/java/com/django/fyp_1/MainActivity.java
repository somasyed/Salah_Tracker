
package com.django.fyp_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.lang.Math;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Integer.valueOf;
import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor accelerometer;
    String TAG="Main";
    TextView tvX,tvY,tvZ,tvDPC;//txD;
    private BufferedWriter mW;
    File mFile;
    int i=0;
    Boolean startFlag;
    StringBuilder sb;
    Button start,stop,save;
    //int prevX,prevY,prevZ;
    float myX,myY,myZ;
    Date currentTime;
    String filetime;
    String[] filetimearray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startFlag=false;
        sb=new StringBuilder();
        sb.append("angleX,").append("angleY,").append("angleZ,Time,\n");
        tvX=findViewById(R.id.tvX);
        tvY=findViewById(R.id.tvY);
        tvZ=findViewById(R.id.tvZ);
        //txD=findViewById(R.id.textView18);
        tvDPC=findViewById(R.id.tvDPC);
        start=findViewById(R.id.btnStart);
        stop=findViewById(R.id.btnEnd);
        save=findViewById(R.id.btnSave);
        /*prevX=0;
        prevY=0;
        prevZ=0;*/

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        TimelyData();
        tvDPC.setText("0");

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTime = Calendar.getInstance().getTime();
                filetimearray=currentTime.toString().split(" ");
                filetime=""+filetimearray[1]+filetimearray[2]+"_"+filetimearray[3];
                startFlag=true;
                toastMsg("Data Recording Started");
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFlag=false;
                toastMsg("Data Recording Stopped");

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(sb);
                toastMsg("Recorded Data Saved");
            }
        });

    }

    private void TimelyData() {



       Timer timer=new Timer();
       timer.schedule(new TimerTask() {
           @Override
           public void run() {



               if (startFlag) {
               if (myX<=10 && myY<=10 && myZ<=10 && myX>=(-10) && myY>=(-10) && myZ>=(-10)) {


                       currentTime = Calendar.getInstance().getTime();
                       String[] time = currentTime.toString().split(" ");

                       sb.append("" + myX + ",").append("" + myY + ",").append("" + myZ + ",").append(time[3] + ",\n");
                       i++;


                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               tvDPC.setText("" + i);
                           }
                       });

                   }
                   }



           }
       },250,250);

    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){


        //Log.d(TAG, "onSensorChanged: X="+ sensorEvent.values[0]+" Y="+sensorEvent.values[1]+" Z="+sensorEvent.values[2]);


        myX=(sensorEvent.values[0]);
        myY=(sensorEvent.values[1]);
        myZ=(sensorEvent.values[2]);
        //float dist= (float) sqrt(((prevX-myX)*(prevX-myX))+((prevY-myY)*(prevY-myY))+((prevZ-myZ)*(prevZ-myZ)));
        //txD.setText("DIST: "+dist);
        tvX.setText(""+myX);
        tvY.setText(""+myY);
        tvZ.setText(""+myZ);




    }
    }

    @SuppressWarnings("unchecked")
    private void record(StringBuilder sb) {
        if (mW == null) {

            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFYPTraces");

            dir.mkdirs();
            mFile = new File(dir, new StringBuilder().append(getString(R.string.app_name)).append("_Data_"+filetime).append(".csv").toString());
            try {
                mW = new BufferedWriter(new FileWriter(mFile));
                mW.write(sb.toString());
                mW.flush();
                mW.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,"ERORRR",Toast.LENGTH_LONG).show();
            }
            mFile.exists();
        }

    }



    @Override
    protected void onDestroy() {
        //record(sb);
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();

    }
}
