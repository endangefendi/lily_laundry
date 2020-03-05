package com.fend.loundry.Cucian_masuk;

public class Cucian_masuk {
    private String id;
    private String nama_paket;
    private String harga_paket;
    private String berat;
    private String status;
    private String nama_pelanggan;
    private String harga_total;
    private String tglmasuk;
    public String getStatus() {
        return status;
    }


    public Cucian_masuk(){

    }
    public Cucian_masuk(String id,String nama_pelanggan,String nama_paket,
                        String harga_paket,String berat,String harga_total,String tglmasuk,String status){
        this.id=id;
        this.nama_pelanggan=nama_pelanggan;
        this.status=status;
        this.nama_paket=nama_paket;
        this.harga_paket=harga_paket;
        this.berat=berat;
        this.harga_total=harga_total;
        this.tglmasuk=tglmasuk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }


    public String getHarga_total() {
        return harga_total;
    }


    public String getTglmasuk() {
        return tglmasuk;
    }



}
