package com.example.pawhand2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText userMail,userPassword;
    Button loginBtn;
    FirebaseAuth mAuth;
    TextView hyperLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userMail=findViewById(R.id.login_Mail);
        userPassword=findViewById(R.id.login_password);
        loginBtn=findViewById(R.id.loginBtn);
        mAuth=FirebaseAuth.getInstance();
        hyperLink=findViewById(R.id.hyperlink);

        String Textt="Don't have any Account? Register";
        SpannableString ss= new SpannableString(Textt);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        ss.setSpan(clickableSpan,24,32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        hyperLink.setText(ss);
        hyperLink.setMovementMethod(LinkMovementMethod.getInstance());

        Button button = findViewById(R.id.logingate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Home.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email= userMail.getText().toString();
                String pasword = userPassword.getText().toString();
                if(email.isEmpty()||pasword.isEmpty())
                {
                    Toast.makeText(Login.this,"Wrong ID or Password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    signIn(email,pasword);
                }

            }
        });
    }

    private void signIn(String email, String pasword) {

        mAuth.signInWithEmailAndPassword(email,pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    updateUI();

                }
                else
                {
                    Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateUI() {
        Intent intent=new Intent(Login.this, Home.class);
        startActivity(intent);
    }

}