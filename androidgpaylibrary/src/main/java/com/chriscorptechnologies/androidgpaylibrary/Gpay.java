package com.chriscorptechnologies.androidgpaylibrary;

import com.chriscorptechnologies.androidgpaylibrary.modele.GpayFinalTransaction;
import com.chriscorptechnologies.androidgpaylibrary.modele.GpayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ChrisCorpTechnologies on 05/05/2016.
 */
public class Gpay {

    public String auth_code;
    GpayRequest gpayRequestObject = null;
    private HashMap<String, String> authValues = new HashMap<>();
    private HashMap<String, String> invoiceValues = new HashMap<>();
    private String authJson, invoiceJson, devptMode;

    public Gpay() {

    }

    /**
     * Obtenir le code d'authenfication: auth_code (Authentification Code) généré automatiquement
     * et a une durée de vie de quelques minutes pour finaliser la transaction.
     *
     * @param listener En cas de succès :
     *                 la méthode vous renvoie le code d'authentification via {@link ResponseListener#onSuccess(String)}
     *                 <p/>
     *                 En cas d'échec :
     *                 la méthode vous renvoie la description de l'erreur via {@link ResponseListener#onFailed(String)}
     */

    public void gpayGetAuth(final ResponseListener listener) {

        jsonCreator(authValues, invoiceValues);


        Call<GpayRequest> requestCall = getGsonWebServiceManager(devptMode).gpayRequest(authJson, invoiceJson);

        requestCall.enqueue(new Callback<GpayRequest>() {
            @Override
            public void onResponse(Call<GpayRequest> call, Response<GpayRequest> response) {
                GpayRequest rep = response.body();
                if (rep.getAuth_code() != null) {
                    listener.onSuccess(rep.getAuth_code());
                    auth_code = rep.getAuth_code();
                } else {
                    listener.onFailed(rep.getError_description());
                }

            }

            @Override
            public void onFailure(Call<GpayRequest> call, Throwable t) {
                listener.onFailed(t.getMessage());

            }

        });


    }

    /**
     * Initialisation des clés d'authentification à l'API
     *
     * @param public_key Accès public_key
     * @param secret_key Accès secret_key
     */
    public void setApiKey(String public_key, String secret_key) {
        authValues.put("public_key", public_key);
        authValues.put("secret_key", secret_key);
    }


    /**
     * Choix du mode de développement Test
     * ce mode permet tout simplement de tester Gpay sans faire de "réelles" transactions.
     * On dit alors qu'on est en mode test ou développement.
     */
    public void setModeTest() {
        this.devptMode = "test";
    }

    /**
     * Choix du mode de développement Live
     * ce mode s'utilise lorsqu'on est prêt à déployer l'application.
     * Ainsi sous ce mode, toute transaction sera considérée comme vraie.
     */
    public void setModeLive() {
        this.devptMode = "live";
    }

    /**
     * Initialisation des paramètres de paiement
     *
     * @param amount              La somme Totale (TTC) a défalqué sur le compte du client
     * @param description         Le nom ou une petite description de l'article ou des produits.
     * @param client_phone_number Le numéro du client avec lequel doit se faire la transaction MTN Mobile Money(MMoney) ou Flooz.
     *                            (Utiliser le numéro  61580258 pour un client avec un compte chargé et
     *                            63404001 pour client qui n'a pas assez de fond sur son compte)
     */
    public void setInvoice(String amount, String description, String client_phone_number) {
        invoiceValues.put("amount", amount);
        invoiceValues.put("description", description);
        invoiceValues.put("client", client_phone_number);
    }


    /**
     * Finaliser la transaction
     *
     * @param uac      (User Authentification Code) Code de 9 chiffres envoyé par Gpay au client par SMS
     *                 Simulation pour mode test (uac = "89657p839")
     * @param listener
     */
    public void gpayTransaction(String uac, final ResponseListener listener) {

        Call<GpayFinalTransaction> finalTransactionRequest = getGsonWebServiceManager(devptMode).gpayTransaction(auth_code, uac);
        finalTransactionRequest.enqueue(new Callback<GpayFinalTransaction>() {
            @Override
            public void onResponse(Call<GpayFinalTransaction> call, Response<GpayFinalTransaction> response) {
                GpayFinalTransaction transactionRep = response.body();
                if (transactionRep.getTransaction_id() != null) {
                    listener.onSuccess(transactionRep.getTransaction_id());
                } else {
                    listener.onFailed(transactionRep.getError_description());
                }

            }

            @Override
            public void onFailure(Call<GpayFinalTransaction> call, Throwable t) {
                listener.onFailed(t.getMessage());
            }
        });

    }

    private void jsonCreator(HashMap<String, String> authValues, HashMap<String, String> invoiceValues) {
        Gson gson = new GsonBuilder().create();
        authJson = gson.toJson(authValues);
        invoiceJson = gson.toJson(invoiceValues);
    }

    private Webservice getGsonWebServiceManager(String mode) {

        String BASE_URL;
        switch (mode) {
            case "test":
                BASE_URL = "http://sandbox.mygpay.com/appapi/";
                break;
            case "live":
                BASE_URL = "http://sandbox.mygpay.com/appapi/";
                break;
            default:
                BASE_URL = "http://sandbox.mygpay.com/appapi/";
        }

        //Christophe KINDJI code for retrofit Log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        //
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(Webservice.class);
    }
}
