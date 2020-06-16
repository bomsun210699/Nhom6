package com.caosiqui.quicaoit.appbandienthoaididong.model;

/**
 * Created by HP on 10/15/2017.
 */

public class Loaisp {
    public int Id;
    public String Tenloaisp;
    public  String Hinhanhsp;

    public Loaisp(int id, String tenloaisp, String hinhanhsp) {
        Id = id;
        Tenloaisp = tenloaisp;
        Hinhanhsp = hinhanhsp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenloaisp() {
        return Tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        Tenloaisp = tenloaisp;
    }

    public String getHinhanhsp() {
        return Hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        Hinhanhsp = hinhanhsp;
    }
}
