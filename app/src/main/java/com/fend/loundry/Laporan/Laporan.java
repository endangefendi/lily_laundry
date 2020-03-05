package com.fend.loundry.Laporan;

public class Laporan {
    private String id;
    private String nama_pelanggan;
    private String nama_paket;
    private String harga_paket;
    private String berat;
    private String harga_total;
    private String tglmasuk;
    private String tglambil;
    private String bayar;
    private String kembali;


    public Laporan(){

    }
    public Laporan(String id,String nama_pelanggan,String nama_paket,String harga_paket,
                        String berat,String harga_total, String tglmasuk, String tglambil,
                        String bayar, String kembali){
        this.id=id;
        this.nama_pelanggan=nama_pelanggan;
        this.nama_paket=nama_paket;
        this.harga_paket=harga_paket;
        this.berat=berat;
        this.harga_total=harga_total;
        this.tglmasuk=tglmasuk;
        this.tglambil=tglambil;
        this.bayar=bayar;
        this.kembali=kembali;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }


    public String getNama_paket() {
        return nama_paket;
    }

    public String getHarga_paket() {
        return harga_paket;
    }

    public String getBerat() {
        return berat;
    }

    public String getHarga_total() {
        return harga_total;
    }

    public String getTglmasuk() {
        return tglmasuk;
    }


    public String getTglambil() {
        return tglambil;
    }


    public String getBayar() {
        return bayar;
    }


    public String getKembali() {
        return kembali;
    }
}


