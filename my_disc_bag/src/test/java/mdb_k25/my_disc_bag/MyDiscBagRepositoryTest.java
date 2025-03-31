package mdb_k25.my_disc_bag;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import mdb_k25.my_disc_bag.domain.Disc;
import mdb_k25.my_disc_bag.domain.DiscRepository;
import mdb_k25.my_disc_bag.domain.AppUser;
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
        AppUser user2 = new AppUser("admin", "adminpassword", "ADMIN");

        Disc disc = new Disc("Omega AP", "Millenium Discs", "Millenium", 180, 2, 3, 0, 0, 15.90, category, false, user2);
        repository.save(disc);
        assertThat(disc.getId()).isNotNull();
    }

    @Test
    public void editNewDisc() {
        List<Disc> discs = repository.findByName("Judge");
        assertThat(discs).isNotEmpty();
        Disc disc = discs.get(0);
        disc.setManufacturer("Latitude 64");
        repository.save(disc);

        List<Disc> updatedDiscs = repository.findByName("Judge");
        assertThat(updatedDiscs.get(0).getManufacturer()).isEqualTo("Latitude 64");
    }

    @Test
    public void deleteNewDisc() {
        List<Disc> discs = repository.findByName("Judge");
        Disc disc = discs.get(0);
        repository.delete(disc);
        List<Disc> newDiscs = repository.findByName("Judge");
        assertThat(newDiscs).hasSize(0);
    }
}