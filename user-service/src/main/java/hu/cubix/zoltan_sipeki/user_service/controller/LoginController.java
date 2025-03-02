package hu.cubix.zoltan_sipeki.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.zoltan_sipeki.user_service.dto.LoginDto;
import hu.cubix.zoltan_sipeki.user_service.dto.RegistrationDto;
import hu.cubix.zoltan_sipeki.user_service.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegistrationDto dto) {
        loginService.register(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto) {
        var usernameDefined = StringUtils.hasText(dto.username());
        var passwordDefined = StringUtils.hasText(dto.password());
        var facebookTokenDefined = StringUtils.hasText(dto.facebookToken());

        if ((usernameDefined || passwordDefined) && facebookTokenDefined) {
            throw new IllegalArgumentException("Only one of username/password or facebookToken can be defined");
        }

        if (facebookTokenDefined) {
            return loginService.loginWithFacebook(dto.facebookToken());
        }

        if (!usernameDefined || !passwordDefined) {
            throw new IllegalArgumentException("Either username and password or facebookToken must be defined");
        }

        return loginService.login(dto.username(), dto.password());
    }
}
