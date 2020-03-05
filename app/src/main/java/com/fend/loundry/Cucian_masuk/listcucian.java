package com.fend.loundry.Cucian_masuk;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fend.loundry.R;

import java.util.List;

public class listcucian extends ArrayAdapter<Cucian_masuk> {
private Activity context;
        List<Cucian_masuk> pktcucimasuk;
public listcucian(Activity context, List<Cucian_masuk> pktcucimasuk) {
        super(context, R.layout.activity_listcucian,pktcucimasuk);
        this.context=context;
        this.pktcucimasuk=pktcucimasuk;
        }
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_listcucian, null, true);
        TextView nm = (TextView) listViewItem.findViewById(R.id.nama);
        TextView jc = (TextView) listViewItem.findViewById(R.id.jc);
        TextView harawal = (TextView) listViewItem.findViewById(R.id.hargaawal);
        TextView ber = (TextView) listViewItem.findViewById(R.id.berat);
        TextView hartot = (TextView) listViewItem.findViewById(R.id.hargatotal);
        TextView tglm = (TextView) listViewItem.findViewById(R.id.tglmas);
        TextView sta = (TextView) listViewItem.findViewById(R.id.status);
        Cucian_masuk pks = pktcucimasuk.get(position);
        nm.setText(pks.getNama_pelanggan());
        jc.setText(pks.getNama_paket());
        harawal.setText(pks.getHarga_paket());
        ber.setText(pks.getBerat());
        hartot.setText(pks.getHarga_total());
        tglm.setText(pks.getTglmasuk());
        sta.setText(pks.getStatus());
        return listViewItem;
        }
        }
