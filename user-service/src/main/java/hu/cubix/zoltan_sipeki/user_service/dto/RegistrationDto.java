package hu.cubix.zoltan_sipeki.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegistrationDto(

    @NotEmpty
    @Email 
    String email,
    
    @NotEmpty 
    String username,
    
    @NotEmpty 
    String password) {

}
