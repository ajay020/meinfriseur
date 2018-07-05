package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.RequestInterface;
import friseurbarbers.mein.meinfriseur.Retrofit.ServerRequest;
import friseurbarbers.mein.meinfriseur.Retrofit.ServerResponse;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginScreen extends AppCompatActivity {
    Animation downtoup;
    ImageView logo;
    TextView create_Account;
    Button login;
    EditText user_Email, user_password;
    ProgressDialog progressDialog;
    String str_user_Email,str_user_password;
    ArrayList<MeinFriseurModule> meinFriseurModuleArrayList = new ArrayList<MeinFriseurModule>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
//        progressDialog = new ProgressDialog(LoginScreen.this);
//        progressDialog.setMessage("Please wait");

        logo = (ImageView) findViewById(R.id.logo);
        user_Email = (EditText) findViewById(R.id.user_Email);
        user_password = (EditText) findViewById(R.id.user_Password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                str_user_Email=user_Email.getText().toString();
//                str_user_password=user_password.getText().toString();
//                loginProcess(str_user_Email,str_user_password);
//                progressDialog.show();

                Intent i = new Intent(LoginScreen.this, HomeScreen.class);
                startActivity(i);
                overridePendingTransition(R.anim.downtoup, R.anim.uptodown);


            }
        });

        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        logo.setAnimation(downtoup);
        create_Account = (TextView) findViewById(R.id.create_Account);
        create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, RegistrationScreen.class);
                startActivity(i);
                overridePendingTransition(R.anim.downtoup, R.anim.uptodown);

            }
        });
    }

//    private void loginProcess(final String email,final String password) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.LOGIN_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
//
//        Map<String, String> map = new HashMap<>();
//
//
//
//           // map.put("usertype", usertype);
//            map.put("email", email);
//            map.put("password", password);
//
//
//
//        Call<ArrayList<MeinFriseurModule>> response = requestInterface.getData(Constant.LOGIN_URL, map);
//        response.enqueue(new Callback<ArrayList<MeinFriseurModule>>() {
//            @Override
//            public void onResponse(Call<ArrayList<MeinFriseurModule>> call, retrofit2.Response<ArrayList<MeinFriseurModule>> response) {
//
//                Log.e("response", response.body().size() + "");
//                progressDialog.dismiss();
//
//
//                if(response.body().get(0).getResult().equalsIgnoreCase("1")) {
//                    Toast.makeText(LoginScreen.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
//                    if (response.body().get(0).getUsertype().equalsIgnoreCase("Friseur")) {
//                        Intent i=new Intent(LoginScreen.this,VendorActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
//                    else
//                    {
//                        Intent i=new Intent(LoginScreen.this,HomeScreen.class);
//                        startActivity(i);
//                        finish();
//                    }
//                }
//                if(response.body().get(0).getResult().equalsIgnoreCase("2")) {
//                    Toast.makeText(LoginScreen.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                if(response.body().get(0).getResult().equalsIgnoreCase("3")) {
//                    Toast.makeText(LoginScreen.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                meinFriseurModuleArrayList = response.body();
//                Log.e("name", response.body().get(0).getResult());
//                Log.e("email", response.body().get(0).getMessage());
//            }
//
//
//            @Override
//            public void onFailure(Call<ArrayList<MeinFriseurModule>> call, Throwable t) {
//                //progress.setVisibility(View.GONE);
//
//                progressDialog.dismiss();
//                Log.e("Error", t.getLocalizedMessage());
//            }
//        });
//    }
}