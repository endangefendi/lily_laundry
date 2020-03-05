package com.fend.loundry.Paket_cuci;

public class Paket {
    private String id,harga_paket,nama_paket;

    public Paket(String id,String nama_paket,String harga_paket){
        this.id=id;
        this.nama_paket=nama_paket;
        this.harga_paket=harga_paket;


    }
    public Paket(){

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
}
