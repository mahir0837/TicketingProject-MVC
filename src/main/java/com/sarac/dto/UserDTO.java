package com.sarac.dto;

import com.sarac.enums.Gender;
import lombok.*;

import javax.validation.constraints.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotBlank
    @Size(max=15,min=2)
    private String firstName;

    @NotBlank
    @Size(max=15,min=2)
    private String lastName;

    @NotBlank
    @Email
    private String userName;

    @NotBlank
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")//smallletter,CapitalLetter,At list 4Character and SpecialCharacter and numbers
    private String passWord;

    private boolean enabled;

    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;

    @NotNull
    private RoleDTO role;

    @NotNull
    private Gender gender;
}
