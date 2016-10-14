package com.chamayetu.chamayetu.dasboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.adapters.ActivityRecyclerAdapter;
import com.chamayetu.chamayetu.graph.StatementBarGraph;
import com.chamayetu.chamayetu.models.ActivityModel;
import com.chamayetu.chamayetu.utils.Contract;

import com.chamayetu.chamayetu.models.StatementPojo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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
/**
 * ChamaYetu
 * com.chamayetu.chamayetu.mychama
 * Created by lusinabrian on 28/09/16.
 * Description: View that will handle Chama Details and data for the user's chama
 */

public class DashboardView extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {
    public static final String DASHBOARDVIEW_TAG = DashboardView.class.getSimpleName();

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

        StatementBarGraph statementBarGraph = new StatementBarGraph(mBarChart, getActivity());
        statementBarGraph.initGraph();

        // initialize recycler adapter
        initActivityRecycler();
        initFirebaseDatabase();
        return rootView;
    }

    /**Initilizes the Activity Recycler to display activity for a particular chama
     * The notificationCounter variable will be used to post badges in the drawer menu
     * it will record any change on this node and update the user
     * Initialize the arraylist that will store the information of activities
     * initialize an instance of the Firebase database*/
    /*todo: change child node from boda to current user's chama*/
    public void initActivityRecycler(){
        activityModelList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        SharedPreferences mNotification = getActivity().getSharedPreferences(Contract.NOTIFICATION_SP_FILE,0);
        SharedPreferences.Editor editor = mNotification.edit();
        mDatabase.child(Contract.ACTIVITY_NODE).child("boda").addValueEventListener(
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
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /*Get statement node of boda node*/
        /*TODO: get node of client's statement*/
        mDatabase.child(Contract.STATEMENT_NODE).child("boda")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        StatementPojo statementPojo = dataSnapshot.getValue(StatementPojo.class);
                        statementPojo = new StatementPojo(statementPojo.getDateFrom(),
                                statementPojo.getDateTo(), statementPojo.getTitle(),
                                statementPojo.getTotalAmount(),statementPojo.getOutgoings(), statementPojo.getFundsReceived());

                        Log.d(DASHBOARDVIEW_TAG, statementPojo.toString());

                        outgoingsField.setText(String.valueOf(statementPojo.getOutgoings()) + "KSH");
                        fundsRecievedField.setText(String.valueOf(statementPojo.getFundsReceived()) + "KSH");
                        chamaBalance.setText(String.valueOf("Ksh. " + statementPojo.getTotalAmount()));
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
                /*display the full statement*/
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
