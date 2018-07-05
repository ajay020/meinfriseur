package friseurbarbers.mein.meinfriseur.Adapter;

import android.content.Context;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import friseurbarbers.mein.meinfriseur.AcitivityScreen.HomeScreen;
import friseurbarbers.mein.meinfriseur.AcitivityScreen.ListOfServices;
import friseurbarbers.mein.meinfriseur.AcitivityScreen.UpdateProfile;
import friseurbarbers.mein.meinfriseur.Fragment.HomePage;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.RequestInterface;
import friseurbarbers.mein.meinfriseur.Retrofit.Shared;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aarya on 2/5/2018.
 */

public class SalonListAdapter extends RecyclerView.Adapter<SalonListAdapter.ViewHolder> {

    AppCompatActivity appCompatActivity;
   ArrayList<MeinFriseurModuleHelper> meinFriseurModuleList;
    SharedPreferences sharedPreferences;
    private ArrayList<MeinFriseurModuleHelper> filterList;

    public SalonListAdapter(AppCompatActivity appCompatActivity, ArrayList<MeinFriseurModuleHelper> meinFriseurModules) {
        this.appCompatActivity = appCompatActivity;
        this.meinFriseurModuleList = meinFriseurModules;
        this.filterList = new ArrayList<MeinFriseurModuleHelper>();
        this.filterList.addAll(meinFriseurModuleList);
    }

    @Override
    public SalonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.barbershoplist, parent, false);/// to inflate the layout here
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(SalonListAdapter.ViewHolder holder, int position) {
        final MeinFriseurModuleHelper meinFriseurModule=meinFriseurModuleList.get(position);

        holder.title.setText(meinFriseurModule.getOrganization());
        //holder.imageView.setImageResource(demoModule.getImage());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean b) {


                insertProcess(HomeScreen.str_id,meinFriseurModule.getOrganization());
            }
        });


    }

    @Override
    public int getItemCount() {
        return meinFriseurModuleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView title;
        ImageView imageView;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            imageView=(ImageView)itemView.findViewById(R.id.image);
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

    private void insertProcess(String id,String organization) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BarberFav_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.e("Demo","demo");

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Map<String, String> map = new HashMap<>();


        map.put("userid",id);
        map.put("organization",organization);


        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.getData(Constant.BarberFav_URL, map);
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
    public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());
            meinFriseurModuleList.clear();
        if (charText.length()>3) {
            if (charText.length() == 0) {
                meinFriseurModuleList.addAll(filterList);
            } else {
                for (MeinFriseurModuleHelper wp : filterList) {
                    if (wp.getOrganization().toLowerCase(Locale.getDefault()).contains(charText)) {
                        meinFriseurModuleList.add(wp);
                    }
                }
            }

            notifyDataSetChanged();
        }
    }

}

