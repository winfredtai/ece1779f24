package com.team9.ece1779f24.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames ="email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(max=20)
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Size(max=50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max=100)
    @Column(name = "password")
    private String password;

    @Column(length = 25)
    private String phoneNumber;

    @Column()
    private String billingAddress;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<Cart> carts = new ArrayList<>();

    // For user initialization with Username+Email+Password
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}