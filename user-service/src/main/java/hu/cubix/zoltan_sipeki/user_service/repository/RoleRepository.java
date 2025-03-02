package hu.cubix.zoltan_sipeki.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.cubix.zoltan_sipeki.user_service.model.WebshopRole;

@Repository
public interface RoleRepository extends JpaRepository<WebshopRole, Long> {

    public Optional<WebshopRole> findByName(String name);
}
