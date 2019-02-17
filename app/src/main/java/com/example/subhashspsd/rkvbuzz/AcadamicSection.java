package com.example.subhashspsd.rkvbuzz;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AcadamicSection extends AppCompatActivity {
    EditText publiclink,desc,header,locallink,editText;
    String PublicLink,LocalLink,Desc,Header;

    Uri pdfUri;
    Acadamics ex;
    ImageButton imageButton;
    TextView textView;
    String id;

    private FirebaseAuth firebaseAuth;

    FirebaseDatabase fd=FirebaseDatabase.getInstance();
    DatabaseReference mDatabase=fd.getReference("Acadamics");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acadamic_section);

        firebaseAuth = FirebaseAuth.getInstance();
        publiclink=(EditText) findViewById(R.id.editText1);
        locallink=(EditText) findViewById(R.id.editText2);
        desc=(EditText) findViewById(R.id.editText3);
        header=(EditText) findViewById(R.id.editText4);
        imageButton = (ImageButton) findViewById(R.id.selectFile);
        editText = (EditText) findViewById(R.id.pdfname);
        textView = (TextView) findViewById(R.id.textView);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (ContextCompat.checkSelfPermission(AcadamicSection.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        selectPdf();
                    } else
                        ActivityCompat.requestPermissions(AcadamicSection.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf();
        } else
            Toast.makeText(this, "Pls Give Permission To Access Media", Toast.LENGTH_LONG).show();
    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            textView.setText("Selected file:-" + editText.getText().toString() + ".pdf");
        }
    }

    public void InsertData(View view) {
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        PublicLink=publiclink.getText().toString().trim();
        LocalLink=locallink.getText().toString().trim();
        Desc=desc.getText().toString().trim();
        Header=header.getText().toString().trim();
        if(!(TextUtils.isEmpty(Desc)))
        {
            id=mDatabase.push().getKey();
            if(pdfUri!=null)
            {
                uploadFile();
            }
            else
            {
                work(" ");
            }
            /*Acadamics ex=new Acadamics(PublicLink,LocalLink,Header,Desc,thisDate);
            mDatabase.child(id).setValue(ex);
            Notify nt =new Notify();
            nt.execute(Header,Desc);
            Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_SHORT).show();*/
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Sir,Could you Please Enter Some Description",Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadFile() {
        String filename = editText.getText().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        storageReference.child("AcadamicUploads").child(filename).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String ur = taskSnapshot.getDownloadUrl().toString();
                        work(ur);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

    }

    private void work(String url) {
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        ex=new Acadamics(PublicLink,LocalLink,Header,Desc,thisDate,url);
        mDatabase.child(id).setValue(ex);
        Notify nt =new Notify();
        nt.execute(Header,Desc);
        Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(this,AcadamicsEd.class );
        startActivity(i);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item3:
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(this, Login.class));


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
