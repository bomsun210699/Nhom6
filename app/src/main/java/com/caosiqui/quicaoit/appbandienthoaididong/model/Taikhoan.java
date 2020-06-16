package com.caosiqui.quicaoit.appbandienthoaididong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HP on 12/6/2017.
 */

public class Taikhoan  implements Serializable {
    @SerializedName("id")
    @Expose
    int ID;

    @SerializedName("tenkhachhang")
    @Expose
    String Tenkhachhang;

    @SerializedName("sodienthoai")
    @Expose
    String Sodienthoai;

    @SerializedName("email")
    @Expose
    String Email;

    @SerializedName("diachi")
    @Expose
    String Diachi;

    @SerializedName("tendn")
    @Expose
    String Tendn;

    @SerializedName("matkhau")
    @Expose
    String Matkhau;
    public Taikhoan(){
        ID = 0;
        Tenkhachhang = "";
        Sodienthoai = "";
        Email = "";
        Diachi = "";
        Tendn = "";
        Matkhau = "";
    }

    public Taikhoan(int id,String tenkhachhang, String sodienthoai, String email, String diachi, String tendn, String matkhau) {
        ID = id;
        Tenkhachhang = tenkhachhang;
        Sodienthoai = sodienthoai;
        Email = email;
        Diachi = diachi;
        Tendn = tendn;
        Matkhau = matkhau;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public String getTenkhachhang() {
        return Tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        Tenkhachhang = tenkhachhang;
    }

    public String getSodienthoai() {
        return Sodienthoai;
    }

    public void setSodienthoai(String dienthoai) {
        Sodienthoai = dienthoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getTendangnhap() {
        return Tendn;
    }

    public void setTendangnhap(String tendangnhap) {
        Tendn = tendangnhap;
    }

    public String getMatkhau() {
        return Matkhau;
    }

    public void setMatkhau(String matkhau) {
        Matkhau = matkhau;
    }
}