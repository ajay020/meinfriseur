package friseurbarbers.mein.meinfriseur.Adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import friseurbarbers.mein.meinfriseur.AcitivityScreen.ListOfServices;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.R;

/**
 * Created by Aarya on 2/5/2018.
 */

public class ApointmentAdapter extends RecyclerView.Adapter<ApointmentAdapter.ViewHolder> {

    AppCompatActivity appCompatActivity;
    List<MeinFriseurModule> meinFriseurModuleList;

    public ApointmentAdapter(AppCompatActivity appCompatActivity, List<MeinFriseurModule> meinFriseurModules) {
        this.appCompatActivity = appCompatActivity;
        this.meinFriseurModuleList = meinFriseurModules;
    }

    @Override
    public ApointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointmenttime, parent, false);/// to inflate the layout here
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ApointmentAdapter.ViewHolder holder, int position) {
        final MeinFriseurModule meinFriseurModule=meinFriseurModuleList.get(position);
        holder.title.setText(meinFriseurModule.getSalontitle());
        //holder.imageView.setImageResource(demoModule.getImage());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean b) {

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
            title=(TextView)itemView.findViewById(R.id.time);

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

