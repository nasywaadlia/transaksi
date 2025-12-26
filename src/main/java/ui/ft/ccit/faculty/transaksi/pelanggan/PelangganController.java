package ui.ft.ccit.faculty.transaksi.pelanggan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ui.ft.ccit.faculty.transaksi.pelanggan.model.Pelanggan;
import ui.ft.ccit.faculty.transaksi.pelanggan.service.PelangganService;

import java.util.List;

@RestController
@RequestMapping("/api/pelanggan")
public class PelangganController {

    private final PelangganService service;

    public PelangganController(PelangganService service) {
        this.service = service;
    }

    // GET semua pelanggan
    @GetMapping
    public List<Pelanggan> getAll() {
        return service.getAll();
    }

    // GET pelanggan by id
    @GetMapping("/{id}")
    public Pelanggan getById(@PathVariable String id) {
        return service.getById(id);
    }

    // POST tambah pelanggan
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pelanggan create(@RequestBody Pelanggan pelanggan) {
        return service.save(pelanggan);
    }

    // PUT update pelanggan
    @PutMapping("/{id}")
    public Pelanggan update(@PathVariable String id, @RequestBody Pelanggan pelanggan) {
        return service.update(id, pelanggan);
    }

    // DELETE hapus pelanggan
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
