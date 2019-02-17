package com.example.subhashspsd.rkvbuzz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth fAuth;
    EditText usname;
    EditText passwd;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser==null)
        {
            Toast.makeText(this,"Login pls",Toast.LENGTH_SHORT).show();
        }
        //String chkemail=currentUser.getEmail();
        else if(currentUser.getEmail().equals("r141640@rguktrkv.ac.in"))
        {
            Toast.makeText(getApplicationContext(),"Welcome to Academics Section",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getApplicationContext(),AcadamicsEd.class);
            startActivity(i);
        }
        else if(currentUser.getEmail().equals("r141192@rguktrkv.ac.in"))
        {
            Toast.makeText(getApplicationContext(),"Welcome to StudentClub Section",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getApplicationContext(),StudentClubEd.class);
            startActivity(i);
        }
        else if(currentUser.getEmail().equals("r141994@rguktrkv.ac.in"))
        {
            Toast.makeText(getApplicationContext(),"Welcome to Events Section",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getApplicationContext(),EventsEd.class);
            startActivity(i);
        }
        else if(currentUser.getEmail().equals("r141189@rguktrkv.ac.in"))
        {
            Toast.makeText(getApplicationContext(),"Welcome to Examination Section",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getApplicationContext(),ExaminationEd.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        usname=(EditText) findViewById(R.id.uname);
        passwd=(EditText)findViewById(R.id.pwd);
    }

    public void All(View view) {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    public void myLogin(View view) {


        final String email,password;
        email=usname.getText().toString();
        password=passwd.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(),"please enter valid details",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(email.equals("r141189@rguktrkv.ac.in"))
                            {
                                Toast.makeText(getApplicationContext(),"Welcome to Examination Section",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),ExaminationEd.class);
                                startActivity(i);
                            }
                            else if(email.equals("r141640@rguktrkv.ac.in"))
                            {
                                Toast.makeText(getApplicationContext(),"Welcome to Acadamic Section",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),AcadamicsEd.class);
                                startActivity(i);
                            }
                            else if(email.equals("r141192@rguktrkv.ac.in"))
                            {
                                Toast.makeText(getApplicationContext(),"Welcome to StudentClub Section",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),StudentClubEd.class);
                                //Log.d("heloo","world");
                                startActivity(i);
                            }
                            else if(email.equals("r141994@rguktrkv.ac.in"))
                            {
                                Toast.makeText(getApplicationContext(),"Welcome to Events Section",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),EventsEd.class);
                                startActivity(i);
                            }
                            else
                            {
                                Intent i=new Intent(getApplicationContext(),StudentSection.class);
                                startActivity(i);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
