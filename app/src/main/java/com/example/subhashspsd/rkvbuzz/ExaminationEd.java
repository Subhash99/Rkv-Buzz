package com.example.subhashspsd.rkvbuzz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ExaminationEd extends AppCompatActivity {

    ListView listViewEx;
    DatabaseReference databaseExams;
    List<Examination> exList;
    List<String> keyList;
    private FirebaseAuth firebaseAuth;

    private FirebaseAuth fAuth;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser==null)
        {
            Toast.makeText(this,"Login pls",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_ed);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseExams=FirebaseDatabase.getInstance().getReference("Examination");
        listViewEx=(ListView)findViewById(R.id.lvex);
        exList=new ArrayList<>();
        keyList=new ArrayList<>();
        databaseExams.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                exList.clear();
                for(DataSnapshot exSnapshot : dataSnapshot.getChildren())
                {
                    Examination ex=exSnapshot.getValue(Examination.class);
                    exList.add(ex);
                    keyList.add(exSnapshot.getKey());
                }

                Collections.reverse(exList);
                Collections.reverse(keyList);

                ExList adapter=new ExList(ExaminationEd.this,exList);
                listViewEx.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewEx.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Examination ex=exList.get(position);
                showUpdateDialog(keyList.get(position),ex.getHeader(),ex.getDescription(),ex.getLocalLink(),ex.getPublicLink(),ex.getUrl());
                return false;
            }
        });
    }


    private void showUpdateDialog(final String key,final String header,final String desc,final String local,final String publiclink,final String url)
    {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(ExaminationEd.this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);
        final Button buttonUpdate=(Button)dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete=(Button)dialogView.findViewById(R.id.buttonDelete);
        final EditText et1=(EditText)dialogView.findViewById(R.id.desc);
        final EditText et2=(EditText)dialogView.findViewById(R.id.head);
        final EditText et3=(EditText)dialogView.findViewById(R.id.local);
        final EditText et4=(EditText)dialogView.findViewById(R.id.publiclink);
        et1.setText(header);
        et2.setText(desc);
        et3.setText(publiclink);
        et4.setText(local);
        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update(key,header,desc,local,publiclink);
                String h=et1.getText().toString().trim();
                String d=et2.getText().toString().trim();
                String l=et4.getText().toString().trim();
                String p=et3.getText().toString().trim();
                DatabaseReference myref=FirebaseDatabase.getInstance().getReference("Examination").child(key);
                SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                Date todayDate = new Date();
                String thisDate = currentDate.format(todayDate);
                Examination exam=new Examination(p,l,h,d,thisDate,url);
                myref.setValue(exam);
                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArtist(key);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteArtist(String id)
    {
        DatabaseReference myref=FirebaseDatabase.getInstance().getReference("Examination").child(id);
        myref.removeValue();
        keyList.remove(id);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.insertex:
                Intent i=new Intent(getApplicationContext(),ExaminationSection.class);
                startActivity(i);
                return true;
            case R.id.logout:
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(this, Login.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void insData(View view) {

        Intent i=new Intent(getApplicationContext(),ExaminationSection.class);
        startActivity(i);

    }

    public void onBackPressed()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ExaminationEd.this);

        // set title
        alertDialogBuilder.setTitle("Exit");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you really want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
