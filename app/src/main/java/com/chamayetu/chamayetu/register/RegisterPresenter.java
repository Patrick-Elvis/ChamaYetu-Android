package com.chamayetu.chamayetu.register;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 15/10/16.
 * Description: Presenter implementation calls the interactor(use case handler) to get results from business/domain layer
 */

public interface RegisterPresenter{

    void validateCredentials(String email, long phoneNumber, String password, String retypePassword);

    void onDestroy();
}
