package com.oaacelasu.placementez.login.presenter;

import android.app.Activity;
import android.content.Intent;

import com.facebook.login.widget.LoginButton;

/**
 * name : LoginPresenter
 * author : root
 * date : 23/05/18
 * description :
 */
public interface LoginPresenter {

    void signIn(String username, String password, Activity activity); //Interactor
    void loginSucces();
    void loginError(String error);
    void onCreate(); //Interactor
    void onCreate(Activity activity, LoginButton fbButton); //Interactor
    void createAccount(Activity activity, String email, String password); //Interactor
    void createAccountSucces();
    void createAccountError();
    void onStart(); //Interactor
    void onStop(); //Interactor
    void goHome();
    void onActivityResult(final int requestCode, final int resulCode, final Intent data); //Interactor
}
