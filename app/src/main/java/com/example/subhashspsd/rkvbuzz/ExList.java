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
 * Created by SubhashSpsd on 09-Nov-18.
 */

public class ExList extends ArrayAdapter<Examination> {
    private Activity context;
    private List<Examination> exList;
    int img=R.drawable.ic_attachment_black_24dp;
    public ExList(Activity context,List<Examination> exList)
    {
        super(context,R.layout.singlerow,exList);
        this.context=context;
        this.exList=exList;
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
        Examination ex=exList.get(position);
        head.setText(ex.getHeader());
        desc.setText(ex.getDescription());
        dat.setText(ex.getDate());
        String h;
        h = ex.getHeader().charAt(0)+"";

        ss.setText(h);
        image.setImageResource(img);
        return listViewItem;

    }
}
