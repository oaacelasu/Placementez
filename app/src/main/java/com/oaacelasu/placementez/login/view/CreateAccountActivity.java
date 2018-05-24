package com.oaacelasu.placementez.login.view;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.oaacelasu.placementez.R;
import com.oaacelasu.placementez.login.presenter.LoginPresenter;
import com.oaacelasu.placementez.login.presenter.LoginPresenterImpl;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccount{


    private LoginPresenter presenter;

    private Button btnJoinUs;
    private TextInputEditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.create_account_title_toolbar), true);

        btnJoinUs = (Button) findViewById(R.id.btnJoinUs);
        edtEmail = (TextInputEditText) findViewById(R.id.edtEmailCreateAccount);
        edtPassword = (TextInputEditText) findViewById(R.id.edtPasswordCreateAccount);

        presenter = new LoginPresenterImpl(this);
        presenter.onCreate();

        btnJoinUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                        createAccount();
                    }
                });
            }

    public void createAccount() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        presenter.createAccount(this, email, password);
    }

    @Override
    public void createAccountSucces() {
        Toast.makeText(CreateAccountActivity.this, getResources().getText(R.string.create_account_succes), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createAccountError() {
        Toast.makeText(CreateAccountActivity.this, getResources().getText(R.string.create_account_fail), Toast.LENGTH_SHORT).show();
    }


    private void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void goToHome(View view) {
        goToHome();
    }



    public void goToHome() {
        Intent intent = new Intent(this, LoginViewImpl.class);
        startActivity(intent);
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
}
