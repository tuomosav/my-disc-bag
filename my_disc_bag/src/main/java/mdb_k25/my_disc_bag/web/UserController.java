package mdb_k25.my_disc_bag.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.*;

import mdb_k25.my_disc_bag.domain.RegisterForm;
import mdb_k25.my_disc_bag.domain.AppUser;
import mdb_k25.my_disc_bag.domain.AppUserRepository;

@Controller
public class UserController {
    private AppUserRepository repository; 
	
	public UserController(AppUserRepository appUserReporitory) {
		this.repository = appUserReporitory; 
	}
	
    @RequestMapping(value = "register")
    public String addDisc(Model model){
    	model.addAttribute("registerform", new RegisterForm());
        return "register";
    }	
    
    // Create new user. Check if user already exists & form validation w/ BindingResult

    @PostMapping("/saveuser")
    public String save(@Valid @ModelAttribute("registerform") RegisterForm registerForm, BindingResult bindingResult) {
    	if (!bindingResult.hasErrors()) { // Validation error check
    		if (registerForm.getPassword().equals(registerForm.getPasswordCheck())) { // Checking for password match		
	    		String pwd = registerForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	AppUser newUser = new AppUser();
		    	newUser.setPasswordHash(hashPwd);
		    	newUser.setUsername(registerForm.getUsername());
		    	newUser.setRole("USER");
		    	if (repository.findByUsername(registerForm.getUsername()) == null) { // Checking if user already exists
		    		repository.save(newUser);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "register";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
    			return "register";
    		}
    	}
    	else {
    		return "register";
    	}
    	return "redirect:/login";    	
    }    
}