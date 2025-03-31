package mdb_k25.my_disc_bag.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mdb_k25.my_disc_bag.domain.AppUser;
import mdb_k25.my_disc_bag.domain.AppUserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

    AppUserRepository repository;

    public UserDetailServiceImpl(AppUserRepository appUserRepository) {
        this.repository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser curruser = repository.findByUsername(username);
        if (curruser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return curruser; // Return the AppUser object directly
    }
}