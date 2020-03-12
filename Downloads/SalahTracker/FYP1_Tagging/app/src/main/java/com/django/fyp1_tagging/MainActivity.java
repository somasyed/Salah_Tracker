package com.django.fyp1_tagging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.valueOf;

public class MainActivity extends AppCompatActivity {

    StringBuilder sb;
    private BufferedWriter mW;
    File mFile;
    Button btnStamp,btnSave;
    TextView tvTSD,tvCP;
    Date currentTime;
    String filetime;
    String[] filetimearray;
    String[] postures;
    int count=0;
    RadioGroup rakatGroup;
    RadioButton rakat;
    int pos,siz,rakatCount;
    Boolean temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        sb=new StringBuilder();
        sb.append("TimeStamp,Posture,RakatCount,\n");
        tvTSD=findViewById(R.id.tvTSD);
        tvCP=findViewById(R.id.tvCPname);
        tvTSD.setText(""+count);
        btnStamp=findViewById(R.id.btnTimeStamp);
        btnSave=findViewById(R.id.btnSAVE);
        rakatGroup=findViewById(R.id.rakats);
        int selectedID=rakatGroup.getCheckedRadioButtonId();
        rakat=findViewById(selectedID);
        pos=0;
        siz=0;
        rakatCount=0;
        temp=true;

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

        btnStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (temp){

                    currentTime = Calendar.getInstance().getTime();
                    filetimearray=currentTime.toString().split(" ");
                    filetime=""+filetimearray[1]+filetimearray[2]+"_"+filetimearray[3];

                    int selectedID=rakatGroup.getCheckedRadioButtonId();
                    rakat=(RadioButton)findViewById(selectedID);
                    temp=false;

                    String RakatCheck=rakat.getText().toString();
                    if (RakatCheck.equals("2")){
                        rakatCount=2;
                        postures=new String[]{
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration",
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration","Long Sitting","End Namaz"};
                        siz=postures.length;
                    } else if (RakatCheck.equals("3")){
                        rakatCount=3;
                        postures=new String[]{
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration",
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration","Long Sitting",
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration","Long Sitting","End Namaz"};
                        siz=postures.length;

                    } else if (RakatCheck.equals("4")){
                        rakatCount=4;
                        postures=new String[]{
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration",
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration","Long Sitting",
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration",
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration","Long Sitting","End Namaz"};
                        siz=postures.length;
                    } else{
                        rakatCount=2;
                        postures=new String[]{
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration",
                                "Long Standing", "Bowing", "Short Standing", "1st Prostration", "Short Sitting", "2nd Prostration","Long Sitting","End Namaz"};
                        siz=postures.length;
                    }
                }
                String showprev=postures[pos%siz];
                pos++;
                String show=postures[pos%siz];
                tvCP.setText(showprev);
                btnStamp.setText("Start "+show);

                currentTime = Calendar.getInstance().getTime();
                String[] time=currentTime.toString().split(" ");

                /*//time to seconds
                String[] TimeSecs=time[3].toString().split(":");
                int hrsSec= valueOf(TimeSecs[0]) * 3600;
                int MinSec= valueOf(TimeSecs[1]) * 60;
                int Sec= valueOf(TimeSecs[2]);
                int totSecs=hrsSec+MinSec+Sec;
                //--------*/

                sb.append(time[3]+","+showprev+","+rakatCount+","+",\n");
                count++;
                tvTSD.setText(""+count);
                toastMsg("TimeStamp Recorded");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(sb);
                toastMsg("TimeStamps Saved To File");

            }
        });
    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();

    }
    @SuppressWarnings("unchecked")
    private void record(StringBuilder sb) {
        if (mW == null) {

            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFYPTraces");

            dir.mkdirs();
            mFile = new File(dir, new StringBuilder().append(getString(R.string.app_name)).append("_"+filetime).append(".csv").toString());
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
}
