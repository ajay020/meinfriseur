package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;
import android.support.annotation.IdRes;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import friseurbarbers.mein.meinfriseur.CircularAnim;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.RequestInterface;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationScreen extends AppCompatActivity {
    TextView already_account;
    LinearLayout catergory_linear, gender_linear;
    EditText regis_userName, regis_email, regis_phoneNumber, regis_password;
    RadioGroup user_TypeRadio;
    RadioButton user, barberShop;
    RadioGroup gender_Type;
    RadioButton gender_male, gender_female;
    RadioGroup category_Type;
    RadioButton category_gents, category_ladies, category_unisex;
    RadioButton radio_button, radio_category, radio_gender;
    String str_regis_username, str_regis_email, str_regis_phoneNumber, str_regis_password;
    String str_user, str_gender, str_category;
    Button signup;
    ProgressDialog progressDialog;
    ArrayList<MeinFriseurModule> meinFriseurModuleArrayList=new ArrayList<MeinFriseurModule>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

//sharedPreferences=
//        progressDialog = new ProgressDialog(RegistrationScreen.this);
//        progressDialog.setMessage("Please wait");


        gender_Type = (RadioGroup) findViewById(R.id.genderGroup);
        gender_Type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rd, @IdRes int checkedId) {
                for (int i = 0; i < rd.getChildCount(); i++) {
                    radio_gender = (RadioButton) rd.getChildAt(i);
                    int id = radio_gender.getId();
                    if (radio_gender.getId() == checkedId) {
                        str_gender = radio_gender.getText().toString();
                        // return;
                    }
                }
            }
        });
        gender_male = (RadioButton) findViewById(R.id.male);
        gender_female = (RadioButton) findViewById(R.id.female);

        category_Type = (RadioGroup) findViewById(R.id.category_Type);
        category_Type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rd, @IdRes int checkedId) {
                for (int i = 0; i < rd.getChildCount(); i++) {
                    radio_category = (RadioButton) rd.getChildAt(i);
                    int id = radio_category.getId();
                    if (radio_category.getId() == checkedId) {
                        str_category = radio_category.getText().toString();
                        // return;
                    }
                }
            }
        });
        category_gents = (RadioButton) findViewById(R.id.gents);
        category_ladies = (RadioButton) findViewById(R.id.female);
        category_unisex = (RadioButton) findViewById(R.id.unisex);

        regis_userName = (EditText) findViewById(R.id.regis_userName);
        regis_phoneNumber = (EditText) findViewById(R.id.regis_Phone);
        regis_email = (EditText) findViewById(R.id.regis_Email);
        regis_password = (EditText) findViewById(R.id.regis_Password);
        already_account = (TextView) findViewById(R.id.already_account);
        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationScreen.this, LoginScreen.class);
                startActivity(i);
                overridePendingTransition(R.anim.downtoup, R.anim.uptodown);

            }
        });
        user = (RadioButton) findViewById(R.id.regis_user);
        barberShop = (RadioButton) findViewById(R.id.regis_barbershop);
        user_TypeRadio = (RadioGroup) findViewById(R.id.radioGroup);
        user_TypeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rd, @IdRes int checkedId) {
                for (int i = 0; i < rd.getChildCount(); i++) {
                    radio_button = (RadioButton) rd.getChildAt(i);
                    int id = radio_button.getId();
                    if (radio_button.getId() == checkedId) {
                        str_user = radio_button.getText().toString();
                        // return;
                    }
                }
            }
        });
        catergory_linear = (LinearLayout) findViewById(R.id.lineaer1);
        gender_linear = (LinearLayout) findViewById(R.id.linear);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircularAnim.fullActivity(RegistrationScreen.this, v).colorOrImageRes(R.color.colorPrimary)
                        .go(new CircularAnim.OnAnimationEndListener() {

                            @Override
                            public void onAnimationEnd() {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(RegistrationScreen.this)
                                            .toBundle();
                                }

                                catergory_linear.setVisibility(View.GONE);
                                gender_linear.setVisibility(View.VISIBLE);


                            }
                        });
            }
        });
        barberShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircularAnim.fullActivity(RegistrationScreen.this, v).colorOrImageRes(R.color.colorPrimary)
                        .go(new CircularAnim.OnAnimationEndListener() {

                            @Override
                            public void onAnimationEnd() {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(RegistrationScreen.this)
                                            .toBundle();
                                }

                                catergory_linear.setVisibility(View.VISIBLE);
                                gender_linear.setVisibility(View.GONE);


                            }
                        });
            }
        });
        signup = (Button) findViewById(R.id.signUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegistrationScreen.this,LoginScreen.class);
                startActivity(i);
                // registerDetail();
