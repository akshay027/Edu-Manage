/*
package com.maxlore.edumanage.Activities.ParentActivities;

*/
/**
 * Created by Shrey on 03-Mar-17.
 *//*



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


import com.maxlore.edumanage.R;

import org.json.JSONObject;

public class PaymentActivity extends Activity {
    final String public_key = "rzp_test_42nSc5NsN6ZaFR"; //Enter key here
    private EditText etamountfees;
    double d, a;
    private String finalamount, amtcon;
    private String sppay,feeid;

    public PaymentActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        etamountfees = (EditText) findViewById(R.id.etamountfees1);
        // payment button created by you in xml layout
        View button = (View) findViewById(R.id.pay_btn);
        Intent Intent = this.getIntent();
        d = Intent.getDoubleExtra("amountconvertedtorupee", 0.0);
        sppay = Intent.getStringExtra("paymenttype");
        feeid = Intent.getStringExtra("feeid");
        // you need to pass current activity in order to let razorpay create CheckoutActivity
        final Activity activity = this;
        final CheckoutFragment co = new CheckoutFragment(d,sppay,feeid);

        co.setPublicKey(public_key);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject options = new JSONObject("{" +
                            "description: 'Fees'," +
                            "image: 'https://rzp-mobile.s3.amazonaws.com/images/rzp.png'," +
                            "currency: 'INR'}"
                    );
                    options.put("amount", d);
                    options.put("name", "maxlore Solutions");
                    options.put("prefill", new JSONObject("{email: 'maxlore10@gmail.com', contact: '1234567890'}"));
                    co.open(activity, options);

                } catch (Exception e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            ;
        });
    }
}
*/
