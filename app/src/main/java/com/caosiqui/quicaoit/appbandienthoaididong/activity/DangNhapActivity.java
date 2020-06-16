package com.caosiqui.quicaoit.appbandienthoaididong.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.caosiqui.quicaoit.appbandienthoaididong.R;
import com.caosiqui.quicaoit.appbandienthoaididong.api.ApiHelper;
import com.caosiqui.quicaoit.appbandienthoaididong.model.Taikhoan;
import com.caosiqui.quicaoit.appbandienthoaididong.ultil.CheckConnection;
import com.caosiqui.quicaoit.appbandienthoaididong.ultil.Server;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.widget.Toast.LENGTH_SHORT;

public class DangNhapActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    Button btnRegister;

    public static  int idTaikoan =-1;

    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();



        setContentView(R.layout.activity_dang_nhap);

        initIteminActivity();
        EventButton();



        //xin quyen facebok
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
       // loginButton.setFragment();
        setLoginFacebook();

        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
                Log.e("HH",currentProfile.getName());
                Log.e("HH",currentProfile.getId());
                Toast.makeText(getBaseContext(),"Đăng nhập thành công", LENGTH_SHORT).show();
                Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                startActivity(intent);

            }
        };
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode,data);
    }

    void setLoginFacebook(){
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //lấy dữ liệu về
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Toast.makeText(getBaseContext(),"Đăng nhập thành công", LENGTH_SHORT).show();
                        Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,first_nane");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                //đăng nhập thất bại
            }
        });
    }



    //    Ánh xạ element
    void initIteminActivity(){
        loginButton = (LoginButton) findViewById(R.id.login_button);
        txtUsername = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }

//    event login
    private void EventButton() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                    Intent intent = new Intent(DangNhapActivity.this,DangKyActivity.class);
                    startActivity(intent);
                }else {
                    CheckConnection.ShoToast_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối");
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().length()==0 || txtPassword.getText().length()==0)
                    Toast.makeText(getBaseContext(),getString(R.string.error_empty), Toast.LENGTH_SHORT).show();
                else{
                    if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                        doLogin();
                    }else {
                        CheckConnection.ShoToast_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối");
                    }
                }
            }
        });
    }


    private MaterialDialog  showProgessbar(){

//        return new MaterialDialog.Builder(this)
//               .title("Huong")
//               .content("Dep tra")
//               .positiveText("Ok")
//               .onPositive(new MaterialDialog.SingleButtonCallback() {
//                   @Override
//                   public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//
//                   }
//               })
//               .negativeText("cancle")
//                .cancelable(false)
//               .show();

       return new MaterialDialog.Builder(this)
                .content("Vui lòng chờ")
                .progress(true, 0)
                .show();

    }
    private void doLogin() {
        final MaterialDialog dialog = showProgessbar();


        String tendn = txtUsername.getText().toString();
        String matkhau = txtPassword.getText().toString();
        Call<Taikhoan> taikhoan  = ApiHelper.getAPIService().login(tendn,matkhau);

                taikhoan.enqueue(new Callback<Taikhoan>() {
            @Override
            public void onResponse(Call<Taikhoan> call, retrofit2.Response<Taikhoan> response) {
                idTaikoan = response.body().getID();

                if(idTaikoan>0){
                    Toast.makeText(getBaseContext(),"Đăng nhập thành công", LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                    intent.putExtra("id",idTaikoan);
                    dialog.dismiss();
                    startActivity(intent);
                }
                else{

                    Toast.makeText(getBaseContext(),"Đăng nhập thất bại", LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Taikhoan> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Đăng nhập thất bại", LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
