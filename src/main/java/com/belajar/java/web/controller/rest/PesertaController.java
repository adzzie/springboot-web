package com.belajar.java.web.controller.rest;

import com.belajar.java.web.dao.PesertaDao;
import com.belajar.java.web.entity.Peserta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public Page<Peserta> cariPeserta(Pageable page){
        return pd.findAll(page);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertPesertaBaru(@RequestBody Peserta p){
        pd.save(p);
    }




}
