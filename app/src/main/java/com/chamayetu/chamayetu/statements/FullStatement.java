package com.chamayetu.chamayetu.statements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.chamayetu.chamayetu.R;

import com.chamayetu.chamayetu.adapters.FullStatementViewHolder;
import com.chamayetu.chamayetu.models.FullStatementModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chamayetu.chamayetu.utils.Contract.CHAMA_STATEMENT_TITLE;
import static com.chamayetu.chamayetu.utils.Contract.FULL_STATEMENT_CHOICE;
import static com.chamayetu.chamayetu.utils.Contract.FULL_STATEMENT_NODE;
import static com.chamayetu.chamayetu.utils.Contract.STATEMENT_NODE;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 12/10/16.
 * Description: Display a full statement to the user
 */

public class FullStatement extends AppCompatActivity implements FullStatementView, AppBarLayout.OnOffsetChangedListener{

    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private View mFab;
    private StatementPresenter statementPresenter;
    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<FullStatementModel, FullStatementViewHolder> statementFirebaseRecyclerAdapter;
    private String chamaStatmentTitle;

    /*ui references*/
    @BindView(R.id.full_statement_collapsingtoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.full_statement_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.full_statement_toolbar) Toolbar mToolbar;
    @BindView(R.id.full_statement_cardview) CardView mCardView;
    @BindView(R.id.full_statement_recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.full_statement_progressbar) ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullstatement_layout);
        ButterKnife.bind(this);
        Bundle receiveUserChoice = getIntent().getExtras();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /*initialize the presenter*/
        statementPresenter = new StatementPresenterImpl(this, new FindItemsInteractorImpl());

        //extract the data and store for processing
        CharSequence statementPeriod = receiveUserChoice.getCharSequence(FULL_STATEMENT_CHOICE);
        chamaStatmentTitle = receiveUserChoice.getString(CHAMA_STATEMENT_TITLE);

        /*set the title to the currently viewed chama statement*/
        collapsingToolbarLayout.setTitle(chamaStatmentTitle + " Statement");

        /*go back to dashboard view in MainActivity*/
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        /*add a listener for changing of offset*/
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;

                //animation for fab
                //ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                //animation for fab
                //ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        statementPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        statementPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(FullStatementModel items) {
        /*initialize the FirebaseRecyclerAdapter*/
        statementFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FullStatementModel,
                FullStatementViewHolder>(

                FullStatementModel.class,
                R.layout.fullstatement_item_layout,
                FullStatementViewHolder.class,
                mDatabase.child(STATEMENT_NODE).child(chamaStatmentTitle).child(FULL_STATEMENT_NODE)
                )
        {
            @Override
            protected void populateViewHolder(FullStatementViewHolder viewHolder,
                                              FullStatementModel model, int position) {
                viewHolder.bind(model);
            }
        };
        /*set the adapter*/
        mRecyclerView.setAdapter(statementFirebaseRecyclerAdapter);
    }

    @Override
    public void openItemActivity() {

    }

    @Override
    public void showMessage(String message, int messageType) {
        TastyToast.makeText(this, message, TastyToast.LENGTH_LONG, messageType);
    }
}
