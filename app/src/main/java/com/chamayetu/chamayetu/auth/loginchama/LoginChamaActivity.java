package com.chamayetu.chamayetu.auth.loginchama;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chamayetu.chamayetu.R;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 28/10/16.
 * Description:The LoginChamaActivity that will handle logging into a particular chama
 */

public class LoginChamaActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chama_login_layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
