package com.belajar.java.web.dao;

import com.belajar.java.web.entity.Peserta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Description PesertaDao
 *
 * @author aji gojali
 */
public interface PesertaDao extends CrudRepository<Peserta, String>, PagingAndSortingRepository<Peserta, String> {

}
