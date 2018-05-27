package com.oaacelasu.placementez.login.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.oaacelasu.placementez.home.view.ContainerActivityViewImpl;
import com.oaacelasu.placementez.R;
import com.oaacelasu.placementez.login.presenter.LoginPresenter;
import com.oaacelasu.placementez.login.presenter.LoginPresenterImpl;

public class LoginViewImpl extends AppCompatActivity implements LoginView{

    private TextInputEditText edtUsernameLogin, edtPasswordLogin;
    private Button btnLogin;
    private ProgressBar progressBarLogin;
    private LoginPresenter presenter;
    private LoginButton btnSignInFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        edtUsernameLogin = (TextInputEditText) findViewById(R.id.edtUsernameLogin);
        edtPasswordLogin = (TextInputEditText) findViewById(R.id.edtPasswordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressBarLogin= (ProgressBar) findViewById(R.id.progressBarLogin);
        btnSignInFacebook = (LoginButton) findViewById(R.id.btnSignInFacebook);
        enableImputs();
        hidePogressBar();
        presenter = new LoginPresenterImpl(this);
        onCreate();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUsernameLogin.getText().toString().length()>0 && edtPasswordLogin.getText().toString().length() >0 ) {
                    signIn();
                }
                else {
                    Toast.makeText(LoginViewImpl.this, getResources().getString(R.id.loginErrorMessage), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void enableImputs() {
        edtUsernameLogin.setEnabled(true);
        edtPasswordLogin.setEnabled(true);
        btnLogin.setEnabled(true);
        btnSignInFacebook.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        edtUsernameLogin.setEnabled(false);
        edtPasswordLogin.setEnabled(false);
        btnLogin.setEnabled(false);
        btnSignInFacebook.setEnabled(false);
    }

    @Override
    public void showpogressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePogressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String Error) {

    }

    @Override
    public void goToCreateAcoount() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToContainerActivity() {
        Intent intent = new Intent(this, ContainerActivityViewImpl.class);
        startActivity(intent);
        Toast.makeText(LoginViewImpl.this, getResources().getString(R.string.login_succes), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToGithub() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/oaacelasu/Placementez"));
        startActivity(intent);
    }

    @Override
    public void signIn() {
        String username = edtUsernameLogin.getText().toString();
        String password = edtPasswordLogin.getText().toString();
        presenter.signIn(username, password, this);
    }

    @Override
    public void onCreate() {
        presenter.onCreate(this, btnSignInFacebook);
    }

    public void goToCreateAccount(View view) {
        this.goToCreateAcoount();
    }

    public void goToGithub(View view) {
        goToGithub();
    }

    public void goToContainerActivity(View view) {
        goToContainerActivity(); // revisar
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }
}
