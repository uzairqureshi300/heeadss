package com.app.heads.PatientDetails;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.heads.Adapters.Spinner_Adapter;
import com.app.heads.Helpers.UserSession;
import com.app.heads.Models.RegionsModel;
import com.app.heads.PractitionerLogin.PractitionerLoginScreen;
import com.app.heads.PractitionerRegistration.PractitionerRegistrationScreen;
import com.app.heads.PractitionerRegistration.PractitionerRegistratopnSingleton;
import com.app.heads.R;
import com.app.heads.Splash.SplashPresenter;
import com.app.heads.WelcomePatient.WelcomePatientScreen;

import java.util.ArrayList;
import java.util.List;

public class PatientDetailsScreen extends AppCompatActivity implements View.OnClickListener,Spinner.OnItemSelectedListener{
    private EditText editText_name, editText_age;
    private TextView textView_name, textView_age, textView_region, textView_next,textView_welcome,textView_getstarted;
    private Spinner spinner_region;
    RegionsModel regionsModel;
    private UserSession userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_screen);
        setViews();
        FetchRegions();

    }

        private void setViews() {
            editText_name = findViewById(R.id.name_editext);
            editText_age = findViewById(R.id.age_editext);
            textView_name = findViewById(R.id.name_label__textview);
            textView_age = findViewById(R.id.age_label__textview);
            textView_region = findViewById(R.id.region_label__textview);
            textView_next = findViewById(R.id.next_label__textview);
            textView_welcome=findViewById(R.id.welcome_textview);
            textView_getstarted=findViewById(R.id.getstarted_textview);
            spinner_region = findViewById(R.id.spinner_region);

            editText_name.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
            editText_age.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
            textView_name.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
            textView_age.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
            textView_region.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
            textView_next.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
            textView_welcome.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
            textView_getstarted.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));

            Spannable spannable = new SpannableString(getResources().getString(R.string.welcome));
            spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 9,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 25, 35,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textView_welcome.setText(spannable);
            textView_next.setOnClickListener(this);

            spinner_region.setOnItemSelectedListener(this);
            userSession=new UserSession(PatientDetailsScreen.this);
        }

    private void FetchRegions(){
        Spinner_Adapter spinner_adapter = new Spinner_Adapter(PatientDetailsScreen.this, PractitionerRegistratopnSingleton.getInstance().getRegions());
        spinner_region.setAdapter(spinner_adapter);
    }

    private void validate(){

        if(editText_name.getText().toString().equals("")){
            Toast.makeText(this, "Name should not be empty", Toast.LENGTH_SHORT).show();

        }
        else if(editText_age.getText().toString().equals("")){
            Toast.makeText(this, "Age should not be empty", Toast.LENGTH_SHORT).show();
        }

        else{
            Register();
        }

    }



    private void Register(){
        if(regionsModel!=null) {
       //     userSession.setUserid(jsonObject.getJSONObject("data").getString("id"));
            userSession.setUsername(editText_name.getText().toString());
            Intent intent = new Intent(PatientDetailsScreen.this, WelcomePatientScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();


        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_anim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_label__textview:
                validate();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        regionsModel= PractitionerRegistratopnSingleton.getInstance().getRegions().get(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
