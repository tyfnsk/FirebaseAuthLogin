package com.tayfunisik.firebaseauthlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullname_input, mail_input, password_input, confirmpassword_input;
    private Button btnRgsSave;
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
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        fullname_input = findViewById(R.id.rgsfullname);
        mail_input = findViewById(R.id.rgsemail);
        password_input = findViewById(R.id.rgspassword);
        confirmpassword_input = findViewById(R.id.rgsconfirmpassword);
        btnRgsSave = findViewById(R.id.btnsave);

        btnRgsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mail_input.getText().toString();
                String sifre = password_input.getText().toString();
                String sifretekrar = confirmpassword_input.getText().toString();

                RegisterAccount(mail,sifre,sifretekrar);
            }
        });



    }
    //Firebase mail ve şifre ile kullanıcı kaydı oluşturma
    private void RegisterAccount(String mail, String sifre, String sifretekrar) {
        int sifre_karakter = sifre.length();
        int sifretekrar_karakter = sifretekrar.length();
        if(mail.matches("") || sifre.matches("") || sifretekrar.matches("")){
            Toast.makeText(getApplication(),"Mail ve Şifre Alanı Boş Bırakılamaz",Toast.LENGTH_LONG).show();
        }else {
            if(!Functions.isEmailValid(mail)) {
                Toast.makeText(getApplication(), "Mail Formatı Yanlış", Toast.LENGTH_LONG).show();
            }else if(sifre_karakter < 6 || sifretekrar_karakter < 6){
                Toast.makeText(getApplication(), "Oluşturulan Şifre 6 Karakterden Az Olamaz", Toast.LENGTH_LONG).show();
            }else if(!sifre.matches(sifretekrar)){
                Toast.makeText(getApplication(), "Şifreler Uyumsuz Girildi", Toast.LENGTH_LONG).show();
            }else{
                mAuth.createUserWithEmailAndPassword(mail, sifre)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    finish();
                                }else {
                                    Toast.makeText(getApplication(), "Hata Oluştu, Tekrar Deneyin!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
    }


}
