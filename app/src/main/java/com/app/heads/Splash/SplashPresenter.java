package com.app.heads.Splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.app.heads.Adapters.BaseAdap_questions;
import com.app.heads.Adapters.DomainBaseAdapter;
import com.app.heads.Adapters.Spinner_Adapter;
import com.app.heads.Helpers.Api_Requests;
import com.app.heads.Helpers.UserSession;
import com.app.heads.Models.Data;
import com.app.heads.Models.DomainModel;
import com.app.heads.Models.QuestionsModel;
import com.app.heads.Models.RegionsModel;
import com.app.heads.PractitionerRegistration.PractitionerRegistratopnSingleton;
import com.app.heads.QuestionAnswer.QuestionsAnswersFragment;
import com.app.heads.WelcomePatient.WelcomePatientScreen;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by warisali on 19/01/2018.
 */

public class SplashPresenter implements IResult {

    Context context;
    Api_Requests api_requests;
    Splash_View splash_view;
    ISplashPresenter iSplashPresenter;
    String message_type, email, password;
    Spinner spinner;
    ListView listView;
    Fragment fragment;
    Activity activity;

    public SplashPresenter(Context context, Splash_View splash_view, ISplashPresenter iSplashPresenter) {
        this.context = context;
        this.splash_view = splash_view;
        this.api_requests = new Api_Requests(this, context);
        this.iSplashPresenter = iSplashPresenter;
    }

    public SplashPresenter(Context context, Splash_View splash_view, Spinner spinner) {
        this.context = context;
        this.splash_view = splash_view;
        this.api_requests = new Api_Requests(this, context);
        this.iSplashPresenter = iSplashPresenter;
        this.spinner = spinner;
    }

    public SplashPresenter(Context context, Splash_View splash_view) {
        this.context = context;
        this.splash_view = splash_view;
        this.api_requests = new Api_Requests(this, context);
    }

    public SplashPresenter(Context context, Activity activity, Splash_View splash_view, ListView listView) {
        this.context = context;
        this.splash_view = splash_view;
        this.api_requests = new Api_Requests(this, context);
        this.listView = listView;
        this.activity = activity;
    }


    public void FetchRolesFromServer(String method) {
        message_type = method;
        splash_view.showProgress();
        api_requests.getRoles(method);
    }

    public void FetchRegionsFromServer(String method) {
        message_type = method;

        api_requests.getRegions(method);
    }

    public void getQuestions(String method, String domain_id) {
        message_type = method;
        splash_view.showProgress();
        api_requests.FetchQuestions(method, domain_id, new UserSession(context).getRole_id());
    }

    public void Login(String method, String username, String password) {
        message_type = method;
        splash_view.showProgress();
        api_requests.LoginUser(method, username, password);
    }

    public void Forgot_Password(String method, String email) {
        message_type = method;
        splash_view.showProgress();
        api_requests.ForgotPassword(method, email);
    }

    public void Register(String method, final String first_name, final String last_name, final String email, final String password, final String region_id) {
        message_type = method;
        this.email = email;
        this.password = password;

        splash_view.showProgress();
        api_requests.RegisterUser(method, first_name, last_name, email, password, region_id);
    }

    @Override
    public void notifySuccess(String response) {
        splash_view.hideProgress();

        if (response != null) {
            switch (message_type) {
                case "roles":

                    Log.e("response_roles", response);

                    setRoles(response);

                    break;
                case "regions":
                    splash_view.hideProgress();

                    Log.e("response_regions", response);
                    setregions(response);
                    break;
                case "registration":
                    Log.e("response_registration", response);
                    setRegister(response);
                    break;
                case "login":
                    splash_view.hideProgress();

                    Log.e("response_login", response);
                    setLogin(response);
                case "password/email":
                    Log.e("response_password/email", response);
                    setForgotPassword(response);
                    break;
                case "domains":
                    Log.e("response_domains", response);
                    setDomains(response);
                    break;
                case "questions":
                    Log.e("response_questions", response);
                    setQuestions(response);
                    //       setDomains(response);
                    break;


            }

        }
    }

    @Override
    public void notifyError(VolleyError error) {
        splash_view.hideProgress();
        splash_view.showError(error);
        Log.e("error_response", error.toString());
    }

