package com.example.criswiz.emergencyalertandsafety;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    String email;
    EditText e3_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Intent intent = getIntent();

        if (intent != null){
            email = intent.getStringExtra("email");
        }
    }

    public void goToNameActivity(View v){

        if (e3_password.getText().toString().length() > 6){
            Intent intent = new Intent(PasswordActivity.this, NameActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", e3_password.getText().toString());
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Password lenght should be mote than 6 characters", Toast.LENGTH_SHORT).show();
        }
    }
}
