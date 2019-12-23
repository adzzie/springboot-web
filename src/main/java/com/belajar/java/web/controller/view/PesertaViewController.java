package com.belajar.java.web.controller.view;

import com.belajar.java.web.dao.PesertaDao;
import com.belajar.java.web.entity.Peserta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Description PesertaController
 *
 * @author aji gojali
 */
@Controller
@RequestMapping("/peserta")
public class PesertaViewController {

    @Autowired
    private PesertaDao pd;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        dataFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dataFormat, true));
    }

    @GetMapping(value = {"","/","/index"})
    public String index(Model model){
        model.addAttribute("daftarPeserta", pd.findAll());
        return "peserta/index";
    }

    @GetMapping("/form" )
    public String view(@RequestParam(value = "id", required = false) String id, Peserta peserta, Model model){
        String method = "post";
        if (id != null && !id.isEmpty()){
            Optional<Peserta> p = pd.findById(id);
            if (p.orElse(null) != null){
                peserta = p.orElse(null);
                method = "put";
                System.out.println("masuk put");
            }
        }
        System.out.println("method "+method);
        model.addAttribute("method", method);
        model.addAttribute("peserta", peserta);
        return "peserta/form";
    }
//    @GetMapping("/form" )
//    public String view(@RequestParam("id") String id, Model model){
//
//        return "peserta/form";
//    }

    @PutMapping("/form")
    public String edit(@Valid Peserta peserta, BindingResult errors, Model model){
        if(errors.hasErrors()){
            return "peserta/form";
        }
        pd.save(peserta);
        return "redirect:index";
    }

    @PostMapping("/form")
    public String save(@Valid Peserta peserta, BindingResult errors){
        if(errors.hasErrors()){
            return "peserta/form";
        }
        pd.save(peserta);
        return "redirect:index";
    }

    @GetMapping("/hapus")
    public String delete(@RequestParam("id") String id, Model model){
        pd.deleteById(id);
//        if(p.orElse(null) == null){
//            model.addAttribute("error","Data tidak ada");
//        }else {
//            model.addAttribute("message", "Hapus data sukses");
//        }
        return "redirect:index";
    }
}
