package com.chamayetu.chamayetu.auth.loginchama;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chamayetu.chamayetu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chamayetu.chamayetu.utils.Contract.LOGINCHAMA_TAG;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 28/10/16.
 * Description:The LoginChamaActivity that will handle logging into a particular chama
 */

public class LoginChamaActivity extends AppCompatActivity implements LoginChamaView{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private LoginChamaPresenter loginChamaPresenter;
    private MaterialDialog materialDialog;

    //UI references
    @BindView(R.id.chama_login_recyclerView) RecyclerView chamaLoginRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chama_login_layout);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user !=null){
                Log.d(LOGINCHAMA_TAG, "onAuthStateChanged:signedIn: " + user.getUid());
            }else{
                Log.d(LOGINCHAMA_TAG, "onAuthStateChanged:signedout");
            }
        };



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
