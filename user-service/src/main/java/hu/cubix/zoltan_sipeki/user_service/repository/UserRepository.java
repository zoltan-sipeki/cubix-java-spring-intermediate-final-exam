package hu.cubix.zoltan_sipeki.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.cubix.zoltan_sipeki.user_service.model.WebshopUser;

@Repository
public interface UserRepository extends JpaRepository<WebshopUser, Long> {

    public Optional<WebshopUser> findByUsername(String username);
}
