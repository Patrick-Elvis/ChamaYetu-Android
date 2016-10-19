package com.chamayetu.chamayetu.statements;

import com.chamayetu.chamayetu.models.FullStatementModel;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description:
 */

interface FindItemsInteractor {
    interface OnFinishedListener {
        /**when finished loading set the items to recycler*/
        void onFinished(FullStatementModel items);
    }

    void findItems(OnFinishedListener listener);
}
