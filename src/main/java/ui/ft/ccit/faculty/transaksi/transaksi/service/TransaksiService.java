package ui.ft.ccit.faculty.transaksi.transaksi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ui.ft.ccit.faculty.transaksi.transaksi.dto.TransaksiResponse;
import ui.ft.ccit.faculty.transaksi.barang.model.Barang;
import ui.ft.ccit.faculty.transaksi.barang.model.BarangRepository;
import ui.ft.ccit.faculty.transaksi.detail.model.DetailTransaksi;
import ui.ft.ccit.faculty.transaksi.detail.model.DetailTransaksiId;
import ui.ft.ccit.faculty.transaksi.detail.model.DetailTransaksiRepository;
import ui.ft.ccit.faculty.transaksi.karyawan.model.KaryawanRepository;
import ui.ft.ccit.faculty.transaksi.pelanggan.model.PelangganRepository;
import ui.ft.ccit.faculty.transaksi.transaksi.dto.TransaksiRequest;
import ui.ft.ccit.faculty.transaksi.transaksi.model.Transaksi;
import ui.ft.ccit.faculty.transaksi.transaksi.model.TransaksiRepository;

@Service
public class TransaksiService {

    private final TransaksiRepository transaksiRepo;
    private final DetailTransaksiRepository detailRepo;
    private final BarangRepository barangRepo;
    private final PelangganRepository pelangganRepo;
    private final KaryawanRepository karyawanRepo;

    public TransaksiService(
            TransaksiRepository transaksiRepo,
            DetailTransaksiRepository detailRepo,
            BarangRepository barangRepo,
            PelangganRepository pelangganRepo,
            KaryawanRepository karyawanRepo) {

        this.transaksiRepo = transaksiRepo;
        this.detailRepo = detailRepo;
        this.barangRepo = barangRepo;
        this.pelangganRepo = pelangganRepo;
        this.karyawanRepo = karyawanRepo;
    }

    // ================= CRUD DASAR =================

    public List<Transaksi> getAll() {
        return transaksiRepo.findAll();
    }

    public Transaksi getById(String id) {
        return transaksiRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));
    }

    public void delete(String id) {
        transaksiRepo.deleteById(id);
    }

    // ================= TRANSAKSI REAL =================

    @Transactional
    public TransaksiResponse createTransaksi(TransaksiRequest request) {

        // 1️⃣ Validasi pelanggan
        pelangganRepo.findById(request.getIdPelanggan())
                .orElseThrow(() -> new RuntimeException("Pelanggan tidak ditemukan"));

        // 2️⃣ Validasi karyawan
        karyawanRepo.findById(request.getIdKaryawan())
                .orElseThrow(() -> new RuntimeException("Karyawan tidak ditemukan"));

        // 3️⃣ Simpan transaksi (HEADER)
        Transaksi transaksi = new Transaksi(
                request.getKodeTransaksi(),
                LocalDateTime.now(),
                request.getIdPelanggan(),
                request.getIdKaryawan()
        );
        transaksiRepo.save(transaksi);

        // 4️⃣ Simpan detail transaksi + update stok
        for (TransaksiRequest.DetailRequest d : request.getDetails()) {

            Barang barang = barangRepo.findById(d.getIdBarang())
                    .orElseThrow(() ->
                            new RuntimeException("Barang " + d.getIdBarang() + " tidak ditemukan"));

            if (barang.getStok() < d.getJumlah()) {
                throw new RuntimeException(
                        "Stok barang " + barang.getNama() + " tidak mencukupi");
            }

            // kurangi stok
            barang.setStok((short) (barang.getStok() - d.getJumlah()));
            barangRepo.save(barang);

            // BUAT DETAIL TRANSAKSI (SESUAI ENTITY)
            DetailTransaksi detail = new DetailTransaksi();
            detail.setId(new DetailTransaksiId(
                    transaksi.getKodeTransaksi(),
                    barang.getIdBarang()
            ));
            detail.setTransaksi(transaksi);
            detail.setBarang(barang);
            detail.setJumlah(d.getJumlah());
           

            detailRepo.save(detail);
        }
         List<DetailTransaksi> savedDetails =
            detailRepo.findByTransaksi_KodeTransaksi(transaksi.getKodeTransaksi());

    List<TransaksiResponse.DetailResponse> detailResponses =
            savedDetails.stream()
                    .map(d -> new TransaksiResponse.DetailResponse(
                            d.getBarang().getIdBarang(),
                            d.getJumlah(),
                            d.getSubtotal()
                    ))
                    .toList();

    Double total = detailResponses.stream()
            .mapToDouble(TransaksiResponse.DetailResponse::getSubtotal)
            .sum();

    return new TransaksiResponse(
            transaksi.getKodeTransaksi(),
            LocalDateTime.now(),                 // ⬅ aman
        request.getIdPelanggan(),            // ⬅ dari request
        request.getIdKaryawan(),             // ⬅ dari request
        detailResponses,
        total
    );
    }
    @Transactional
