package com.oaacelasu.placementez.login.interactor;

import android.app.Activity;
import android.content.Intent;

import com.facebook.login.widget.LoginButton;
import com.oaacelasu.placementez.login.presenter.LoginPresenter;
import com.oaacelasu.placementez.login.repository.LoginRepository;
import com.oaacelasu.placementez.login.repository.LoginRepositoryImpl;

/**
 * name : LoginInteractorImpl
 * author : root
 * date : 23/05/18
 * description :
 */
public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter presenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
        repository = new LoginRepositoryImpl(presenter);
    }

    @Override
    public void signIn(String username, String password, Activity activity) {
        repository.signIn(username, password, activity);

    }

    @Override
    public void onCreate() {
        repository.onCreate();
    }

    @Override
    public void onCreate(Activity activity, LoginButton fbButton) {
        repository.onCreate(activity, fbButton);
    }

    @Override
    public void createAccount(Activity activity, String email, String password) {
        repository.createAccount(activity, email, password);
    }

    @Override
    public void onStart() {
        repository.onStart();
    }

    @Override
    public void onStop() {
        repository.onStop();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resulCode, final Intent data) {
        repository.onActivityResult(requestCode, resulCode, data);
    }
}
