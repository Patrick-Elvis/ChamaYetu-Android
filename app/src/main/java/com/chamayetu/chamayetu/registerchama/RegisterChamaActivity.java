package com.chamayetu.chamayetu.registerchama;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.chamayetu.chamayetu.R;

import butterknife.BindView;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.registerchama
 * Created by lusinabrian on 15/10/16.
 * Description: Activity to register a new chama
 */

public class RegisterChamaActivity extends AppCompatActivity {
    @BindView(R.id.signup_chamaname_id) EditText chamaName;
    @BindView(R.id.signup_chamaMembers_id) EditText chamaMemberNumbers;
    @BindView(R.id.signup_chama_bankAccount_id) EditText chamaAccountNo;
    @BindView(R.id.signup_chama_bankName_id) EditText chamaBankName;

    @BindView(R.id.signup_chama_button_id) EditText chamaSignUpBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chama_form);
    }
}
