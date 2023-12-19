package com.dagbok.dagbok;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DagbokController {
    
    @Autowired
    private DagbokRepository dagbokRepository;

    @GetMapping
    public String getIndex(Model model) {
        LocalDate today = LocalDate.now();
        model.addAttribute("dagböcker", dagbokRepository.findByNotDeletedAndDate(today));
        return "index";
    }
    @PostMapping("/skapa")
    public String skapaInlägg(@RequestParam("rubrik") String nyRubrik,
                              @RequestParam("datum") LocalDate nyDatum,
                              @RequestParam("text") String nyText) {
        Dagbok dagbok = new Dagbok();
        dagbok.setRubrik(nyRubrik);
        dagbok.setDatum(nyDatum);
        dagbok.setText(nyText);
        dagbokRepository.save(dagbok);
        return "redirect:/";
    }
    @GetMapping("/radera")
    public String radera(@RequestParam int id) {
        dagbokRepository.raderadDagbok(id);
        return "redirect:/dagbok";
    }
  
    @PostMapping("/redigera")
    public String redigeraInlägg(@RequestParam("id") int id,
                                 @RequestParam("rubrik") String nyRubrik,
                                 @RequestParam("datum") LocalDate nyDatum,
                                 @RequestParam("text") String nyText) {
        dagbokRepository.redigeraDagbok(id, nyRubrik, nyDatum, nyText);
        return "redirect:/dagbok";
    }
    @GetMapping("/dagbok")
    public String visaDagbok(Model model) {
        model.addAttribute("dagböcker", dagbokRepository.findByNotDeleted());
        return "dagbok";
    }
  
   @GetMapping("/visaMedDatum")
    public String visaMedDatumPage() {
        return "visaMedDatum";
    }

    @PostMapping("/visaMedDatum")
    public String visaMedDatum(@RequestParam("datum") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum, Model model) {
        model.addAttribute("dagböcker", dagbokRepository.searchByDate(datum));
        return "visaMedDatum";
    }
}