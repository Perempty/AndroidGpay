package com.chriscorptechnologies.androidgpaysample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chriscorptechnologies.androidgpaylibrary.Gpay;
import com.chriscorptechnologies.androidgpaylibrary.ResponseListener;

public class MainActivity extends AppCompatActivity {
    String uac = "89657p839";
    Gpay gpay = new Gpay();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gpay.setModeTest();

        String public_key = "your public key";
        String private_key = "your secret_key";

        gpay.setApiKey(public_key, private_key);

        String amount = "10000";
        String description = "Transaction test library gpay";
        String numero_client = "22963404001";

        gpay.setInvoice(amount, description, numero_client);

        gpay.gpayGetAuth(new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                Log.d("Your auth code is", str);
                Toast.makeText(MainActivity.this, "Auth code: " + str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String str) {
                Log.d("Error description", str);
                Toast.makeText(MainActivity.this, "Error description: " + str, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void appelTransaction(View v) {
        gpay.gpayTransaction(uac, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                Toast.makeText(MainActivity.this, "Transaction id: " + str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String str) {
                Toast.makeText(MainActivity.this, "Error description: " + str, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
