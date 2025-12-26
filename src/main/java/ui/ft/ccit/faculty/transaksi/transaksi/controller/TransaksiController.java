package ui.ft.ccit.faculty.transaksi.transaksi.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import ui.ft.ccit.faculty.transaksi.transaksi.dto.TransaksiResponse;
import ui.ft.ccit.faculty.transaksi.transaksi.dto.TransaksiRequest;
import ui.ft.ccit.faculty.transaksi.transaksi.model.Transaksi;
import ui.ft.ccit.faculty.transaksi.transaksi.service.TransaksiService;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    private final TransaksiService service;

    public TransaksiController(TransaksiService service) {
        this.service = service;
    }

    // GET semua transaksi
    @GetMapping
    public List<Transaksi> getAll() {
        return service.getAll();
    }

    // GET transaksi by id
    @GetMapping("/{id}")
    public Transaksi getById(@PathVariable String id) {
        return service.getById(id);
    }

    // POST transaksi REAL (return body)
    @PostMapping
public ResponseEntity<?> create(@RequestBody TransaksiRequest request) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.createTransaksi(request));
}


    // DELETE transaksi (nanti bisa rollback stok)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
