package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.RequestInterface;
import friseurbarbers.mein.meinfriseur.Retrofit.Shared;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginScreen4 extends AppCompatActivity {
EditText password,conf_password;
String str_username,str_number,str_password,str_confPassword;
    TextView signUp;
    ArrayList<MeinFriseurModuleHelper> meinFriseurModuleArrayList=new ArrayList<MeinFriseurModuleHelper>();
ProgressDialog progressDialog;
    SharedPreferences sharedPreferences1;
   public static String profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen4);
        sharedPreferences1=getSharedPreferences(Shared.profile_info,MODE_PRIVATE);

        progressDialog = new ProgressDialog(LoginScreen4.this);
      progressDialog.setMessage("Warten Sie mal");
        password=(EditText)findViewById(R.id.password);
        conf_password=(EditText)findViewById(R.id.conf_password);
        signUp=(TextView)findViewById(R.id.singup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_username=LoginScreen1.userName.getText().toString();
                str_number=LoginScreen2.number.getText().toString();
                str_password=password.getText().toString();
                str_confPassword=conf_password.getText().toString();
                profilepic=LoginScreen3.imagePath;

            if(str_password.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen4.this).create();
                    alertDialog.setMessage("Bitte gib ein Passwort ein");
                    alertDialog.setButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                }
                            });

                    alertDialog.show();


                }
                else if(str_confPassword.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen4.this).create();
                    alertDialog.setMessage("Bitte bestätige dein Passwort");
                    alertDialog.setButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                }
                            });

                    alertDialog.show();


                }
                else if(!str_confPassword.equals(str_password) ){
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen4.this).create();
                    alertDialog.setTitle("oops!");
                    alertDialog.setMessage("Deine Passwörter stimmen nicht überein");
                    alertDialog.setButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                }
                            });
                    alertDialog.show();
                }
                else {
//                  if (!str_username.isEmpty() && LoginScreen1.gender.isEmpty() && str_number.isEmpty() && str_password.isEmpty())
//                  {
                                Log.e("Register","Register");
                    progressDialog.show();
                register(str_username, LoginScreen1.gender, str_number, profilepic,LoginScreen1.str_dateofBirth,str_password);

                    //}

                }
            }
        });
    }
    private void register(final String name,final String gender ,String number,String profilepic,String dateofbirth,String password)
    {

        profilepic= sharedPreferences1.getString(Shared.encrypt_pic,"");
        final String pic=profilepic;


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.REGISTER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Map<String,String> map=new HashMap<>();
        map.put("name", name);
        map.put("gender", gender);
        map.put("phonenumber", number);
        map.put("dateofbirth",dateofbirth);
        map.put("password", password);
        Log.e("kya hua","kya hua");

        File file=new File(profilepic);
        final RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestBody);

            Log.e("ImageName",file.getName());
        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.updateProfile(Constant.REGISTER_URL,map,body);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, Response<ArrayList<MeinFriseurModuleHelper>> response) {

                //  progressDialog.dismiss();
                Log.e("Sucess","Sucess");
                Log.e("response", response.body().size()+"");
//            Log.e("id",response.body().get(0).getSid());
                Log.e("Name", response.body().get(0).getName()+"");
                Log.e("Profile",response.body().get(0).getPhoto()+"");

                // Log.e("response", response.body().get(0).getGender()+"");

                progressDialog.dismiss();
//                Intent i=new Intent(LoginScreen4.this,HomeScreen.class);
//                startActivity(i);
                if(response.body().get(0).getResult().equalsIgnoreCase("1"))
                {
                    Toast.makeText(LoginScreen4.this,response.body().get(0).getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("response",response.body().get(0).getMessage());
//                    Log.e("name",response.body().get(0).getName());
                    SharedPreferences.Editor editor=sharedPreferences1.edit();
                    editor.putString(Shared.id,response.body().get(0).getId());
                    editor.putString(Shared.name,response.body().get(0).getName());
                    editor.putString(Shared.email, response.body().get(0).getEmail());
                    editor.putString(Shared.number,response.body().get(0).getPhonenumber());
                    editor.putString(Shared.dateofbirth,response.body().get(0).getDateofbirth());
                    editor.putString(Shared.photo,response.body().get(0).getPhoto());
                    editor.putString(Shared.gender,response.body().get(0).getGender());

                    // editor.putString(Shared.profile_address,userlocation);
//                    editor.putString(Shared.name,name);
//                    editor.putString(Shared.profile_pic,pic);
                    //  editor.putString(Shared.gender,gender);
                    editor.apply();

                }
                if(response.body().get(0).getResult().equalsIgnoreCase("2"))
                {

                    Toast.makeText(LoginScreen4.this,response.body().get(0).getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("response",response.body().get(0).getMessage());

                    SharedPreferences.Editor editor=sharedPreferences1.edit();
                    editor.putString(Shared.id,response.body().get(0).getId());
                    editor.putString(Shared.name,response.body().get(0).getName());
//                    // editor.putString(Shared.profile_address,userlocation);
//                    editor.putString(Shared.name,name);
//                    editor.putString(Shared.profile_pic,pic);
                    // editor.putString(Shared.gender,gender);
                    editor.apply();

                }

                    Intent i=new Intent(LoginScreen4.this,HomeScreen.class);
                    startActivity(i);
                    finish();

                meinFriseurModuleArrayList=response.body();

            }

            @Override
            public void onFailure(Call<ArrayList<MeinFriseurModuleHelper>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error","error");
                Log.e("ErrorResponse",t.getMessage());
//                Intent i=new Intent(LoginScreen4.this,HomeScreen.class);
//                startActivity(i);
//                finish();

//                progressDialog.dismiss();
                //   Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

}
