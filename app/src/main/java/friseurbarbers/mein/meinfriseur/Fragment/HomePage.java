package friseurbarbers.mein.meinfriseur.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import friseurbarbers.mein.meinfriseur.AcitivityScreen.BarberListName;
import friseurbarbers.mein.meinfriseur.AcitivityScreen.HomeScreen;
import friseurbarbers.mein.meinfriseur.AcitivityScreen.LoginScreen;
import friseurbarbers.mein.meinfriseur.AcitivityScreen.SelectItemActivity;
import friseurbarbers.mein.meinfriseur.AcitivityScreen.SplashScreen;
import friseurbarbers.mein.meinfriseur.Adapter.FavListAdapter;
import friseurbarbers.mein.meinfriseur.Adapter.SalonListAdapter;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.RequestInterface;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;
import friseurbarbers.mein.meinfriseur.slider.DescriptionAnimation1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomePage extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    int splashWaitingTime = 1 * 1000;// 3 seconds

    private SliderLayout mDemoSlider;
    RecyclerView recyclerView;
    ArrayList<MeinFriseurModuleHelper> meinFriseurModuleList = new ArrayList<MeinFriseurModuleHelper>();
    FavListAdapter favListAdapter;
    public static ImageView additem;
    public static TextView dein_title, search;
    SharedPreferences sharedPreferencestitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        initView();
        additem = (ImageView) view.findViewById(R.id.additem);
        dein_title = (TextView) view.findViewById(R.id.title1);

        search = (TextView) view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SelectItemActivity.class);
                startActivity(i);
            }
        });
        sharedPreferencestitle = getActivity().getSharedPreferences("title", Context.MODE_PRIVATE);

        if (!sharedPreferencestitle.getString("demotitle", "").equals("true")) {

            title();
        } else {
            search.setText("Zur Suche");
        }
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BarberListName.class);
                startActivity(i);
            }
        });
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        favProcess(HomeScreen.str_id);
        handle();
        slider();

        return view;
    }

    public void title() {
        sharedPreferencestitle.edit().putString("demotitle", "true").apply();
        search.setText("Suche jetzt deinen Friseur und speichere ihn in deinen Favoriten ab");

    }

    public void handle() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


            }
        }, splashWaitingTime);
    }

    public void slider() {
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.slide);
        file_maps.put("Big Bang Theory", R.drawable.slide1);
        file_maps.put("House of Cards", R.drawable.slide2);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    // .description(name)// name of the image
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation1());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        //mDemoSlider.stopAutoCycle();
        mDemoSlider.startAutoCycle();
        super.onStop();
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        mDemoSlider.startAutoCycle();
        // Toast.makeText(getActivity(),slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void favProcess(final String userid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.favList_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Map<String, String> map = new HashMap<>();


        // map.put("usertype", usertype);
        map.put("userid", userid);


        Call<ArrayList<MeinFriseurModuleHelper>> response = requestInterface.getData(Constant.favList_URL, map);
        response.enqueue(new Callback<ArrayList<MeinFriseurModuleHelper>>() {
            @Override
            public void onResponse(Call<ArrayList<MeinFriseurModuleHelper>> call, retrofit2.Response<ArrayList<MeinFriseurModuleHelper>> response) {

                Log.e("response", response.body().size() + "");


                meinFriseurModuleList = response.body();
                favListAdapter = new FavListAdapter((AppCompatActivity) getActivity(), meinFriseurModuleList);
                recyclerView.setAdapter(favListAdapter);
                //     Log.e("organization",response.body().get(0).getOrganization());
                ///  Log.e("id",response.body().get(0).getId());
//                Intent i=new Intent(getActivity(),HomeScreen.class);
//                startActivity(i);

            }


            @Override
            public void onFailure(Call<ArrayList<MeinFriseurModuleHelper>> call, Throwable t) {
                //progress.setVisibility(View.GONE);

                // progressDialog.dismiss();
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }

    public void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));


    }

}







