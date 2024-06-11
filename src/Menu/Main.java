/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Menu;

/**
 *
 * @author USER
 */
import CRUD.JenisInformasi;
import CRUD.Pelayanan;
import CRUD.Pemohon;
import CRUD.Sidang;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Input data JenisInformasi
        try (Scanner scanner = new Scanner(System.in)) {
            // Input data JenisInformasi
            System.out.println("Masukkan nama jenis informasi:");
            String namaJenisInformasi = scanner.nextLine();
            JenisInformasi jenisInformasi = new JenisInformasi(namaJenisInformasi); // Perubahan disini
            
            // Input data Pelayanan
            System.out.println("Masukkan nama pelayanan:");
            String namaPelayanan = scanner.nextLine();
            Pelayanan pelayanan = new Pelayanan(namaPelayanan, jenisInformasi);
            
            // Input data Pemohon
            System.out.println("Masukkan nama pemohon:");
            String namaPemohon = scanner.nextLine();
            System.out.println("Masukkan alamat pemohon:");
            String alamatPemohon = scanner.nextLine();
            Pemohon pemohon = new Pemohon(namaPemohon, alamatPemohon);
            
            // Input data Sidang
            System.out.println("Masukkan nama sidang:");
            String namaSidang = scanner.nextLine();
            System.out.println("Masukkan tanggal sidang (YYYY-MM-DD):");
            String tanggalSidang = scanner.nextLine();
            System.out.println("Masukkan waktu sidang (HH:MM):");
            String waktuSidang = scanner.nextLine();
            Sidang sidang = new Sidang(namaSidang, tanggalSidang, waktuSidang, pelayanan, pemohon);
            
            // Output data
            System.out.println("\nData yang dimasukkan:");
            System.out.println("Jenis Informasi: " + jenisInformasi.getNamaJenisInformasi());
            System.out.println("Nama Pelayanan: " + pelayanan.getNamaPelayanan() + ", Jenis Informasi: " + pelayanan.getJenisInformasi().getNamaJenisInformasi());
            System.out.println("Nama Pemohon: " + pemohon.getNama() + ", Alamat: " + pemohon.getAlamat());
            System.out.println("Nama Sidang: " + sidang.getNamaSidang() + ", Tanggal: " + sidang.getTanggal() + ", Waktu: " + sidang.getWaktu());
        }
    }
}

