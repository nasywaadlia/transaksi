package ui.ft.ccit.faculty.transaksi.karyawan.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KaryawanRepository
        extends JpaRepository<Karyawan, String> {
    // id_karyawan = String (char(4))
}
