package com.belajar.java.web.controller.view;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description HaloController
 *
 * @author aji gojali
 */
@Controller
@RequestMapping("/halo")
public class HaloController {

    @GetMapping("")
    public void halohtml(@RequestParam(value = "nama", required = false) String nama, Model hasil){

        hasil.addAttribute("nama", nama);
        hasil.addAttribute("waktu", new Date());
//        return "halo";
    }
}
