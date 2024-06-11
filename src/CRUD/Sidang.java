/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

/**
 *
 * @author USER
 */

public class Sidang {
    private final String namaSidang;
    private final String tanggal;
    private final String waktu;
    private final Pelayanan pelayanan;
    private final Pemohon pemohon;

    public Sidang(String namaSidang, String tanggal, String waktu, Pelayanan pelayanan, Pemohon pemohon) {
        this.namaSidang = namaSidang;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.pelayanan = pelayanan;
        this.pemohon = pemohon;
    }

    public String getNamaSidang() {
        return namaSidang;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    // Tambahan kode jika diperlukan
}
