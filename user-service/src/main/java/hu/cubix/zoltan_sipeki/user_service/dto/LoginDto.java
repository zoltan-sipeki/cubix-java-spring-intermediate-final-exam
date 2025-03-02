package hu.cubix.zoltan_sipeki.user_service.dto;

public record LoginDto(
    String username,
    String password,
    String facebookToken) {

}
