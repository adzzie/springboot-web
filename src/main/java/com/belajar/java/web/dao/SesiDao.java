package com.belajar.java.web.dao;

import com.belajar.java.web.entity.Materi;
import com.belajar.java.web.entity.Sesi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Description SesiDao
 *
 * @author aji gojali
 */
public interface SesiDao extends CrudRepository<Sesi, String>, PagingAndSortingRepository<Sesi, String> {
    public Page<Sesi> findByMateri(Materi m, Pageable page);

    @Query("select x from Sesi x where " +
            "x.mulai >=:m and " +
            "x.mulai <:s and " +
            "x.materi.kode =:k " +
            "order by x.mulai desc")
    public Page<Sesi> cariBerdasarkanTanggalMulaiDanKodeMateri(
            @Param("m") Date mulai,
            @Param("s") Date sampai,
            @Param("k") String kode,
            Pageable page);
}
