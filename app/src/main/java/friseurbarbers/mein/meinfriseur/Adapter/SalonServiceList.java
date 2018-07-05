package friseurbarbers.mein.meinfriseur.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import friseurbarbers.mein.meinfriseur.AcitivityScreen.AppointmentScreen;
import friseurbarbers.mein.meinfriseur.AcitivityScreen.ListOfServices;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;

/**
 * Created by Aarya on 2/5/2018.
 */

public class SalonServiceList extends RecyclerView.Adapter<SalonServiceList.ViewHolder> {

    AppCompatActivity appCompatActivity;
    List<MeinFriseurModule> meinFriseurModuleList;
    SharedPreferences sharedPreferences;
    public SalonServiceList(AppCompatActivity appCompatActivity, List<MeinFriseurModule> meinFriseurModules) {
        this.appCompatActivity = appCompatActivity;
        this.meinFriseurModuleList = meinFriseurModules;
        sharedPreferences=appCompatActivity.getSharedPreferences(Constant.Shared, Context.MODE_PRIVATE);

    }

    @Override
    public SalonServiceList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salonservicelist, parent, false);/// to inflate the layout here
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final SalonServiceList.ViewHolder holder, int position) {
        final MeinFriseurModule meinFriseurModule=meinFriseurModuleList.get(position);

        holder.title.setText(meinFriseurModule.getSalontitle());
        holder.imageView.setImageResource(meinFriseurModule.getImage());
        holder.appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("ServiceList",new Gson().toJson(meinFriseurModule));
                editor.putString("title",holder.title.getText().toString());
                editor.apply();
                Intent i=new Intent(appCompatActivity,AppointmentScreen.class);
                appCompatActivity.startActivity(i);

            }
        });
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean b) {
//                Intent i=new Intent(appCompatActivity, ListOfServices.class);
//                i.putExtra("SalonName",meinFriseurModule);
//                appCompatActivity.startActivity(i);
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
        TextView appointment;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            appointment=(TextView)itemView.findViewById(R.id.appointment);
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
}

