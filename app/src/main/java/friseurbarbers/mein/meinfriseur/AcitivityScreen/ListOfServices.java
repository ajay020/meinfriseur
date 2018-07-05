package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import friseurbarbers.mein.meinfriseur.Adapter.SalonListAdapter;
import friseurbarbers.mein.meinfriseur.Adapter.SalonServiceList;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;

public class ListOfServices extends AppCompatActivity {
Toolbar toolbar;
    TextView salon_Name;
    MeinFriseurModule meinFriseurModule;
    RecyclerView service_List_Recycler;
    List<MeinFriseurModule> meinFriseurModuleList;
    SalonServiceList salonServiceList;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_services);
        sharedPreferences=getSharedPreferences(Constant.Shared, Context.MODE_PRIVATE);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
            }
        });
        salon_Name=(TextView)findViewById(R.id.salon_Name);
        if(getIntent().getSerializableExtra("SalonName")==null){

            salon_Name.setText(sharedPreferences.getString("title",""));
        }else {
            meinFriseurModule = (MeinFriseurModule) getIntent().getSerializableExtra("SalonName");
            salon_Name.setText(meinFriseurModule.getSalontitle());
        }
        service_List_Recycler=(RecyclerView)findViewById(R.id.serviceList_Recycler);
        initView();
    }
    public void initView()
    {
        service_List_Recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListOfServices.this);
        service_List_Recycler.setLayoutManager(layoutManager);
        data();
        salonServiceList=new SalonServiceList(ListOfServices.this,meinFriseurModuleList);
        service_List_Recycler.setAdapter(salonServiceList);

    }
    public void data()
    {
        meinFriseurModuleList=new ArrayList<MeinFriseurModule>();
        MeinFriseurModule meinFriseurModule=new MeinFriseurModule("Haare schneiden",R.drawable.haircutformen);
        meinFriseurModuleList.add(meinFriseurModule);
        MeinFriseurModule meinFriseurModule1=new MeinFriseurModule("Haare färben",R.drawable.hairdie);
        meinFriseurModuleList.add(meinFriseurModule1);
        MeinFriseurModule meinFriseurModule2=new MeinFriseurModule("Rasieren",R.drawable.saving);
        meinFriseurModuleList.add(meinFriseurModule2);
        MeinFriseurModule meinFriseurModule3=new MeinFriseurModule("Frisur",R.drawable.hairstyle);
        meinFriseurModuleList.add(meinFriseurModule3);
    }
}
