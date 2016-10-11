package com.chamayetu.chamayetu.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.main.MainActivity;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 11/10/16.
 * Description: Displays a brief success page for the user before the main activity
 */

public class LoginSuccess extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success_layout);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        /*start the next activity*/
        startActivity(new Intent(LoginSuccess.this, MainActivity.class));
    }

}

