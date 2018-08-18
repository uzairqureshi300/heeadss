package com.app.heads.PractitionerRegistration;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
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
import com.app.heads.Adapters.Spinner_Adapter;
import com.app.heads.Models.RegionsModel;
import com.app.heads.PatientDetails.PatientDetailsScreen;
import com.app.heads.PractitionerLogin.PractitionerLoginScreen;
import com.app.heads.R;
import com.app.heads.Splash.ISplashPresenter;
import com.app.heads.Splash.SplashPresenter;
import com.app.heads.Splash.SplashScreen;
import com.app.heads.Splash.Splash_View;
import com.app.heads.Utils.No_InternetDialog;

public class PractitionerRegistrationScreen extends AppCompatActivity implements View.OnClickListener,Splash_View,Spinner.OnItemSelectedListener {

    private TextView fname_textview,lname_textview,email_textview,password_textview,
                    region_textview,registrationhead_textview,already_textview,login_textview;

    private EditText fname_editext,lname_editext,email_editext,password_editext;
    private Spinner region_spinner;
    private Button register_btn;
    private SplashPresenter splah_presenter;
    private No_InternetDialog no_internetDialog;
    RegionsModel regionsModel;
    private View loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practitioner_registration_screen);
        setViews();
        setClickListeners();
        FetchRegions();
    }


    private void setViews() {
        fname_textview = findViewById(R.id.name_label__textview);
        lname_textview = findViewById(R.id.lname_label__textview);
        email_textview = findViewById(R.id.email_label__textview);
        password_textview = findViewById(R.id.password_label__textview);
        region_textview = findViewById(R.id.region_label__textview);
        registrationhead_textview=findViewById(R.id.registration_head);
        already_textview=findViewById(R.id.already_label__textview);
        login_textview = findViewById(R.id.login_label__textview);
        loader=findViewById(R.id.loader);

        fname_editext = findViewById(R.id.fname_editext);
        lname_editext = findViewById(R.id.lname_editext);
        email_editext = findViewById(R.id.email_editext);
        password_editext = findViewById(R.id.password_editext);

        region_spinner = findViewById(R.id.spinner_region);

        register_btn = findViewById(R.id.btn_register);

        fname_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        lname_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        email_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
        password_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
        region_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
        registrationhead_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
        already_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        login_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));

        fname_editext.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        lname_editext.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        email_editext.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        password_editext.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        loader.setVisibility(View.GONE);

        register_btn.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));

        login_textview.setPaintFlags(login_textview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        region_spinner.setOnItemSelectedListener(this);
    }

    private void setClickListeners() {
        register_btn.setOnClickListener(this);
        login_textview.setOnClickListener(this);
    }
    private void FetchRegions(){
        Spinner_Adapter spinner_adapter = new Spinner_Adapter(PractitionerRegistrationScreen.this, PractitionerRegistratopnSingleton.getInstance().getRegions());
        region_spinner.setAdapter(spinner_adapter);
    }
    private void validate(){

        if(fname_editext.getText().toString().equals("")){
            Toast.makeText(this, "First Name should not be empty", Toast.LENGTH_SHORT).show();

        }
        else if(lname_editext.getText().toString().equals("")){
            Toast.makeText(this, "Last Name should not be empty", Toast.LENGTH_SHORT).show();
        }
        else if(email_editext.getText().toString().equals("")){
            Toast.makeText(this, "Email should not be empty", Toast.LENGTH_SHORT).show();
        }
        else if(!email_editext.getText().toString().contains("@") || !email_editext.getText().toString().contains(".")){
            Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
        }
        else if(email_editext.getText().toString().equals("")){
            Toast.makeText(this, "email should not be empty", Toast.LENGTH_SHORT).show();
        }
        else if(password_editext.getText().toString().equals("")){
            Toast.makeText(this, "password should not be empty", Toast.LENGTH_SHORT).show();
        }
        else if(password_editext.getText().toString().length()<5){
            Toast.makeText(this, "Password length should be greater than 5 characters", Toast.LENGTH_SHORT).show();
        }
        else{
            Register();
        }

    }

    private void Register(){
        if(regionsModel!=null) {
            splah_presenter = new SplashPresenter(PractitionerRegistrationScreen.this, this, region_spinner);
            splah_presenter.Register("registration", fname_editext.getText().toString(), lname_editext.getText().toString(),
                    email_editext.getText().toString(), password_editext.getText().toString(), regionsModel.getId()+"");
        }
        else{
            FetchRegions();
        }
        }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                validate();
                break;
            case R.id.login_label__textview:
                startActivity(new Intent(PractitionerRegistrationScreen.this, PractitionerLoginScreen.class));
                overridePendingTransition(R.anim.enter_anim,0);

                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_anim);

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
                no_internetDialog = new No_InternetDialog(PractitionerRegistrationScreen.this, PractitionerRegistrationScreen.this);
                no_internetDialog.showDialog();
            } else {
                no_internetDialog.showDialog();

            }
            no_internetDialog.ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    no_internetDialog.dialog.dismiss();
                    FetchRegions();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
   try{
       if(no_internetDialog.dialog!=null && no_internetDialog.dialog.isShowing()){
           no_internetDialog.hideDialog();
       }
   }catch(Exception ex){
       Log.e("Exception_dialog",ex.toString());
   }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);

        regionsModel= PractitionerRegistratopnSingleton.getInstance().getRegions().get(i);

        Log.e("ddddd",regionsModel.getName());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
