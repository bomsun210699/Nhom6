package com.caosiqui.quicaoit.appbandienthoaididong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.caosiqui.quicaoit.appbandienthoaididong.R;
import com.caosiqui.quicaoit.appbandienthoaididong.adapter.GiayNamAdapter;
import com.caosiqui.quicaoit.appbandienthoaididong.model.SanPham;
import com.caosiqui.quicaoit.appbandienthoaididong.ultil.CheckConnection;
import com.caosiqui.quicaoit.appbandienthoaididong.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GiayNamActivity extends AppCompatActivity {
    Toolbar toolbardtip;
    ListView lvdtip;
    GiayNamAdapter giayNamAdapter;
    ArrayList<SanPham> mangdtip;
    int idgn = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limmitdata = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giay_nam);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdLoaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }else {
            CheckConnection.ShoToast_Short(getApplicationContext(),"Hãy kiểm tra lại internet");
            finish();
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.caosiqui.quicaoit.appbandienthoaididong.activity.Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreData() {
        lvdtip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangdtip.get(position));
                startActivity(intent);
            }
        });
        lvdtip.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListViewew, int i) {


            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limmitdata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();

                }


            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandienthoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendtip = "";
                int Giadtip = 0;
                String Hinhanhdtip = "";
                String Motadtip = "";
                int Idspdtip = 0;
                if (response !=null && response.length()!= 2){
                    lvdtip.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendtip = jsonObject.getString("tensp");
                            Giadtip = jsonObject.getInt("giasp");
                            Hinhanhdtip = jsonObject.getString("hinhanhsp");
                            Motadtip = jsonObject.getString("motasp");
                            Idspdtip = jsonObject.getInt("idsanpham");
                            mangdtip.add(new SanPham(id,Tendtip,Giadtip,Hinhanhdtip,Motadtip,Idspdtip));
                            giayNamAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limmitdata = true;
                    lvdtip.removeFooterView(footerview);
                    CheckConnection.ShoToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(idgn));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardtip);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardtip.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdLoaisp() {
        idgn = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idgn + "");
    }


    private void AnhXa() {
        toolbardtip = (Toolbar) findViewById(R.id.toolbargiaynam);
        lvdtip = (ListView) findViewById(R.id.listviewgiaynam);
        mangdtip = new ArrayList<>();
        giayNamAdapter = new GiayNamAdapter(getApplicationContext(),mangdtip);
        lvdtip.setAdapter(giayNamAdapter);
        LayoutInflater  inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();

    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvdtip.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message =mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
