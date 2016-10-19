package com.chamayetu.chamayetu.statements;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description:
 */

public interface StatementPresenter {
    /**when activity is resumed*/
    void onResume();

    /** performs actions on user click, passes the position to implementer*/
    void onItemClicked(int position);

    /**destroys the view in case of errors*/
    void onDestroy();
}
