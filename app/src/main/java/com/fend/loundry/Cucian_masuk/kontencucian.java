package com.fend.loundry.Cucian_masuk;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fend.loundry.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class kontencucian extends AppCompatActivity {
    List<Cucian_masuk> pktcucimasuk;
        ListView listView;

private DatabaseReference databaseReference;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontencucian);

        listView = (ListView) findViewById(R.id.listcucian);
        databaseReference = FirebaseDatabase.getInstance().getReference("Cucian_masuk");

        pktcucimasuk = new ArrayList<>();
        cetaklist(this, listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cucian_masuk pkt = pktcucimasuk.get(position);
                        updatestatus(pkt.getId(), pkt.getNama_pelanggan(), pkt.getNama_paket(), pkt.getHarga_paket(), pkt.getBerat(),
                                pkt.getHarga_total(), pkt.getTglmasuk(), pkt.getStatus());
                }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Cucian_masuk pkt = pktcucimasuk.get(position);
                        updatestatus(pkt.getId(), pkt.getNama_pelanggan(), pkt.getNama_paket(), pkt.getHarga_paket(), pkt.getBerat(),
                                pkt.getHarga_total(), pkt.getTglmasuk(), pkt.getStatus());
                        return false;
                }
        });
}

public void cetaklist(final Activity context, final ListView listView) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Cucian_masuk");
        pktcucimasuk=new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) {
        pktcucimasuk.clear();
        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                Cucian_masuk pkt=childSnapShot.getValue(Cucian_masuk.class);
        pktcucimasuk.add(pkt);
        }
        listcucian listadapter = new listcucian(context, pktcucimasuk);
        //attaching adapter to the listview
        listView.setAdapter(listadapter);
        }

@Override
public void onCancelled(@NonNull DatabaseError databaseError) {

        }
        });
        }


public void updatestatus(final String id, final String namaaa, final String jniscuci, final String hargaawal, final String berat, final String hargatotal,
final String tglmasuk, final String status){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.updatestatus, null);
        dialogBuilder.setView(dialogView);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnselesai);
        dialogBuilder.setTitle("Lily Laundry").setIcon(R.drawable.logo);
        TextView nama= dialogView.findViewById(R.id.nama);
        nama.setText(namaaa);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        String st="Selesai";
        Ubahstatus(id,namaaa,jniscuci,hargaawal,berat,hargatotal,tglmasuk,st);
        b.dismiss();
        }
        });

        }


private boolean Ubahstatus(String x, String a, String b, String c,String d,String e,String f,String g) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Cucian_masuk").child(x);
        Cucian_masuk pkt = new Cucian_masuk(x, a, b,c,d,e,f,g);
        dR.setValue(pkt);
        return true;
        }
        }
