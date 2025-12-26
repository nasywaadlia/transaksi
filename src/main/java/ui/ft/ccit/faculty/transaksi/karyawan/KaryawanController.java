package ui.ft.ccit.faculty.transaksi.karyawan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ui.ft.ccit.faculty.transaksi.karyawan.model.Karyawan;
import ui.ft.ccit.faculty.transaksi.karyawan.service.KaryawanService;

import java.util.List;

@RestController
@RequestMapping("/api/karyawan")
public class KaryawanController {

    private final KaryawanService service;

    public KaryawanController(KaryawanService service) {
        this.service = service;
    }

    // GET semua karyawan
    @GetMapping
    public List<Karyawan> getAll() {
        return service.getAll();
    }

    // GET karyawan by id
    @GetMapping("/{id}")
    public Karyawan getById(@PathVariable String id) {
        return service.getById(id);
    }

    // POST tambah karyawan
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Karyawan create(@RequestBody Karyawan karyawan) {
        return service.save(karyawan);
    }

    // PUT update karyawan
    @PutMapping("/{id}")
    public Karyawan update(@PathVariable String id, @RequestBody Karyawan karyawan) {
        return service.update(id, karyawan);
    }

    // DELETE hapus karyawan
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
