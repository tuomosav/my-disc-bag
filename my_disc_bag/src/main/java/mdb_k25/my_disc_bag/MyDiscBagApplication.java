package mdb_k25.my_disc_bag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mdb_k25.my_disc_bag.domain.AppUser;
import mdb_k25.my_disc_bag.domain.AppUserRepository;
import mdb_k25.my_disc_bag.domain.Disc;
import mdb_k25.my_disc_bag.domain.DiscRepository;
import mdb_k25.my_disc_bag.domain.Category;
import mdb_k25.my_disc_bag.domain.CategoryRepository;

@SpringBootApplication
public class MyDiscBagApplication {

	private static final Logger log = LoggerFactory.getLogger(MyDiscBagApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyDiscBagApplication.class, args);
	}

	@Bean
	public CommandLineRunner myDiscBag(DiscRepository repository, CategoryRepository crepository, AppUserRepository aurepository) {
		return (args) -> {
			log.info("save one disc and category for a test, creating users");

			if (crepository.count() == 0 && repository.count() == 0 && aurepository.count() == 0) {
			Category category1 = new Category("Putter");

			crepository.save(category1);
	
			repository.save(new Disc("Judge", "Dynamic Discs", "Classic Blend", 175, 12.90, category1));

			//Luodaan käyttäjiä: admin/admin , user/user
			AppUser user1 = new AppUser("user", "$2a$10$myvtgbh8XXeQISLXD/qKf.V1RhkX2aWi/MeFbmli.h6rxQQwgvtpy", "user@email.com", "USER");
			AppUser user2 = new AppUser("admin", "$2a$10$/6t0t59glyl56NbvbJrb7OR9rgFjnwO5pyoj7.gNUFKKQDS3wKe9W", "admin@email.com", "ADMIN");
			aurepository.save(user1);
			aurepository.save(user2);
			}
			
			log.info("fetch all discs");
			for (Disc disc : repository.findAll()) {
				log.info(disc.toString());
			}

			log.info("fetching discs with name Judge");
			for (Disc disc : repository.findByName("Judge")) {
				log.info(disc.toString());
			}
		
		};
	}
}
