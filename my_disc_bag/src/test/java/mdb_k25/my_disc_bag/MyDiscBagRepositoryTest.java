package mdb_k25.my_disc_bag;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import mdb_k25.my_disc_bag.domain.Disc;
import mdb_k25.my_disc_bag.domain.DiscRepository;
import mdb_k25.my_disc_bag.domain.Category;
import mdb_k25.my_disc_bag.domain.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MyDiscBagRepositoryTest {

    @Autowired
    private DiscRepository repository;

    @Autowired
    private CategoryRepository crepository;

    @Test
    public void findByNameShouldReturnDisc() {
        List<Disc> discs = repository.findByName("Judge");

        assertThat(discs).hasSize(1);
        assertThat(discs.get(0).getManufacturer()).isEqualTo("Dynamic Discs");
    }

    @Test
    public void createNewDisc() {
        Category category = new Category("Approach discs");
        crepository.save(category);
        Disc disc = new Disc("Omega AP", "Millenium Discs", "Champion", 180, 15.90, category, false);
        repository.save(disc);
        assertThat(disc.getId()).isNotNull();
    }

//    @Test
//    public void editNewDisc() {
//        List<Disc> discs = repository.findByName("Judge");
//        Disc disc = discs.get(0);
//        repository.edit(disc);
//    }

    @Test
    public void deleteNewDisc() {
        List<Disc> discs = repository.findByName("Judge");
        Disc disc = discs.get(0);
        repository.delete(disc);
        List<Disc> newDiscs = repository.findByName("Judge");
        assertThat(newDiscs).hasSize(0);
    }
}