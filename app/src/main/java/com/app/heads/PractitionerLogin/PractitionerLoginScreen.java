package com.app.heads.PractitionerLogin;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.VolleyError;
import com.app.heads.Models.RegionsModel;
import com.app.heads.PractitionerRegistration.PractitionerRegistrationScreen;
import com.app.heads.R;
import com.app.heads.Splash.SplashPresenter;
import com.app.heads.Splash.Splash_View;
import com.app.heads.Utils.ForgotPasswordDialog;
import com.app.heads.Utils.No_InternetDialog;
import com.app.heads.WelcomePatient.WelcomePatientScreen;

public class PractitionerLoginScreen extends AppCompatActivity implements View.OnClickListener,Splash_View {

    private TextView username_textview, password_textview,
            loginhead_textview, forgot_textview, register_textview;

    private EditText username_editext, password_editext;
    private Button login_btn;
    private SplashPresenter splah_presenter;
    private No_InternetDialog no_internetDialog;
    private ForgotPasswordDialog forgotPasswordDialog;
    RegionsModel regionsModel;
    private View loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practitioner_login_screen);
        setViews();
        setClickListeners();
    }

    private void setViews() {
        username_textview = findViewById(R.id.username_label__textview);
        password_textview = findViewById(R.id.password_label__textview);
        loginhead_textview = findViewById(R.id.login_head);
        forgot_textview = findViewById(R.id.forgot_label__textview);
        register_textview = findViewById(R.id.register_label__textview);
        loader=findViewById(R.id.loader);

        username_editext = findViewById(R.id.username_editext);
        password_editext = findViewById(R.id.password_editext);


        login_btn = findViewById(R.id.btn_login);

        username_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        loginhead_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        forgot_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
        password_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
        register_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));

        username_editext.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        password_editext.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        loader.setVisibility(View.GONE);
        login_btn.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));

        forgot_textview.setPaintFlags(forgot_textview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


    }

    private void setClickListeners() {
        login_btn.setOnClickListener(this);
        forgot_textview.setOnClickListener(this);
        register_textview.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                validate();
                break;
            case R.id.forgot_label__textview:
                if (forgotPasswordDialog == null) {
                    forgotPasswordDialog = new ForgotPasswordDialog(PractitionerLoginScreen.this, PractitionerLoginScreen.this);
                    forgotPasswordDialog.showDialog();
                } else {
                    forgotPasswordDialog.showDialog();

                }
                forgotPasswordDialog.cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgotPasswordDialog.dialog.dismiss();


                    }
                });
                forgotPasswordDialog.ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgotPasswordDialog.dialog.dismiss();
                        ForgotPasswordLink();

                    }
                });

                break;
            case R.id.register_label__textview:
                finish();
                overridePendingTransition(0, R.anim.exit_anim);

                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_anim);

    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(no_internetDialog!=null) {
                if (no_internetDialog.dialog != null && no_internetDialog.dialog.isShowing()) {
                    no_internetDialog.hideDialog();
                }
            }
            if(forgotPasswordDialog!=null) {
                if (forgotPasswordDialog.dialog != null && forgotPasswordDialog.dialog.isShowing()) {
                    forgotPasswordDialog.hideDialog();
                }
            }
        }catch(Exception ex){
            Log.e("Exception_dialog",ex.toString());
        }
    }

    @Override
    public void showProgress() {
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loader.setVisibility(View.GONE);

    }

    @Override
    public void showError(VolleyError error) {
        if(error instanceof NetworkError) {
            if (no_internetDialog == null) {
                no_internetDialog = new No_InternetDialog(PractitionerLoginScreen.this, PractitionerLoginScreen.this);
                no_internetDialog.showDialog();
            } else {
                no_internetDialog.showDialog();

            }
            no_internetDialog.ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    no_internetDialog.dialog.dismiss();

                }
            });
        }
    }
    private void validate(){

        if(username_editext.getText().toString().equals("")){
            Toast.makeText(this, "Username should not be empty", Toast.LENGTH_SHORT).show();

        }

        else if(password_editext.getText().toString().equals("")){
            Toast.makeText(this, "password should not be empty", Toast.LENGTH_SHORT).show();
        }
        else if(password_editext.getText().toString().length()<5){
            Toast.makeText(this, "Password length should be greater than 5 characters", Toast.LENGTH_SHORT).show();
        }
        else{
            Login();
        }

    }
    private void Login(){
            splah_presenter = new SplashPresenter(PractitionerLoginScreen.this, this);
            splah_presenter.Login("login", username_editext.getText().toString(), password_editext.getText().toString());
    }
    private void ForgotPasswordLink(){

        if(!forgotPasswordDialog.instruction_textview.getText().toString().contains("@") || !forgotPasswordDialog.instruction_textview.getText().toString().contains(".")){
            Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
        }
        else if(forgotPasswordDialog.instruction_textview.getText().toString().equals("")){
            Toast.makeText(this, "Email should not be empty", Toast.LENGTH_SHORT).show();

        }
        else {
            splah_presenter = new SplashPresenter(PractitionerLoginScreen.this, this);
            splah_presenter.Forgot_Password("password/email", forgotPasswordDialog.instruction_textview.getText().toString());
        }

        }

}
