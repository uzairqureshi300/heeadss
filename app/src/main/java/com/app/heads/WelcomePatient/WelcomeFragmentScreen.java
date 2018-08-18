package com.app.heads.WelcomePatient;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.VolleyError;
import com.app.heads.Helpers.UserSession;
import com.app.heads.QuestionAnswer.QuestionsAnswersFragment;
import com.app.heads.R;
import com.app.heads.Splash.SplashPresenter;
import com.app.heads.Splash.Splash_View;
import com.app.heads.Utils.No_InternetDialog;
import com.wang.avi.AVLoadingIndicatorView;

public class WelcomeFragmentScreen extends Fragment implements Splash_View {
    private TextView patientname_textview, section_textview;
    private ListView listView;
    private FragmentManager myFragmentManager;
    private FragmentTransaction myFragmentTransaction;
    WelcomePatientScreen welcomePatientScreen;
    private SplashPresenter splah_presenter;
    private View domains_view;
    private AVLoadingIndicatorView loader;
    private No_InternetDialog no_internetDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=inflater.inflate(R.layout.fragment_welcome_fragment_screen, container, false);
        setViews(v);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Domains();
    }

    private void setViews(View views) {
        welcomePatientScreen=new WelcomePatientScreen();
        patientname_textview = views.findViewById(R.id.patient_name);
        section_textview = views.findViewById(R.id.choose_section);
        listView = views.findViewById(R.id.sections_list);
        domains_view=views.findViewById(R.id.domains_view);
        loader=views.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        domains_view.setVisibility(View.GONE);
        patientname_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
        section_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        patientname_textview.setText("Hi "+new UserSession(getActivity()).getUsername());

    }

    private void Domains(){
        splah_presenter = new SplashPresenter(getActivity(),getActivity(), this,listView);
        splah_presenter.FetchRolesFromServer("domains");
    }
    @Override
    public void showProgress() {
        loader.setVisibility(View.VISIBLE);
        domains_view.setVisibility(View.GONE);

    }

    @Override
    public void hideProgress() {
        loader.setVisibility(View.GONE);
        domains_view.setVisibility(View.VISIBLE);

    }

    @Override
    public void showError(VolleyError error) {
        if(error instanceof NetworkError) {
            if (no_internetDialog == null) {
                no_internetDialog = new No_InternetDialog(getContext(), getActivity());
                no_internetDialog.showDialog();
            } else {
                no_internetDialog.showDialog();

            }
            no_internetDialog.ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    no_internetDialog.dialog.dismiss();
                    Domains();
                }
            });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            if(no_internetDialog!=null) {
                if (no_internetDialog.dialog != null && no_internetDialog.dialog.isShowing()) {
                    no_internetDialog.hideDialog();
                }
            }
        }catch(Exception ex){
            Log.e("Exception_dialog",ex.toString());
        }
    }
}
