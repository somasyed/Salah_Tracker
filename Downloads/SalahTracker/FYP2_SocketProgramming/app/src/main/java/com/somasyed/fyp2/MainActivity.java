package com.somasyed.fyp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.icu.util.Output;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


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

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    TextView label;
   // EditText sentence;
    Button predict;
    public Handler handler;

//sensor
    SensorManager sensorManager;
    Sensor accelerometer;
    String TAG="Main";
    TextView tvX,tvY,tvZ; //tvDPC;//txD;
    private BufferedWriter mW;
    File mFile;
    int i=0;
    Boolean startFlag;
    StringBuilder sb;
    Button start;
    //int prevX,prevY,prevZ;
    int myX,myY,myZ;
    Date currentTime;
    String filetime;
    String[] filetimearray;
//sensorend

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 //sensor----------------------------------------------------
        handler=new Handler();
        startFlag=false;
        sb=new StringBuilder();
        sb.append("angleX,").append("angleY,").append("angleZ,Time,\n");
        tvX=findViewById(R.id.tvX);
        tvY=findViewById(R.id.tvY);
        tvZ=findViewById(R.id.tvZ);
        //txD=findViewById(R.id.textView18);
       // tvDPC=findViewById(R.id.tvDPC);
      //  start=findViewById(R.id.btnStart);
        //stop=findViewById(R.id.btnEnd);
       // save=findViewById(R.id.btnSave);
        /*prevX=0;
        prevY=0;
        prevZ=0;*/

   /*     start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(intent);
            }
        });
*/

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        TimelyData();
      //  tvDPC.setText("0");

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
 /*       start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTime = Calendar.getInstance().getTime();
                filetimearray=currentTime.toString().split(" ");
                filetime=""+filetimearray[1]+filetimearray[2]+"_"+filetimearray[3];
                startFlag=true;
                toastMsg("Data Recording Started");
            }
        });

*/
//sensorend-------------------------------------------------

       label = findViewById(R.id.label);
     //  sentence = findViewById(R.id.sentence);
        predict = findViewById(R.id.predict);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("X", tvX.getText());
                    obj.put("Y", tvY.getText());
                    obj.put("Z", tvZ.getText());
                    sendMsg(obj);

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendMsg(final JSONObject obj) {
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("192.168.43.158",5005);
                    OutputStream out = s.getOutputStream();
                    PrintWriter output = new PrintWriter(out);
                    output.println(obj.toString());
                    output.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    final String str = reader.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject obj = new JSONObject(str);
                                String lab = obj.getString("label");
                                    label.setText(lab);

                                /*if (lab.equalsIgnoreCase("0")) {
                                    label.setText("ham");
                                }
                                else {
                                    label.setText("spam");
                                }

                                 */
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
//connection---------------------------------------------
    //sensor
    private void TimelyData() {

        Timer timer = new Timer(); // creating timer

        // scheduling the task for repeated fixed-delay execution, beginning after the specified delay
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new TimerTask() {
                    @Override
                    public void run() {
                        getData();
                    }
                });

            }
        }, 800, 1000);


    }
    public void getData()
    {

        try {
            JSONObject obj = new JSONObject();
            obj.put("X", tvX.getText());
            obj.put("Y", tvY.getText());
            obj.put("Z", tvZ.getText());
            sendMsg(obj);

        }
        catch (JSONException e){
            e.printStackTrace();
        }



        if (startFlag) {
            if (myX<=50 && myY<=50 && myZ<=50 && myX>=(-50) && myY>=(-50) && myZ>=(-50)) {


                currentTime = Calendar.getInstance().getTime();
                String[] time = currentTime.toString().split(" ");

                sb.append("" + (int) myX + ",").append("" + (int) myY + ",").append("" + (int) myZ + ",").append(time[3] + ",\n");
                i++;


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // tvDPC.setText("" + i);
                    }
                });

            }
        }
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


            myX=(int)(sensorEvent.values[0]*5);
            myY=(int)(sensorEvent.values[1]*5);
            myZ=(int)(sensorEvent.values[2]*5);
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


    //sensorend
     /*  @Override
 public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onDestroy() {
        //record(sb);
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();

    }

   */


    public void onClickLookingToSell(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
        startActivity(intent);
    }

}
