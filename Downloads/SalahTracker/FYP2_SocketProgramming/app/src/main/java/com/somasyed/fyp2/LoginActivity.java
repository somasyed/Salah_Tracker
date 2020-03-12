package com.somasyed.fyp2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText username;
    EditText password;
  //  private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        //mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.email);
        password = findViewById(R.id.password);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    public void onClickRegister(View v){
        Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
        startActivity(intent);
    }

    private void loginUser() {

        String un = username.getText().toString().trim();
        String p = password.getText().toString().trim();

        if (un.isEmpty()) {
            username.setError("Email is required");
            username.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(un).matches()) {
            username.setError("Please enter a valid email address");
            username.requestFocus();
            return;
        }

        if (p.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (p.length() < 6) {
            password.setError("Please enter a password of 6 or more characters");
            password.requestFocus();
            return;
        }

     /*   mAuth.signInWithEmailAndPassword(un, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Logged in!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }
}
