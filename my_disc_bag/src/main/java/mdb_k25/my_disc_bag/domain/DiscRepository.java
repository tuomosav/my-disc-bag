package mdb_k25.my_disc_bag.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DiscRepository extends CrudRepository<Disc, Long> {

    List<Disc> findByName(String name);
    List<Disc> findDiscByCategoryNameAndUser(String name, AppUser user);
    List<Disc> findDiscByCategoryName(String name);
    List<Disc> findByUser(AppUser user);
    List<Disc> findByUserAndLostTrue(AppUser user);
    List<Disc> findByLostTrue();

}