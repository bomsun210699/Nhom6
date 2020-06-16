package com.caosiqui.quicaoit.appbandienthoaididong.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.caosiqui.quicaoit.appbandienthoaididong.R;
import com.caosiqui.quicaoit.appbandienthoaididong.api.ApiHelper;
import com.caosiqui.quicaoit.appbandienthoaididong.model.Taikhoan;
import com.caosiqui.quicaoit.appbandienthoaididong.ultil.CheckConnection;
import com.caosiqui.quicaoit.appbandienthoaididong.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class Thongtinkhachhang extends AppCompatActivity {

    EditText txtFullname,txtEmail,txtPhone,txtAddress;
    Button btnxacnhan,btntrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        AnhXa();
        getTaiKhoan();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else {
            CheckConnection.ShoToast_Short(getApplicationContext(),"kiểm tra lại kết nối");
        }
    }

    private MaterialDialog showProgessbar(){

        return new MaterialDialog.Builder(this)
                .content("Vui lòng chờ")
                .progress(true, 0)
                .show();

    }

    void getTaiKhoan(){
        final MaterialDialog dialog = showProgessbar();
        Call<Taikhoan> taikhoan  = ApiHelper.getAPIService().findById(DangNhapActivity.idTaikoan+"");
        taikhoan.enqueue(new Callback<Taikhoan>() {
            @Override
            public void onResponse(Call<Taikhoan> call, retrofit2.Response<Taikhoan> response) {
                if(!response.body().getTendangnhap().isEmpty()){
                    txtPhone.setText(response.body().getSodienthoai());
                    txtEmail.setText(response.body().getEmail());
                    txtAddress.setText(response.body().getDiachi());
                    txtFullname.setText(response.body().getTenkhachhang());
                    dialog.dismiss();
                }
                else{
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Taikhoan> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenkhachhang = txtFullname.getText().toString().trim();
                final String sodienthoai = txtPhone.getText().toString().trim();
                final String email = txtEmail.getText().toString().trim();
                final String diachi = txtAddress.getText().toString().trim();
                if (tenkhachhang.length()>0 && sodienthoai.length()>0 && email.length()>0 && diachi.length()>0){
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("1")){
                                MainActivity.manggiohang.clear();
                                CheckConnection.ShoToast_Short(getApplicationContext(),"Chúc mừng bạn đã đặt hàng thành công");
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                CheckConnection.ShoToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                            }else {
                                CheckConnection.ShoToast_Short(getApplicationContext(),"Giở hàng của bạn bị lỗi");
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            JSONArray jsonArray = new JSONArray();
                            for (int i=0 ; i< MainActivity.manggiohang.size() ; i++){
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("madonhang",DangNhapActivity.idTaikoan);
                                    jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                    jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                    jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                    jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluong());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);

                            }
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("json",jsonArray.toString());

                            return hashMap;
                        }
                    };
                    queue.add(request);

                }else {
                    CheckConnection.ShoToast_Short(getApplicationContext(),"Kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void AnhXa() {
        txtFullname = (EditText) findViewById(R.id.txtTenKhackHang);
        txtPhone = (EditText) findViewById(R.id.txtSodienthoai);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtAddress = (EditText) findViewById(R.id.txtDiachi);
        btnxacnhan = (Button) findViewById(R.id.buttonxacnhan);
        btntrove = (Button) findViewById(R.id.buttontrove);


    }
}
