package mdb_k25.my_disc_bag.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;

import mdb_k25.my_disc_bag.domain.RegisterForm;
import mdb_k25.my_disc_bag.domain.AppUser;
import mdb_k25.my_disc_bag.domain.AppUserRepository;
import java.util.Optional;

@Controller
public class UserController {
    private AppUserRepository repository; 
	
	public UserController(AppUserRepository appUserReporitory) {
		this.repository = appUserReporitory; 
	}
	
    @RequestMapping(value = "register")
    public String addUser(Model model){
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

	// List all users (admin!)
    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", repository.findAll());
        return "userlist";
    }

	// Delete user by id (admin!)
    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/admin/users";
    }

	// Show edit form for user (admin!)
    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        Optional<AppUser> user = repository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "edituser";
        }
        return "redirect:/admin/users";
    }

	// Save edited user details (admin!)
    @PostMapping("/admin/updateuser")
    public String updateUser(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "editUser"; // Returning to edit form if validation fails
		}
	
		AppUser existingUser = repository.findById(user.getId()).orElse(null);
		if (existingUser != null) {
			// Preserve existing password if no new password is provided
			user.setPasswordHash(existingUser.getPassword());
	
			// Save the updated user
			repository.save(user);
		}
	
		return "redirect:/admin/users";
	}

	@RequestMapping("/userlist")
	public String redirectToAdminUsers() {
    	return "redirect:/admin/users";
	}
}