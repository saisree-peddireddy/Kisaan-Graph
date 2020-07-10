package com.example.pricegraph;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

class DBHelper extends SQLiteOpenHelper {
    Context context;
    public final static String DATABASE_NAME = "beetrootdatabase";
    public final static int DATABASE_VERSION = 1;

    private final static String sqlQuery="create table beetroottable (state varchar(25),district varchar(25),market varchar(25),commodity varchar(25),variety varchar(25), arrival_date varchar(25),min_price varchar(25), max_price varchar(25), modal_price varchar(25))";


    public DBHelper(Context context){
        super(context,DATABASE_NAME ,null, 1);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Toast.makeText(context,"Inside onCreate",Toast.LENGTH_LONG).show();
        sqLiteDatabase.execSQL(sqlQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}





//    private void convertTheFile(File myCSVFile,SQLiteDatabase sqLiteDatabase) {
//        try {
//            FileReader fileReader = new FileReader(myCSVFile);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line = "";
//            String tableName = "beetroottable";
//            String columns = "state , district , market ,commodity ,variety , arrival_date ,min_price , max_price , modal_price ";
//            String str1  = "INSERT INTO " + tableName + " (" + columns + ") values(";
//            String str2 = ");";
//
//            sqLiteDatabase.beginTransaction();
//
//            while ((line = bufferedReader.readLine()) != null) {
//                StringBuilder sb = new StringBuilder(str1);
//                String[] str = line.split(",");
//                sb.append("'" + str[0] + "',");
//                sb.append(str[1] + "',");
//                sb.append(str[2] + "',");
//                sb.append(str[3] + "'");
//                sb.append(str[4] + "'");
//                sb.append(str2);
//                sqLiteDatabase.execSQL(sb.toString());
//            }
//
//            sqLiteDatabase.setTransactionSuccessful();
//            sqLiteDatabase.endTransaction();
//
//        }
//        catch (FileNotFoundException e){
//            return;
//        }
//        catch (IOException e){
//            return;
//        }
//    }
//
//    private File downloadFile(File myCSVFile) {
//        String u = "https://data.gov.in/node/6673497/download?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvZGF0YS5nb3YuaW5cLyIsImF1ZCI6Imh0dHBzOlwvXC9kYXRhLmdvdi5pblwvIiwiaWF0IjoxNTc3MzY5OTAxLCJuYmYiOjE1NzczNjk5MDEsImV4cCI6MTU3NzM2OTkzMSwiZGF0YSI6eyJuaWQiOiI2NjczNDk3In19.6S82n5vIKTPO9X6djRzlLmZ9XDm7MSsvUc9mWnZ8K44";
//
//        try{
//            URL url = new URL(u);
//            URLConnection connection = url.openConnection();
//            int contentLength = connection.getContentLength();
//
//            DataInputStream stream = new DataInputStream(url.openStream());
//
//            byte[] buffer = new byte[contentLength];
//            stream.readFully(buffer);
//            stream.close();
//
//            DataOutputStream fos = new DataOutputStream(new FileOutputStream(myCSVFile));
//            fos.write(buffer);
//            fos.flush();
//            fos.close();
//        }
//        catch(FileNotFoundException e){
//            Log.d("Tag = ",e+"");
//            return null;
//        }
//        catch (IOException e){
//            Log.d("Tag = ",e+"");
//            return null;
//        }
//        return myCSVFile;
//    }