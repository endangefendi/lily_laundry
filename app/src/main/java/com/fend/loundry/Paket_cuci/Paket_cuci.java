package com.fend.loundry.Paket_cuci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fend.loundry.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Paket_cuci extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private EditText jncuci;
    private EditText hrgcuci;

    ListView listview;
    List<Paket> paketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeniscuci);
        jncuci=(EditText)findViewById(R.id.jenis);
        hrgcuci=(EditText)findViewById(R.id.harga);

        listview=(ListView)findViewById(R.id.list);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Paket_cuci");
        paketList=new ArrayList<>();
        cetaklist(this,listview);


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int ps, long id) {
                Paket pkt = paketList.get(ps);
                updatedelete(pkt.getId(),pkt.getNama_paket(),pkt.getHarga_paket());
                return false;
            }
        });
        cetaklist(this,listview);
    }

    public void cetaklist(final Activity context, final ListView listView) {
        paketList=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paketList.clear();
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    Paket pkt=childSnapShot.getValue(Paket.class);
                    paketList.add(pkt);
                }
                listpaket listadapter = new listpaket(context, paketList);
                //attaching adapter to the listview
                listView.setAdapter(listadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updatedelete(final String id, String jenis, String harga){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updatedelete, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Edit | Delete").setIcon(R.drawable.logo);

        final EditText etjenis = (EditText) dialogView.findViewById(R.id.updatejenis);
        final EditText etharga = (EditText) dialogView.findViewById(R.id.updateharga);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.button2);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.button);

        etjenis.setText(jenis);
        etharga.setText(harga);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jns = etjenis.getText().toString().trim();
                String hrga = etharga.getText().toString().trim();
                if (!TextUtils.isEmpty(jns)) {
                    UbahJeniscuci(id, jns, hrga);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusJeniscuci(id);
                b.dismiss();
            }
        });
    }


    private boolean UbahJeniscuci(String x, String y, String z) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Paket_cuci").child(x);
        Paket pkt = new Paket(x, y, z);
        dR.setValue(pkt);
        Toast.makeText(getApplicationContext(), "Berhasil Di Edit Terupdate", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean hapusJeniscuci(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Paket_cuci").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Berhasil Di Hapus", Toast.LENGTH_LONG).show();
        return true;
    }

    public void Simpan(View view) {
        String id=databaseReference.push().getKey();
        String jn=jncuci.getText().toString();
        String hg=hrgcuci.getText().toString();
        if(jn.equals("")&&hg.equals("")){
            jncuci.setError("Data tidak boleh kosong");
        }else if(jn.equals("")){
            hrgcuci.setError("Isi jenis dulu!!");
        }else if (hg.equals("")){
            jncuci.setError("Isi harga dulu!!");
        }
        else {
            Paket pkt = new Paket(id, jn, hg);
            databaseReference.child(id).setValue(pkt);
            Toast.makeText(this, "Berhasil menambahkan data", Toast.LENGTH_LONG).show();
            jncuci.setText("");
            hrgcuci.setText("");
            cetaklist(this,listview);
        }
    }
}
