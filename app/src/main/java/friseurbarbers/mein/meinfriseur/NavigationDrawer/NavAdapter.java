package friseurbarbers.mein.meinfriseur.NavigationDrawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import friseurbarbers.mein.meinfriseur.R;


/**
 * Created by Aarya on 6/23/2017.
 */

public class NavAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Integer> expandableListTitle;
    private HashMap<Integer, List<Integer>> expandableListDetail;


    public NavAdapter(Context context, ArrayList<Integer> expandableListTitle,
                      LinkedHashMap<Integer, List<Integer>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }
    @Override
    public Object getChild(int listPosition, int expandedListPosition)
    {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }
    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_child, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.child);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.icon1);
        expandedListTextView.setText(expandedListText);

        return convertView;
    }
    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Integer title = (Integer) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_header, null);


        }
        TextView textView=(TextView)convertView.findViewById(R.id.tvHeader);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.ivGroupIndicator);
        ImageView imageView1=(ImageView)convertView.findViewById(R.id.icon);

        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);
        imageView.setSelected(isExpanded);
        if(listPosition==0)
        {
            imageView.setVisibility(View.VISIBLE);

            imageView1.setImageResource(R.drawable.bell);
        }


        else if(listPosition==1)
        {
            imageView.setVisibility(View.GONE);
            imageView1.setImageResource(R.drawable.logout);
        }
        else if(listPosition==2)
        {
            imageView.setVisibility(View.GONE);
            imageView1.setImageResource(R.drawable.contactus);


        }
        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}