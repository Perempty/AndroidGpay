package com.chriscorptechnologies.androidgpaylibrary.modele;

/**
 * Created by ChrisCorpTechnologies on 05/05/2016.
 */
public class GpayFinalTransaction {

    private String error_code;
    private String transaction_id;
    private String error_description;

    @Override
    public String toString() {
        return "GpayFinalTransaction{" +
                "error_code='" + error_code + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", error_description='" + error_description + '\'' +
                '}';
    }

    public String getError_code() {
        return error_code;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getError_description() {
        return error_description;
    }
}