public TransaksiResponse updateTransaksi(String kodeTransaksi, TransaksiRequest request) {

    // 1️⃣ Ambil transaksi lama
    Transaksi transaksi = transaksiRepo.findById(kodeTransaksi)
            .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));

    // 2️⃣ Validasi pelanggan & karyawan
    pelangganRepo.findById(request.getIdPelanggan())
            .orElseThrow(() -> new RuntimeException("Pelanggan tidak ditemukan"));

    karyawanRepo.findById(request.getIdKaryawan())
            .orElseThrow(() -> new RuntimeException("Karyawan tidak ditemukan"));

    // 3️⃣ Ambil detail lama → rollback stok
    List<DetailTransaksi> oldDetails =
            detailRepo.findByTransaksi_KodeTransaksi(kodeTransaksi);

    for (DetailTransaksi d : oldDetails) {
        Barang barang = d.getBarang();
        barang.setStok((short) (barang.getStok() + d.getJumlah()));
        barangRepo.save(barang);
    }

    // 4️⃣ Hapus detail lama
    detailRepo.deleteAll(oldDetails);

    // 6️⃣ Simpan detail baru + kurangi stok
    for (TransaksiRequest.DetailRequest d : request.getDetails()) {

        Barang barang = barangRepo.findById(d.getIdBarang())
                .orElseThrow(() ->
                        new RuntimeException("Barang " + d.getIdBarang() + " tidak ditemukan"));

        if (barang.getStok() < d.getJumlah()) {
            throw new RuntimeException(
                    "Stok barang " + barang.getNama() + " tidak mencukupi");
        }

        barang.setStok((short) (barang.getStok() - d.getJumlah()));
        barangRepo.save(barang);

        DetailTransaksi detail = new DetailTransaksi();
        detail.setId(new DetailTransaksiId(
                transaksi.getKodeTransaksi(),
                barang.getIdBarang()
        ));
        detail.setTransaksi(transaksi);
        detail.setBarang(barang);
        detail.setJumlah(d.getJumlah());

        detailRepo.save(detail);
    }

    // 7️⃣ Build response
    List<DetailTransaksi> savedDetails =
            detailRepo.findByTransaksi_KodeTransaksi(transaksi.getKodeTransaksi());

    List<TransaksiResponse.DetailResponse> detailResponses =
            savedDetails.stream()
                    .map(d -> new TransaksiResponse.DetailResponse(
                            d.getBarang().getIdBarang(),
                            d.getJumlah(),
                            d.getSubtotal()
                    ))
                    .toList();

    Double total = detailResponses.stream()
            .mapToDouble(TransaksiResponse.DetailResponse::getSubtotal)
            .sum();

   return new TransaksiResponse(
    transaksi.getKodeTransaksi(),
    LocalDateTime.now(),          // atau transaksi.getCreatedAt kalau ada
    request.getIdPelanggan(),
    request.getIdKaryawan(),
    detailResponses,
    total
);
}
}
