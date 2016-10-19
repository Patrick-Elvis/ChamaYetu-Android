package com.chamayetu.chamayetu.statements;

import java.util.List;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description:
 */

public interface FindItemsInteractor {
    interface OnFinishedListener {
        /***/
        void onFinished(List<String> items);
    }

    void findItems(OnFinishedListener listener);
}
