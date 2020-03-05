package com.fend.loundry.Paket_cuci;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fend.loundry.Paket_cuci.Paket;
import com.fend.loundry.R;

import java.util.List;

public class listpaket extends ArrayAdapter<Paket> {

    private Activity context;
    List<Paket> pkt;

    public listpaket(Activity context, List<Paket> pkt) {
        super(context, R.layout.activity_listpaket, pkt);
        this.context=context;
        this.pkt=pkt;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_listpaket, null, true);

        TextView juduljenis = (TextView) listViewItem.findViewById(R.id.judul);
        TextView hargajenis = (TextView) listViewItem.findViewById(R.id.hargapaket);

        Paket pk = pkt.get(position);
        juduljenis.setText(pk.getNama_paket());
        hargajenis.setText(pk.getHarga_paket());

        return listViewItem;
    }


}
