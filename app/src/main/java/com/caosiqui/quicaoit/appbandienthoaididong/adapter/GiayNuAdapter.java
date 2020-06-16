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
 * Created by HP on 10/22/2017.
 */

public class GiayNuAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arraygiaynu;

    public GiayNuAdapter(Context context, ArrayList<SanPham> arraygiaynu) {
        this.context = context;
        this.arraygiaynu = arraygiaynu;
    }

    @Override
    public int getCount() {
        return arraygiaynu.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiaynu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttendienthoaisamsung,txtgiadienthoaisamsung,txtmotadienthoaisamsung;
        public ImageView imgdienthoaisamsung;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giaynu,null);
            viewHolder.txttendienthoaisamsung = (TextView) convertView.findViewById(R.id.textviewtendienthoaisamsung);
            viewHolder.txtgiadienthoaisamsung = (TextView) convertView.findViewById(R.id.textviewgiadienthoaisamsung);
            viewHolder.txtmotadienthoaisamsung = (TextView) convertView.findViewById(R.id.textviewmotadienthoaisamsung);
            viewHolder.imgdienthoaisamsung = (ImageView) convertView.findViewById(R.id.imagesviewdienthoaisamsung);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txttendienthoaisamsung.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoaisamsung.setText("Cost : " + decimalFormat.format(sanPham.getGiasanpham()) + " VND ");
        viewHolder.txtmotadienthoaisamsung.setMaxLines(2);//set mo ta 2 dong
        viewHolder.txtmotadienthoaisamsung.setEllipsize(TextUtils.TruncateAt.END); //hien thi dau ... o mo ta sp
        viewHolder.txtmotadienthoaisamsung.setText(sanPham.getMotasanpham());
        Picasso.with(context).load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.x)
                .into(viewHolder.imgdienthoaisamsung);
        return convertView;

    }
}
