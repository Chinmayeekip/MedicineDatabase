package com.example.medicine_alert_database_final;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    //references to button and other controls
    Button btn_add;
    EditText med_name, editTextDate,med_time;
    ListView lv_medicine_list;
    ArrayAdapter medicineArrayAdapter;
    DataBaseHelper dataBaseHelper;
    int count = 0;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_add = findViewById(R.id.btn_add);
        med_name = findViewById(R.id.med_name);
        editTextDate = findViewById(R.id.med_date);
        med_time = findViewById(R.id.med_time);
        lv_medicine_list = findViewById(R.id.lv_medicine_list);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        ShowMedicineOnListView(dataBaseHelper);

        final AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //button listener
        btn_add.setOnClickListener(v -> {
            MedicineModel medicineModel;

            try {
                medicineModel = new MedicineModel(-1,med_name.getText().toString(),editTextDate.getText().toString(),med_time.getText().toString());

            }
            catch (Exception e)
            {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                medicineModel = new MedicineModel(-1,"error","error","error");
            }

            dataBaseHelper.addOne(medicineModel);

            ShowMedicineOnListView(dataBaseHelper);
            String str=editTextDate.getText().toString();
            String day_time=med_time.getText().toString();
            String[]res=str.split("-");
            int Int_day=Integer.parseInt(res[0]);
            int Int_month=Integer.parseInt(res[1]);
            int Int_year=Integer.parseInt(res[2]);
            Calendar calendar= Calendar.getInstance();
            calendar.clear();
            calendar.setTimeInMillis(System.currentTimeMillis());


            switch(day_time){
                case"Morning":
                    calendar.set(Calendar.YEAR,Int_year);
                    calendar.set(Calendar.MONTH,Int_month-1);
                    calendar.set(Calendar.DAY_OF_MONTH,Int_day);
                    calendar.set(Calendar.AM_PM, Calendar.AM );
                    calendar.set(Calendar.HOUR, 8);
                    calendar.set(Calendar.MINUTE, 30);
                    calendar.set(Calendar.SECOND, 0 );
                    break;
                case"Afternoon":
                    calendar.set(Calendar.YEAR,Int_year);
                    calendar.set(Calendar.MONTH,Int_month-1);
                    calendar.set(Calendar.DAY_OF_MONTH,Int_day);
                    calendar.set(Calendar.AM_PM, Calendar.PM );
                    calendar.set(Calendar.HOUR,2);
                    calendar.set(Calendar.MINUTE,30);
                    calendar.set(Calendar.SECOND, 0 );
                    break;
                case"Evening":
                    calendar.set(Calendar.YEAR,Int_year);
                    calendar.set(Calendar.MONTH,Int_month-1);
                    calendar.set(Calendar.DAY_OF_MONTH,Int_day);
                    calendar.set(Calendar.AM_PM, Calendar.PM );
                    calendar.set(Calendar.HOUR,5);
                    calendar.set(Calendar.MINUTE,25);
                    calendar.set(Calendar.SECOND, 0 );
                    break;
                case"Night":
                    calendar.set(Calendar.YEAR,Int_year);
                    calendar.set(Calendar.MONTH,Int_month-1);
                    calendar.set(Calendar.DAY_OF_MONTH,Int_day);
                    calendar.set(Calendar.AM_PM, Calendar.PM );
                    calendar.set(Calendar.HOUR,9);
                    calendar.set(Calendar.MINUTE,30);
                    calendar.set(Calendar.SECOND, 0 );
                    break;
                default:
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    break;
            }

            Intent intent = new Intent(MainActivity.this, MyAlarmReceiver.class);
            intent.putExtra("REQUEST_CODE",count);
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this,count,intent,0);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);

            }
            else
            {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
            }
            Toast.makeText(MainActivity.this,"Alarm is Set",Toast.LENGTH_SHORT).show();

            count++;

        });

        lv_medicine_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicineModel clickedMedicine = (MedicineModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedMedicine);
                ShowMedicineOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this," Deleted ",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void ShowMedicineOnListView(DataBaseHelper dataBaseHelper) {
        medicineArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_medicine_list.setAdapter(medicineArrayAdapter);

    }


}


