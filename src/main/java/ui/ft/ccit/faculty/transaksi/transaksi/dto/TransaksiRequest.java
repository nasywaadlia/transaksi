package ui.ft.ccit.faculty.transaksi.transaksi.dto;

import java.util.List;

public class TransaksiRequest {

    private String kodeTransaksi;
    private String idPelanggan;
    private String idKaryawan;
    private List<DetailRequest> details;

    // ===== Getter & Setter TransaksiRequest =====

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public List<DetailRequest> getDetails() {
        return details;
    }

    public void setDetails(List<DetailRequest> details) {
        this.details = details;
    }

    // ===== Inner Class DetailRequest =====
    public static class DetailRequest {

        private String idBarang;
        private int jumlah;

        public String getIdBarang() {
            return idBarang;
        }

        public void setIdBarang(String idBarang) {
            this.idBarang = idBarang;
        }

        public int getJumlah() {
            return jumlah;
        }

        public void setJumlah(int jumlah) {
            this.jumlah = jumlah;
        }
    }
}
