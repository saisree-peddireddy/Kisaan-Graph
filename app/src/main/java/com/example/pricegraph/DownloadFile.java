package com.example.pricegraph;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

class DownloadFile extends AsyncTask<String,String,String> {

    MyTask postDownloadTask;
    public DownloadFile(MyTask postDownloadTask)
    {
        this.postDownloadTask = postDownloadTask;
    }
    @Override
    protected String doInBackground(String... strings) {

        String myCSVFile="",myCSVFile2="";
        try {
        String u = "https://data.gov.in/sites/default/files/Ginger(Dry)_2019.csv";


            URL url = new URL(u);
            URLConnection connection = url.openConnection();
            int contentLength = connection.getContentLength();

            DataInputStream stream = new DataInputStream(url.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();
            myCSVFile = new String(buffer);

            String v = "https://data.gov.in/sites/default/files/Beetroot_2019.csv";
            url = new URL(v);
            connection = url.openConnection();
            contentLength = connection.getContentLength();
            stream = new DataInputStream(url.openStream());
            buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();
            myCSVFile2 = new String(buffer);

        }
        catch (Exception e){
            Log.d("Readthis : ",e+"");
        }
        Log.d("myCSVFile",myCSVFile);
        return myCSVFile+myCSVFile2;
    }
    @Override
    protected void onPostExecute(String result) {
        this.postDownloadTask.execute((result));
    }

}
