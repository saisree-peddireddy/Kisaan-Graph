package com.example.pricegraph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    AutoCompleteTextView commodityACTV,stateACTV,districtACTV;
    String states[];
    String district[];
    String comms[];
    SharedPreferences sp;
    DBHelper dbhelper;
    SQLiteDatabase db;
    Button btn ;
    String dataToForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        states = new String[]{"Gujarat", "Madhya Pradesh", "Chattisgarh", "Punjab", "Rajasthan", "Meghalaya", "Tripura", "Mizoram", "Maharashtra", "state", "Andaman and Nicobar", "Himachal Pradesh", "Karnataka", "Uttar Pradesh", "Jammu and Kashmir", "Assam", "Haryana", "NCT of Delhi", "West Bengal", "Andhra Pradesh", "Odisha", "Telangana", "Uttrakhand", "Kerala"};
        district= new String[]{"Belgaum", "Sirmore", "Harda", "Badaun", "Kollam", "Vadodara(Baroda)", "Tarntaran", "Nuapada", "Dehradoon", "Pathanamthitta", "Idukki", "Fatehabad", "Wayanad", "Moga", "kapurthala", "Bargarh", "Mumbai", "Thirssur", "Kozhikode(Calicut)", "Mysore", "Jalaun (Orai)", "Ferozpur", "Tumkur", "Jabalpur", "Dhar", "Jodhpur", "Jalgaon", "Amritsar", "Hoshiarpur", "Faizabad", "Bhadrak", "Osmanabad", "Kasargod", "Kolar", "Ahmednagar", "Chandrapur", "Sangli", "Aurangabad", "Auraiya", "Srinagar", "Burdwan", "Haveri", "Haridwar", "Gomati", "Udupi", "Palwal", "Ganjam", "UdhamSinghNagar", "Indore", "Solan", "Davangere", "Ajmer", "district", "Mandi", "Balasore", "Hissar", "Rohtak", "Chittor", "Malappuram", "Koria", "Nanital", "Shajapur", "Birbhum", "Patiala", "Chikmagalur", "West Jaintia Hills", "Ludhiana", "Yamuna Nagar", "Bolangir", "Palakad", "Balrampur", "Sultanpur", "Kheda", "Ernakulam", "Ratnagiri", "Aizawl", "Jalandhar", "Narsinghpur", "Hyderabad", "Kottayam", "Surat", "Mohali", "South District", "Kolkata", "Chamrajnagar", "Shimoga", "Dewas", "East Khasi Hills", "Bharuch", "Khandwa", "Anupur", "Kota", "Bangalore", "Bellary", "Chhindwara", "South Andaman", "East Jaintia Hills", "Angul", "Burhanpur", "West Garo Hills", "Nashik", "Mansa", "Darjeeling", "Gurdaspur", "Kalahandi", "Mayurbhanja", "Ahmedabad", "Sonitpur", "Kullu", "Madikeri(Kodagu)", "Ambala", "Muktsar", "Panipat", "Amarawati", "Kurukshetra", "Hassan", "Nagpur", "South West Garo Hills", "Delhi", "Satara", "Ranga Reddy", "Bhopal", "Khurda", "Faridabad", "Thiruvananthapuram", "Nongpoh (R-Bhoi)", "Mahbubnagar", "Faridkot", "Jajpur", "Dhenkanal", "Raigarh", "Pune", "Anand", "Fatehgarh", "Dahod", "Alappuzha"
        };
        comms = new String[]{"Beetroot","Ginger(Dry)"};
        sp=getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        getReady();
        dbhelper = new DBHelper(this);
        db = dbhelper.getWritableDatabase();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,states);
        stateACTV = findViewById(R.id.actv1);
        stateACTV.setThreshold(1);
        stateACTV.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,district);
        districtACTV = findViewById(R.id.actv2);
        districtACTV.setThreshold(1);
        districtACTV.setAdapter(arrayAdapter1);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,comms);
        commodityACTV = findViewById(R.id.actv0);
        commodityACTV.setThreshold(1);
        commodityACTV.setAdapter(arrayAdapter2);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"You clicked me",Toast.LENGTH_LONG).show();
                dataToForward = fetchMyData();

                goForward();
            }
        });


    }

    private void goForward() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Data",dataToForward);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private String fetchMyData() {
        //read the state name
        String commodityName = commodityACTV.getText().toString();

        String stateName = stateACTV.getText().toString();

        Log.d("see me","fetchMyData: "+stateName);
//        //read the district name
        String districtName = districtACTV.getText().toString();
        Log.d("see me","fetchMyData: "+districtName);


        try {
            db = dbhelper.getWritableDatabase();
            Cursor c = db.rawQuery("Select * from beetroottable where state = '"+stateName+"' AND district = '"+districtName+"' AND commodity = '"+commodityName+"'", null);

            String date="";
            while (c.moveToNext()) {
                Log.d("see me", "fetchMyData: " + c.getString(0));
                String dateAndPrice = c.getString(0)+" "+c.getString(1);
                date+= c.getString(0)+" "+c.getString(1)+";";
                //++i;
            }

            //tv.setText(tv.getText() + date +"\n");
            Log.d("textt",date);
            TextView tv = findViewById(R.id.tv);
            tv.setText(date);
            return date;
        }catch(Exception e){

            Log.d("Fetch ",e+"");
           // tv.setText(tv.getText()+" "+e);
        }
        return  "";
    }

    private void getReady() {
        if(sp.contains("Data")){
        }
        else{
            makeDatabaseReady();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Data","True");
            editor.commit();
            Log.d("MakeDatabaseReady ","Committed");
        }
    }

    private void makeDatabaseReady() {

        insertRecords();
    }

    private void insertRecords() {

        DownloadFile downloadFile = new DownloadFile(new MyTask() {
            @Override
            public void execute(String myCSVFile) {
                try{
                    String lines[] = myCSVFile.split("\n");
                    int i=0;
                    for( String line : lines) {
                        if(line.substring(0,14).equals("Gujarat,Anand"))   continue;
                        else if (line.substring(0,49).equals("Karnataka,Bangalore,\"Binny Mill (F&V), Bangalore\"")) continue;
                        String values[] = line.split(",");
                        ContentValues cv = new ContentValues();
                        cv.put("state",values[0]);
                        cv.put("district",values[1]);
                        cv.put("market",values[2]);
                        cv.put("commodity",values[3]);
                        cv.put("variety",values[4]);
                        cv.put("arrival_date",values[5]);
                        cv.put("min_price",values[6]);
                        cv.put("max_price",values[7]);
                        cv.put("modal_price",values[8]);
                        db.insert("beetroottable",null,cv);
                    }
                }
                catch(Exception e){
                    Log.d("Exception",e+"");
                }
            }
        });
        downloadFile.execute();
    }


}

