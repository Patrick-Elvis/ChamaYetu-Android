package com.chamayetu.chamayetu.statements;

import com.chamayetu.chamayetu.models.FullStatementModel;

import java.util.List;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description:
 */

public interface FindItemsInteractor {
    interface OnFinishedListener {
        /**when finished loading set the items to recycler*/
        void onFinished(FullStatementModel items);
    }

    void findItems(OnFinishedListener listener);
}
