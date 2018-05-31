package com.example.criswiz.emergencyalertandsafety;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

public class Home extends AppCompatActivity {

    Button jLogin, jNewsfeed;

    private final static int MAX_PERMISSION=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        jNewsfeed = findViewById(R.id.news_feed);
        jNewsfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,NewsFeedActivity.class);
                startActivity(intent);
            }
        });

        jLogin = findViewById(R.id.btnSignIn);
        jLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setAllowNewEmailAccounts(true).build(),MAX_PERMISSION
                );
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAX_PERMISSION){
            startNewActivity(resultCode,data);
        }
    }

    private void startNewActivity(int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            Intent intent = new Intent(Home.this,ListOnline.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Login Failed!!!", Toast.LENGTH_SHORT).show();
        }
    }

}

