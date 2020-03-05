package com.fend.loundry.Laporan;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fend.loundry.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Laporan_Pendapatan extends AppCompatActivity {
    List<Laporan> pktlaporan;
    TextView dapatsemua,dapathari,dapatkemarin;
    protected String strharga,tglkemarin,tglsekarang;
    Double harga,kema;
    final Calendar myCalendar=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan__pendapatan);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tglsekarang=(sdf.format(myCalendar.getTime()));
        String seka=tglsekarang.toString().substring(0,2);
        kema=Double.parseDouble(seka);
        kema=kema-1;
        tglkemarin=String.valueOf(kema);

        tglkemarin=tglkemarin.substring(0,2)+tglsekarang.substring(2,10);

        dapatkemarin=(TextView)findViewById(R.id.dapatkemarin);
        dapatsemua=(TextView) findViewById(R.id.dapatsemua);
        dapathari=(TextView) findViewById(R.id.dapathari);
        pktlaporan=new ArrayList<>();

        setSemua();
        setKemarin();
        setHari();
    }
    private void setKemarin() {
        final DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        rootRef2.child("Laporan").orderByChild("tglambil").equalTo(tglkemarin).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double count = (double) 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    strharga = (String) ds.child("harga_total").getValue();
                    if (strharga != null) {
                        harga = Double.parseDouble(strharga);
                        count = (count + harga);
                    }
                    dapatkemarin.setText("Rp : "+String.valueOf(count));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void setSemua() {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Laporan").orderByChild("harga_total").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double count = (double) 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    strharga = (String) ds.child("harga_total").getValue();
                    if (strharga != null) {
                        harga = Double.parseDouble(strharga);
                        count = (count + harga);
                    }
                    dapatsemua.setText("Rp : "+String.valueOf(count));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void setHari() {
        final DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        rootRef2.child("Laporan").orderByChild("tglambil").equalTo(tglsekarang).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double count = (double) 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    strharga = (String) ds.child("harga_total").getValue();
                    if (strharga != null) {
                        harga = Double.parseDouble(strharga);
                        count = (count + harga);
                    }
                    dapathari.setText("Rp : "+String.valueOf(count));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
