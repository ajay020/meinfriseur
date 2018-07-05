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

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.Shared;

public class LoginScreen3 extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen3);
        sharedPreferences1=getSharedPreferences(Shared.profile_info,MODE_PRIVATE);

        imageView=(CircleImageView)findViewById(R.id.profile_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        txt_continue=(TextView)findViewById(R.id.continuescreen3);
        txt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagePath==null) {

                    AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen3.this).create();
                    alertDialog.setMessage("Lade ein Profilfoto hoch");
                    alertDialog.setButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                }
                            });

                    alertDialog.show();
                }
                else {
                    Intent i = new Intent(LoginScreen3.this, LoginScreen4.class);
                    startActivity(i);
                    finish();
                }
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
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(LoginScreen3.this);
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
//                            imageView.setImageBitmap(bitmap);


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


}
