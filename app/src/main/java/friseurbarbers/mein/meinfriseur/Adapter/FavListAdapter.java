package friseurbarbers.mein.meinfriseur.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import friseurbarbers.mein.meinfriseur.AcitivityScreen.HomeScreen;
import friseurbarbers.mein.meinfriseur.Fragment.HomePage;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.RequestInterface;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aarya on 2/5/2018.
 */

public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.ViewHolder> {

    AppCompatActivity appCompatActivity;
   ArrayList<MeinFriseurModuleHelper> meinFriseurModuleList;

    public FavListAdapter(AppCompatActivity appCompatActivity, ArrayList<MeinFriseurModuleHelper> meinFriseurModules) {
        this.appCompatActivity = appCompatActivity;
        this.meinFriseurModuleList = meinFriseurModules;

    }

    @Override
    public FavListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salonlist, parent, false);/// to inflate the layout here
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(FavListAdapter.ViewHolder holder, int position) {
        final MeinFriseurModuleHelper meinFriseurModule=meinFriseurModuleList.get(position);

        holder.title.setText(meinFriseurModule.getOrganization());
        if(position==0)
        {
           HomePage.dein_title.setText("Dein Favorit");
        }
        if(position==1)
        {
            HomePage.dein_title.setText("Deine Favoriten");
        }
        if(position==2)
        {
            HomePage.additem.setVisibility(View.GONE);
            HomePage.dein_title.setText("Deine Favoriten");

        }
        //holder.imageView.setImageResource(demoModule.getImage());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean b) {


               // insertProcess(HomeScreen.str_id,meinFriseurModule.getOrganization());
            }
        });
        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProcess(meinFriseurModule.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return meinFriseurModuleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView title;
        ImageView imageView,deleteitem;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            imageView=(ImageView)itemView.findViewById(R.id.image);
            deleteitem=(ImageView)itemView.findViewById(R.id.deleteitem);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

    private void insertProcess(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.favDeleteList_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.e("Demo","demo");

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Map<String, String> map = new HashMap<>();


        map.put("id",id);



        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.getData(Constant.favDeleteList_URL, map);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, retrofit2.Response<ArrayList<MeinFriseurModuleHelper>> response) {

                Log.e("response", response.body().size() + "");
                //progressDialog.dismiss();


                if (response.body().get(0).getResult().equalsIgnoreCase("1")) {
                    // Toast.makeText(PhonecallActivity.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Response",response.body().get(0).getMessage());

                }
                if (response.body().get(0).getResult().equalsIgnoreCase("2")) {
                    // Toast.makeText(PhonecallActivity.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();

                }
                if (response.body().get(0).getResult().equalsIgnoreCase("3")) {
                    //  Toast.makeText(PhonecallActivity.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


                meinFriseurModuleList=new ArrayList<MeinFriseurModuleHelper>();
                meinFriseurModuleList = response.body();
                Intent i=new Intent(appCompatActivity,HomeScreen.class);
                appCompatActivity.startActivity(i);
                Log.e("Result", response.body().get(0).getResult());
                Log.e("Message", response.body().get(0).getMessage());
            }


            @Override
            public void onFailure(Call<ArrayList<MeinFriseurModuleHelper>> call, Throwable t) {
                //progress.setVisibility(View.GONE);

                //   progressDialog.dismiss();
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }

}

