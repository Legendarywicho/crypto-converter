package com.luis_santiago.cryptoconverter.tools;

import android.database.Observable;

import com.google.gson.Gson;
import com.luis_santiago.cryptoconverter.Model.Payload;
import com.luis_santiago.cryptoconverter.Model.Response;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Luis Fernando Santiago Ruiz on 1/14/18.
 */

public class ApiClient {

    private static final String BASE_URL = "http://api.bitso.com/v3/ticker/";

    public static ApiClient mApiClient;

    public static ApiInterface mRequest;

    private ApiClient(){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        mRequest = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getApiClient(){
        if(mApiClient == null){
            mApiClient = new ApiClient();
        }
        return mApiClient;
    }

    public rx.Observable<Response> getLatestPrices(){
        return mRequest.getLatestValues();
    }
}
