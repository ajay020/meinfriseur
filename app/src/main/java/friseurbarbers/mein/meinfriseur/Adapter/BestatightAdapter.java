package friseurbarbers.mein.meinfriseur.Adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;

/**
 * Created by Aarya on 2/5/2018.
 */

public class BestatightAdapter extends RecyclerView.Adapter<BestatightAdapter.ViewHolder> {

    AppCompatActivity appCompatActivity;
    List<MeinFriseurModuleHelper> meinFriseurModuleList;

    public BestatightAdapter(AppCompatActivity appCompatActivity, List<MeinFriseurModuleHelper> meinFriseurModules) {
        this.appCompatActivity = appCompatActivity;
        this.meinFriseurModuleList = meinFriseurModules;
    }

    @Override
    public BestatightAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bestatight, parent, false);/// to inflate the layout here
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(BestatightAdapter.ViewHolder holder, int position) {
        final MeinFriseurModuleHelper meinFriseurModule=meinFriseurModuleList.get(position);
        holder.zid.setText(meinFriseurModule.getTerminid());
        holder.friseur.setText(meinFriseurModule.getFriseurName());
        holder.datum.setText(meinFriseurModule.getDatum());
        holder.zeit.setText(meinFriseurModule.getZeit());
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
       TextView zid,friseur,datum,zeit;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            zid=(TextView)itemView.findViewById(R.id.termin_id);
            friseur=(TextView)itemView.findViewById(R.id.friseurName);
            datum=(TextView)itemView.findViewById(R.id.datum);
            zeit=(TextView)itemView.findViewById(R.id.zeit);
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

