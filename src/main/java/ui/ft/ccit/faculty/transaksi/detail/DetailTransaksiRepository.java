package ui.ft.ccit.faculty.transaksi.detail.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetailTransaksiRepository
        extends JpaRepository<DetailTransaksi, DetailTransaksiId> {

    List<DetailTransaksi> findByTransaksi_KodeTransaksi(String kodeTransaksi);
}
