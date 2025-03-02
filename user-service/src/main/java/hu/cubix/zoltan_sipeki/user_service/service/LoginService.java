package hu.cubix.zoltan_sipeki.user_service.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTService;
import hu.cubix.zoltan_sipeki.user_service.dto.RegistrationDto;
import hu.cubix.zoltan_sipeki.user_service.model.WebshopUser;
import hu.cubix.zoltan_sipeki.user_service.repository.RoleRepository;
import hu.cubix.zoltan_sipeki.user_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JWTService jwtService;

    private RestClient facebookEndpoint = RestClient.create();

    @PostConstruct
    public void init() {
        var messageConverter = new HttpMessageConverter<Object>() {
            private HttpMessageConverter<Object> jacksonConverter = new MappingJackson2HttpMessageConverter();

            @Override
            public boolean canRead(Class<?> clazz, MediaType mediaType) {
                return true;
            }

            @Override
            public boolean canWrite(Class<?> clazz, MediaType mediaType) {
                return true;
            }

            @Override
            public Object read(Class<? extends Object> clazz, HttpInputMessage inputMessage)
                    throws IOException, HttpMessageNotReadableException {
                return jacksonConverter.read(clazz, inputMessage);
            }

            @Override
            public void write(Object t, MediaType contentType, HttpOutputMessage outputMessage)
                    throws IOException, HttpMessageNotWritableException {
                jacksonConverter.write(t, contentType, outputMessage);
            }

            @Override
            public List<MediaType> getSupportedMediaTypes() {
                return jacksonConverter.getSupportedMediaTypes();
            }
        };

        this.facebookEndpoint = RestClient.builder().messageConverters(converters -> converters.add(messageConverter))
                .build();
    }

    public void register(RegistrationDto dto) {
        try {
            var role = roleRepository.findByName("ROLE_CUSTOMER").get();
    
            var user = new WebshopUser();
            user.setUsername(dto.username());
            user.setPassword(passwordEncoder.encode(dto.password()));
            user.setEmail(dto.email());
            user.setRoles(Set.of(role));
    
            userRepository.saveAndFlush(user);
        }
        catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username or email already exists");
        }
    }

    public String login(String username, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        return jwtService.createToken((User) authentication.getPrincipal());
    }

    public String loginWithFacebook(String facebookToken) {
        var response = facebookEndpoint.get().uri("https://graph.facebook.com/v22.0/me?fields=id,email")
                .headers(h -> h.setBearerAuth(facebookToken)).retrieve()
                .body(HashMap.class);

        var facebookId = (String) response.get("id");
        var email = (String) response.get("email");

        var userDetails = createUserIfNotExist(facebookId, email);

        return jwtService.createToken((User) userDetails);
    }

    @Transactional
    private UserDetails createUserIfNotExist(String facebookId, String email) {
        var user = new WebshopUser();
        try {
            var role = roleRepository.findByName("ROLE_CUSTOMER").get();

            user.setFacebookId(facebookId);
            user.setEmail(email);
            user.setUsername(email);
            user.setPassword("");
            user.setRoles(Set.of(role));

            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
        }

        return new User(user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList());
    }

}
