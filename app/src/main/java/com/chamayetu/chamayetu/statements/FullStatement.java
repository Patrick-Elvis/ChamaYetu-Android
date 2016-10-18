package com.chamayetu.chamayetu.statements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chamayetu.chamayetu.R;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 12/10/16.
 * Description: Display a full statement to the user
 */

public class FullStatement extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullstatement_layout);
    }
}
