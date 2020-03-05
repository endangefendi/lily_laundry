package com.fend.loundry.Cucian_masuk;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fend.loundry.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class mencuci extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText nama,hargawal,berat,tglmasuk;
    protected String idd,jns,hrga;
    Spinner spiner;
    Button button;
    final Calendar myCalendar=Calendar.getInstance();
    DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mencuci);
        nama=(EditText)findViewById(R.id.nama);
        hargawal=(EditText)findViewById(R.id.hargaawal);
        berat=(EditText)findViewById(R.id.berat);
        tglmasuk=(EditText)findViewById(R.id.tanggal);
        spiner=(Spinner)findViewById(R.id.spinerjenis);
        button= (Button)findViewById(R.id.button3) ;
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tglmasuk.setText(sdf.format(myCalendar.getTime()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (berat.getText().toString().equals("")) {
                    berat.setError("Tidak boleh kosong");
                    berat.requestFocus();
                } else if (nama.getText().toString().equals("")) {
                    nama.setError("Tidak boleh kosong");
                    nama.requestFocus();
                } else {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Cucian_masuk");
                    String id = rootRef.push().getKey();
                    String jns = spiner.getSelectedItem().toString().trim();
                    String nm = nama.getText().toString();
                    String hrgawal = hargawal.getText().toString();
                    String brt = berat.getText().toString();
                    int total = Integer.parseInt(hrgawal) * Integer.parseInt(brt);
                    String hrgtotal = Integer.toString(total);
                    String tglm = tglmasuk.getText().toString();
                    String stt = "On Proses";
                    if ((nama.getText().toString().equals("")) && (hargawal.getText().toString().equals("")) && (berat.getText().toString()
                            .equals("")) && (tglmasuk.getText().toString().equals("dd/mm/yyyy"))) {
                        Toast.makeText(mencuci.this, "Lengkapi data diatas!", Toast.LENGTH_SHORT).show();
                    } else {
                        Cucian_masuk pkt = new Cucian_masuk(id, nm, jns, hrgawal, brt, hrgtotal, tglm, stt);
                        db.child(id).setValue(pkt);
                        nama.setText("");
                        berat.setText("");
                        Toast.makeText(mencuci.this, "Cucian masuk ditambahkan!", Toast.LENGTH_SHORT).show();
                    }
                    konfir();
                }
            }
            private void konfir() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mencuci.this);
                builder.setCancelable(false);
                builder.setTitle("Ada cucian baru ?");
                builder.setMessage("Tekan (Iya) jika ada");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                        Intent back = new Intent();
                        setResult(RESULT_OK, back);
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        spiner.setOnItemSelectedListener(this);
        spin();
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        tglmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mencuci.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tglmasuk.setText(sdf.format(myCalendar.getTime()));
    }
    public void spin(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Paket_cuci").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> esr = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    idd=areaSnapshot.getKey();
                    jns = areaSnapshot.child("nama_paket").getValue(String.class);
                    hrga = areaSnapshot.child("harga_paket").getValue(String.class);
                    esr.add(jns);
                }
                ArrayAdapter<String> adpter = new ArrayAdapter<String>(mencuci.this, android.R.layout.simple_spinner_item, esr);
                adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spiner.setAdapter(adpter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Query query= rootRef.child("Paket_cuci").orderByChild("nama_paket").equalTo(spiner.getSelectedItem().toString().trim());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    idd=areaSnapshot.getKey();
                    hrga = areaSnapshot.child("harga_paket").getValue(String.class);
                    jns = areaSnapshot.child("nama_paket").getValue(String.class);
                    hargawal.setText(hrga);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void lihat(View view) {
        Intent intent=new Intent(mencuci.this, kontencucian.class);
        startActivity(intent);
    }
}
