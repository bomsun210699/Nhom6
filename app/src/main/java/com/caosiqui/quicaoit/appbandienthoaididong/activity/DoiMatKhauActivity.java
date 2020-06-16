package com.caosiqui.quicaoit.appbandienthoaididong.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.caosiqui.quicaoit.appbandienthoaididong.R;
import com.caosiqui.quicaoit.appbandienthoaididong.api.ApiHelper;
import com.caosiqui.quicaoit.appbandienthoaididong.model.Taikhoan;
import com.caosiqui.quicaoit.appbandienthoaididong.ultil.CheckConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText txtUsername;
    EditText txtCurentPassword;
    EditText txtNewPassword;
    EditText txtRePassword;
    Button btnCancel;
    Button btnAccept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        initIteminActivity();
        EventButton();
    }

    //    Ánh xạ element
    void initIteminActivity(){
        txtUsername = (EditText) findViewById(R.id.txtUserName);
        txtCurentPassword = (EditText) findViewById(R.id.txtCurentPassword);
        txtNewPassword = (EditText) findViewById(R.id.txtNewPassword);
        txtRePassword = (EditText) findViewById(R.id.txtRePassword);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnAccept = (Button) findViewById(R.id.btnAccept);
    }

    //    event login
    private void EventButton() {

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUsername.getText().length()==0 || txtCurentPassword.getText().length()==0 || txtNewPassword.getText().length()==0 || txtRePassword.getText().length()==0)
                    Toast.makeText(getBaseContext(),"Vui lòng nhập đầy đủ", LENGTH_SHORT).show();
                else{
                    final String rematkhau = txtRePassword.getText().toString().trim();
                    final String matkhau = txtNewPassword.getText().toString().trim();
                    if(!rematkhau.equals(matkhau)){
                        Log.e("abhu",matkhau + rematkhau);
                        Toast.makeText(getBaseContext(),"Mật khẩu nhập lại không đúng", LENGTH_SHORT).show();
                    }
                    else{
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            doimatkhau();
                        }else {
                            CheckConnection.ShoToast_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối");
                        }
                    }

                }
            }
        });

    }

    void  doimatkhau(){
        final String tendn = txtUsername.getText().toString().trim();
        final String matkhaucu = txtCurentPassword.getText().toString().trim();
        final String matkhau = txtNewPassword.getText().toString().trim();
        Call<Taikhoan> taikhoan  = ApiHelper.getAPIService().changePassword(tendn,matkhaucu,matkhau);
        taikhoan.enqueue(new Callback<Taikhoan>() {
            @Override
            public void onResponse(Call<Taikhoan> call, Response<Taikhoan> response) {
                if(!response.body().getTendangnhap().isEmpty()){
                    DangNhapActivity.idTaikoan = response.body().getID();
                    Toast.makeText(getBaseContext(),"Đổi mật khẩu thành công\nVui lòng đăng nhập lại", LENGTH_SHORT).show();
                    Intent intent = new Intent(DoiMatKhauActivity.this,DangNhapActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),"Đổi mật khẩu thất bại", LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Taikhoan> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Đổi mật khẩu thất bại!", LENGTH_SHORT).show();
            }
        });
    }
}
