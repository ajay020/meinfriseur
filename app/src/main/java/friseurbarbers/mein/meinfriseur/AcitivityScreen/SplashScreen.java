package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.Shared;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;

public class SplashScreen extends AppCompatActivity {
    int splashWaitingTime = 3 * 1000;// 3 seconds
    public static ArrayList<MeinFriseurModuleHelper> barberlistname = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences(Shared.barberList, MODE_PRIVATE);
        splashCode();
        barberlist();
    }

    public void splashCode() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, LoginScreen1.class);
                finish();
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        }, splashWaitingTime);
    }

    private void barberlist() {

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(SplashScreen.this, Constant.BaberList_URL, null, "application/json", new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    // progressDialog.dismiss();
                    Log.e("response course...", new String(responseBody));


                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        Log.e("length", jsonArray.length() + "");

                        try {

                            barberlistname = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<MeinFriseurModuleHelper>>() {
                            }.getType());
                            Log.e("length", barberlistname.size() + "");
                            Log.e("barberid", barberlistname.get(0).getId());
                            Log.e("barberorganization", barberlistname.get(0).getOrganization());


                        } catch (Exception e) {
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
