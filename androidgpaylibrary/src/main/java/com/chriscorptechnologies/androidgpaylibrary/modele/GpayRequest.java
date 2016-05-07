package com.chriscorptechnologies.androidgpaylibrary.modele;

/**
 * Created by ChrisCorpTechnologies on 05/05/2016.
 */
public class GpayRequest {
    private String error_code;
    private String error_description;
    private String auth_code;

    @Override
    public String toString() {
        return "GpayRequest{" +
                "error_code='" + error_code + '\'' +
                ", error_description='" + error_description + '\'' +
                ", auth_code='" + auth_code + '\'' +
                '}';
    }

    public String getError_code() {
        return error_code;
    }

    public String getError_description() {
        return error_description;
    }

    public String getAuth_code() {
        return auth_code;
    }
}
