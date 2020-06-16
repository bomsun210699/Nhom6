package com.caosiqui.quicaoit.appbandienthoaididong.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caosiqui.quicaoit.appbandienthoaididong.R;
import com.caosiqui.quicaoit.appbandienthoaididong.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by HP on 10/20/2017.
 */

public class GiayNamAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arraygiaynam;

    public GiayNamAdapter(Context context, ArrayList<SanPham> arraygiaynam) {
        this.context = context;
        this.arraygiaynam = arraygiaynam;
    }

    @Override
    public int getCount() {
        return arraygiaynam.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiaynam.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttendienthoaiiphone,txtgiadienthoaiiphone,txtmotadienthoaiiphone;
        public ImageView imgdienthoaiiphone;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giaynam,null);
            viewHolder.txttendienthoaiiphone = (TextView) convertView.findViewById(R.id.textviewtendienthoaiiphone);
            viewHolder.txtgiadienthoaiiphone = (TextView) convertView.findViewById(R.id.textviewgiadienthoaiiphone);
            viewHolder.txtmotadienthoaiiphone = (TextView) convertView.findViewById(R.id.textviewmotadienthoaiiphone);
            viewHolder.imgdienthoaiiphone = (ImageView) convertView.findViewById(R.id.imagesviewdienthoaiiphone);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txttendienthoaiiphone.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoaiiphone.setText("Cost : " + decimalFormat.format(sanPham.getGiasanpham()) + " VND ");
        viewHolder.txtmotadienthoaiiphone.setMaxLines(2);//set mo ta 2 dong
        viewHolder.txtmotadienthoaiiphone.setEllipsize(TextUtils.TruncateAt.END); //hien thi dau ... o mo ta sp
        viewHolder.txtmotadienthoaiiphone.setText(sanPham.getMotasanpham());
        Picasso.with(context).load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.x)
                .into(viewHolder.imgdienthoaiiphone);
        return convertView;

    }
}
