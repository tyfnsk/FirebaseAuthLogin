package com.tayfunisik.firebaseauthlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Typeface tf1,tf2;
    Button login, register;
    private ActivityOptions options = null;
    private Context mContext = this;
    private ImageView logo;
    private EditText mail_input,password_input;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        logo = findViewById(R.id.logo);
        mail_input = findViewById(R.id.lgnemail);
        password_input = findViewById(R.id.lgnpassword);

        tf1= Typeface.createFromAsset(getAssets(),"fonts/exo.ttf");
        tf2= Typeface.createFromAsset(getAssets(),"fonts/jura.ttf");

        login = findViewById(R.id.btnlogin);
        register = findViewById(R.id.btnregister);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mail_input.getText().toString();
                String sifre = password_input.getText().toString();

                LoginUser(mail,sifre);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                mContext.startActivity(intent);
            }
        });


    }

    //Firebase signin methodu ile kullanıcıyı login ediyoruz
    private void LoginUser(String mail, String sifre) {
        if(mail.matches("") || sifre.matches("")){
            Toast.makeText(getApplication(),"Mail veya Şifre Alanı Boş Bırakılamaz",Toast.LENGTH_LONG).show();
        }else{
            mAuth.signInWithEmailAndPassword(mail,sifre)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //interpolator kullanılarak butona tıklanıldığında bounce animasyonu veriliyor
                                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                                double animationDuration = 2000;
                                myAnim.setDuration((long)animationDuration);
                                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                                myAnim.setInterpolator(interpolator);
                                login.setAnimation(myAnim);
                                login.startAnimation(myAnim);
                                //transition kullanılarak activiti geçişinde logoya animasyon veriliyor
                                options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, logo, mContext.getString(R.string.picture_transition_name));
                                mContext.getString(R.string.picture_transition_name);
                                Intent intent = new Intent(mContext, MainActivity.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mContext.startActivity(intent, options.toBundle());
                                finish();
                            }else{
                                Toast.makeText(getApplication(),"Mail veya Şifre Yanlış Girildi",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }



}
