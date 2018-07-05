package friseurbarbers.mein.meinfriseur.Adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import friseurbarbers.mein.meinfriseur.Fragment.ApiontmentScreen;
import friseurbarbers.mein.meinfriseur.R;

/**
 * Created by Aarya on 2/10/2018.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    AppCompatActivity appCompatActivity;
    List<DateModule> meinFriseurModuleList;

    public DateAdapter(AppCompatActivity appCompatActivity, List<DateModule> meinFriseurModules) {
        this.appCompatActivity = appCompatActivity;
        this.meinFriseurModuleList = meinFriseurModules;
    }

    @Override
    public DateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointmentdate, parent, false);/// to inflate the layout here
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(DateAdapter.ViewHolder holder, int position) {
        final DateModule meinFriseurModule=meinFriseurModuleList.get(position);
      //  holder.title.setText(meinFriseurModule.getDate());
        String dt="dd MMM";

       Calendar calendar = Calendar.getInstance();
        // Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, meinFriseurModule.getDate());

        SpannableString ss= new SpannableString(dt);
        ss.setSpan(new RelativeSizeSpan(2f), 0,5, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(dt, Locale.US);
        holder.title.setText(sdf.format(calendar.getTime()));
        if (meinFriseurModuleList.get(position).isSelected()) {
            holder.cardView.setBackgroundColor(Color.parseColor("#000000"));
            holder.title.setTextColor(Color.WHITE);
        } else {
            holder.cardView.setBackgroundColor(Color.TRANSPARENT);

        }
        //  holder.title.setText(dt.toString());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new ApiontmentScreen();
                FragmentTransaction fragmentTransaction=appCompatActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,fragment);
                fragmentTransaction.commit();
            }
        });
        //holder.imageView.setImageResource(demoModule.getImage());
    }

    @Override
    public int getItemCount() {
        return meinFriseurModuleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.date);
            cardView=(CardView)itemView.findViewById(R.id.cardview);

        }
    }
    public void setSelected(int pos) {
        try {

            meinFriseurModuleList.get(pos).setSelected(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

