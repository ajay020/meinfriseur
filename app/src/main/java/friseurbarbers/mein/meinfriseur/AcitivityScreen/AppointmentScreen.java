package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import friseurbarbers.mein.meinfriseur.Adapter.DateAdapter;
import friseurbarbers.mein.meinfriseur.Adapter.DateModule;
import friseurbarbers.mein.meinfriseur.Fragment.ApiontmentScreen;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;

public class AppointmentScreen extends AppCompatActivity {
    Toolbar toolbar;
    TextView service_Name;
    ImageView service_Image;
    MeinFriseurModule meinFriseurModule;
    RecyclerView recyclerView;
    DateAdapter dateAdapter;
    List<DateModule> dateModuleslist;
    ImageView back_Arrow;
    ArrayList<DateModule> dateModules=new ArrayList<DateModule>();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_screen);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences(Constant.Shared,MODE_PRIVATE);
        if(!sharedPreferences.getString("ServiceList","").isEmpty()){
            Log.e("hello","hello");

          //  meinFriseurModule=new Gson().fromJson(sharedPreferences.getString("ServiceList",""), new TypeToken<MeinFriseurModule>(){}.getType());
            meinFriseurModule=new Gson().fromJson(sharedPreferences.getString("ServiceList",""), MeinFriseurModule.class);

        }/*else{

            String data=getIntent().getStringExtra("ServiceList");

            Log.e("ServiceList",data);
            dateModules=new Gson().fromJson(data, new TypeToken<ArrayList<DateModule>>(){}.getType());
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(Constant.ServiceList,new Gson().toJson(dateModules));
            editor.apply();
        }*/


        back_Arrow=(ImageView)findViewById(R.id.back);
        back_Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AppointmentScreen.this,ListOfServices.class);
                startActivity(i);
            }
        });
        service_Name=(TextView)findViewById(R.id.service_Name);
        service_Image=(ImageView)findViewById(R.id.imageview_placeholder);
      //  meinFriseurModule=(MeinFriseurModule)getIntent().getSerializableExtra("ServiceList");
        service_Image.setImageResource(meinFriseurModule.getImage());
        service_Name.setText(meinFriseurModule.getSalontitle());
        recyclerView=(RecyclerView)findViewById(R.id.date_recyclerview);
        initView();
        Fragment fragment=new ApiontmentScreen();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.commit();

    }
    public void initView()
    {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(AppointmentScreen.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        data();
        dateAdapter=new DateAdapter(AppointmentScreen.this,dateModuleslist);
        recyclerView.setAdapter(dateAdapter);

    }
    public void data()
    {
        dateModuleslist=new ArrayList<DateModule>();
        for(int i=0;i<7;i++) {
            DateModule dateModule = new DateModule(i);

            dateModuleslist.add(dateModule);
        }
    }
}
