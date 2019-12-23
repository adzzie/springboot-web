package com.belajar.java.web.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Description Materi
 *
 * @author aji gojali
 */
@Entity
public class Materi {
    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull @Column(unique = true, length = 10)
    private String kode;

    @NotNull
    private String nama;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "materi"
    )
    private List<Sesi> daftarSesi = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<Sesi> getDaftarSesi() {
        return daftarSesi;
    }

    public void setDaftarSesi(List<Sesi> daftarSesi) {
        this.daftarSesi = daftarSesi;
    }
}
