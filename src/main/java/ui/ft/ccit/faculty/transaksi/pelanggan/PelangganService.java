package ui.ft.ccit.faculty.transaksi.pelanggan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ui.ft.ccit.faculty.transaksi.pelanggan.model.Pelanggan;
import ui.ft.ccit.faculty.transaksi.pelanggan.model.PelangganRepository;

@Service
public class PelangganService {

    private final PelangganRepository repo;

    public PelangganService(PelangganRepository repo) {
        this.repo = repo;
    }

    public List<Pelanggan> getAll() {
        return repo.findAll();
    }

    public Pelanggan getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pelanggan tidak ditemukan"));
    }

    public Pelanggan save(Pelanggan pelanggan) {
        return repo.save(pelanggan);
    }

    public Pelanggan update(String id, Pelanggan pelanggan) {
        getById(id);
        pelanggan.setIdPelanggan(id);
        return repo.save(pelanggan);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
