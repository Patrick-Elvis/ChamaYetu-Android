package com.chamayetu.chamayetu.dasboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.utils.Contract;
import com.chamayetu.chamayetu.utils.models.ChamaPojo;
import com.chamayetu.chamayetu.utils.models.StatementPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robinhood.spark.SparkView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.mychama
 * Created by lusinabrian on 28/09/16.
 * Description: View that will handle Chama Details and data for the user's chama
 */

public class DashboardView extends Fragment implements View.OnClickListener{
    public static final String DASHBOARDVIEW_TAG = DashboardView.class.getSimpleName();

    @BindView(R.id.mychamastatement_card) CardView statementCard;
    @BindView(R.id.mychamaagenda_card) CardView agendaCard;
    @BindView(R.id.mychamamembers_card) CardView membersCard;
    @BindView(R.id.mychamanxtmeeting_card) CardView nxtMeetingCard;
    @BindView(R.id.mychamaexpected_amt_card) CardView expectedAmtCard;
    @BindView(R.id.mychama_graph_view_card) CardView graphCard;

    @BindView(R.id.chama_graphView) SparkView graphView;

    @BindView(R.id.tv_statement_bal_view) TextView chamaBalance;

    @BindView(R.id.tv_outgoing_field) TextView outgoingsField;
    @BindView(R.id.tv_fundsReceived_label) TextView fundsRecievedField;
    @BindView(R.id.btn_full_statement) Button btnFullStatement;
    @BindView(R.id.btn_mini_statment) Button btnMiniStatement;

    @BindView(R.id.nxtmeeting_time_field) TextView nxtMeetingTime;
    @BindView(R.id.nxtmeeting_venue_field) TextView nxtMeetingVenue;
    @BindView(R.id.milestone_field) TextView milestioneView;
    @BindView(R.id.milestone_date_field)TextView milestoneDate;
    @BindView(R.id.members_number) TextView memberNumbers;
    @BindView(R.id.expextedamt_number) TextView expectedAmt;

    private DatabaseReference mDatabase;

    public DashboardView() {}

    public static Fragment newInstance(){
        DashboardView dashboardView = new DashboardView();
        dashboardView.setRetainInstance(true);
        return dashboardView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboardview_layout, container, false);
        ButterKnife.bind(this, rootView);

        initFirebaseDatabase();
        return rootView;
    }

    /**Initialize Firebase Database*/
    private void initFirebaseDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /*Get boda node*/
        /*TODO: get node of client's chama*/
        mDatabase.child(Contract.CHAMA_NODE).child("boda")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ChamaPojo chamaPojo = dataSnapshot.getValue(ChamaPojo.class);
                chamaPojo = new ChamaPojo(chamaPojo.getDateCreated(),
                        chamaPojo.getNextMeetingTime(), chamaPojo.getMilestoneDate(),
                        chamaPojo.getMembers(),chamaPojo.getTotalAmount(),chamaPojo.getAmountExpected(),
                        chamaPojo.getName(),chamaPojo.getVenue(),chamaPojo.getMilestone());

                Log.d(DASHBOARDVIEW_TAG +"ChamaNode: ", String.valueOf(chamaPojo.getNextMeetingTime()) + " " + chamaPojo.getVenue() + " " + chamaPojo.getMilestone() + " " +chamaPojo.getMembers() );

                nxtMeetingTime.setText(chamaPojo.getNextMeetingTime());
                nxtMeetingVenue.setText(chamaPojo.getVenue());
                milestioneView.setText(chamaPojo.getMilestone());
                memberNumbers.setText(String.valueOf(chamaPojo.getMembers()));
                milestoneDate.setText(chamaPojo.getMilestoneDate());
                expectedAmt.setText(String.valueOf(chamaPojo.getAmountExpected()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*Get statement node of boda node*/
        /*TODO: get node of client's statement*/
        mDatabase.child(Contract.STATEMENT_NODE).child("boda")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        StatementPojo statementPojo = dataSnapshot.getValue(StatementPojo.class);
                        statementPojo = new StatementPojo(statementPojo.getDateFrom(),
                                statementPojo.getDateTo(), statementPojo.getTitle(),
                                statementPojo.getTotalAmount());

                        Log.d(DASHBOARDVIEW_TAG +"Statement: ", statementPojo.getDateFrom()+ " "+
                                statementPojo.getDateTo()+ " "+ statementPojo.getTitle()+ " "+
                                statementPojo.getTotalAmount());

                        outgoingsField.setText(statementPojo.getDateFrom());
                        fundsRecievedField.setText(statementPojo.getDateTo());
                        chamaBalance.setText(String.valueOf("Ksh. " + statementPojo.getTotalAmount()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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


}
