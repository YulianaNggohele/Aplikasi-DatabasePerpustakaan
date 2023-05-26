--
-- File generated with SQLiteStudio v3.4.4 on Thu May 25 18:44:32 2023
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: buku
DROP TABLE IF EXISTS buku;

CREATE TABLE IF NOT EXISTS buku (
    id           INTEGER   PRIMARY KEY AUTOINCREMENT,
    judul        TEXT (50),
    penulis      TEXT (50),
    tahun_terbit INTEGER
);


-- Table: peminjaman
DROP TABLE IF EXISTS peminjaman;

CREATE TABLE IF NOT EXISTS peminjaman (
    id                   INTEGER   PRIMARY KEY AUTOINCREMENT,
    id_buku              INTEGER   REFERENCES buku (id),
    tanggal_peminjaman   TEXT (50),
    tanggal_pengembalian TEXT (50) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;