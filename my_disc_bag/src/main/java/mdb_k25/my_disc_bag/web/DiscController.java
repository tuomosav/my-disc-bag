package mdb_k25.my_disc_bag.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import mdb_k25.my_disc_bag.domain.DiscRepository;
import mdb_k25.my_disc_bag.domain.CategoryRepository;
import mdb_k25.my_disc_bag.domain.Disc;


@Controller
public class DiscController {

    private DiscRepository repository;

    private CategoryRepository crepository;

    public DiscController(DiscRepository discRepository, CategoryRepository categoryRepository) {
        this.repository = discRepository;
        this.crepository = categoryRepository;
    }

    //Show login
    @RequestMapping(value="/login")
    public String login() {
        return "login";
    }

    //Show all discs
    @GetMapping("/disclist")
    public String discList(Model model) {
        model.addAttribute("discs", repository.findAll());
        return "disclist";
    }
    
    //Add new disc
    @RequestMapping(value = "/add-disc")
    public String addDisc(Model model) {
        model.addAttribute("disc", new Disc());
        model.addAttribute("categories", crepository.findAll());
        return "add-disc";
    }
    
    //Save new disc
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("disc") Disc disc, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Errors errors " + disc);
            model.addAttribute("disc", disc);
            model.addAttribute("categories", crepository.findAll());
            return "add-disc";
        }
        System.out.println("Save " + disc);
        repository.save(disc);
        return "redirect:disclist";
    }
    
    //Delete disc
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteDisc(@PathVariable("id") Long discId, Model model) {
        repository.deleteById(discId);
        return "redirect:../disclist";
    }

    //Edit disc
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editDisc(@PathVariable("id") Long discId, Model model) {
        model.addAttribute("disc", repository.findById(discId));
        model.addAttribute("categories", crepository.findAll());
        return "edit-disc";
    }
    
}