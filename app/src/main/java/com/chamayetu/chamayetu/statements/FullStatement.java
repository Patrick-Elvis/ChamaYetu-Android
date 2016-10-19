package com.chamayetu.chamayetu.statements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import com.chamayetu.chamayetu.R;

import butterknife.BindView;

import static com.chamayetu.chamayetu.utils.Contract.FULL_STATEMENT_CHOICE;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 12/10/16.
 * Description: Display a full statement to the user
 */

public class FullStatement extends AppCompatActivity{
    @BindView(R.id.full_statement_collapsingtoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.full_statement_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.full_statement_toolbar) Toolbar mToolbar;
    @BindView(R.id.full_statement_cardview) CardView mCardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullstatement_layout);
        Bundle receiveUserChoice = getIntent().getExtras();

        //extract the data and store for processing
        CharSequence statementPeriod = receiveUserChoice.getCharSequence(FULL_STATEMENT_CHOICE);

    }
}
