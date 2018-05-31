package com.example.criswiz.emergencyalertandsafety;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteCode extends AppCompatActivity {

    String email, password, date, isSharing, code, name, userId;
    Uri imageUri;
    ProgressDialog progressDialog;

    TextView t1;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        t1 = findViewById(R.id.textView);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();

        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        if (intent != null){
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
            code = intent.getStringExtra("code");
            date = intent.getStringExtra("date");
            isSharing = intent.getStringExtra("isSharing");
            imageUri = intent.getParcelableExtra("ImageUri");
        }
        t1.setText(code);
    }

    public void registerUser(View v){
        progressDialog.setMessage("Please wait while we are creating an account for you");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Insert value in Real time database
                    CreateUser createUser = new CreateUser(name,email,password,code,"false","na","na","na");
                    user = auth.getCurrentUser();
                    userId = user.getUid();
                    
                    reference.child(userId).setValue(createUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(InviteCode.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(InviteCode.this, MyNavigationDrawerActiviity.class);
                                startActivity(intent);
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(InviteCode.this, "Could not register user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
