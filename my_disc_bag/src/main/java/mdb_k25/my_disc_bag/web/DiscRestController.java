package mdb_k25.my_disc_bag.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import mdb_k25.my_disc_bag.domain.Disc;
import mdb_k25.my_disc_bag.domain.DiscRepository;
import mdb_k25.my_disc_bag.domain.CategoryRepository;


@RestController
public class DiscRestController {

    private static final Logger log = LoggerFactory.getLogger(DiscRestController.class);

    private final DiscRepository discRepository;
    private CategoryRepository cRepository;

    public DiscRestController(DiscRepository discRepository, CategoryRepository cRepository) {
        this.discRepository = discRepository;
        this.cRepository = cRepository;
    }
    
    //Return list of discs
    @GetMapping("/discs")
    public Iterable<Disc> getDiscs() {
        log.info("Fetch and return discs");
        return discRepository.findAll();
    }
    
    //Add new disc
    @PostMapping("discs")
    Disc newDisc(@RequestBody Disc newDisc) {
        log.info("Save new disc " + newDisc);
        return discRepository.save(newDisc);
    }
    
    //Edit existing disc's information
    @PutMapping("discs/{id}")
    Disc editDisc(@RequestBody Disc editedDisc, @PathVariable Long id) {
        log.info("Edit disc " + editedDisc);
        editedDisc.setId(id);
        return discRepository.save(editedDisc);
    }

    //Delete existing disc
    @DeleteMapping("/discs/{id}")
    public Iterable<Disc> deleteDisc(@PathVariable Long id) {
        log.info("Delete disc, id = " + id);
        discRepository.deleteById(id);
        return discRepository.findAll();
    }

    //Find one disc and return it
    @GetMapping("/discs/{id}")
    Optional<Disc> getDisc(@PathVariable Long id) {
        log.info("Find disc, id = " + id);
        return discRepository.findById(id);
    }
    
    //Find disc by category
    @GetMapping("/discs/category/{name}")
    List<Disc> getDiscByCategoryName(@PathVariable String name) {
        log.info("Find disc, category = " + name);
        return discRepository.findDiscByCategoryName(name);
    }
    
}