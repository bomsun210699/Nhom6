package com.caosiqui.quicaoit.appbandienthoaididong.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 12/7/2017.
 */
//khởi tạo retrofit và Gson là parse từ object sang Gson
public class ApiHelper {
    public  static APIService apiService;

    public  static APIService getAPIService(){
        if(apiService == null)
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit build = new Retrofit.Builder()
                    .baseUrl(APIService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            apiService = build.create(APIService.class);
        }
        return apiService;
    }
}
