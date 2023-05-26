/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import main.Buku;
import main.Peminjaman;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame {

    private Model model;
    private JList<Buku> listBuku;
    private JList<Peminjaman> listPeminjaman;
    private DefaultListModel<Buku> bukuListModel;
    private DefaultListModel<Peminjaman> peminjamanListModel;

    public View(Model model) {
        this.model = model;

        // Pengaturan frame
        setTitle("Sistem Manajemen Perpustakaan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Membuat komponen
        bukuListModel = new DefaultListModel<>();
        listBuku = new JList<>(bukuListModel);
        JScrollPane scrollPaneBuku = new JScrollPane(listBuku);
        JLabel labelBuku = new JLabel("Daftar Buku");

        peminjamanListModel = new DefaultListModel<>();
        listPeminjaman = new JList<>(peminjamanListModel);
        JScrollPane scrollPanePeminjaman = new JScrollPane(listPeminjaman);
        JLabel labelPeminjaman = new JLabel("Daftar Peminjaman");

        JButton tambahBukuButton = new JButton("Tambah Buku");
        JButton editBukuButton = new JButton("Edit Buku");
        JButton hapusBukuButton = new JButton("Hapus Buku");
        JButton tambahPeminjamanButton = new JButton("Tambah Peminjaman");
        JButton hapusPeminjamanButton = new JButton("Hapus Peminjaman");

        // Mengatur tata letak
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        add(labelBuku, gbc);
        gbc.gridy++;
        add(scrollPaneBuku, gbc);
        gbc.gridy++;
        add(tambahBukuButton, gbc);
        gbc.gridy++;
        add(editBukuButton, gbc);
        gbc.gridy++;
        add(hapusBukuButton, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        add(labelPeminjaman, gbc);
        gbc.gridy++;
        add(scrollPanePeminjaman, gbc);
        gbc.gridy++;
        add(tambahPeminjamanButton, gbc);
        gbc.gridy++;
        add(hapusPeminjamanButton, gbc);

        // Mengatur action listener
        tambahBukuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDialogTambahBuku();
            }
        });

        editBukuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buku selectedBuku = listBuku.getSelectedValue();
                if (selectedBuku != null) {
                    showDialogEditBuku(selectedBuku);
                }
            }
        });

        hapusBukuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buku selectedBuku = listBuku.getSelectedValue();
                if (selectedBuku != null) {
                    int result = JOptionPane.showConfirmDialog(View.this, "Apakah Anda yakin ingin menghapus buku ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        model.hapusBuku(selectedBuku);
                        updateDaftarBuku();
                    }
                }
            }
        });

        tambahPeminjamanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDialogTambahPeminjaman();
            }
        });

        hapusPeminjamanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Peminjaman selectedPeminjaman = listPeminjaman.getSelectedValue();
                if (selectedPeminjaman != null) {
                    int result = JOptionPane.showConfirmDialog(View.this, "Apakah Anda yakin ingin menghapus peminjaman ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        model.hapusPeminjaman(selectedPeminjaman);
                        updateDaftarPeminjaman();
                    }
                }
            }
        });
    }

    public void showView() {
        updateDaftarBuku();
        updateDaftarPeminjaman();
        setVisible(true);
    }

    public void updateDaftarBuku() {
        bukuListModel.clear();
        List<Buku> daftarBuku = model.getDaftarBuku();
        for (Buku buku : daftarBuku) {
            bukuListModel.addElement(buku);
        }
    }

    public void updateDaftarPeminjaman() {
        peminjamanListModel.clear();
        List<Peminjaman> daftarPeminjaman = model.getDaftarPeminjaman();
        for (Peminjaman peminjaman : daftarPeminjaman) {
            peminjamanListModel.addElement(peminjaman);
        }
    }

    private void showDialogTambahBuku() {
        JTextField judulField = new JTextField();
        JTextField penulisField = new JTextField();
        JTextField tahunTerbitField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Judul:"));
        panel.add(judulField);
        panel.add(new JLabel("Penulis:"));
        panel.add(penulisField);
        panel.add(new JLabel("Tahun Terbit:"));
        panel.add(tahunTerbitField);

        int result = JOptionPane.showConfirmDialog(View.this, panel, "Tambah Buku", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String judul = judulField.getText();
            String penulis = penulisField.getText();
            String tahunTerbitText = tahunTerbitField.getText();
            int tahunTerbit = 0;
            if (!tahunTerbitText.isEmpty()) {
                tahunTerbit = Integer.parseInt(tahunTerbitText);
            }

            Buku buku = new Buku(0, judul, penulis, tahunTerbit);
            model.tambahBuku(buku);
            updateDaftarBuku();
        }
    }

    private void showDialogEditBuku(Buku buku) {
        JTextField judulField = new JTextField(buku.getJudul());
        JTextField penulisField = new JTextField(buku.getPenulis());
        JTextField tahunTerbitField = new JTextField(Integer.toString(buku.getTahunTerbit()));

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Judul:"));
        panel.add(judulField);
        panel.add(new JLabel("Penulis:"));
        panel.add(penulisField);
        panel.add(new JLabel("Tahun Terbit:"));
        panel.add(tahunTerbitField);

        int result = JOptionPane.showConfirmDialog(View.this, panel, "Edit Buku", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String judul = judulField.getText();
            String penulis = penulisField.getText();
            int tahunTerbit = Integer.parseInt(tahunTerbitField.getText());
            buku.setJudul(judul);
            buku.setPenulis(penulis);
            buku.setTahunTerbit(tahunTerbit);

            model.editBuku(buku);
            updateDaftarBuku();
        }
    }

    private void showDialogTambahPeminjaman() {
    JComboBox<Buku> bukuComboBox = new JComboBox<>(model.getDaftarBuku().toArray(new Buku[0]));
    JTextField tanggalPeminjamanField = new JTextField();
    JTextField tanggalPengembalianField = new JTextField();

    JPanel panel = new JPanel(new GridLayout(0, 2));
    panel.add(new JLabel("Buku:"));
    panel.add(bukuComboBox);
    panel.add(new JLabel("Tanggal Peminjaman:"));
    panel.add(tanggalPeminjamanField);
    panel.add(new JLabel("Tanggal Pengembalian:"));
    panel.add(tanggalPengembalianField);

    int result = JOptionPane.showConfirmDialog(View.this, panel, "Tambah Peminjaman", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        Buku selectedBuku = (Buku) bukuComboBox.getSelectedItem();
        int bukuId = selectedBuku.getId();
        String tanggalPeminjaman = tanggalPeminjamanField.getText();
        String tanggalPengembalian = tanggalPengembalianField.getText();

        Peminjaman peminjaman = new Peminjaman(selectedBuku.getId(), selectedBuku.getIdBuku(), tanggalPeminjaman, tanggalPengembalian);
        model.tambahPeminjaman(peminjaman);
        updateDaftarPeminjaman();
        List<Peminjaman> daftarPeminjaman = model.getDaftarPeminjaman();

    }
}

}

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
