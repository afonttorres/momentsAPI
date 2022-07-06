package com.factoria.moments.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String avatarUrl;
    private String email;
    private String name;
    private String password;
    private String bannerUrl;
    private long followers;
    private long following;
    private String description;
}