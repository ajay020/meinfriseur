package friseurbarbers.mein.meinfriseur.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import friseurbarbers.mein.meinfriseur.Adapter.ApointmentAdapter;
import friseurbarbers.mein.meinfriseur.Adapter.SalonListAdapter;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.R;


public class ApiontmentScreen extends Fragment {
    RecyclerView recyclerView;
    List<MeinFriseurModule> meinFriseurModuleList;
    ApointmentAdapter apointmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_apiontment_screen, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.time_recyclerview);
        initView();
        return view;
    }
    public void initView()
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
      //  recyclerView.setLayoutManager(layoutManager);
        data();
        apointmentAdapter=new ApointmentAdapter((AppCompatActivity)getActivity(),meinFriseurModuleList);
        recyclerView.setAdapter(apointmentAdapter);

    }
    public void data()
    {
        meinFriseurModuleList=new ArrayList<MeinFriseurModule>();
        MeinFriseurModule meinFriseurModule=new MeinFriseurModule("9:00AM");
        meinFriseurModuleList.add(meinFriseurModule);
        MeinFriseurModule meinFriseurModule1=new MeinFriseurModule("10:00AM");
        meinFriseurModuleList.add(meinFriseurModule1);
        MeinFriseurModule meinFriseurModule2=new MeinFriseurModule("11:00AM ");
        meinFriseurModuleList.add(meinFriseurModule2);
        MeinFriseurModule meinFriseurModule3=new MeinFriseurModule("12:00PM");
        meinFriseurModuleList.add(meinFriseurModule3);
        MeinFriseurModule meinFriseurModule4=new MeinFriseurModule("1:00PM");
        meinFriseurModuleList.add(meinFriseurModule4);
        MeinFriseurModule meinFriseurModule5=new MeinFriseurModule("2:00PM");
        meinFriseurModuleList.add(meinFriseurModule5);
        MeinFriseurModule meinFriseurModule6=new MeinFriseurModule("3:00PM");
        meinFriseurModuleList.add(meinFriseurModule6);
        MeinFriseurModule meinFriseurModule7=new MeinFriseurModule("4:00PM");
        meinFriseurModuleList.add(meinFriseurModule7);
        MeinFriseurModule meinFriseurModule8=new MeinFriseurModule("5:00PM");
        meinFriseurModuleList.add(meinFriseurModule8);
        MeinFriseurModule meinFriseurModule9=new MeinFriseurModule("6:00PM");
        meinFriseurModuleList.add(meinFriseurModule9);
        MeinFriseurModule meinFriseurModule10=new MeinFriseurModule("7:00PM");
        meinFriseurModuleList.add(meinFriseurModule10);
        MeinFriseurModule meinFriseurModule11=new MeinFriseurModule("8:00PM");
        meinFriseurModuleList.add(meinFriseurModule11);
    }

}
