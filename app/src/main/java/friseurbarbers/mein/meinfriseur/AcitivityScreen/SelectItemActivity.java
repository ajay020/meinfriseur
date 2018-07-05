package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.RequestInterface;
import friseurbarbers.mein.meinfriseur.Retrofit.Shared;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectItemActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvMajor, tvSpecific , tvGender;
    List<MeinFriseurModuleHelper> data ;
    String itemList[] ;
    boolean checkedItems[] ;
    private SharedPreferences sharedPreferences;
    private String gender;
    private ProgressDialog progressDialog ;
    private List<MeinFriseurModuleHelper> responselist=new ArrayList<>();
    private List<String> selectedItem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);
        data = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        tvMajor = (TextView) findViewById(R.id.tv_major);
        tvSpecific = (TextView) findViewById(R.id.tv_specific);
        tvGender = (TextView) findViewById(R.id.tv_gender);
        tvMajor.setOnClickListener(this);
        tvSpecific.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(Shared.profile_info,MODE_PRIVATE);
        getGenderFromPreference();
    }

    private void getGenderFromPreference() {
        gender = sharedPreferences.getString(Shared.gender, "");
        Log.e("genderdata",gender);
        if(!gender.equals("")){
            tvGender.setText(gender);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_major:
                progressDialog.show();
                getMajorListData();

                break;
            case R.id.tv_specific:
                progressDialog.show();
                getSpecificListData();
                break;
        }
    }

    private void getSpecificListData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.specficlist_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Map<String, String> map = new HashMap<>();
        map.put("gender",gender);

        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.getData(Constant.specficlist_URL, map);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, Response<ArrayList<MeinFriseurModuleHelper>> response) {
                progressDialog.dismiss();
                if(response != null){
                    responselist = response.body();
                    Log.v("responsesize",responselist.size()+"");
                    Log.e("response", response.body().size() + "");
                    if(responselist != null) {
                        data.clear();
                        data.addAll(responselist);
                        addDataToSpecificList();
                        openDialog(itemList, checkedItems);
                    }

                }

            }

            private void addDataToSpecificList() {
                itemList = new String[data.size()];
                checkedItems = new boolean[data.size()];
                for(int i=0; i<data.size(); i++){
                    String service = data.get(i).getService();
                    itemList[i] = service;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MeinFriseurModuleHelper>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"couldn't fetch data",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMajorListData() {
        Log.e("genderdata",gender);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.majorlist_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Map<String, String> map = new HashMap<>();
        map.put("gender",gender);

        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.getData(Constant.majorlist_URL, map);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, Response<ArrayList<MeinFriseurModuleHelper>> response) {
                progressDialog.dismiss();
                if(response != null){
                    responselist = response.body();
                    Log.v("responsesize",responselist.size()+"");
                    Log.e("response", response.body().size() + "");
                    if(responselist != null) {
                        data.clear();
                        data.addAll(responselist);
                        addDataToMajorList();
                        openDialog(itemList, checkedItems);
                    }

                }

            }

            private void addDataToMajorList() {
                itemList = new String[data.size()];
                checkedItems = new boolean[data.size()];
                for(int i=0; i<data.size(); i++){
                    String service = data.get(i).getService();
                    itemList[i] = service;
                }

            }

            @Override
            public void onFailure(Call<ArrayList<MeinFriseurModuleHelper>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"couldn't fetch data",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openDialog(final String[] itemList, boolean[] checkedItems) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Items");

        builder.setMultiChoiceItems(itemList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                // user checked or unchecked a box
                if (isChecked == true) {
                     Toast.makeText(getApplicationContext()," selected ="+ position,Toast.LENGTH_SHORT).show();
                     selectedItem.add(itemList[position]);
                } else {
                     Toast.makeText(getApplicationContext()," deselected ="+position,Toast.LENGTH_SHORT).show();
                     selectedItem.remove(itemList[position]);
                }

            }
        });

// add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                Toast.makeText(getApplicationContext(),"selectedListSize = "+selectedItem.size(),Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
/*
    private void selectSpecificItem() {

        AlertDialog.Builder specificAlertDialog = new AlertDialog.Builder(SelectItemActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_specfic_layout, null);
        specificAlertDialog.setView(view);
        specificAlertDialog.setTitle("Select Specific Item");
        ListView listViewSpecific = (ListView) view.findViewById(R.id.lv_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, specificList);
        listViewSpecific.setAdapter(adapter);
        specificAlertDialog.show();
    }*/
/*
    private void selectMjorItem() {
      *//*  AlertDialog.Builder majorAlertDialog = new AlertDialog.Builder(SelectItemActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_specfic_layout, null);
        majorAlertDialog.setView(view);
        majorAlertDialog.setTitle("Select Major Item");
        ListView listViewMajor = view.findViewById(R.id.lv_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemList);
        listViewMajor.setAdapter(arrayAdapter);
        majorAlertDialog.setItems(itemList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        majorAlertDialog.show();*//*

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose some animals");

// add a checkbox list
        final String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
        boolean[] checkedItems = new boolean[animals.length];
        builder.setMultiChoiceItems(animals, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                // user checked or unchecked a box
                if(isChecked == true){
                   // Toast.makeText(getApplicationContext(),animals[position]+" selected "+isChecked,Toast.LENGTH_SHORT).show();
                }else{
                   // Toast.makeText(getApplicationContext(),animals[position]+" deselected "+isChecked,Toast.LENGTH_SHORT).show();
                }

            }
        });

// add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
               // Toast.makeText(getApplicationContext(),which+"",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/
}