package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.RequestInterface;
import friseurbarbers.mein.meinfriseur.Retrofit.Shared;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginScreen1 extends AppCompatActivity {
TextView txt_continue;
   public static EditText userName;
    RadioButton radio_button;
    public static RadioGroup radioGroup;
    public static String gender=null;
    RadioButton male,female;
    Button btn_facebook;
    CallbackManager callbackManager;
    String userId, username;
String str_userName,str_gender;
    ArrayList<MeinFriseurModuleHelper> meinFriseurModuleArrayList=new ArrayList<MeinFriseurModuleHelper>();
SharedPreferences sharedPreferences;
    private DatePicker datePicker;
    private Calendar calendar;
    public static TextView dateView;
    private int year, month, day;
    public static String str_dateofBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen1);
        dateView=(TextView)findViewById(R.id.dateOfBirth);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);

//                Toast.makeText(getApplicationContext(), "ca",
//                        Toast.LENGTH_SHORT)
//                        .show();
            }
        });
        sharedPreferences=getSharedPreferences(Shared.profile_info,MODE_PRIVATE);
        userId=sharedPreferences.getString(Shared.id,"");
        Log.e("UserId",userId);


        if(!userId.isEmpty())
        {
            Intent intent = new Intent(LoginScreen1.this, HomeScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        else{
        btn_facebook=(Button)findViewById(R.id.btn_facebook);
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fbInit();
//                LoginManager.getInstance().logInWithReadPermissions(
//
//                        LoginScreen1.this,
//                        Arrays.asList("public_profile","user_birthday" ,"user_friends", "email"));
                popup();
            }
        });
        userName=(EditText)findViewById(R.id.userName);
        male=(RadioButton)findViewById(R.id.male);
        female=(RadioButton)findViewById(R.id.female);
       radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rd, @IdRes int checkedId) {
                for (int i = 0; i < rd.getChildCount(); i++) {
                    radio_button = (RadioButton) rd.getChildAt(i);
                    int id = radio_button.getId();
                    if (radio_button.getId() == checkedId) {
                        gender = radio_button.getText().toString();
                        // return;
                    }
                }
            }
        });
        txt_continue=(TextView)findViewById(R.id.continuescreen);
        txt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_userName = userName.getText().toString();
                str_dateofBirth=dateView.getText().toString();
                if (str_userName.isEmpty()) {

                    AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen1.this).create();
                    alertDialog.setMessage(" Gib deine Namen ein");
                    alertDialog.setButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                }
                            });

                    alertDialog.show();
                }
               else if (gender==null) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen1.this).create();
                    alertDialog.setMessage("Wähle ein Geschlecht");
                    alertDialog.setButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                }
                            });

                    alertDialog.show();
                }
                    else {
                    Intent i = new Intent(LoginScreen1.this, LoginScreen2.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }}

