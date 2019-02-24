package com.cokilabs.nfc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Jadwal implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_hari")
    @Expose
    private Integer id_hari;
    @SerializedName("hari")
    @Expose
    private String hari;
    @SerializedName("jam_awal")
    @Expose
    private String jamAwal;
    @SerializedName("jam_akhir")
    @Expose
    private String jamAkhir;
    @SerializedName("kode_makul")
    @Expose
    private String kodeMakul;
    @SerializedName("nama_makul")
    @Expose
    private String namaMakul;
    @SerializedName("ruang_kuliah")
    @Expose
    private String ruangKuliah;
    @SerializedName("jumlah_peserta")
    @Expose
    private Integer jumlahPeserta;
    @SerializedName("sks")
    @Expose
    private Integer sks;
    @SerializedName("dosen1")
    @Expose
    private String dosen1;
    @SerializedName("dosen2")
    @Expose
    private String dosen2;
    @SerializedName("pertemuan_ke")
    @Expose
    private Integer pertemuanKe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJamAwal() {
        return jamAwal;
    }

    public void setJamAwal(String jamAwal) {
        this.jamAwal = jamAwal;
    }

    public String getJamAkhir() {
        return jamAkhir;
    }

    public void setJamAkhir(String jamAkhir) {
        this.jamAkhir = jamAkhir;
    }

    public String getKodeMakul() {
        return kodeMakul;
    }

    public void setKodeMakul(String kodeMakul) {
        this.kodeMakul = kodeMakul;
    }

    public String getNamaMakul() {
        return namaMakul;
    }

    public void setNamaMakul(String namaMakul) {
        this.namaMakul = namaMakul;
    }

    public String getRuangKuliah() {
        return ruangKuliah;
    }

    public void setRuangKuliah(String ruangKuliah) {
        this.ruangKuliah = ruangKuliah;
    }

    public Integer getJumlahPeserta() {
        return jumlahPeserta;
    }

    public void setJumlahPeserta(Integer jumlahPeserta) {
        this.jumlahPeserta = jumlahPeserta;
    }

    public Integer getSks() {
        return sks;
    }

    public void setSks(Integer sks) {
        this.sks = sks;
    }

    public String getDosen1() {
        return dosen1;
    }

    public void setDosen1(String dosen1) {
        this.dosen1 = dosen1;
    }

    public String getDosen2() {
        return dosen2;
    }

    public void setDosen2(String dosen2) {
        this.dosen2 = dosen2;
    }

    public Integer getPertemuanKe() {
        return pertemuanKe;
    }

    public void setPertemuanKe(Integer pertemuanKe) {
        this.pertemuanKe = pertemuanKe;
    }

    public Integer getId_hari() {
        return id_hari;
    }

    public void setId_hari(Integer id_hari) {
        this.id_hari = id_hari;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }
}
