package com.dhimanstudio.the_killer_zone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.dhimanstudio.pubg_multiplayer.R;

import com.dhimanstudio.the_killer_zone.model.AppEnvironment;
import com.dhimanstudio.the_killer_zone.model.AppPreference;
import com.dhimanstudio.the_killer_zone.model.BaseApplication;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;

import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;


import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    private AppPreference mAppPreference;
    public static final String TAG = "PaymentActivity : ";

    PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
    //declare paymentParam object
    PayUmoneySdkInitializer.PaymentParam paymentParam = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        //declare paymentParam object
        PayUmoneySdkInitializer.PaymentParam paymentParam = null;


        launchPayUMoneyFlow();
        /*------------- PAYMENT ---------------*/
    }

    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

//        Use this to set your custom text on result screen button
//        payUmoneyConfig.setDoneButtonText(((EditText) findViewById(R.id.status_page_et)).getText().toString());
//        Use this to set your custom title for the activity
//        payUmoneyConfig.setPayUmoneyActivityTitle(((EditText) findViewById(R.id.activity_title_et)).getText().toString());
//        payUmoneyConfig.disableExitConfirmation(isDisableExitConfirmation);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();



        //////////////////////////////////////////
        String txnId =  "TXN"+System.currentTimeMillis() + "";
        //String txnId = "TXNID720431525261327973";
        String phone = "7087294676";
        String productName = "kz";
        String firstName = "Akshay";
        String email = "dhimanvlogs@gmail.com";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";
        String merchantId ="6779961";
        String merchantkey="KWCRzGYb";

//        AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        builder.setAmount("10")
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(true)
                .setKey(merchantkey)
                .setMerchantId(merchantId);

        try {
            mPaymentParams = builder.build();

            /*
             * Hash should always be generated from your server side.
             * */
                generateHashFromServer(mPaymentParams);

            /*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * */

//            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);
//
//            if (AppPreference.selectedTheme != -1) {
//                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,PaymentActivity.this, AppPreference.selectedTheme,mAppPreference.isOverrideResultScreen());
//            } else {
//                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,PaymentActivity.this, R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());
//            }

        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            payNowButton.setEnabled(true);
        }
    }




    public void generateHashFromServer(PayUmoneySdkInitializer.PaymentParam paymentParam) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.

        HashMap<String, String> params = paymentParam.getParams();

        // lets create the post params
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayUmoneyConstants.KEY, params.get(PayUmoneyConstants.KEY)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.AMOUNT, params.get(PayUmoneyConstants.AMOUNT)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.TXNID, params.get(PayUmoneyConstants.TXNID)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.EMAIL, params.get(PayUmoneyConstants.EMAIL)));
        postParamsBuffer.append(concatParams("productinfo", params.get(PayUmoneyConstants.PRODUCT_INFO)));
        postParamsBuffer.append(concatParams("firstname", params.get(PayUmoneyConstants.FIRSTNAME)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF1, params.get(PayUmoneyConstants.UDF1)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF2, params.get(PayUmoneyConstants.UDF2)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF3, params.get(PayUmoneyConstants.UDF3)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF4, params.get(PayUmoneyConstants.UDF4)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF5, params.get(PayUmoneyConstants.UDF5)));

        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();

        // lets make an api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }



    /*GET HASH FROM SEVER  START*/


    private class GetHashesFromServerTask extends AsyncTask<String, String, String> {
            //        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(PaymentActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
            //            progressDialog = new ProgressDialog(PaymentActivity.this);
            //            progressDialog.setMessage("Please wait...");
            //            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... postParams) {

            String merchantHash = "";
            try {
                //TODO Below url is just for testing purpose, merchant needs to replace this with their server side hash generation url
//                URL url = new URL("https://www.mobilesdetail.com/killerzone/response_hash.php");
                URL url = new URL("https://www.mobilesdetail.com/killerzone/new_hash.php");


                String postParam = postParams[0];

                byte[] postParamsByte = postParam.getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn instanceof HttpsURLConnection) {
                    HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                    httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                    httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
                }
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);

                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();


                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }

                JSONObject response = new JSONObject("{payment_hash:"+responseStringBuffer.toString()+"}");




                Iterator<String> payuHashIterator = response.keys();

                while (payuHashIterator.hasNext()) {
                    String key = payuHashIterator.next();


                    switch (key) {
                        /**
                         * This hash is mandatory and needs to be generated from merchant's server side
                         *
                         */
//                        case "D/hash":
                        case "payment_hash":
                            merchantHash = response.getString(key);
                            break;
                        default:
                            break;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.print("MalformedURLException");
            } catch (ProtocolException e) {
                e.printStackTrace();
                System.out.print("ProtocolException");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("IOException");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return merchantHash;
        }



        @Override
        protected void onPostExecute(String merchantHash) {
            super.onPostExecute(merchantHash);

            //            progressDialog.dismiss();
            //            payNowButton.setEnabled(true);



            if (merchantHash.isEmpty() || merchantHash.equals("")) {
                Toast.makeText(PaymentActivity.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
            } else {
                mPaymentParams.setMerchantHash(merchantHash);

                if (AppPreference.selectedTheme != -1) {
//                    mAppPreference.isOverrideResultScreen()
                    PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentActivity.this, AppPreference.selectedTheme, true);
                } else {
                    PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentActivity.this, R.style.AppTheme_default, true);
                }
            }
        }
    }




    /*GET HASH FROM SEVER END */
    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

//    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {
//
//        StringBuilder stringBuilder = new StringBuilder();
//        HashMap<String, String> params = paymentParam.getParams();
//        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");
//
//        AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
//        stringBuilder.append(appEnvironment.salt());
//
//        String hash = hashCal(stringBuilder.toString());
//        paymentParam.setMerchantHash(hash);
//
//        return paymentParam;
//    }






    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("PaymentActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    Log.d("payment","Transaction SUCCESS");
                } else {
                    //Failure Transaction
                    Log.d("payment","Transaction FAILED");
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();

                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();

            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }
        }
    }


}