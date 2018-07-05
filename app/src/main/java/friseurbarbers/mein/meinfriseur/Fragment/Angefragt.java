package friseurbarbers.mein.meinfriseur.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import friseurbarbers.mein.meinfriseur.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Angefragt extends Fragment {





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_angefragt, container, false);

        return view;
    }

}
