package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
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

import static java.security.AccessController.getContext;

public class UpdateProfile extends AppCompatActivity {
    TextView txt_continue;
    CircleImageView imageView;
    FloatingActionButton camera_button;
    Bitmap bitmap=null;
    Uri uri=null;
    public static final int RESULT_CROP=1001;
    private Uri picUri=null;
    final int CROP_PIC = 3;
    String editUserProfile="null";
    ProgressDialog progressDialog;
    public static String imagePath;
    SharedPreferences sharedPreferences,sharedPreferences1;
    String profile;
    String profilepic;

    String profileimage;
    public static String str_id;
    ArrayList<MeinFriseurModuleHelper> meinFriseurModuleArrayList=new ArrayList<MeinFriseurModuleHelper>();
String name,str_dteofbirth,dtr_number;
    TextView txtname,dateofbirth,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        progressDialog=new ProgressDialog(UpdateProfile.this);
        progressDialog.setTitle("Plese wait");
        sharedPreferences1 = getSharedPreferences(Shared.profile_info, MODE_PRIVATE);

        sharedPreferences = getSharedPreferences(Shared.profile_info, MODE_PRIVATE);
        name = sharedPreferences.getString(Shared.name, "");
        str_dteofbirth=sharedPreferences.getString(Shared.dateofbirth,"");
        dtr_number=sharedPreferences.getString(Shared.number, "");
        txtname=(TextView)findViewById(R.id.name);
        txtname.setText(name);
        dateofbirth=(TextView)findViewById(R.id.dateOfBirth);
        dateofbirth.setText(str_dteofbirth);

        number=(TextView)findViewById(R.id.number);
        number.setText(dtr_number);

        profileimage=sharedPreferences.getString(Shared.photo,"");
        Bitmap bm = StringToBitMap(profileimage);
         str_id=sharedPreferences.getString(Shared.id,"");

        Log.e("updateid",str_id);
     //   profile = sharedPreferences1.getString(Shared.photo, "");
        Log.e("Updatename",name);
        imageView=(CircleImageView)findViewById(R.id.profile_image);
        Picasso.with(getApplicationContext()).load(profileimage).resize(2000, 2000).skipMemoryCache().into(imageView);
     imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 selectImage();
            }
        });
        txt_continue=(TextView)findViewById(R.id.update);
        txt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilepic=imagePath;
                String updatename=name;



                    updateProfile(str_id,updatename,profilepic);
                    progressDialog.show();

            }
        });
        camera_button=(FloatingActionButton)findViewById(R.id.fab);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }}
    private void selectImage()
    {
        final CharSequence[] items = {
                "Foto machen","Aus der Bibliothek auswählen",
                "Abbrechen" };
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(UpdateProfile.this);
        builder.setTitle("Bild hinzufügen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                if(items[item].equals("Foto machen"))
                {
                    cameraIntent();
                }
                if(items[item].equals("Aus der Bibliothek auswählen"))
                {
                    galleryIntent();
                }
                if(items[item].equals("Abbrechen"))
                {
                    dialogInterface.dismiss();
                }

            }
        });
        builder.show();
    }
    private  void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);


    }
    private  void galleryIntent()
    {
        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("hello","check");
        try {

            if (data != null) {
                if (requestCode == 2) {

                    try {
                        Log.d("hello", "check");
                        uri = data.getData();
                        //uri=CropImage.getPickImageResultUri(getApplication(),data);
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                        startCropImageActivity(uri);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (requestCode == 1) {
                    Log.d("hello", "check");
                    // picUri=data.getData();
                    //  uri = (Uri) data.getExtras().get("data");
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] imageBytes = baos.toByteArray();
                    File file = new File(getCacheDir(), "uri");
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                        fos.write(imageBytes);
                        fos.flush();
                        fos.close();
                        uri = Uri.fromFile(file);
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                        startCropImageActivity(uri);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    startCropImageActivity(uri);

                }
                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Log.e("hello","hello");
                    // circleImageView.setImageURI(result.getUri());
                    try {


                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                        Log.e("bitmap", bitmap + "");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        byte[] imageBytes = baos.toByteArray();


                        imagePath= MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);


                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(Uri.parse(imagePath), filePathColumn, null, null, null);

                        if (cursor != null) {
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imagePath = cursor.getString(columnIndex);

                            Glide.with(getApplicationContext()).load(new File(imagePath)).into(imageView);
//                            SharedPreferences.Editor editor=sharedPreferences1.edit();
//                            editor.putString(Shared.encrypt_pic,imagePath);
//                            editor.apply();
                            imageView.setImageBitmap(bitmap);


                        }else{


                        }







                        editUserProfile = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        SharedPreferences.Editor editor=sharedPreferences1.edit();
                        editor.putString(Shared.encrypt_pic,imagePath);
                        editor.apply();

                        imageView.setImageBitmap(bitmap);
                        //  uploadFile(, "My Image");
                        // updateProfile(editUserProfile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch(Exception e){}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (uri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            //  startCropImageActivity(uri);
        } else {
            // Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    private void startCropImageActivity(Uri uri){



        CropImage.activity(uri).setCropShape(CropImageView.CropShape.OVAL)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }




    private void updateProfile(String id,String name,String profilepic)
    {

        profilepic= sharedPreferences1.getString(Shared.encrypt_pic,"");
        final String pic=profilepic;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.Update_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();




        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("name", name);

        Log.e("kya hua","kya hua");

        File file=new File(profilepic);
        final RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestBody);

        Log.e("ImageName",file.getName());
        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.updateProfile(Constant.Update_URL,map,body);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, Response<ArrayList<MeinFriseurModuleHelper>> response) {

                //  progressDialog.dismiss();
                Log.e("Sucess","Sucess");
                Log.e("response", response.body().size()+"");
                Log.e("upateid",response.body().get(0).getId()+"");
//            Log.e("id",response.body().get(0).getSid());
                Log.e("Name", response.body().get(0).getName()+"");
                Log.e("Profile",response.body().get(0).getPhoto()+"");
                Log.e("Demo","Demo");
                // Log.e("response", response.body().get(0).getGender()+"");

                progressDialog.dismiss();

                if(response.body().get(0).getResult().equalsIgnoreCase("1"))
                {
                    Log.e("Demo1","Demo1");

                    Toast.makeText(UpdateProfile.this,response.body().get(0).getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("response",response.body().get(0).getMessage());
                    Log.e("upateid",response.body().get(0).getId()+"");
                    Log.e("UpdateName", response.body().get(0).getName()+"");
                    Log.e("UpateProfile",response.body().get(0).getPhoto()+"");
                    SharedPreferences.Editor editor=sharedPreferences1.edit();
                    editor.putString(Shared.id,response.body().get(0).getId());
                    editor.putString(Shared.name,response.body().get(0).getName());
                    editor.putString(Shared.photo,response.body().get(0).getPhoto());


                    editor.apply();

                }
                if(response.body().get(0).getResult().equalsIgnoreCase("2"))
                {
                    Log.e("Demo2","Demo2");

                    Toast.makeText(UpdateProfile.this,response.body().get(0).getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("response",response.body().get(0).getMessage());

                    SharedPreferences.Editor editor=sharedPreferences1.edit();
                    editor.putString(Shared.id,response.body().get(0).getId());
                    editor.putString(Shared.name,response.body().get(0).getName());

                    editor.apply();

                }



                meinFriseurModuleArrayList=response.body();
                Intent i=new Intent(UpdateProfile.this,HomeScreen.class);
                startActivity(i);

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



