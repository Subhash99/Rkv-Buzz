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

public class AcList extends ArrayAdapter<Acadamics> {
    private Activity context;
    private List<Acadamics> acList;
    int img=R.drawable.ic_attachment_black_24dp;
    public AcList(Activity context,List<Acadamics> acList)
    {
        super(context,R.layout.singlerow,acList);
        this.context=context;
        this.acList=acList;
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
        Acadamics ac=acList.get(position);
        head.setText(ac.getHeader());
        desc.setText(ac.getDescription());
        dat.setText(ac.getDate());
        String h;
        h = ac.getHeader().charAt(0)+"";

        ss.setText(h);
        image.setImageResource(img);
        return listViewItem;

    }
}
