package com.stefanus.firebase.dbfirebase;

/**
 * Created by Stefanus on 05/09/2017.
 */

public class BiodataModel {

    public static final String TAG_NAMA = "nama";
    public static final String TAG_TTL = "ttl";
    public static final String TAG_JK = "jk";
    public static final String TAG_ALAMAT = "alamat";

    private String nama;
    private String ttl;
    private String jk;
    private String alamat;

    public BiodataModel() {
    }

    public String getNama() {
        return nama;
    }

    public BiodataModel setNama(String nama) {
        this.nama = nama;
        return this;
    }

    public String getTtl() {
        return ttl;
    }

    public BiodataModel setTtl(String ttl) {
        this.ttl = ttl;
        return this;
    }

    public String getJk() {
        return jk;
    }

    public BiodataModel setJk(String jk) {
        this.jk = jk;
        return this;
    }

    public String getAlamat() {
        return alamat;
    }

    public BiodataModel setAlamat(String alamat) {
        this.alamat = alamat;
        return this;
    }
}
