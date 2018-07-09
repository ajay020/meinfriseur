package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
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

    TextView tvMajor, tvSpecific, tvGender, tvSelectedMajorItem, tvSelectedSpecificItem, tvSearch;
    List<MeinFriseurModuleHelper> data;
    String majorItems[];
    String specificItems[];
    boolean majorCheckedItems[];
    boolean specificCheckedItems[];
    private String gender;
    private ProgressDialog progressDialog;
    private List<MeinFriseurModuleHelper> responselist = new ArrayList<>();
    private List<String> selectedSpecificItemList = new ArrayList<>();
    private List<String> selectedMajorItemList = new ArrayList<>();
    private String majorOrSpecific = "";
    private String selectedItemString = "";
    private String[] savedMajorItems = null;
    private boolean[] savedMajorCheckedItems = null;
    private String[] savedSpecificItems = null;
    private boolean[] savedSpecificCheckedItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);
        data = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        tvMajor = (TextView) findViewById(R.id.tv_major);
        tvSpecific = (TextView) findViewById(R.id.tv_specific);
        tvGender = (TextView) findViewById(R.id.tv_gender);
        tvSelectedMajorItem = (TextView) findViewById(R.id.tv_major_item_selected);
        tvSelectedSpecificItem = (TextView) findViewById(R.id.tv_specific_item_selected);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        tvSearch.setOnClickListener(this);
        tvMajor.setOnClickListener(this);
        tvSpecific.setOnClickListener(this);
        getGenderFromPreference();
        getListDataFromPreference();
    }

    private void getListDataFromPreference() {
        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String majorItemStr = preferences.getString(Shared.MAJOR_ITEM_LIST, null);
        String majorCheckdStr = preferences.getString(Shared.MAJOR_CHECKED_ITEMS, null);
        String specificItemStr = preferences.getString(Shared.SPECIFIC_ITEM_LIST, null);
        String specificCheckedStr = preferences.getString(Shared.SPECIFIC_CHECKED_ITEMS, null);


        if (majorItemStr != null && majorCheckdStr != null) {
            majorOrSpecific = "major";
            Type type1 = new TypeToken<String[]>() {
            }.getType();
            Type type2 = new TypeToken<boolean[]>() {
            }.getType();
            savedMajorItems = gson.fromJson(majorItemStr, type1);
            savedMajorCheckedItems = gson.fromJson(majorCheckdStr, type2);

            if(savedMajorItems != null && savedMajorCheckedItems != null) {
                setTextToSelectedItem(savedMajorItems, savedMajorCheckedItems, majorOrSpecific);
                majorItems = new String[savedMajorItems.length];
                majorCheckedItems = new boolean[savedMajorCheckedItems.length];
                for(int i=0; i<savedMajorCheckedItems.length; i++){
                    majorCheckedItems[i] = savedMajorCheckedItems[i];
                    majorItems[i] = savedMajorItems[i];
                }
            }

//            Log.v("savedString", majorItemStr + majorCheckdStr);
//            Toast.makeText(this, list1.size() + "", Toast.LENGTH_SHORT).show();
        }
        if (specificItemStr != null && specificCheckedStr != null) {
            majorOrSpecific = "specific";
            Type type3 = new TypeToken<String[]>() {
            }.getType();
            Type type4 = new TypeToken<boolean[]>() {
            }.getType();
            savedSpecificItems = gson.fromJson(specificItemStr, type3);
            savedSpecificCheckedItems = gson.fromJson(specificCheckedStr, type4);

            if(savedSpecificItems != null && savedSpecificCheckedItems != null) {
                setTextToSelectedItem(savedSpecificItems, savedSpecificCheckedItems, majorOrSpecific);
                specificItems = new String[savedSpecificItems.length];
                specificCheckedItems = new boolean[savedSpecificCheckedItems.length];
                for(int i=0; i<savedSpecificCheckedItems.length; i++){
                    specificItems[i] = savedSpecificItems[i];
                    specificCheckedItems[i] = savedSpecificCheckedItems[i];
                }
            }
        }

    }

    private void setTextToSelectedItem(String[] selectedItems, boolean[] checkedItems, String majorOrSpecific) {
          selectedItemString = "";
        for (int i = 0; i < selectedItems.length; i++) {
            if (checkedItems[i] == true) {
                    selectedItemString = selectedItemString + selectedItems[i] + " ,";
            }
        }
        if (majorOrSpecific.equals("major")) {
            tvSelectedMajorItem.setText(selectedItemString);
        } else if (majorOrSpecific.equals("specific")) {
            tvSelectedSpecificItem.setText(selectedItemString);
        }
    }

    private void getGenderFromPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(Shared.profile_info, MODE_PRIVATE);
        gender = sharedPreferences.getString(Shared.gender, "");
        Log.e("genderdata", gender);
        if (!gender.equals("")) {
            tvGender.setText(gender);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_major:

                majorOrSpecific = "major";
                if (savedMajorCheckedItems != null && savedMajorItems != null) {
                    openDialog(savedMajorItems, savedMajorCheckedItems);
                } else {
                    getMajorListData();
                }

                break;
            case R.id.tv_specific:
                majorOrSpecific = "specific";
                if (savedSpecificItems != null && savedSpecificCheckedItems != null) {
                    openDialog(savedSpecificItems, savedSpecificCheckedItems);
                } else {
                    getSpecificListData();
                }

                break;
            case R.id.tv_search:
                Gson gson = new Gson();
                String majorJsonStr = gson.toJson(selectedMajorItemList);
                String specificJsonStr = gson.toJson(selectedSpecificItemList);
                Intent searchIntent = new Intent(this, SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Shared.MAJOR_JSON_KEY, majorJsonStr);
                bundle.putString(Shared.SPECIFIC_JSON_KEY, specificJsonStr);
                searchIntent.putExtras(bundle);

                startActivity(searchIntent);
                break;
        }
    }


    private void getSpecificListData() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.specficlist_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Map<String, String> map = new HashMap<>();
        map.put("gender", gender);

        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.getData(Constant.specficlist_URL, map);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, Response<ArrayList<MeinFriseurModuleHelper>> response) {
                progressDialog.dismiss();
                if (response != null) {
                    responselist = response.body();
                    Log.v("responsesize", responselist.size() + "");
                    Log.e("response", response.body().size() + "");
                    if (responselist != null) {
                        data.clear();
                        data.addAll(responselist);
                        addDataToSpecificList();
                        openDialog(specificItems, specificCheckedItems);
                    }

                }

            }

            private void addDataToSpecificList() {
                specificItems = new String[data.size()];
                specificCheckedItems = new boolean[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    String service = data.get(i).getService();
                    specificItems[i] = service;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MeinFriseurModuleHelper>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "couldn't fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMajorListData() {
        progressDialog.show();
        Log.e("genderdata", gender);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.majorlist_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Map<String, String> map = new HashMap<>();
        map.put("gender", gender);

        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.getData(Constant.majorlist_URL, map);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, Response<ArrayList<MeinFriseurModuleHelper>> response) {
                progressDialog.dismiss();
                if (response != null) {
                    responselist = response.body();
                    Log.v("responsesize", responselist.size() + "");
                    Log.e("response", response.body().size() + "");
                    if (responselist != null) {
                        data.clear();
                        data.addAll(responselist);
                        addDataToMajorList();
                        openDialog(majorItems, majorCheckedItems);
                    }

                }

            }

            private void addDataToMajorList() {
                majorItems = new String[data.size()];
                majorCheckedItems = new boolean[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    String service = data.get(i).getService();
                    majorItems[i] = service;
                }

            }

            @Override
            public void onFailure(Call<ArrayList<MeinFriseurModuleHelper>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "couldn't fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openDialog(final String[] itemList, final boolean[] checkedItems) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Items");
        //selectedItemList.clear();

        builder.setMultiChoiceItems(itemList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                // user checked or unchecked a box
                if (majorOrSpecific.equals("major")) {
                    if (isChecked == true) {
                        // selectedItemList.add(itemList[position]);
                        majorCheckedItems[position] = true;
                    } else {
                        //selectedItemList.remove(itemList[position]);
                        majorCheckedItems[position] = false;
                    }
                } else if (majorOrSpecific.equals("specific")) {
                    if (isChecked == true) {
                        //selectedItemList.add(itemList[position]);
                        specificCheckedItems[position] = true;

                    } else {
                        //selectedItemList.remove(itemList[position]);
                        specificCheckedItems[position] = false;
                    }
                }
            }
        });

