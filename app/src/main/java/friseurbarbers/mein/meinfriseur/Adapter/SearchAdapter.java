package friseurbarbers.mein.meinfriseur.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MeinFriseurModuleHelper> dataList;
    private LayoutInflater inflater;

    public SearchAdapter(Context context, ArrayList<MeinFriseurModuleHelper> dataList) {
        this.context  = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        View view = inflater.inflate(R.layout.row_recycler_view,parent,false);
         holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.tvOrgnisation.setText(dataList.get(position).getOrganization());
      holder.tvEmail.setText(dataList.get(position).getEmail());
      holder.tvAddress.setText(dataList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvOrgnisation,tvEmail,tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrgnisation = (TextView) itemView.findViewById(R.id.tv_organisation);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }
}
