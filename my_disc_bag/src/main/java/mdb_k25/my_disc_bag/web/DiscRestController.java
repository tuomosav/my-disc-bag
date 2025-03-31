package mdb_k25.my_disc_bag.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import mdb_k25.my_disc_bag.domain.Disc;
import mdb_k25.my_disc_bag.domain.DiscRepository;
import mdb_k25.my_disc_bag.domain.AppUser;
import mdb_k25.my_disc_bag.domain.AppUserRepository;
import mdb_k25.my_disc_bag.domain.CategoryRepository;


@RestController
public class DiscRestController {

    private static final Logger log = LoggerFactory.getLogger(DiscRestController.class);

    private final DiscRepository discRepository;
    private CategoryRepository cRepository;
    private AppUserRepository auRepository;

    public DiscRestController(DiscRepository discRepository, CategoryRepository cRepository, AppUserRepository auRepository) {
        this.discRepository = discRepository;
        this.cRepository = cRepository;
        this.auRepository = auRepository;
    }
    
    //Return list of discs
    @GetMapping("/discs")
    public List<Disc> getDiscs(@AuthenticationPrincipal AppUser user) {
    log.info("Fetch and return discs for user: " + user.getUsername());
    if (user.getRole().equals("ADMIN")) {
        return (List<Disc>) discRepository.findAll();  // Admin sees all discs
    } else {
        return discRepository.findByUser(user);  // Users see only their own discs
    }
}
    
    //Add new disc
    @PostMapping("discs")
    Disc newDisc(@RequestBody Disc newDisc, @AuthenticationPrincipal AppUser user) {
        log.info("User " + user.getUsername() + " adds a new disc.");
        newDisc.setUser(user);
        return discRepository.save(newDisc);
    }
    
    //Edit existing disc's information
    @PutMapping("discs/{id}")
    public ResponseEntity<?> editDisc(@RequestBody Disc editedDisc, @PathVariable Long id, @AuthenticationPrincipal AppUser user) {
    Optional<Disc> discOptional = discRepository.findById(id);
    
    if (discOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Disc existingDisc = discOptional.get();

    if (!user.getRole().equals("ADMIN") && !existingDisc.getUser().equals(user)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only edit your own discs.");
    }

    log.info("User " + user.getUsername() + " edits disc with ID: " + id);
    editedDisc.setId(id);
    editedDisc.setUser(existingDisc.getUser()); // Preserve the original owner
    return ResponseEntity.ok(discRepository.save(editedDisc));
}

    //Delete existing disc
    @DeleteMapping("/discs/{id}")
    public ResponseEntity<?> deleteDisc(@PathVariable Long id, @AuthenticationPrincipal AppUser user) {
        Optional<Disc> discOptional = discRepository.findById(id);
    
        if (discOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
    
        Disc disc = discOptional.get();
    
        if (!user.getRole().equals("ADMIN") && !disc.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own discs.");
        }
    
        log.info("User " + user.getUsername() + " deletes disc with ID: " + id);
        discRepository.deleteById(id);
        return ResponseEntity.ok("Disc deleted successfully");
    }

    //Find one disc and return it
    @GetMapping("/discs/{id}")
    public ResponseEntity<?> getDisc(@PathVariable Long id, @AuthenticationPrincipal AppUser user) {
        Optional<Disc> discOptional = discRepository.findById(id);
    
        if (discOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
    
        Disc disc = discOptional.get();
    
        if (!user.getRole().equals("ADMIN") && !disc.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only view your own discs.");
        }
    
        log.info("User " + user.getUsername() + " accesses disc with ID: " + id);
        return ResponseEntity.ok(disc);
    }
    
    //Find disc by category
    @GetMapping("/discs/category/{name}")
    public List<Disc> getDiscByCategoryName(@PathVariable String name, @AuthenticationPrincipal AppUser user) {
        log.info("User " + user.getUsername() + " searches discs by category: " + name);
        
        if (user.getRole().equals("ADMIN")) {
            return discRepository.findDiscByCategoryName(name);
        } else {
            return discRepository.findDiscByCategoryNameAndUser(name, user);
        }
    }
    
}