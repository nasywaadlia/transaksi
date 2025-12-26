package ui.ft.ccit.faculty.transaksi.detail.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DetailTransaksiId implements Serializable {

    @Column(name = "kode_transaksi", length = 4)
    private String kodeTransaksi;

    @Column(name = "id_barang", length = 4)
    private String idBarang;

    public DetailTransaksiId() {}

    public DetailTransaksiId(String kodeTransaksi, String idBarang) {
        this.kodeTransaksi = kodeTransaksi;
        this.idBarang = idBarang;
    }

    // getters & setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailTransaksiId)) return false;
        DetailTransaksiId that = (DetailTransaksiId) o;
        return Objects.equals(kodeTransaksi, that.kodeTransaksi)
            && Objects.equals(idBarang, that.idBarang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kodeTransaksi, idBarang);
    }
}
