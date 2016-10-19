package com.chamayetu.chamayetu.statements;

import java.util.List;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description: View of the Full statement view*/

interface FullStatementView {
    /**show progress to user when fetching items for recyclerview*/
    void showProgress();

    /**hide progress when loading is complete*/
    void hideProgress();

    /**set the items to the recyclerView*/
    void setItems(List<String> items);

    void openItemActivity();

    /**display message to user if error occurs*/
    void showMessage(String message, int messageType);
}
