/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

/**
 *
 * @author USER
 */
public class Pelayanan {
    private final String namaPelayanan;
    private final JenisInformasi jenisInformasi;

    public Pelayanan(String namaPelayanan, JenisInformasi jenisInformasi) {
        this.namaPelayanan = namaPelayanan;
        this.jenisInformasi = jenisInformasi;
    }

    public String getNamaPelayanan() {
        return namaPelayanan;
    }

    public JenisInformasi getJenisInformasi() {
        return jenisInformasi;
    }

    // Tambahan kode jika diperlukan
}


