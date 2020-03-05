package com.fend.loundry.Laporan;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fend.loundry.R;
import com.fend.loundry.home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class kontenlaporan extends AppCompatActivity {
    List<Laporan> pktlaporan;
    ListView listView;
    TextView textView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontenlaporan);
        textView= (TextView) findViewById(R.id.dapat);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(kontenlaporan.this, Laporan_Pendapatan.class);
                startActivity(intent3);
            }
        });

        listView=(ListView)findViewById(R.id.listlaporan);
        databaseReference = FirebaseDatabase.getInstance().getReference("Laporan");

        pktlaporan=new ArrayList<>();
        cetaklist(this,listView);

    }

    public void cetaklist(final Activity context, final ListView listView) {

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Laporan");
        pktlaporan=new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pktlaporan.clear();
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Laporan pkt=childSnapShot.getValue(Laporan.class);
                    pktlaporan.add(pkt);
                }
                listlaporan listadapter = new listlaporan(context, pktlaporan);
                //attaching adapter to the listview
                listView.setAdapter(listadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
