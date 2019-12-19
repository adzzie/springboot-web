package com.belajar.java.web.controller.rest;

import com.belajar.java.web.dao.PesertaDao;
import com.belajar.java.web.entity.Peserta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description PesertaController
 *
 * @author aji gojali
 */
@RestController
@RequestMapping("/peserta")
public class PesertaController {
    @Autowired
    private PesertaDao pd;

    @GetMapping("/cari")
    public Page<Peserta> cariPeserta(Pageable page){
        return pd.findAll(page);
    }
}
