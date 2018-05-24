package com.oaacelasu.placementez.login.repository;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oaacelasu.placementez.login.presenter.LoginPresenter;
import java.util.Arrays;

/**
 * name : LoginRepositoryImpl
 * author : root
 * date : 23/05/18
 * description :
 */
public class LoginRepositoryImpl implements LoginRepository {

    LoginPresenter presenter;


    private static final String TAG = "CreateAccountActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private CallbackManager callbackManager;

    public LoginRepositoryImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void signIn(String username, String password, Activity activity) {

        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    presenter.loginSucces();
                }else{
                    presenter.loginError("Ocurrió un error");
                }
            }
        });
    }

    @Override
    public void onCreate() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "Usuario logeado" + firebaseUser.getEmail());
                }else{
                    Log.w(TAG, "Usuario no logeado");
                }
            }
        };
    }

    @Override
    public void onCreate(final Activity activity, LoginButton fbButton) {
        onCreate();
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        fbButton.setReadPermissions(Arrays.asList("email"));
        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.w(TAG, "Facebook login succes token: " + loginResult.getAccessToken().getApplicationId());
                signInFacebookFirebase(loginResult.getAccessToken(), activity);
            }

            @Override
            public void onCancel() {
                Log.w(TAG, "Facebook login cancelado ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "Facebook login error: " + error.toString());
                error.printStackTrace();
            }
        });

    }

    private void signInFacebookFirebase(AccessToken accessToken, Activity activity) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    presenter.goHome();
                }else{
                    presenter.loginError("Ocurrió un error");
                }
            }
        });
    }

    @Override
    public void createAccount(Activity activity, String email, String password) {


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    presenter.createAccountSucces();
                }else{
                    presenter.createAccountError();

                }
            }
        });
    }

    @Override
    public void onStart() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resulCode, final Intent data) {
        callbackManager.onActivityResult(requestCode, resulCode, data);
    }


}
