package com.somasyed.fyp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisterUser extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    EditText username;
    EditText password;
    private EditText confirmPassword;
    private ImageView imageView;
    Button signupButton;
    Button chooseImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.passwordConf);
        imageView = findViewById(R.id.imageView);
        signupButton = findViewById(R.id.signupButton);
        chooseImageButton = findViewById(R.id.chooseImageButton);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "User Registered!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                //intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
