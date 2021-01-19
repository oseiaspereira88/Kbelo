package com.example.kbelo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class IntroActivity extends AppCompatActivity {
    ConstraintLayout clAppIntro;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler1 = new Handler(), handler2 = new Handler(), handler3 = new Handler();
        img = (ImageView) findViewById(R.id.imgLogo);
        clAppIntro = (ConstraintLayout) findViewById(R.id.clAppIntro);

        // tarefa postergada por 5000 milissegundos
        handler1.postDelayed(new Runnable() {
            public void run() {
                Intent it = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(it);
                finish();
            }
        }, 5000);

        // tarefa postergada por 500 milissegundos
        handler2.postDelayed(new Runnable() {
            public void run() {
                //animação 01
                clAppIntro.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(700)
                        .repeat(0)
                        .playOn(clAppIntro);
            }
        }, 500);

        // tarefa postergada por 2600 milissegundos
        handler3.postDelayed(new Runnable() {
            public void run() {
                //animação 03
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(0)
                        .playOn(findViewById(R.id.imgLogo));
            }
        }, 2600);
    }
}