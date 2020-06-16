package com.caosiqui.quicaoit.appbandienthoaididong.activity;

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

public class UpdateInfoActivity extends AppCompatActivity {
    EditText txtFullname;
    EditText txtEmail;
    EditText txtPhone;
    EditText txtAddress;
    Button btnCancel;
    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        initIteminActivity();
        getTaiKhoan();
        EventButton();
    }

    void getTaiKhoan(){
        Call<Taikhoan> taikhoan  = ApiHelper.getAPIService().findById(DangNhapActivity.idTaikoan+"");
        taikhoan.enqueue(new Callback<Taikhoan>() {
            @Override
            public void onResponse(Call<Taikhoan> call, retrofit2.Response<Taikhoan> response) {
                if(!response.body().getTendangnhap().isEmpty()){
                    txtPhone.setText(response.body().getSodienthoai());
                    txtEmail.setText(response.body().getEmail());
                    txtAddress.setText(response.body().getDiachi());
                    txtFullname.setText(response.body().getTenkhachhang());
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<Taikhoan> call, Throwable t) {

            }
        });
    }

    //    Ánh xạ element
    void initIteminActivity(){
        txtFullname = (EditText) findViewById(R.id.txtFullName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
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
                if(txtFullname.getText().length()==0 || txtEmail.getText().length()==0 || txtPhone.getText().length()==0 || txtAddress.getText().length()==0)
                    Toast.makeText(getBaseContext(),"Vui lòng nhập đầy đủ", LENGTH_SHORT).show();
                else{
                    if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                        capnhattaikhoan();
                    }else {
                        CheckConnection.ShoToast_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối");
                    }

                }
            }
        });

    }

    void  capnhattaikhoan(){
        final String tenkhachhang = txtFullname.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();
        final String sodienthoai = txtPhone.getText().toString().trim();
        final String diachi = txtAddress.getText().toString().trim();
        Call<Taikhoan> taikhoan  = ApiHelper.getAPIService().updateData(tenkhachhang,email,sodienthoai,diachi,DangNhapActivity.idTaikoan+"");
        taikhoan.enqueue(new Callback<Taikhoan>() {
            @Override
            public void onResponse(Call<Taikhoan> call, Response<Taikhoan> response) {
                if(!response.body().getTendangnhap().isEmpty()){
                    DangNhapActivity.idTaikoan = response.body().getID();
                    Toast.makeText(getBaseContext(),"Cập nhật thành công", LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateInfoActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),"Cập nhật thất bại", LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Taikhoan> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Cập nhật thất bại!", LENGTH_SHORT).show();
            }
        });
    }
}
