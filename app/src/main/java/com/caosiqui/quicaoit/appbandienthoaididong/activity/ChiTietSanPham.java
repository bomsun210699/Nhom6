package com.caosiqui.quicaoit.appbandienthoaididong.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.caosiqui.quicaoit.appbandienthoaididong.R;
import com.caosiqui.quicaoit.appbandienthoaididong.model.Giohang;
import com.caosiqui.quicaoit.appbandienthoaididong.model.SanPham;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btnaddcart;
    int id = 0 ;
    String TenChitiet = "";
    int GiaChitiet = 0;
    String HinhanhChitiet = "";
    String MotaChitiet = "";
    int Idsanpham = 0;

    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        AnhXa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
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

    /**
     * Share LinkFB
     * @param title Tiêu đề
     * @param linkShare link cần share
     * @param imgThumnal ảnh thu nhỏ
     *  Ở đây mình có gắn thêm phần Hashtag
     */
    public void shareLinkFB(String title, String linkShare, String imgThumnal) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setImageUrl(Uri.parse(imgThumnal))
                .setContentUrl(Uri.parse(linkShare))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#AndroidCoBan.Com")
                        .build())
                .build();
        ShareDialog.show(this,content);
    }

    /**
     * Share Photo
     * @param b Hình ảnh dạng bitmap
     * @param caption thêm caption
     */
    public void sharePhoto(Bitmap b, String caption) {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(b)
                .setCaption(caption)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo).build();
        ShareDialog.show(this, content);
    }


    private void EventButton() {

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgChitiet.setDrawingCacheEnabled(true);
                Bitmap bmap = imgChitiet.getDrawingCache();
                //shareLinkFB("Share","h-team.tk",HinhanhChitiet);
                sharePhoto(bmap,"Share fb");
            }
        });


        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i<MainActivity.manggiohang.size();i++){
                        if (MainActivity.manggiohang.get(i).getIdsp() == id){
                            MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluong() >=10){
                                MainActivity.manggiohang.get(i).setSoluong(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(GiaChitiet * MainActivity.manggiohang.get(i).getSoluong());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * GiaChitiet;
                        MainActivity.manggiohang.add(new Giohang(id,TenChitiet,Giamoi,HinhanhChitiet,soluong));

                    }

                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * GiaChitiet;
                    MainActivity.manggiohang.add(new Giohang(id,TenChitiet,Giamoi,HinhanhChitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), com.caosiqui.quicaoit.appbandienthoaididong.activity.Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayadapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayadapter);
    }

    private void GetInformation() {

        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getID();
        TenChitiet = sanPham.getTensanpham();
        GiaChitiet = sanPham.getGiasanpham();
        HinhanhChitiet = sanPham.getHinhanhsanpham();
        MotaChitiet = sanPham.getMotasanpham();
        Idsanpham = sanPham.getIDsanpham();
        txtten.setText(TenChitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Cost : " + decimalFormat.format(GiaChitiet) + "VND");
        txtmota.setText(MotaChitiet);
        Picasso.with(getApplicationContext()).load(HinhanhChitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.x)
                .into(imgChitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarChitiet = (Toolbar) findViewById(R.id.toolbardetails);
        imgChitiet = (ImageView) findViewById(R.id.imagesdetails);
        txtten = (TextView) findViewById(R.id.textviewnamedetails);
        txtgia = (TextView) findViewById(R.id.textviewcostdetails);
        txtmota = (TextView) findViewById(R.id.textviewsells);
        spinner = (Spinner) findViewById(R.id.spinner);
       btnaddcart = (Button) findViewById(R.id.buttonaddcart);
       btnShare = (Button) findViewById(R.id.btnShare);
    }
}