    private void setRoles(String response) {

        try {
            ArrayList<Data> data = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("success")) {

                Gson gson = new Gson();

                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int a = 0; a < jsonArray.length(); a++) {
                    Data datamodel = new Data();
                    JSONObject object = jsonArray.getJSONObject(a);

                    datamodel.setId(object.getInt("id"));
                    datamodel.setName(object.getString("name"));

                    data.add(datamodel);
                }
                SplashSingleton.getInstance().setRoles(data);
                iSplashPresenter.getList(data);
                FetchRegionsFromServer("regions");
            }
        } catch (Exception ex) {
            Log.e("Roles_Exception", ex.toString());

        }


    }

    private void setregions(String response) {
        try {
            ArrayList<RegionsModel> regionsModels = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("status").equals("success")) {
                Gson gson = new Gson();

                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int a = 0; a < jsonArray.length(); a++) {
                    RegionsModel datamodel = new RegionsModel();
                    JSONObject object = jsonArray.getJSONObject(a);

                    datamodel.setId(object.getInt("id"));
                    datamodel.setName(object.getString("name"));
                    datamodel.setCreatedAt(object.getString("created_at"));
                    datamodel.setUpdatedAt(object.getString("updated_at"));

                    regionsModels.add(datamodel);
                }
                PractitionerRegistratopnSingleton.getInstance().setRegions(regionsModels);

            } else {
                Toast.makeText(context, "no region found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.e("Reions_Exception", ex.toString());
        }
    }

    private void setRegister(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("success")) {
//                Intent intent=new Intent(context, PractitionerLoginScreen.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
                Login("login", email, password);
            }
            else if(jsonObject.getString("status").equals("error")){
                Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();

            }

            else {
                Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception es) {
            Log.e("Exception_registration", es.toString());

        }
    }

    private void setLogin(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("success")) {
                UserSession userSession = new UserSession(context);
                userSession.setUserid(jsonObject.getJSONObject("data").getString("id"));
                userSession.setUsername(jsonObject.getJSONObject("data").getString("email"));
                userSession.setEmail(jsonObject.getJSONObject("data").getString("email"));

                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("roles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    userSession.setUserType(object.getString("name"));
                    userSession.setRole_id(object.getString("id"));
                    userSession.setRole_name(object.getString("name"));


                }
                Intent intent = new Intent(context, WelcomePatientScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);


            }
            else if(jsonObject.getString("status").equals("fail")){
                Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();

            }

            else {
                Toast.makeText(context, "Failed to login", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception es) {
            Log.e("Exception_login", es.toString());

        }
    }

    private void setForgotPassword(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("success")) {
                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            }
        } catch (Exception ex) {
            Log.e("ForgotPassword_Ex", ex.toString());
        }
    }

    private void setDomains(String response) {
        try {
            final ArrayList<DomainModel> domainModels = new ArrayList<>();
            DomainBaseAdapter baseAdap = new DomainBaseAdapter(context, domainModels);
            listView.setAdapter(baseAdap);

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("success")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    DomainModel domainModel = new DomainModel();
                    JSONObject object = jsonArray.getJSONObject(i);

                    domainModel.setId(object.getInt("id"));
                    domainModel.setName(object.getString("name"));
                    domainModel.setSort(object.getInt("sort"));
                    domainModel.setCreatedAt(object.getString("created_at"));
                    domainModel.setUpdatedAt(object.getString("updated_at"));
                    domainModels.add(domainModel);

                }
                baseAdap.setList(domainModels);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        fragment = new QuestionsAnswersFragment();
                        Bundle args = new Bundle();
                        args.putString("domain_id", domainModels.get(i).getId() + "");
                        fragment.setArguments(args);
                        ((WelcomePatientScreen) activity).CreateContainer(fragment, "questions");
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("Domain_Ex ", ex.toString());
        }
    }

    private void setQuestions(String response) {
        try {
            ArrayList<QuestionsModel> questionsModels=new ArrayList<>();
            BaseAdap_questions baseAdap_questions=new BaseAdap_questions(context,questionsModels);
           listView.setAdapter(baseAdap_questions);
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getString("status").equals("success")){
               JSONArray jsonArray=jsonObject.getJSONArray("data");
               for(int i=0;i<jsonArray.length();i++){
                   QuestionsModel questionsModel=new QuestionsModel();
                   JSONObject object=jsonArray.getJSONObject(i);
                    questionsModel.setId(object.getInt("id"));
                    questionsModel.setQuestion(object.getString("question"));
                   questionsModel.setSort(object.getInt("sort"));
                   questionsModel.setCreatedAt(object.getString("created_at"));
                   questionsModel.setUpdatedAt(object.getString("updated_at"));
                    questionsModels.add(questionsModel);
               }
               baseAdap_questions.setList(questionsModels);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
            }
        } catch (Exception e) {
            Log.e("Questions_Exception", e.toString());

        }
    }
}
