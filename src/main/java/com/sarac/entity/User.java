package com.sarac.entity;

import com.sarac.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@Entity
//@Where(clause = "is_deleted=false")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