//facebook integration
    public void getKryHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.darshan.nepal.facebookloginexample", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.d("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }



    public void fbInit() {
        getKryHash();

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {
                                        // Application code
                                        Log.v("LoginActivity",
                                                response.toString());
                                        try {
                                            String email=null;
                                            String fbBirthday = null;

                                            String name = object
                                                    .getString("name");
                                            try {
                                                email = object.getString("email");
                                                fbBirthday = object.getString("birthday");



                                            }

                                            catch (Exception e)
                                            {}
                                            String profile=object.getString("picture");
                                            JSONObject pic_object1= (JSONObject) new JSONTokener(profile).nextValue();

                                            JSONObject pic_object2 = pic_object1.getJSONObject("data");
                                            profile = (String) pic_object2.get("url");

                                          //  String phonenumber=object.getString("phone");
                                         //   socialLogin(name,email);
                                            //Inten use here for nxt activity
                                        //    Toast.makeText(getBaseContext(), name+":"+email, Toast.LENGTH_SHORT).show();
                                            Log.d("NAME Email phonenumber", name+":"+email);
                                            Log.e("Profile", profile);
                                            Log.e("Birthday",fbBirthday);

//
//
// SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
//                                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                                           editor.putString(Shared.name, name);
//                                           editor.putString(Shared.email, email);
//                                            editor.putString(Shared.photo,profile);
//                                            editor.commit();

                                            // if(!email.isEmpty()){
//                                            Intent intent = new Intent(L, Welcome.class);
//                                            startActivity(intent);
//                                            Intent i=new Intent(LoginScreen1.this,HomeScreen.class);
//                                            startActivity(i);
//                                        finish();
                                            // }
                                            fbProcess(name,email,profile,fbBirthday);
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields",
                                "id,name,email,gender, picture.type(large),birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {



                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    //facebookdata save into the server
    private void fbProcess(final String name,final String email,final String profile,final String dateofBirt) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.Facebook_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Map<String, String> map = new HashMap<>();


        map.put("name", name);
        map.put("phonenumber", email);
        map.put("photo",profile);
        map.put("dateofbirth",dateofBirt);





        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.getData(Constant.Facebook_URL, map);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, retrofit2.Response<ArrayList<MeinFriseurModuleHelper>> response) {

                Log.e("response", response.body().size() + "");
                //progressDialog.dismiss();
//                Log.e("Id",response.body().get(0).getId());
             //  Log.e("Name",response.body().get(0).getName());
                //Log.e("Email",response.body().get(0).getEmail());

             if(response.body().get(0).getResult().equalsIgnoreCase("1"))
                {
                    Toast.makeText(LoginScreen1.this,response.body().get(0).getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("response",response.body().get(0).getMessage());
                   // Log.e("response",response.body().get(0).getPhoto());

//                    Log.e("name",response.body().get(0).getName());
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(Shared.id,response.body().get(0).getId());
                    editor.putString(Shared.name,response.body().get(0).getName());
                    editor.putString(Shared.email, response.body().get(0).getEmail());
                    editor.putString(Shared.photo,response.body().get(0).getPhoto());

                    //  editor.putString(Shared.photo,response.body().get(0).getPhoto());

                    // editor.putString(Shared.profile_address,userlocation);
//                    editor.putString(Shared.name,name);
//                    editor.putString(Shared.profile_pic,pic);
                    //  editor.putString(Shared.gender,gender);
                    editor.apply();

                }
                if(response.body().get(0).getResult().equalsIgnoreCase("2"))
                {

                    Toast.makeText(LoginScreen1.this,response.body().get(0).getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("response",response.body().get(0).getMessage());
                //    Log.e("resp photo",response.body().get(0).getPhoto());

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(Shared.id,response.body().get(0).getId());
                    editor.putString(Shared.name,response.body().get(0).getName());
                    editor.putString(Shared.photo,response.body().get(0).getPhoto());
//                    // editor.putString(Shared.profile_address,userlocation);
//                    editor.putString(Shared.name,name);
//                    editor.putString(Shared.profile_pic,pic);
                    // editor.putString(Shared.gender,gender);
                    editor.apply();

                }


                meinFriseurModuleArrayList=response.body();
                Log.e("Result",response.body().get(0).getResult());
                Log.e("Message",response.body().get(0).getMessage());

                Intent i=new Intent(LoginScreen1.this,HomeScreen.class);
                startActivity(i);
                finish();

            }


            @Override
            public void onFailure(Call<ArrayList<MeinFriseurModuleHelper>> call, Throwable t) {
                //progress.setVisibility(View.GONE);

                //   progressDialog.dismiss();
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private void popup() {




        final Dialog alert = new Dialog(LoginScreen1.this);

        alert.setContentView(R.layout.fbmessage);
       TextView ok=(TextView)alert.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbInit();
                LoginManager.getInstance().logInWithReadPermissions(

                        LoginScreen1.this,
                        Arrays.asList("public_profile","user_birthday" ,"user_friends", "email"));
            }
        });
        TextView cancel=(TextView)alert.findViewById(R.id.cancel_action);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(true);
        alert.show();

    }
}
