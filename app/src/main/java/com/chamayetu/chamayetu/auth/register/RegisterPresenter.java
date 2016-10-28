package com.chamayetu.chamayetu.auth.register;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.register
 * Created by lusinabrian on 15/10/16.
 * Description: Presenter implementation calls the interactor(use case handler) to get results from business/domain layer
 */

public interface RegisterPresenter{

    /**pass these parameters to the interactor for validation*/
    void validateCredentials(String name, String email, long phoneNumber, String password, String retypePassword);

    /**pass the chama name to the interactor*/
    void validateChamaCredentials(String chamaName,int memberNo, String bankName,int accountNo);

    void onDestroy();
}
