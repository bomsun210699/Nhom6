package com.caosiqui.quicaoit.appbandienthoaididong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.caosiqui.quicaoit.appbandienthoaididong.R;
import com.caosiqui.quicaoit.appbandienthoaididong.activity.MainActivity;
import com.caosiqui.quicaoit.appbandienthoaididong.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by HP on 10/28/2017.
 */

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayListgiohang;

    public GioHangAdapter(Context context, ArrayList<Giohang> arrayListgiohang) {
        this.context = context;
        this.arrayListgiohang = arrayListgiohang;
    }

    @Override
    public int getCount() {
        return arrayListgiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListgiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus,btnvalus,btnplus;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang = (TextView) convertView.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang = (TextView) convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = (ImageView) convertView.findViewById(R.id.imagesgiohang);
            viewHolder.btnminus = (Button) convertView.findViewById(R.id.buttonminus);
            viewHolder.btnvalus = (Button) convertView.findViewById(R.id.buttonvalues);
            viewHolder.btnplus = (Button) convertView.findViewById(R.id.buttonplus);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp()) + "VND");
        Picasso.with(context).load(giohang.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.x)
                .into(viewHolder.imggiohang);
        viewHolder.btnvalus.setText(giohang.getSoluong() + "");
        int sl = Integer.parseInt(viewHolder.btnvalus.getText().toString());
        if (sl>=10){
            viewHolder.btnvalus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if (sl<=1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if (sl>=1){
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        final ViewHolder finalViewHolder2 = viewHolder;
        final ViewHolder finalViewHolder3 = viewHolder;
        final ViewHolder finalViewHolder4 = viewHolder;
        final ViewHolder finalViewHolder5 = viewHolder;
        final ViewHolder finalViewHolder7 = viewHolder;
        final ViewHolder finalViewHolder8 = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalus.getText().toString()) +1;
                int slht = MainActivity.manggiohang.get(position).getSoluong();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht ;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + "VND");
                com.caosiqui.quicaoit.appbandienthoaididong.activity.Giohang.EventUltil();
                if (slmoinhat > 9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder8.btnvalus.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder7.btnvalus.setText(String.valueOf(slmoinhat));
                }
            }
        });
        final ViewHolder finalViewHolder6 = viewHolder;
        final ViewHolder finalViewHolder9 = viewHolder;
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalus.getText().toString()) -1;
                int slht = MainActivity.manggiohang.get(position).getSoluong();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht ;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + "VND");
                com.caosiqui.quicaoit.appbandienthoaididong.activity.Giohang.EventUltil();
                if (slmoinhat < 2){
                    finalViewHolder2.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder2.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder9.btnvalus.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder3.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder3.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder6.btnvalus.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }
}