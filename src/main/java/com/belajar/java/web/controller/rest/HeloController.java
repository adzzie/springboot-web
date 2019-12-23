package com.belajar.java.web.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description HeloController
 *
 * @author aji gojali
 */
@RestController
@RequestMapping("/halo")
public class HeloController {

    @GetMapping("")
    public Map<String, Object> halo(@RequestParam(value = "nama", required = false) String nama){
        Map<String, Object> hasil = new HashMap<>();
        hasil.put("waktu", new Date());
        hasil.put("nama",nama);
        return hasil;
    }
}
