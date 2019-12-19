package com.belajar.java.web.controller.rest;

import com.belajar.java.web.dao.PesertaDao;
import com.belajar.java.web.entity.Peserta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
    public void insertPesertaBaru(
            @RequestBody @Valid Peserta p){
        pd.save(p);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePeserta(
            @PathVariable("id") String id,
            @RequestBody @Valid Peserta p){
        p.setId(id);
        pd.save(p);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Peserta> cariPesertaById(@PathVariable("id") String id){
        Optional<Peserta> p =  pd.findById(id);
        if(p.orElse(null) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.of(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Peserta> hapusPeserta(@PathVariable("id") String id){
        Optional<Peserta> p = pd.findById(id);
        if(p.orElse(null) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pd.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
