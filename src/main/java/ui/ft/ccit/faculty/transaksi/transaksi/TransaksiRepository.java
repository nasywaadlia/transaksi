package ui.ft.ccit.faculty.transaksi.transaksi.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaksiRepository
        extends JpaRepository<Transaksi, String> {
    // kode_transaksi = String (char(4))
}
