package ui.ft.ccit.faculty.transaksi.transaksi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TransaksiResponse {

    private String kodeTransaksi;
    private LocalDateTime tglTransaksi;
    private String idPelanggan;
    private String idKaryawan;
    private List<DetailResponse> details;
    private Double total;

    public TransaksiResponse(
            String kodeTransaksi,
            LocalDateTime tglTransaksi,
            String idPelanggan,
            String idKaryawan,
            List<DetailResponse> details,
            Double total
    ) {
        this.kodeTransaksi = kodeTransaksi;
        this.tglTransaksi = tglTransaksi;
        this.idPelanggan = idPelanggan;
        this.idKaryawan = idKaryawan;
        this.details = details;
        this.total = total;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public LocalDateTime getTglTransaksi() {
        return tglTransaksi;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public List<DetailResponse> getDetails() {
        return details;
    }

    public Double getTotal() {
        return total;
    }

    // ===== INNER DETAIL RESPONSE =====
    public static class DetailResponse {
        private String idBarang;
        private Integer jumlah;
        private Double subtotal;

        public DetailResponse(String idBarang, Integer jumlah, Double subtotal) {
            this.idBarang = idBarang;
            this.jumlah = jumlah;
            this.subtotal = subtotal;
        }

        public String getIdBarang() {
            return idBarang;
        }

        public Integer getJumlah() {
            return jumlah;
        }

        public Double getSubtotal() {
            return subtotal;
        }
    }
}
