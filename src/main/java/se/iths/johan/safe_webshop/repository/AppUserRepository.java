package se.iths.johan.safe_webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.johan.safe_webshop.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {


    Optional<AppUser> findByUsername(String username);
}
