package com.factoria.moments.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String avatarUrl;
    private String email;
    private String name;
    private String bannerUrl;
    private long followers;
    private long following;
    private String description;
    @JsonIgnore
    private String password;

    @ManyToMany
    private Set<Role> roles;

    public User(String username, String email, String encode, String name){
        this.username = username;
        this.email = email;
        this.password = encode;
        this.name = name;
    }
}