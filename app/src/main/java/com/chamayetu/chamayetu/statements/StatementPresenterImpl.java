package com.chamayetu.chamayetu.statements;

import java.util.List;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description:
 */

public class StatementPresenterImpl implements StatementPresenter, FindItemsInteractor.OnFinishedListener {

    private FullStatementView fullStatementView;
    private FindItemsInteractor findItemsInteractor;

    public StatementPresenterImpl(FullStatementView fullStatementView, FindItemsInteractor findItemsInteractor){
        this.fullStatementView = fullStatementView;
        this.findItemsInteractor = findItemsInteractor;
    }

    @Override
    public void onResume() {
        if (fullStatementView != null) {
            fullStatementView.showProgress();
        }
        /*find the items for the recycler view*/
        findItemsInteractor.findItems(this);
    }

    @Override
    public void onItemClicked(int position) {
        if (fullStatementView != null) {
            /*open more details of the item*/
            fullStatementView.openItemActivity();
        }
    }

    @Override
    public void onDestroy() {
        fullStatementView = null;
    }

    public FullStatementView getFullStatementView(){
        return fullStatementView;
    }

    @Override
    public void onFinished(List<String> items) {
        if (fullStatementView != null) {
            fullStatementView.setItems(items);
            fullStatementView.hideProgress();
        }
    }
}
