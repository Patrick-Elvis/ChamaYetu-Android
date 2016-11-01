package com.chamayetu.chamayetu.auth.loginchama;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.adapters.LoginChamaViewHolder;
import com.chamayetu.chamayetu.models.LoginChamaModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chamayetu.chamayetu.utils.Contract.LOGINCHAMA_TAG;
import static com.chamayetu.chamayetu.utils.Contract.USERNAME_BUNDLE_KEY;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.loginchama
 * Created by lusinabrian on 28/10/16.
 * Description:The LoginChamaActivity that will handle logging into a particular chama
 */

public class LoginChamaActivity extends AppCompatActivity implements LoginChamaView{
    private LoginChamaPresenter loginChamaPresenter;
    private MaterialDialog materialDialog;
    private FirebaseRecyclerAdapter<LoginChamaModel, LoginChamaViewHolder> loginChamaFirebaseRecyclerAdapter;

    //UI references
    @BindView(R.id.chama_login_recyclerView) RecyclerView chamaLoginRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chama_login_layout);
        ButterKnife.bind(this);

        Bundle receiveUsername = getIntent().getExtras();
        String username = receiveUsername.getString(USERNAME_BUNDLE_KEY);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        loginChamaPresenter = new LoginChamaPresenterImpl(
                LoginChamaActivity.this,
                username,
                mDatabase,
                loginChamaFirebaseRecyclerAdapter,
                new LoginChamaInteractorImpl(),
                this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loginChamaPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        loginChamaPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        materialDialog = new MaterialDialog.Builder(LoginChamaActivity.this)
                .title(R.string.progress_dialog_title)
                .theme(Theme.DARK)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();
        chamaLoginRecycler.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        if(materialDialog.isShowing()){
            materialDialog.dismiss();
            chamaLoginRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setAdapter(FirebaseRecyclerAdapter<LoginChamaModel, LoginChamaViewHolder> loginChamaRecyclerAdapter) {
        chamaLoginRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chamaLoginRecycler.setAdapter(loginChamaRecyclerAdapter);
    }

    @Override
    public void openMainActWithChama(int position) {

    }

    @Override
    public void showMessage(String message, int messageType) {
        TastyToast.makeText(this, message, TastyToast.LENGTH_LONG, messageType);
    }

}
