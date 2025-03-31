package mdb_k25.my_disc_bag.web;

//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import mdb_k25.my_disc_bag.domain.DiscRepository;
import mdb_k25.my_disc_bag.domain.AppUser;
import mdb_k25.my_disc_bag.domain.AppUserRepository;
import mdb_k25.my_disc_bag.domain.CategoryRepository;
import mdb_k25.my_disc_bag.domain.Disc;


@Controller
public class DiscController {

    private DiscRepository repository;
    private CategoryRepository crepository;
    private AppUserRepository aurepository;

    public DiscController(DiscRepository discRepository, CategoryRepository categoryRepository, AppUserRepository appUserRepository) {
        this.repository = discRepository;
        this.crepository = categoryRepository;
        this.aurepository = appUserRepository;
    }

    //Show login
    @RequestMapping(value="/login")
    public String login() {
        return "login";
    }
    
    //Show all discs based on role
    @GetMapping("/disclist")
public String discList(Model model, @AuthenticationPrincipal AppUser user) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
        System.out.println("Authentication: " + authentication.getName());
        System.out.println("Principal: " + authentication.getPrincipal());
    } else {
        System.out.println("No authentication found in SecurityContext.");
    }

    if (user == null) {
        System.out.println("No authenticated user found.");
        return "redirect:/login";
    }
    System.out.println("Authenticated user: " + user.getUsername());
    if (user.getRole().equals("ADMIN")) {
        model.addAttribute("discs", repository.findAll());
    } else {
        model.addAttribute("discs", repository.findByUser(user));
    }
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
    public String save(@Valid @ModelAttribute("disc") Disc disc, BindingResult bindingResult, Model model,
    @AuthenticationPrincipal AppUser user, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("Errors errors " + disc);
            model.addAttribute("disc", disc);
            model.addAttribute("categories", crepository.findAll());
            return "add-disc";
        }
        System.out.println("Save " + disc);
        disc.setUser(user);
        repository.save(disc);
        redirectAttributes.addFlashAttribute("successMessage", "Disc added successfully!");
        return "redirect:disclist";
    }
    
    //Delete disc
    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteDisc(@PathVariable("id") Long discId, Model model, @AuthenticationPrincipal AppUser user) {
        Disc disc = repository.findById(discId).orElse(null);

        if (disc == null || (disc.getUser() != null && !disc.getUser().getUsername().equals(user.getUsername()) && !user.getRole().equals("ADMIN"))) {
            return "redirect:/disclist";
        }
        repository.deleteById(discId);
        return "redirect:../disclist";
    }

    //Edit disc
    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editDisc(@PathVariable("id") Long discId, Model model, @AuthenticationPrincipal AppUser user) {
        Disc disc = repository.findById(discId).orElse(null);

        if (disc == null || (disc.getUser() != null && !disc.getUser().getUsername().equals(user.getUsername()) && !user.getRole().equals("ADMIN"))) {
            return "redirect:/disclist";
        }
        model.addAttribute("disc", disc);
        model.addAttribute("categories", crepository.findAll());
        return "edit-disc";
    }
    
}