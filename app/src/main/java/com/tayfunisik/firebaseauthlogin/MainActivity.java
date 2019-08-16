package com.tayfunisik.firebaseauthlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        logout = findViewById(R.id.btnlogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent startPageIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(startPageIntent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //kullanıcı giriş yapmışmı kontrol ediliyor, yapmamış ise login sayfasına yönlendiriliyor.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            SendUsertoLoginActivity();
        }
    }



    private void SendUsertoLoginActivity() {
        Intent startPageIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(startPageIntent);
        finish();
    }


}
