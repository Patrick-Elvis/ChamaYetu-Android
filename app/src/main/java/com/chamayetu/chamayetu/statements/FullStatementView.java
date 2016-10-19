package com.chamayetu.chamayetu.statements;

import java.util.List;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.statements
 * Created by lusinabrian on 19/10/16.
 * Description: View of the Full statement view*/

public interface FullStatementView {
    void showProgress();

    void hideProgress();

    void setItems(List<String> items);

    void showMessage(String message);
}
