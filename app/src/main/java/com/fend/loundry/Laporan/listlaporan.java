package com.fend.loundry.Laporan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fend.loundry.R;

import java.util.List;

public class listlaporan extends ArrayAdapter<Laporan> {
    private Activity context;
    List<Laporan> pktlaporan;
    public listlaporan(Activity context, List<Laporan> pktlaporan) {
        super(context, R.layout.activity_listlaporan,pktlaporan);
        this.context=context;
        this.pktlaporan=pktlaporan;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_listlaporan, null, true);
        TextView nm = (TextView) listViewItem.findViewById(R.id.nama);
        TextView jc = (TextView) listViewItem.findViewById(R.id.jc);
        TextView harawal = (TextView) listViewItem.findViewById(R.id.hargaawal);
        TextView ber = (TextView) listViewItem.findViewById(R.id.berat);
        TextView hartot = (TextView) listViewItem.findViewById(R.id.hargatotal);
        TextView tglm = (TextView) listViewItem.findViewById(R.id.tglmas);
        TextView tglkel = (TextView) listViewItem.findViewById(R.id.tglkel);
        TextView by = (TextView) listViewItem.findViewById(R.id.bayar);
        TextView kem = (TextView) listViewItem.findViewById(R.id.kembali);
        Laporan pks = pktlaporan.get(position);
        nm.setText(pks.getNama_pelanggan());
        jc.setText(pks.getNama_paket());
        harawal.setText(pks.getHarga_paket());
        ber.setText(pks.getBerat());
        hartot.setText(pks.getHarga_total());
        tglm.setText(pks.getTglmasuk());
        tglkel.setText(pks.getTglambil());
        by.setText(pks.getBayar());
        kem.setText(pks.getKembali());
        return listViewItem;
    }
}
