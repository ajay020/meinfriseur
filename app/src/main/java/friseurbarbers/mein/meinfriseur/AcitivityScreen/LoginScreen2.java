package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import friseurbarbers.mein.meinfriseur.R;

public class LoginScreen2 extends AppCompatActivity {
TextView continuescreen;
   public static EditText number;
    String str_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen2);
        number=(EditText)findViewById(R.id.number);
        continuescreen=(TextView)findViewById(R.id.continuescreen2);
        continuescreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_number=number.getText().toString();

                if(str_number.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen2.this).create();
                    alertDialog.setMessage("Gib eine gültige Handynummer ein");
                    alertDialog.setButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                }
                            });

                    alertDialog.show();


                }

                else if(str_number.length()<20 ||str_number.length()>20){
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen2.this).create();
                    alertDialog.setMessage("Gib eine gültige Handynummer ein");
                    alertDialog.setButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                }
                            });

                    alertDialog.show();


                }
                else {
                    Intent i = new Intent(LoginScreen2.this, LoginScreen3.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
