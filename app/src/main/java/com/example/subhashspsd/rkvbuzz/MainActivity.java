package com.example.subhashspsd.rkvbuzz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> keyList;
    ListView list;
    MyAdapter adapter;
    Examination []ex=new Examination[40];
    Acadamics [] ac=new Acadamics[40];
    StudentClubs []sc=new StudentClubs[40];
    Events [] ev=new Events[40];
    ArrayList<String> header=new ArrayList<>();
    ArrayList<String>  description=new ArrayList<>();
    ArrayList<String>  date=new ArrayList<>();
    ArrayList<String>  publicLink=new ArrayList<>();
    ArrayList<String>  localLink=new ArrayList<>();int img=R.drawable.ic_attachment_black_24dp;
    ArrayList<String>  str=new ArrayList<>();
    ArrayList<String>  linkCheck=new ArrayList<>();
    ArrayList<uploadExamination>  urls=new ArrayList<>();

    String examurls;

    private FirebaseAuth fAuth;

    BroadcastReceiver updateUIReciver;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    dataRetrieve(2);
                    return true;
                case R.id.navigation_notifications:
                    dataRetrieve(3);
                    return true;
                case R.id.icon123:
                    dataRetrieve(4);
                    return true;
                case R.id.contact:
                    dataRetrieve(5);
                    return true;
            }
            return false;
        }
    };

    String tkn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("ALL");
        tkn= FirebaseInstanceId.getInstance().getToken();

        IntentFilter filter=new IntentFilter();
        filter.addAction("com.hello.action");
        updateUIReciver = new BroadcastReceiver()
        {
            public void onReceive(Context context,Intent intent)
            {
                Toast.makeText(MainActivity.this,"Broadcast",Toast.LENGTH_SHORT).show();
                dataRetrieve(2);
            }
        };
        registerReceiver(updateUIReciver,filter);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        list=(ListView) findViewById(R.id.lv);
        keyList=new ArrayList<>();
        adapter=new MyAdapter(getApplicationContext(),header,img,description,date,publicLink,localLink,str);
        list.setAdapter(adapter);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s=description.get(position);
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(MainActivity.this);
                View parentView=getLayoutInflater().inflate(R.layout.dialog,null);
                bottomSheetDialog.setContentView(parentView);
                BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from((View)parentView.getParent());
                bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,400,getResources().getDisplayMetrics()));
                bottomSheetDialog.show();
                TextView bottv=(TextView) parentView.findViewById(R.id.bottv);
                TextView bottv1=(TextView) parentView.findViewById(R.id.bottv1);
                TextView bottv2=(TextView) parentView.findViewById(R.id.bottv2);
                TextView bottv3=(TextView) parentView.findViewById(R.id.bottv3);
                TextView bottv4=(TextView) parentView.findViewById(R.id.bottv4);
                bottv1.setText(s);
                bottv.setText(header.get(position));
                bottv2.setText(localLink.get(position));
                bottv3.setText(publicLink.get(position));
                if(linkCheck.get(position)==null)
                {
                    bottv4.setText("");
                }
                else
                {
                    bottv4.setText(linkCheck.get(position));
                }

            }
        });

    }

    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(updateUIReciver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item3:
                Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dataRetrieve(int i)
    {
        FirebaseDatabase fd=FirebaseDatabase.getInstance();
        header.clear();
        description.clear();
        date.clear();
        publicLink.clear();
        localLink.clear();
        str.clear();
        linkCheck.clear();
        adapter.notifyDataSetChanged();
        if(i==1)
        {
            Intent in=new Intent(this,Main2Activity.class);

            startActivity(in);
        }
        else if(i==2) {
            keyList.clear();
            final DatabaseReference mDatabase=fd.getReference("Examination");
            /*final DatabaseReference urlDatabase=fd.getReference("Upload");
            urlDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   // Toast.makeText(MainActivity.this,"Fetching Here ",Toast.LENGTH_LONG).show();
                    for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
                    {
                        uploadExamination data=new uploadExamination(childSnapshot.getValue().toString());
                        urls.add(data);

                    }
                    examurls=urls.get(0).url;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int i=0,j;
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        ex[i] = ds.getValue(Examination.class);
                        keyList.add(ds.getKey());
                        i++;
                    }
                    Collections.reverse(keyList);
                    while(i-->0){
                        header.add(ex[i].getHeader());
                        description.add(ex[i].getDescription());
                        date.add(ex[i].getDate());
                        publicLink.add(ex[i].getPublicLink());
                        localLink.add(ex[i].getLocalLink());
                        linkCheck.add(ex[i].getUrl());
                        String s=ex[i].getHeader();
                        str.add(""+s.charAt(0));

                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        else if(i==3)
        {
            header.clear();
            description.clear();
            date.clear();
            publicLink.clear();
            localLink.clear();
            str.clear();
            keyList.clear();
            final DatabaseReference mDatabase=fd.getReference("Acadamics");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int i=0,j;
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        keyList.add(ds.getKey());
                        ac[i] = ds.getValue(Acadamics.class);
                        i++;
                    }
                    Collections.reverse(keyList);
                    while(i-->0){
                        header.add(ac[i].getHeader());
                        description.add(ac[i].getDescription());
                        date.add(ac[i].getDate());
                        publicLink.add(ac[i].getPublicLink());
                        localLink.add(ac[i].getLocalLink());
                        linkCheck.add(ac[i].getUrl());
                        String s=ac[i].getHeader();
                        str.add(""+s.charAt(0));
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(i==4)
        {
            header.clear();
            description.clear();
            date.clear();
            publicLink.clear();
            localLink.clear();
            str.clear();
            keyList.clear();
            final DatabaseReference mDatabase=fd.getReference("StudentClubs");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int i=0,j;
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        keyList.add(ds.getKey());
                        sc[i] = ds.getValue(StudentClubs.class);
                        i++;
                    }
                    Collections.reverse(keyList);
                    while(i-->0){
                        header.add(sc[i].getHeader());
                        description.add(sc[i].getDescription());
                        date.add(sc[i].getDate());
                        publicLink.add(sc[i].getPublicLink());
                        localLink.add(sc[i].getLocalLink());
                        linkCheck.add(sc[i].getUrl());
                        String s=sc[i].getHeader();
                        str.add(""+s.charAt(0));
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(i==5)
        {
            header.clear();
            description.clear();
            date.clear();
            publicLink.clear();
            localLink.clear();
            str.clear();
            keyList.clear();
            final DatabaseReference mDatabase=fd.getReference("Events");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int i=0,j;
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        keyList.add(ds.getKey());
                        ev[i] = ds.getValue(Events.class);
                        i++;
                    }
                    Collections.reverse(keyList);
                    while(i-->0){
                        header.add(ev[i].getHeader());
                        description.add(ev[i].getDescription());
                        date.add(ev[i].getDate());
                        publicLink.add(ev[i].getPublicLink());
                        localLink.add(ev[i].getLocalLink());
                        linkCheck.add(ev[i].getUrl());
                        String s=ev[i].getHeader();
                        str.add(""+s.charAt(0));
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void insData(View view) {
        Intent i=new Intent(this,Login.class);
        startActivity(i);
    }

    public void onBackPressed()
    {

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(a);
    }

    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        ArrayList<String> header=new ArrayList<>();
        ArrayList<String>  description=new ArrayList<>();
        ArrayList<String>  date=new ArrayList<>();
        ArrayList<String>  publicLink=new ArrayList<>();
        ArrayList<String>  localLink=new ArrayList<>();
        ArrayList<String>  str=new ArrayList<>();
        int img;

        MyAdapter(Context c,ArrayList<String>header,int img,ArrayList<String> description,ArrayList<String> date,ArrayList<String>  publicLink,ArrayList<String>  localLink,ArrayList<String> str)
        {
            super(c,R.layout.singlerow,R.id.t2,header);
            this.context=c;
            this.img=img;
            this.description=description;
            this.date=date;
            this.header=header;
            this.str=str;
            this.localLink=localLink;
            this.publicLink=publicLink;

        }
        @Override
        public View getView(int position, View converrtView, ViewGroup parent)
        {
            LayoutInflater layoutInflater=(LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.singlerow,parent,false);
            ImageView image=(ImageView) row.findViewById(R.id.i1);
            TextView head=(TextView) row.findViewById(R.id.t2);
            TextView desc=(TextView) row.findViewById(R.id.t3);
            TextView dat=(TextView) row.findViewById(R.id.t4);
            TextView ss=(TextView) row.findViewById(R.id.t1);

            image.setImageResource(img);
            head.setText(header.get(position));
            desc.setText(description.get(position));
            dat.setText(date.get(position));
            ss.setText(str.get(position));
            return row;
        }


    }

}
