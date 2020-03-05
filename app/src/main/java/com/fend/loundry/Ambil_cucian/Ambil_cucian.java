package com.fend.loundry.Ambil_cucian;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fend.loundry.Laporan.Laporan;
import com.fend.loundry.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class Ambil_cucian extends AppCompatActivity {
    EditText nmcuci,jncuci,hgcuci,brtcuci,hgtotcuci,tglmsk,tglambl,byr;
    protected String jns,brt,hgwal,hgkhir,tglmas,idd,stt;
    DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
    final Calendar myCalendar=Calendar.getInstance();
    Button ambil;
    int bayar=0,total=0,kembali=0;
    String kembalian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambilcuci);
        setTitle("Ambil Cuci");
        nmcuci=(EditText)findViewById(R.id.nm);
        jncuci=(EditText)findViewById(R.id.jc);
        hgcuci=(EditText)findViewById(R.id.hgawal);
        brtcuci=(EditText)findViewById(R.id.brt);
        hgtotcuci=(EditText)findViewById(R.id.hgtot);
        tglmsk=(EditText)findViewById(R.id.tglmas);
        tglambl=(EditText)findViewById(R.id.tglkel);
        byr=(EditText)findViewById(R.id.bayar);
        ambil=(Button)findViewById(R.id.button5);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tglambl.setText(sdf.format(myCalendar.getTime()));
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        tglambl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Ambil_cucian.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hgckhir = hgtotcuci.getText().toString();
                String tglambc = tglambl.getText().toString();
                String byrc = byr.getText().toString();
                try{
                    bayar = Integer.parseInt(byrc);
                }catch(NumberFormatException ex){ // handle your exception
                    byr.setError("Uang Anda Kurang!");
                    byr.requestFocus();
                }
                try{
                    total = Integer.parseInt(hgckhir);
                }catch(NumberFormatException ex){ // handle your exception
                    byr.setError("Uang Anda Kurang!");
                    byr.requestFocus();
                }
                if (nmcuci.getText().toString().equals("") || nmcuci.getText().toString().equals("Masukkan nama pelanggan")) {
                    nmcuci.setError("Tidak boleh kosong");
                    byr.requestFocus();
                } else if (byr.getText().toString().equals("")) {
                    byr.setError("Bayar dulu senbelum ambil");
                    byr.requestFocus();
                } else if (bayar < total) {
                    byr.setError("Uang Anda Kurang!");
                    byr.requestFocus();
                } else {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Laporan");
                    String id = rootRef.push().getKey();
                    String nmc = nmcuci.getText().toString();
                    String jnc = jncuci.getText().toString();
                    String hgcwal = hgcuci.getText().toString();
                    String brtc = brtcuci.getText().toString();
                    String tglmasc = tglmsk.getText().toString();
                    kembalian = Integer.toString(bayar - total);
                    if (stt.equals("On Proses")) {
                        notif();
                    }else {
                        Laporan pkt = new Laporan(id, nmc, jnc, hgcwal, brtc, hgckhir, tglmasc, tglambc, byrc, kembalian);
                        db.child(id).setValue(pkt);
                        kembalian();
                        //konfir();
                        nmcuci.setText("");
                        jncuci.setText("");
                        hgcuci.setText("");
                        brtcuci.setText("");
                        hgtotcuci.setText("");
                        tglmsk.setText("");
                        byr.setText("");
                        hapuspencuci(idd);
                    }
                }
            }
        });
    }
    private void konfir() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Ambil_cucian.this);
        builder.setCancelable(false);
        builder.setTitle("Ambil lagi ?");
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
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String t=tglmsk.getText().toString();
        String hari=t.substring(0,2);
        int tg= parseInt(hari);
        if(myCalendar.get(Calendar.DAY_OF_MONTH)<tg){
            Toast.makeText(this,"Tanggal salah",Toast.LENGTH_SHORT).show();
        }
        else {
            tglambl.setText(sdf.format(myCalendar.getTime()));
        }
    }
    public void cari(View view) {
        String nama=nmcuci.getText().toString();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Cucian_masuk").orderByChild("nama_pelanggan").startAt(nama).endAt(nama+"\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    idd = areaSnapshot.getKey();
                    jns = areaSnapshot.child("nama_paket").getValue(String.class);
                    brt = areaSnapshot.child("berat").getValue(String.class);
                    hgwal = areaSnapshot.child("harga_paket").getValue(String.class);
                    hgkhir = areaSnapshot.child("harga_total").getValue(String.class);
                    tglmas = areaSnapshot.child("tglmasuk").getValue(String.class);
                    stt = areaSnapshot.child("status").getValue(String.class);
                    jncuci.setText(jns);
                    brtcuci.setText(brt + " kg");
                    hgcuci.setText(hgwal);
                    hgtotcuci.setText(hgkhir);
                    tglmsk.setText(tglmas);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void notif(){
        Button back;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Ambil_cucian.this);
        dialogBuilder.setTitle("Peringatan").setIcon(R.drawable.logo);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.pesan_gagal_ambil, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        back = dialogView.findViewById(R.id.button_cancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }

    private boolean hapuspencuci(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Cucian_masuk").child(id);
        dR.removeValue();
        return true;
    }
    private void kembalian(){
        Button back;
        TextView kembalianAnda;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Ambil_cucian.this);
        dialogBuilder.setTitle("Sukses").setIcon(R.drawable.logo);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.notif_kembalian, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog b = dialogBuilder.create();
        b.show();
        kembalianAnda= dialogView.findViewById(R.id.kembalianAnda);
        kembalianAnda.setText(kembalian);
        back = dialogView.findViewById(R.id.button_cancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.dismiss();
            }
        });
    }
}