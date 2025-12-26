package ui.ft.ccit.faculty.transaksi.transaksi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import ui.ft.ccit.faculty.transaksi.karyawan.model.Karyawan;
import ui.ft.ccit.faculty.transaksi.pelanggan.model.Pelanggan;

@Entity
@Table(name = "transaksi")
public class Transaksi {

    @Id
    @Column(name = "kode_transaksi", length = 4)
    private String kodeTransaksi;

    @Column(name = "tgl_transaksi", nullable = false)
    private LocalDateTime tglTransaksi;

    @Column(name = "id_pelanggan")
    private String idPelanggan;

    @Column(name = "id_karyawan")
    private String idKaryawan;

    protected Transaksi() {}

    public Transaksi(String kodeTransaksi,
                     LocalDateTime tglTransaksi,
                     String idPelanggan,
                     String idKaryawan) {
        this.kodeTransaksi = kodeTransaksi;
        this.tglTransaksi = tglTransaksi;
        this.idPelanggan = idPelanggan;
        this.idKaryawan = idKaryawan;
    }

    // getter & setter
    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }
}
