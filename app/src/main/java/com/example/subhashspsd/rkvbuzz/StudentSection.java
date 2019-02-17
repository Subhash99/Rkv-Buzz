package com.example.subhashspsd.rkvbuzz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class StudentSection extends AppCompatActivity {
    GridLayout mainGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_section);
        mainGrid=(GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);
    }
    private void setSingleEvent(GridLayout mainGrid) {
        for(int i=0;i<mainGrid.getChildCount();i++)
        {
            CardView cardView=(CardView) mainGrid.getChildAt(i);
            final int k=i;
            cardView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    switch (k)
                    {
                        case 0:
                            Uri uri = Uri.parse("http://www.rguktrkv.ac.in/");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                            break;
                        case 1:
                            Uri uri1 = Uri.parse("http://results.rguktrkv.org/");
                            Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                            startActivity(intent1);
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(),"Please Connect to College WiFi",Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Uri uri3 = Uri.parse("http://hub.rguktrkv.org/index.php");
                            Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
                            startActivity(intent3);
                            break;
                        case 4:
                            Uri uri4 = Uri.parse("https://epass.apcfss.in/");
                            Intent intent4 = new Intent(Intent.ACTION_VIEW, uri4);
                            startActivity(intent4);
                            break;

                    }
                }
            });
        }
    }
}
