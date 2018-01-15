package com.luis_santiago.cryptoconverter.tools;

import com.luis_santiago.cryptoconverter.Model.Payload;
import com.luis_santiago.cryptoconverter.Model.Response;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Luis Fernando Santiago Ruiz on 1/14/18.
 */

public interface ApiInterface {

    @GET("?book=btc_mxn")
    rx.Observable<Response> getLatestValues();

}
