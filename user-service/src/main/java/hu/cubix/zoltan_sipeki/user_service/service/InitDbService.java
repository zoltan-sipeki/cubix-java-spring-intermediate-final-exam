package hu.cubix.zoltan_sipeki.user_service.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.zoltan_sipeki.user_service.model.WebshopRole;
import hu.cubix.zoltan_sipeki.user_service.model.WebshopUser;
import hu.cubix.zoltan_sipeki.user_service.repository.RoleRepository;
import hu.cubix.zoltan_sipeki.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void init() {
        var adminRole = WebshopRole.builder().name("ROLE_ADMIN").build();
        var customerRole = WebshopRole.builder().name("ROLE_CUSTOMER").build();

        var adminUser = WebshopUser.builder().username("admin_user").email("admin@admin.com")
                .password(passwordEncoder.encode("password")).roles(Set.of(adminRole, customerRole)).build();

        userRepository.save(adminUser);
        roleRepository.save(adminRole);
        roleRepository.save(customerRole);
    }
}
