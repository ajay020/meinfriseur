package friseurbarbers.mein.meinfriseur.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import friseurbarbers.mein.meinfriseur.AcitivityScreen.AppointmentScreen;
import friseurbarbers.mein.meinfriseur.AcitivityScreen.HomeScreen;
import friseurbarbers.mein.meinfriseur.Adapter.BestatightAdapter;
import friseurbarbers.mein.meinfriseur.Adapter.DateAdapter;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bestatigt extends Fragment {


    RecyclerView recyclerView;
    BestatightAdapter bestatightAdapter;
    List<MeinFriseurModuleHelper> meinFriseurModuleHelperList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bestatigt, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        initView();
        HomeScreen.title.setText("Bestätigt");
        return view;
    }


    public void initView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        data();
        bestatightAdapter = new BestatightAdapter((AppCompatActivity) getActivity(), meinFriseurModuleHelperList);
        recyclerView.setAdapter(bestatightAdapter);

    }

    public void data() {
        meinFriseurModuleHelperList = new ArrayList<MeinFriseurModuleHelper>();
        MeinFriseurModuleHelper meinFriseurModuleHelper = new MeinFriseurModuleHelper("58769008576788", "John Friseur", "28-April-2018", "01:00 PM");
        meinFriseurModuleHelperList.add(meinFriseurModuleHelper);

        MeinFriseurModuleHelper meinFriseurModuleHelper1 = new MeinFriseurModuleHelper("58769008576788", "John Friseur", "28-April-2018", "01:00 PM");
        meinFriseurModuleHelperList.add(meinFriseurModuleHelper1);

    }
}