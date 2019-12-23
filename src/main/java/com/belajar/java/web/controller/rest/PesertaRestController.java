package com.belajar.java.web.controller.rest;

import com.belajar.java.web.dao.PesertaDao;
import com.belajar.java.web.entity.Peserta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Description PesertaController
 *
 * @author aji gojali
 */
@RestController
@RequestMapping("/rest/peserta")
public class PesertaRestController {
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePeserta(
            @PathVariable("id") String id,
            @RequestBody Peserta p){
        p.setId(id);
        pd.save(p);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Peserta> cariPesertaById(@PathVariable("id") String id){
        return pd.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void hapusPeserta(@PathVariable("id") String id){
        pd.deleteById(id);
    }


}
