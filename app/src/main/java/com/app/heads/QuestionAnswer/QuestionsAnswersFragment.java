package com.app.heads.QuestionAnswer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.NetworkError;
import com.android.volley.VolleyError;
import com.app.heads.Adapters.BaseAdap_questions;
import com.app.heads.Helpers.UserSession;
import com.app.heads.R;
import com.app.heads.Splash.SplashPresenter;
import com.app.heads.Splash.Splash_View;
import com.app.heads.Utils.No_InternetDialog;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by uzair qureshi on 7/30/2018.
 */

public class QuestionsAnswersFragment extends Fragment implements Splash_View {
    ListView listView;
    private String domain_id;
    private SplashPresenter splah_presenter;
    private View domains_view;

    private No_InternetDialog no_internetDialog;
    private View loader, questions_view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.patientsquestions_fragment_screen, container, false);
        setViews(v);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        domain_id = getArguments().getString("domain_id");
        Questions();
    }

    private void setViews(View views) {

        listView = views.findViewById(R.id.sections_list);
        loader = views.findViewById(R.id.loader);
        questions_view = views.findViewById(R.id.questions_view);
        loader.setVisibility(View.VISIBLE);
        questions_view.setVisibility(View.GONE);

    }

    private void Questions() {
        splah_presenter = new SplashPresenter(getActivity(), getActivity(), this, listView);
        splah_presenter.getQuestions("questions", domain_id);
    }

    @Override
    public void showProgress() {
        loader.setVisibility(View.VISIBLE);
        questions_view.setVisibility(View.GONE);

    }

    @Override
    public void hideProgress() {
        loader.setVisibility(View.GONE);
        questions_view.setVisibility(View.VISIBLE);

    }

    @Override
    public void showError(VolleyError error) {
        if (error instanceof NetworkError) {
            questions_view.setVisibility(View.GONE);
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


                }
            });
        }
    }
}
