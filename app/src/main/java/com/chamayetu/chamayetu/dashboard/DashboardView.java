package com.chamayetu.chamayetu.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.adapters.ActivityRecyclerAdapter;
import com.chamayetu.chamayetu.graph.StatementBarGraph;
import com.chamayetu.chamayetu.models.ActivityModel;
import com.chamayetu.chamayetu.statements.FullStatement;
import com.chamayetu.chamayetu.utils.Contract;

import com.chamayetu.chamayetu.models.StatementPojo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chamayetu.chamayetu.utils.Contract.ACTIVITY_NODE;
import static com.chamayetu.chamayetu.utils.Contract.CHAMA_GROUPS;
import static com.chamayetu.chamayetu.utils.Contract.CHAMA_NAME_KEY;
import static com.chamayetu.chamayetu.utils.Contract.CHAMA_SP_FILE;
import static com.chamayetu.chamayetu.utils.Contract.DASHBOARDVIEW_TAG;
import static com.chamayetu.chamayetu.utils.Contract.FULL_STATEMENT_CHOICE;
import static com.chamayetu.chamayetu.utils.Contract.NOTIFICATION_SP_FILE;
import static com.chamayetu.chamayetu.utils.Contract.SHAREPREF_PRIVATE_MODE;
import static com.chamayetu.chamayetu.utils.Contract.STATEMENT_NODE;
import static com.chamayetu.chamayetu.utils.Contract.USERS_NODE;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.mychama
 * Created by lusinabrian on 28/09/16.
 * Description: View that will handle Chama Details and data for the user's chama
 */

public class DashboardView extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {

    @BindView(R.id.mychamastatement_card) CardView statementCard;
    @BindView(R.id.mychama_graph_view_card) CardView graphCard;
    @BindView(R.id.chamaactivity_card) CardView activityCard;

    @BindView(R.id.statement_barchart) BarChart mBarChart;
    @BindView(R.id.chamaactivity_recycler) RecyclerView mRecyclerView;

    @BindView(R.id.tv_statement_bal_view) TextView chamaBalance;
    @BindView(R.id.tv_chama_memmbers_id) TextView chamaMembersNo;

    @BindView(R.id.tv_outgoing_field) TextView outgoingsField;
    @BindView(R.id.tv_fundsReceived_field) TextView fundsRecievedField;
    @BindView(R.id.btn_full_statement) Button btnFullStatement;
    @BindView(R.id.btn_mini_statment) Button btnMiniStatement;

    private DatabaseReference mDatabase;
    private ActivityRecyclerAdapter activityRecyclerAdapter;
    private List<ActivityModel> activityModelList;
    private String chamaName;
    private String userName;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;

    public DashboardView() {}

    public static Fragment newInstance(){
        DashboardView dashboardView = new DashboardView();
        dashboardView.setRetainInstance(true);
        return dashboardView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityModelList = new ArrayList<>();
        activityRecyclerAdapter = new ActivityRecyclerAdapter(getActivity(),activityModelList,R.layout.chamaactivity_item_layout);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboardview_layout, container, false);
        ButterKnife.bind(this, rootView);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        //get the currently logged in user, get their username which will act as a node in USERS_NODE
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        String userEmail;
        if (mFirebaseUser != null) {
            userEmail = mFirebaseUser.getEmail();
            int index = userEmail.indexOf('@');
            userName = userEmail.substring(0, index).toLowerCase();
        }

        StatementBarGraph statementBarGraph = new StatementBarGraph(mBarChart, getActivity());
        statementBarGraph.initGraph();

