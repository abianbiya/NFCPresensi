package com.cokilabs.nfc.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mahasiswa implements Serializable
{

    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("nim")
    @Expose
    private String nim;
    @SerializedName("hadir_pada")
    @Expose
    private String hadirPada;
    private final static long serialVersionUID = -9007799964356536757L;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getHadirPada() {
        return hadirPada;
    }

    public void setHadirPada(String hadirPada) {
        this.hadirPada = hadirPada;
    }

    public Mahasiswa(String nama, String nim, String hadirPada) {
        this.nama = nama;
        this.nim = nim;
        this.hadirPada = hadirPada;
    }
}
