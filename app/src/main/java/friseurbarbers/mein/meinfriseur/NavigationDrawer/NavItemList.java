package friseurbarbers.mein.meinfriseur.NavigationDrawer;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import friseurbarbers.mein.meinfriseur.R;


/**
 * Created by Aarya on 6/23/2017.
 */

public class NavItemList {
//    public static LinkedHashMap<Integer, List<Integer>> getData()
//    {
//        LinkedHashMap itemList=new LinkedHashMap<>();
//
//        List<String> home=new ArrayList<String>();
//        home.add("Angefragt");
//        home.add("Bestätigt");
//        home.add("Vergangen");
//
//        List<Integer> logout=new ArrayList<Integer>();
//
//        itemList.put("Deine Termine",home);
//
//        itemList.put(R.string.logout,logout);
//        return itemList;
//    }
public static LinkedHashMap<Integer, List<Integer>> getData()
{
    LinkedHashMap itemList=new LinkedHashMap<>();

    List<String> home=new ArrayList<String>();
    home.add("Angefragt");
    home.add("Bestätigt");
    home.add("Vergangen");
    List<Integer> logout=new ArrayList<Integer>();
    List<Integer> contactUs=new ArrayList<Integer>();




    itemList.put(R.string.home,home);

    itemList.put(R.string.logout,logout);
    itemList.put(R.string.contactus,contactUs);
    return itemList;
}

}
