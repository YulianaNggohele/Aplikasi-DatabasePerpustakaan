/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private Connection connection;
    private List<Buku> daftarBuku;
    private List<Peminjaman> daftarPeminjaman;

    public Model() {
        try {
            // Membuat koneksi ke database SQLite
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:perpustakaan.db");
            daftarBuku = new ArrayList<>();
            daftarPeminjaman = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Koneksi gagal: " + e.getMessage());
        }
    }

    public void tambahBuku(Buku buku) {
    try {
        // Query SQL untuk memasukkan data buku ke database
        String query = "INSERT OR IGNORE INTO buku (id, judul, penulis, tahun_terbit) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, buku.getId());
        statement.setString(2, buku.getJudul());
        statement.setString(3, buku.getPenulis());
        statement.setInt(4, buku.getTahunTerbit());
        statement.executeUpdate();
        
        // Menambahkan buku ke daftar buku
        daftarBuku.add(buku);
    } catch (SQLException e) {
        System.err.println("Error tambah buku: " + e.getMessage());
    }
}

    public void editBuku(Buku buku) {
        try {
            // Query SQL untuk mengupdate data buku di database
            String query = "UPDATE buku SET judul=?, penulis=?, tahun_terbit=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, buku.getJudul());
            statement.setString(2, buku.getPenulis());
            statement.setInt(3, buku.getTahunTerbit());
            statement.setInt(4, buku.getId());
            statement.executeUpdate();

            // Mencari buku dalam daftar buku dan mengupdate datanya
            for (Buku existingBuku : daftarBuku) {
                if (existingBuku.getId() == buku.getId()) {
                    existingBuku.setJudul(buku.getJudul());
                    existingBuku.setPenulis(buku.getPenulis());
                    existingBuku.setTahunTerbit(buku.getTahunTerbit());
                    break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error edit buku: " + e.getMessage());
        }
    }

    public void hapusBuku(Buku buku) {
    try {
        // Query SQL untuk menghapus buku berdasarkan ID
        String query = "DELETE FROM buku WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, buku.getId());
        statement.executeUpdate();

        // Menghapus buku dari daftar buku
        daftarBuku.remove(buku);
    } catch (SQLException e) {
        System.err.println("Error hapus buku: " + e.getMessage());
    }
}

    
    public void tambahPeminjaman(Peminjaman peminjaman) {
        try {
            // Query SQL untuk memasukkan data peminjaman ke database
            String query = "INSERT INTO peminjaman (id, id_buku, tanggal_peminjaman, tanggal_pengembalian) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, peminjaman.getId());
            statement.setInt(2, peminjaman.getIdBuku());
            statement.setString(3, peminjaman.getTanggalPeminjaman());
            statement.setString(4, peminjaman.getTanggalPengembalian());
            statement.executeUpdate();
            
            // Menambahkan peminjaman ke daftar peminjaman
            daftarPeminjaman.add(peminjaman);
        } catch (SQLException e) {
            System.err.println("Error tambah peminjaman: " + e.getMessage());
        }
    }

    public List<Buku> getDaftarBuku() {
        return daftarBuku;
    }

    public List<Peminjaman> getDaftarPeminjaman() {
        return daftarPeminjaman;
    }

    public void loadDaftarBuku() {
        try {
            // Query SQL untuk mengambil data buku dari database
            String query = "SELECT * FROM buku";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            // Membersihkan daftar buku sebelumnya
            daftarBuku.clear();
            
            // Menambahkan buku dari hasil query ke daftar buku
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String judul = resultSet.getString("judul");
                String penulis = resultSet.getString("penulis");
                int tahunTerbit = resultSet.getInt("tahun_terbit");
                Buku buku = new Buku(id, judul, penulis, tahunTerbit);
                daftarBuku.add(buku);
            }
        } catch (SQLException e) {
            System.err.println("Error load daftar buku: " + e.getMessage());
        }
    }

    public void hapusPeminjaman(Peminjaman peminjaman) {
    try {
        // Query SQL untuk menghapus peminjaman berdasarkan ID
        String query = "DELETE FROM peminjaman WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, peminjaman.getId());
        statement.executeUpdate();

        // Menghapus peminjaman dari daftar peminjaman
        daftarPeminjaman.remove(peminjaman);
    } catch (SQLException e) {
        System.err.println("Error hapus peminjaman: " + e.getMessage());
    }
}

    
    public void loadDaftarPeminjaman() {
        try {
            // Query SQL untuk mengambil data peminjaman dari database
            String query = "SELECT * FROM peminjaman";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            // Membersihkan daftar peminjaman sebelumnya
            daftarPeminjaman.clear();
            
            // Menambahkan peminjaman dari hasil query ke daftar peminjaman
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idBuku = resultSet.getInt("id_buku");
                String tanggalPeminjaman = resultSet.getString("tanggal_peminjaman");
                String tanggalPengembalian = resultSet.getString("tanggal_pengembalian");
                Peminjaman peminjaman = new Peminjaman(id, idBuku, tanggalPeminjaman, tanggalPengembalian);
                daftarPeminjaman.add(peminjaman);
            }
        } catch (SQLException e) {
            System.err.println("Error load daftar peminjaman: " + e.getMessage());
        }
    }
}