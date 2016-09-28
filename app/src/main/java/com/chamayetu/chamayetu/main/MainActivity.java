package com.chamayetu.chamayetu.main;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String MAINACT_TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private Uri mPhotoUrl;
    private String mEmail;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private Drawer drawer = null;
    private AccountHeader headerResult = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

    // Define UI elements
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        // Initialize Firebase Remote Config.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mEmail = mFirebaseUser.getEmail();
            mPhotoUrl = mFirebaseUser.getPhotoUrl();
        }
        /*user profile*/
        final IProfile user_profile = new ProfileDrawerItem().withName(mUsername).withEmail(mEmail).withIcon(mPhotoUrl);
        Log.d(MAINACT_TAG,"Username: "+ mUsername + " Email: " + mEmail + " Photo Url: " + mPhotoUrl);

        //create the account header
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        user_profile
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //create the drawer
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        //default screen My Chama
                        new PrimaryDrawerItem().withName(R.string.drawer_item_mychama).withIcon(GoogleMaterial.Icon.gmd_sun).withIdentifier(1),

                        // My Account
                        new PrimaryDrawerItem().withName(R.string.drawer_item_myaccount).withIcon(FontAwesome.Icon.faw_home).withIdentifier(2).withSelectable(false),

                        //Notifications
                        new PrimaryDrawerItem().withName(R.string.drawer_item_notification).withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(3).withSelectable(false),

                        //settings
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(4),

                        // Help
                        new PrimaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(5),

                        //About
                        new PrimaryDrawerItem().withName(R.string.drawer_item_about).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(6)
                ).withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if(drawerItem instanceof Nameable){
                        // perform click events for drawer items
                        switch ((int) drawerItem.getIdentifier()){
                            case 1:
                                //default screen
                                break;
                            case 2:
                                //my account
                                break;
                            case 3:
                                // notifications
                                break;
                            case 4:
                                //settings
                                break;
                            case 5:
                                //default screen
                                break;
                        }

                    }
                    //we do not consume the event and want the Drawer to continue with the event chain
                    return false;
                }).withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        crossfadeDrawerLayout = (CrossfadeDrawerLayout) drawer.getDrawerLayout();
        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = drawer.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    drawer.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action",
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

}
