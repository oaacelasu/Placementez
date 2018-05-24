package com.oaacelasu.placementez.login.presenter;

import android.app.Activity;
import android.content.Intent;

import com.facebook.login.widget.LoginButton;
import com.oaacelasu.placementez.login.interactor.LoginInteractor;
import com.oaacelasu.placementez.login.interactor.LoginInteractorImpl;
import com.oaacelasu.placementez.login.view.CreateAccount;
import com.oaacelasu.placementez.login.view.LoginView;

/**
 * name : LoginPresenterImpl
 * author : root
 * date : 23/05/18
 * description :
 */
public class LoginPresenterImpl implements LoginPresenter{


    private LoginView loginView;
    private CreateAccount createAccount;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this);
    }

    public LoginPresenterImpl(CreateAccount createAccount) {
        this.createAccount = createAccount;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void signIn(String username, String password, Activity activity) {
        loginView.disableInputs();
        loginView.showpogressBar();
        interactor.signIn(username, password, activity);
    }

    @Override
    public void loginSucces() {
        loginView.goToContainerActivity();

        loginView.hidePogressBar();
    }

    @Override
    public void loginError(String error) {
        loginView.enableImputs();
        loginView.hidePogressBar();
        loginView.loginError(error);

    }

    @Override
    public void onCreate() {
        interactor.onCreate();
    }

    @Override
    public void onCreate(Activity activity, LoginButton fbButton) {
        interactor.onCreate(activity, fbButton);
    }

    @Override
    public void createAccount(Activity activity, String email, String password) {
        interactor.createAccount(activity, email, password);
    }

    @Override
    public void createAccountSucces() {
        createAccount.createAccountSucces();
    }

    @Override
    public void createAccountError() {
        createAccount.createAccountError();
    }

    @Override
    public void onStart() {
        interactor.onStart();
    }

    @Override
    public void onStop() {
        interactor.onStop();
    }

    @Override
    public void goHome() {
        loginView.goToContainerActivity();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resulCode, final Intent data) {
        interactor.onActivityResult(requestCode, resulCode, data);
    }

}
