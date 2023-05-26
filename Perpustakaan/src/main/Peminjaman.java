/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

public class Peminjaman {
    private int id;
    private int idBuku;
    private String tanggalPeminjaman;
    private String tanggalPengembalian;

    public Peminjaman(int id, int idBuku, String tanggalPeminjaman, String tanggalPengembalian) {
    this.id = id;
    this.idBuku = idBuku;
    this.tanggalPeminjaman = tanggalPeminjaman;
    this.tanggalPengembalian = tanggalPengembalian;
}


    public int getId() {
        return id;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public String getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public String getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    // Tambahkan setter untuk atribut tanggalPeminjaman dan tanggalPengembalian
    public void setTanggalPeminjaman(String tanggalPeminjaman) {
        this.tanggalPeminjaman = tanggalPeminjaman;
    }

    public void setTanggalPengembalian(String tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }

    // Override method toString() untuk tampilan yang lebih baik pada JList
    @Override
    public String toString() {
        return "ID Buku: " + idBuku + ", Tanggal Peminjaman: " + tanggalPeminjaman + ", Tanggal Pengembalian: " + tanggalPengembalian;
    }
}