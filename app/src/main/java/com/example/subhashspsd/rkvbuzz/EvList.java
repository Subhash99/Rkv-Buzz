package com.example.subhashspsd.rkvbuzz;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SubhashSpsd on 13-Nov-18.
 */

public class EvList extends ArrayAdapter<Events> {

    private Activity context;
    private List<Events> evList;
    int img=R.drawable.ic_attachment_black_24dp;

    public EvList(Activity context,List<Events> evList)
    {
        super(context,R.layout.singlerow,evList);
        this.context=context;
        this.evList=evList;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.singlerow,null,true);
        ImageView image=(ImageView) listViewItem.findViewById(R.id.i1);
        TextView head=(TextView) listViewItem.findViewById(R.id.t2);
        TextView desc=(TextView) listViewItem.findViewById(R.id.t3);
        TextView dat=(TextView) listViewItem.findViewById(R.id.t4);
        TextView ss=(TextView) listViewItem.findViewById(R.id.t1);
        Events ev=evList.get(position);
        head.setText(ev.getHeader());
        desc.setText(ev.getDescription());
        dat.setText(ev.getDate());
        String h;
        h = ev.getHeader().charAt(0)+"";
        ss.setText(h);
        image.setImageResource(img);
        return listViewItem;

    }
}