        // initialize recycler adapter
        initFirebaseDatabase();
        return rootView;
    }

    /**Initilizes the Activity Recycler to display activity for a particular chama
     * The notificationCounter variable will be used to post badges in the drawer menu
     * it will record any change on this node and update the user
     * Initialize the arraylist that will store the information of activities
     * initialize an instance of the Firebase database*/
    /*todo: change child node from boda to current user's chama*/
    public void initActivityRecycler(String chamaName){
        activityModelList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        SharedPreferences mNotification = getActivity().getSharedPreferences(NOTIFICATION_SP_FILE,0);
        SharedPreferences.Editor editor = mNotification.edit();
        mDatabase.child(ACTIVITY_NODE).child(chamaName).addValueEventListener(
                new ValueEventListener() {
                int notificationCounter = 0;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    notificationCounter += 1;
                    editor.putInt(Contract.NOTIFICATION_SP_KEY, notificationCounter);
                    editor.apply();
                    for(DataSnapshot children: dataSnapshot.getChildren()){
                        ActivityModel activityModel1 = children.getValue(ActivityModel.class);
                        activityModel1 = new ActivityModel(
                                activityModel1.getActivityType(),
                                activityModel1.getPerson(),
                                activityModel1.getDate(),
                                activityModel1.getAmount());
                        activityModelList.add(activityModel1);
                    }

                    activityRecyclerAdapter = new ActivityRecyclerAdapter(getActivity(), activityModelList,
                            R.layout.chamaactivity_item_layout);

                    mRecyclerView.setAdapter(activityRecyclerAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(DASHBOARDVIEW_TAG+"DBError", String.valueOf(databaseError));
                    TastyToast.makeText(getActivity(), "Operation cancelled",TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            });
    }

    /**Initialize Firebase Database*/
    private void initFirebaseDatabase() {

        /**Access the user's chama from the user's node*/
        mDatabase.child(USERS_NODE).child(userName).child(CHAMA_GROUPS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> userChamaList = new ArrayList<>();
                /*if the user has only one chama then retrieve data for that one chama*/
                if(dataSnapshot.getChildrenCount() == 1){
                    chamaName = dataSnapshot.getKey();
                }else{
                 /*else loop through them retrieving the keys for each and storing them,
                 only set the 1st chama as the chamaName variable*/
                    for(DataSnapshot d: dataSnapshot.getChildren()){
                        userChamaList.add(d.getKey());
                        Log.d(DASHBOARDVIEW_TAG,d.getKey());
                    }
                    chamaName = userChamaList.get(0);
                }
                updateStatement(chamaName);
                initActivityRecycler(chamaName);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                /*silently log the database error*/
                Log.d(DASHBOARDVIEW_TAG, databaseError.getMessage());
            }
        });
    }

    /**Get the statement of the chama the user is currently logged into*/
    private void updateStatement(String chamaName) {
        /*Get statement node of boda node*/
        mDatabase.child(STATEMENT_NODE).child(chamaName)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        StatementPojo statementPojo = dataSnapshot.getValue(StatementPojo.class);
                        statementPojo = new StatementPojo(statementPojo.getDateFrom(),
                                statementPojo.getDateTo(), statementPojo.getTitle(),
                                statementPojo.getTotalAmount(),statementPojo.getOutgoings(), statementPojo.getFundsReceived());

                        Log.d(DASHBOARDVIEW_TAG, statementPojo.toString());
                        /*if the statement outgoings is 0
                        * set the text to nil*/
                        if(statementPojo.getOutgoings() == 0 || statementPojo.getFundsReceived() == 0
                                || statementPojo.getTotalAmount() == 0){
                            outgoingsField.setText(getString(R.string.nill_value));
                            fundsRecievedField.setText(getString(R.string.nill_value));
                            chamaBalance.setText(getString(R.string.nill_value));
                        }else{
                            outgoingsField.setText(String.valueOf(statementPojo.getOutgoings()) + "KSH");
                            fundsRecievedField.setText(String.valueOf(statementPojo.getFundsReceived()) + "KSH");
                            chamaBalance.setText(String.valueOf("Ksh. " + statementPojo.getTotalAmount()));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(DASHBOARDVIEW_TAG+"DBError", String.valueOf(databaseError));
                        TastyToast.makeText(getActivity(), "Error encountered", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                });
    }

    /**Register click events for the mini and full statements*/
    @OnClick({R.id.btn_full_statement, R.id.btn_mini_statment})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_full_statement:
                //display a dialog of choice of months to pick from
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.full_statement_title)
                        .items(R.array.statement_period_items)
                        .itemsCallbackSingleChoice(-1, (dialog, view, which, text) -> {
                            //storing user choice in a bundle for retrieval in the next activity
                            Intent openFullStatement = new Intent(getActivity(), FullStatement.class);
                            Bundle userChoiceBundle = new Bundle();
                            userChoiceBundle.putCharSequence(FULL_STATEMENT_CHOICE,text);
                            openFullStatement.putExtras(userChoiceBundle);
                            startActivity(openFullStatement);
                            return true;
                        })
                        .theme(Theme.LIGHT)
                        .positiveText(R.string.choose)
                        .positiveColor(ContextCompat.getColor(getActivity(),R.color.light_purple))
                        .negativeText(R.string.cancel)
                        .negativeColor(ContextCompat.getColor(getActivity(),R.color.light_purple))
                        .show();
                break;

            case R.id.btn_mini_statment:
                /*display a mini statement*/
                break;
        }

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
