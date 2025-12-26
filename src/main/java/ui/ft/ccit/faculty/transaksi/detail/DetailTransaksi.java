package ui.ft.ccit.faculty.transaksi.detail.model;

import jakarta.persistence.*;
import ui.ft.ccit.faculty.transaksi.transaksi.model.Transaksi;
import ui.ft.ccit.faculty.transaksi.barang.model.Barang;

@Entity
@Table(name = "detail_transaksi")
public class DetailTransaksi {

    @EmbeddedId
    private DetailTransaksiId id;

    @ManyToOne
    @MapsId("kodeTransaksi")
    @JoinColumn(name = "kode_transaksi", nullable = false)
    private Transaksi transaksi;

    @ManyToOne
    @MapsId("idBarang")
    @JoinColumn(name = "id_barang", nullable = false)
    private Barang barang;

    @Column(name = "jumlah", nullable = false)
    private Integer jumlah;

   @Transient
private Double subtotal;


    public DetailTransaksi() {}
    public DetailTransaksi(
        DetailTransaksiId id,
        Transaksi transaksi,
        Barang barang,
        Integer jumlah
        
) {
    this.id = id;
    this.transaksi = transaksi;
    this.barang = barang;
    this.jumlah = jumlah;
    
}


    // getter & setter
    public DetailTransaksiId getId() {
        return id;
    }

    public void setId(DetailTransaksiId id) {
        this.id = id;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Double getSubtotal() {
    if (barang == null || barang.getHarga() == null || jumlah == null) {
        return 0.0;
    }
    return barang.getHarga() * jumlah;
}

}
