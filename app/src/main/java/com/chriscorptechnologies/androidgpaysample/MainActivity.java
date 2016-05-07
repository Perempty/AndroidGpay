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

        String public_key = "442deb4b98982d09224fdac35694ca0b11e012def4574e9f93cc201408337854c56deae80b1691d748da8a49e404f5b0fab2141f79aeedc534123a933a6c5fc8";
        String private_key = "045354b04ce04c9a40af8aaec7a10795ec2852e8ba7cc6605a7af59a8ea91e346cfec2028ff4e0da73e2b3739c76f901abcb9c4de10ff02b12833db5b1f7ad1d";

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
