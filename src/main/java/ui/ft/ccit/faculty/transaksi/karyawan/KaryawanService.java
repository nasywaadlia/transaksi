package ui.ft.ccit.faculty.transaksi.karyawan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ui.ft.ccit.faculty.transaksi.karyawan.model.Karyawan;
import ui.ft.ccit.faculty.transaksi.karyawan.model.KaryawanRepository;

@Service
public class KaryawanService {

    private final KaryawanRepository repo;

    public KaryawanService(KaryawanRepository repo) {
        this.repo = repo;
    }

    public List<Karyawan> getAll() {
        return repo.findAll();
    }

    public Karyawan getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Karyawan tidak ditemukan"));
    }

    public Karyawan save(Karyawan karyawan) {
        return repo.save(karyawan);
    }

    public Karyawan update(String id, Karyawan karyawan) {
        getById(id);
        karyawan.setIdKaryawan(id);
        return repo.save(karyawan);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
