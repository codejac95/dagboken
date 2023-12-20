package com.dagbok.dagbok;

import java.time.LocalDate;
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
  
   @GetMapping("/visaMellanDatum")
    public String visaMedDatumPage() {
        return "visaMellanDatum";
    }

    @PostMapping("/visaMellanDatum")
    public String visaMedDatum (@RequestParam("datum1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum1,
                                @RequestParam("datum2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum2, Model model) {
        model.addAttribute("dagböcker", dagbokRepository.searchBetweenDate(datum1,datum2));
        return "visaMellanDatum";
    }
}