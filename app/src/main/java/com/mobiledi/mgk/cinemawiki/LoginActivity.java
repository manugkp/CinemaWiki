package com.mobiledi.mgk.cinemawiki;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button login;
    String uname,pwd;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.userName);
        password=(EditText) findViewById(R.id.password);

        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = username.getText().toString();
                pwd = password.getText().toString();;
                if(uname.length()<4)
                {
                    Toast.makeText(getApplicationContext(), " Please enter a valid name ", Toast.LENGTH_LONG).show();
                    username.setError("Invalid Username");
                }
                else if (pwd.length()<4)
                {

                    password.setError("Invalid Password ");
                }
               else{
                if((uname.equals("mobiledi") && pwd.equals("mobiledi@blr"))||(uname.equals("manugk")&& pwd.equals("developer"))) {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Wrong Credentials",
                            Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                }}
            }
        });
    }




}
