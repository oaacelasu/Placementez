package com.oaacelasu.placementez.login.repository;

import android.app.Activity;
import android.content.Intent;

import com.facebook.login.widget.LoginButton;

/**
 * name : LoginRepository
 * author : root
 * date : 23/05/18
 * description :
 */
public interface LoginRepository {
    void signIn(String username, String password, Activity activity);
    void onCreate();
    void onCreate(Activity activity, LoginButton fbButton);
    void createAccount(Activity activity, String email, String password);
    void onStart();
    void onStop();
    void onActivityResult(final int requestCode, final int resulCode, final Intent data);
}
