package com.chriscorptechnologies.androidgpaylibrary;

import com.chriscorptechnologies.androidgpaylibrary.modele.GpayFinalTransaction;
import com.chriscorptechnologies.androidgpaylibrary.modele.GpayRequest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ChrisCorpTechnologies on 05/05/2016.
 */
public interface Webservice {
    public static final String BASE_URL = "http://sandbox.mygpay.com/appapi/";

    //1-Envoi de requête pour authentification
    @FormUrlEncoded
    @POST("prepare")
    Call<GpayRequest> gpayRequest(
            @Field("auth") String authJson,
            @Field("invoice") String invoiceJson
    );

    //2-Requête pour transaction finale
    @FormUrlEncoded
    @POST("transaction")
    Call<GpayFinalTransaction> gpayTransaction(
            @Field("auth_code") String auth_code,
            @Field("uac") String uac
    );
}
