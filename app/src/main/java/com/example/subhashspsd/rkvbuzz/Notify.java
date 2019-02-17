package com.example.subhashspsd.rkvbuzz;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SubhashSpsd on 02-Nov-18.
 */

public class Notify extends AsyncTask<String,String,Void>
{
    @Override
    protected Void doInBackground(String... params) {
        try {
            String header=params[0];
            String desc=params[1];
            //Toast.makeText(this,"in notify",Toast.LENGTH_SHORT).show();
            String n="my string";

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=AIzaSyDzGvX_JEHmmnKnw6iKJxlNuh2XpHEuj0o");
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();

            json.put("to","/topics/ALL");


            JSONObject info = new JSONObject();
            info.put("title", header);   // Notification title
            info.put("body", desc); // Notification body

            json.put("notification", info);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            conn.getInputStream();
            //Toast.makeText(this,"Last",Toast.LENGTH_LONG).show();

        }
        catch (Exception e)
        {
            Log.d("Error",""+e);
            //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
