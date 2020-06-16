package com.caosiqui.quicaoit.appbandienthoaididong.api;

import android.text.Editable;

import com.caosiqui.quicaoit.appbandienthoaididong.model.Taikhoan;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

//interface cho các API
public interface APIService {
    public static final String BASE_URL = "http://bandienthoai79.000webhostapp.com/server/";

    @POST("dangnhap.php")
    @Headers({ "Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @FormUrlEncoded
    Call<Taikhoan> login(
            @Field("tendn") String tendn, @Field("matkhau") String matkhau
    );

    @POST("themtaikhoan.php")
    @Headers({ "Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @FormUrlEncoded
    Call<Taikhoan> addData(
            @Field("tendn") String tendn, @Field("matkhau") String matkhau,
            @Field("tenkhachhang") String tenkhachhang, @Field("sodienthoai") String sodienthoai,
            @Field("email") String email, @Field("diachi") String diachi
    );

    @POST("capnhattaikhoan.php")
    @Headers({ "Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @FormUrlEncoded
    Call<Taikhoan> updateData(
            @Field("tenkhachhang") String tenkhachhang, @Field("email") String email,
            @Field("sodienthoai") String sodienthoai, @Field("diachi") String diachi,
            @Field("id") String id
    );

    @POST("doimatkhau.php")
    @Headers({ "Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @FormUrlEncoded
    Call<Taikhoan> changePassword(
            @Field("tendn") String tendn, @Field("matkhaucu") String matkhaucu,
            @Field("matkhau") String matkhau
    );

    @GET("thongtintaikhoan.php")
    Call<Taikhoan> findById(@Query("id") String id);

}