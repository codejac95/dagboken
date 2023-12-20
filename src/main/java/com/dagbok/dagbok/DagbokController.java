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
        model.addAttribute("diarys", dagbokRepository.findByNotDeletedAndDate(today));
        return "index";
    }
    @PostMapping("/create")
    public String createPost(@RequestParam("header") String newHeader,
                              @RequestParam("date") LocalDate newDate,
                              @RequestParam("text") String newText) {
        Dagbok dagbok = new Dagbok();
        dagbok.setHeader(newHeader);
        dagbok.setDate(newDate);
        dagbok.setText(newText);
        dagbokRepository.save(dagbok);
        return "redirect:/";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        dagbokRepository.deletedDiary(id);
        return "redirect:/allPosts";
    }
  
    @PostMapping("/edit")
    public String editPost(@RequestParam("id") int id,
                                 @RequestParam("header") String newHeader,
                                 @RequestParam("date") LocalDate newDate,
                                 @RequestParam("text") String newText) {
        dagbokRepository.editDiary(id, newHeader, newDate, newText);
        return "redirect:/allPosts";
    }
    @GetMapping("/allPosts")
    public String showDiary(Model model) {
        model.addAttribute("diarys", dagbokRepository.findByNotDeleted());
        return "allPosts";
    }
  
   @GetMapping("/showBetweenDate")
    public String showBetweenDate() {
        return "showBetweenDate";
    }

    @PostMapping("/showBetweenDate")
    public String showBetweenDate (@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                   @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2, Model model) {
        model.addAttribute("diarys", dagbokRepository.searchBetweenDate(date1,date2));
        return "showBetweenDate";
    }
}