//                str_regis_username=regis_userName.getText().toString();
//                str_regis_email=regis_email.getText().toString();
//                str_regis_phoneNumber=regis_phoneNumber.getText().toString();
//                str_regis_password=regis_password.getText().toString();
//
//                if(str_regis_username.equalsIgnoreCase("null"))
//                {
//
//                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationScreen.this).create();
//                    alertDialog.setMessage("Please enter your User Type!!!");
//                    alertDialog.setButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //dismiss the dialog
//                                }
//                            });
//
//                    alertDialog.show();
//                }
//                else if(str_regis_username.isEmpty())
//                {
//
//                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationScreen.this).create();
//                    alertDialog.setMessage("Please enter your Legal Name!!!");
//                    alertDialog.setButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //dismiss the dialog
//                                }
//                            });
//
//                    alertDialog.show();
//                }
//                else if(str_regis_email.isEmpty())
//                {
//                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationScreen.this).create();
//                    alertDialog.setMessage("Please enter your valid Email Id!!!");
//                    alertDialog.setButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //dismiss the dialog
//                                }
//                            });
//
//                    alertDialog.show();
//
//                }
//                else if(str_regis_phoneNumber.isEmpty()){
//                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationScreen.this).create();
//                    alertDialog.setMessage("Please enter your Mobile Number!!!");
//                    alertDialog.setButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //dismiss the dialog
//                                }
//                            });
//
//                    alertDialog.show();
//
//
//                }
//                else if(str_regis_phoneNumber.length()<10 ||str_regis_phoneNumber.length()>10){
//                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationScreen.this).create();
//                    alertDialog.setMessage("Please enter your valid Mobile Number!!!");
//                    alertDialog.setButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //dismiss the dialog
//                                }
//                            });
//
//                    alertDialog.show();
//
//
//                }
//                else if(str_regis_password.isEmpty()){
//                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationScreen.this).create();
//                    alertDialog.setMessage("Please enter your Password!!!");
//                    alertDialog.setButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //dismiss the dialog
//                                }
//                            });
//
//                    alertDialog.show();
//
//
//                }
//
//                else {
//                    // if(!str_regis_username.isEmpty()&& str_regis_email.isEmpty()&& str_regis_phoneNumber.isEmpty()&& str_user.isEmpty()&& str_gender.isEmpty()&& str_category.isEmpty()||str_regis_password.isEmpty()) {
//                    registerProcess(str_regis_username, str_user, str_regis_email, str_regis_phoneNumber, str_gender, str_category, str_regis_password, view);
//                    progressDialog.show();
//                }
//                //}
          }
       });


    }

//    private void registerProcess(final String name,final String usertype,final String email, final String phonenumber, final String gender,final String categorytype,final String pass, final View v) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.REGISTER_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
//        Map<String, String> map = new HashMap<>();
//if(usertype.equalsIgnoreCase("Friseur")) {
//
//    map.put("username", name);
//    map.put("usertype", usertype);
//    map.put("email", email);
//    map.put("phonenumber", phonenumber);
//    map.put("gender", "null");
//    map.put("categorytype", categorytype);
//    map.put("password", pass);
//
//}
//else
//{
//
//    map.put("username", name);
//    map.put("usertype", usertype);
//    map.put("email", email);
//    map.put("phonenumber", phonenumber);
//     map.put("gender", gender);
//    map.put("categorytype", "null");
//    map.put("password",pass);
//}
//        Call<ArrayList<MeinFriseurModule>> response = requestInterface.getData(Constant.REGISTER_URL, map);
//        response.enqueue(new Callback<ArrayList<MeinFriseurModule>>() {
//            @Override
//            public void onResponse(Call<ArrayList<MeinFriseurModule>> call, retrofit2.Response<ArrayList<MeinFriseurModule>> response) {
//
//                Log.e("response", response.body().size() + "");
//                progressDialog.dismiss();
//
//                if(response.body().get(0).getResult().equalsIgnoreCase("1")) {
//                    Toast.makeText(RegistrationScreen.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                if(response.body().get(0).getResult().equalsIgnoreCase("2")) {
//                    Toast.makeText(RegistrationScreen.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                if(response.body().get(0).getResult().equalsIgnoreCase("3")) {
//                    Toast.makeText(RegistrationScreen.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                if(response.body().get(0).getResult().equalsIgnoreCase("4")) {
//                    Toast.makeText(RegistrationScreen.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//
//
//                meinFriseurModuleArrayList=response.body();
//             Log.e("name",response.body().get(0).getResult());
//              Log.e("email",response.body().get(0).getMessage());
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