package com.app.heads.Splash;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.app.heads.Helpers.UserSession;
import com.app.heads.Models.Data;
import com.app.heads.PatientDetails.PatientDetailsScreen;
import com.app.heads.PractitionerRegistration.PractitionerRegistrationScreen;
import com.app.heads.R;
import com.app.heads.WelcomePatient.WelcomePatientScreen;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener ,ISplashPresenter,Splash_View{
    private Button button_patient, button_practitioner;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private View splash_view;
    SweetAlertDialog SweetAlertDialog;
    private SplashPresenter splah_presenter;
    private ImageView splah_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
           setThread();
    }
    private void setThread(){
        Thread thread=null;

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    setViews();

                    if (new UserSession(getApplicationContext()).getUser_id() != null && !new UserSession(getApplicationContext()).getUser_id().equals("0")) {
                        Intent i = new Intent(getApplicationContext(), WelcomePatientScreen.class);
                        startActivity(i);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        }
                    } else {
                        FetchRoles();
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception_thread",e.toString());
                }
            }

        };



        thread.start();


        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }
    private void setViews() {
        button_patient = findViewById(R.id.btn_patient);
        button_practitioner = findViewById(R.id.btn_practitioner);
        avLoadingIndicatorView=findViewById(R.id.loader);
        splash_view=findViewById(R.id.splash_view);
         splah_image=findViewById(R.id.splash_img);
         splah_image.setImageResource(R.drawable.splash_bg);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        splash_view.setVisibility(View.GONE);
        button_patient.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        button_practitioner.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        button_practitioner.setOnClickListener(this);
        button_patient.setOnClickListener(this);

    }

    private void FetchRoles(){
        splah_presenter=new SplashPresenter(SplashScreen.this,this,this);
        splah_presenter.FetchRolesFromServer("roles");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_patient:
                new UserSession(getApplicationContext()).setRole_id(SplashSingleton.getInstance().getRoles().get(3).getId()+"");
                new UserSession(getApplicationContext()).setRole_id(SplashSingleton.getInstance().getRoles().get(3).getName());

                startActivity(new Intent(SplashScreen.this, PatientDetailsScreen.class));
                overridePendingTransition(R.anim.enter_anim,0);
                break;
            case R.id.btn_practitioner:
                new UserSession(getApplicationContext()).setRole_id(SplashSingleton.getInstance().getRoles().get(2).getId()+"");
                new UserSession(getApplicationContext()).setRole_id(SplashSingleton.getInstance().getRoles().get(2).getName());

                startActivity(new Intent(SplashScreen.this, PractitionerRegistrationScreen.class));
                overridePendingTransition(R.anim.enter_anim,0);
                break;

        }
    }

    @Override
    public void getList(ArrayList<Data> data) {
        Log.e("Size",data.size()+"");
    }

    @Override
    public void showProgress() {
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        splash_view.setVisibility(View.GONE);

    }

    @Override
    public void hideProgress() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        splash_view.setVisibility(View.VISIBLE);

    }

    @Override
    public void showError(VolleyError error) {
        splash_view.setVisibility(View.GONE);

        SweetAlertDialog= new SweetAlertDialog(SplashScreen.this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                .setTitleText("No Internet")
                .setCustomImage(R.mipmap.ic_launcher)
                .setConfirmText("Try Again!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog

                                .setConfirmText("Wait");

                        splah_presenter.FetchRolesFromServer("roles");
                                ;
                        sDialog.dismiss();
                    }
                })
                ;
        SweetAlertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(SweetAlertDialog!=null && SweetAlertDialog.isShowing()){
            SweetAlertDialog.dismiss();
        }
    }
}
