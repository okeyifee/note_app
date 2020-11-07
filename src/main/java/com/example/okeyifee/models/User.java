package com.example.okeyifee.models;

import com.example.okeyifee.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static com.example.okeyifee.utils.Role.ROLE_USER;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class User extends BaseModel  {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private String phoneNumber;

    @Column(nullable = true)
    private Boolean isActive;

    @Enumerated(value = EnumType.STRING)
    private Role role = ROLE_USER;

}




