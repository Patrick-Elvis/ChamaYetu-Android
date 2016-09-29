package com.chamayetu.chamayetu.useraccount;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chamayetu.chamayetu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAccountActivity extends AppCompatActivity {
    @BindView(R.id.useracct_profilecard) CardView profileCardView;
    @BindView(R.id.useracct_img) CircleImageView userImagView;
    @BindView(R.id.useracct_chamacard) CardView chamaCardView;
    @BindView(R.id.useracct_displayname) TextView userDisplayName;
    @BindView(R.id.useracct_email) TextView userEmail;
    @BindView(R.id.user_toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.useracct_chamarecycler) RecyclerView mRecyclerView;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useracctact_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        initDataIntoViews();

        //TODO: change user profile in Firebase, display a dialog
        fab.setOnClickListener(view -> Snackbar.make(view,
                "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    /**Fetches data from Firebase,loads them into the views*/
    private void initDataIntoViews() {
        Glide.with(this).load(mFirebaseUser.getPhotoUrl()).into(userImagView);
        userDisplayName.setText(mFirebaseUser.getDisplayName());
        userEmail.setText(mFirebaseUser.getEmail());
    }
}
