package com.fend.loundry;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fend.loundry.Ambil_cucian.Ambil_cucian;
import com.fend.loundry.Cucian_masuk.mencuci;
import com.fend.loundry.Laporan.Laporan_Pendapatan;
import com.fend.loundry.Laporan.kontenlaporan;
import com.fend.loundry.Paket_cuci.Paket_cuci;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        findViewById(R.id.cuci).setOnClickListener(this);
        findViewById(R.id.jeniscuci).setOnClickListener(this);
        findViewById(R.id.ambil).setOnClickListener(this);
        findViewById(R.id.laporan).setOnClickListener(this);
        System.out.println("");
    }
    @Override
    protected void onResume() {
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(home.this, MainActivity.class));
            finish();
        }
        super.onResume();
    }
    private void konfir(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Peringatan !!!");
        builder.setMessage("Anda yakin ingin Keluar ?");
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                logout();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void logout(){
        Toast.makeText(home.this, "Terimakasih", Toast.LENGTH_LONG).show();
        Intent logout = new Intent();
        setResult(RESULT_OK, logout);
        FirebaseAuth.getInstance().signOut();
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            konfir();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cuci:
                Intent intent1 = new Intent(home.this, mencuci.class);
                startActivity(intent1);
                break;
            case R.id.jeniscuci:
                Intent intent = new Intent(home.this, Paket_cuci.class);
                startActivity(intent);
                break;
            case R.id.ambil:
                Intent intent2 = new Intent(home.this, Ambil_cucian.class);
                startActivity(intent2);
                break;
            case R.id.laporan:
                Intent intent3 = new Intent(home.this, kontenlaporan.class);
                startActivity(intent3);
                break;
        }
    }

}