// add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
//                Log.v("selectedlist", selectedItemList + "");
                selectedItemString = "";


                if (majorOrSpecific.equals("major")) {
                    saveDataIntoPreference(majorItems, majorCheckedItems, majorOrSpecific);
                    // selectedMajorItemList.clear();
                    //selectedMajorItemList.addAll(selectedItemList);
                    setTextToSelectedItem(majorItems, majorCheckedItems, majorOrSpecific);


                } else if (majorOrSpecific.equals("specific")) {
                    saveDataIntoPreference(specificItems, specificCheckedItems, majorOrSpecific);
//                    selectedSpecificItemList.clear();
//                    selectedSpecificItemList.addAll(selectedItemList);
                    setTextToSelectedItem(specificItems, specificCheckedItems, majorOrSpecific);
                }

            }
        });
        builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveDataIntoPreference(String[] items, boolean[] checkedItems, String majorOrSpecific) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();

        String itemListJsonStr = gson.toJson(items);
        String checkedListJsonStr = gson.toJson(checkedItems);

        if (majorOrSpecific.equals("major")) {
            editor.putString(Shared.MAJOR_ITEM_LIST, itemListJsonStr);
            editor.putString(Shared.MAJOR_CHECKED_ITEMS, checkedListJsonStr);
        } else if (majorOrSpecific.equals("specific")) {
            editor.putString(Shared.SPECIFIC_ITEM_LIST, itemListJsonStr);
            editor.putString(Shared.SPECIFIC_CHECKED_ITEMS, checkedListJsonStr);
        }

        editor.apply();
    }
}