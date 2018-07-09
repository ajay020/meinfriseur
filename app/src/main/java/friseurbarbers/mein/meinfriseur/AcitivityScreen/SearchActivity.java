package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import friseurbarbers.mein.meinfriseur.Adapter.SearchAdapter;
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

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<MeinFriseurModuleHelper> searchDataList = new ArrayList();
    SearchAdapter adapter;
    ProgressDialog progressDialog;
    private String majorJsonStr;
    private String specificJsonStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getJsonString();
        progressDialog = new ProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getSearchData();
        adapter = new SearchAdapter(this, searchDataList);
        recyclerView.setAdapter(adapter);
    }

    private void getJsonString() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            majorJsonStr = bundle.getString(Shared.MAJOR_JSON_KEY);
            specificJsonStr = bundle.getString(Shared.SPECIFIC_JSON_KEY);
        }
    }

    private void getSearchData() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.search_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Map<String, String> map = new HashMap<>();
        Log.v("specificjson",specificJsonStr+"");
        Log.v("majorjson",majorJsonStr+"");

        map.put("specific", specificJsonStr);
        map.put("major", majorJsonStr);

        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.getData(Constant.search_URL, map);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, Response<ArrayList<MeinFriseurModuleHelper>> response) {
                progressDialog.dismiss();
                if (response != null) {
                    Log.v("response",response+"");
                    ArrayList<MeinFriseurModuleHelper> body = response.body();
                   // Log.v("bodysize",body.size()+"");
                    if (body != null) {
                        searchDataList.addAll(body);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getApplicationContext(),"body is null",Toast.LENGTH_SHORT).show();
                    }
                }

            }


            @Override
            public void onFailure(Call<ArrayList<MeinFriseurModuleHelper>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "couldn't fetch data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
