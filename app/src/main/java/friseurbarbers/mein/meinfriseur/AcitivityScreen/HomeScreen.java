package friseurbarbers.mein.meinfriseur.AcitivityScreen;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import friseurbarbers.mein.meinfriseur.CircularAnim;
import friseurbarbers.mein.meinfriseur.Fragment.Angefragt;
import friseurbarbers.mein.meinfriseur.Fragment.Bestatigt;
import friseurbarbers.mein.meinfriseur.Fragment.ContactUs;
import friseurbarbers.mein.meinfriseur.Fragment.HomePage;
import friseurbarbers.mein.meinfriseur.Fragment.Vergangen;
import friseurbarbers.mein.meinfriseur.NavigationDrawer.NavAdapter;
import friseurbarbers.mein.meinfriseur.NavigationDrawer.NavItemList;
import friseurbarbers.mein.meinfriseur.R;
import friseurbarbers.mein.meinfriseur.Retrofit.Shared;

import static android.content.Context.MODE_PRIVATE;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ExpandableListView expandableListView;
    NavAdapter myAdapter;
    ArrayList<Integer> expandableListTitle;
    LinkedHashMap<Integer, List<Integer>> expandableListDetail;
    SharedPreferences sharedPreferences;
    CircleImageView circleImageView;
    String profile, name;
    TextView user_Name;
    SharedPreferences prefs;
    public static TextView title;
    public static String str_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        title = (TextView) findViewById(R.id.title);
        prefs = PreferenceManager.getDefaultSharedPreferences(HomeScreen.this);
        final SharedPreferences prfs = getSharedPreferences("MySharedPrefLang", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(Shared.profile_info, MODE_PRIVATE);
        name = sharedPreferences.getString(Shared.name, "");
        str_id = sharedPreferences.getString(Shared.id, "");

        Log.e("Home  name", name);
        if (!prefs.getString("popup", "").equals("true")) {

            popup();
        } else {
        }
        Fragment fragment = new HomePage();

        profile = sharedPreferences.getString(Shared.photo, "");
        Log.e("Home Profile", profile);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        Bitmap bm = StringToBitMap(profile);
        circleImageView = (CircleImageView) hView.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this, UpdateProfile.class);
                startActivity(i);
                finish();
            }
        });
        // circleImageView.setImageBitmap(bm);
        ImageView logo = (ImageView) hView.findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomePage();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        user_Name = (TextView) hView.findViewById(R.id.user_Name);
        user_Name.setText(name);
        Picasso.with(getApplicationContext()).load(profile).resize(2000, 2000).skipMemoryCache().into(circleImageView);


        expandableListView = (ExpandableListView) findViewById(R.id.left_drawer);
        expandableListDetail = NavItemList.getData();
        expandableListTitle = new ArrayList<Integer>(expandableListDetail.keySet());
        myAdapter = new NavAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(myAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {

                    if (childPosition == 0) {
                        Fragment fragment = new Angefragt();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, fragment);
                        fragmentTransaction.commit();


                    } else if (childPosition == 1) {
                        Fragment fragment = new Bestatigt();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, fragment);
                        fragmentTransaction.commit();

                    } else {
                        Fragment fragment = new Vergangen();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, fragment);
                        fragmentTransaction.commit();
                    }
                } else {


                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0) {


                }

                if (groupPosition == 1) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences.Editor editor1 = prfs.edit();
                    editor1.clear();
                    editor1.commit();

                    Intent intent = new Intent(HomeScreen.this, LoginScreen1.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
                if (groupPosition == 2) {
                    Fragment fragment = new ContactUs();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.commit();
                } else {
//                        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
//                        drawer1.closeDrawer(GravityCompat.START);
                    return false;
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Log.e("NavSelect", "NavSelect");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void popup() {
        prefs.edit().putString("popup", "true").apply();

        final Dialog alert = new Dialog(HomeScreen.this);

        alert.setContentView(R.layout.homescreenmessage);
        TextView ok = (TextView) alert.findViewById(R.id.allow);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        TextView cancel = (TextView) alert.findViewById(R.id.deny);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(true);
        alert.show();

    }


}
