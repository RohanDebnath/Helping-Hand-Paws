package com.example.pawhand2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView_LoadingPage;
    TextView textView_LoadingPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView_LoadingPage=findViewById(R.id.image_loadingPage);
        textView_LoadingPage=findViewById(R.id.textView_loadingPage);

        imageView_LoadingPage.animate().alpha(0f).setDuration(0);
        textView_LoadingPage.animate().alpha(0f).setDuration(0);

        imageView_LoadingPage.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() { // alpha er jonno agger icon gta vanish hoi ni
            @Override
            public void onAnimationEnd(Animator animation) {
                textView_LoadingPage.animate().alpha(1f).setDuration(800);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

}