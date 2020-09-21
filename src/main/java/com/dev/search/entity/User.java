package com.dev.search.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Principal {

    @Id
    @Column(name = "userNo")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userNo;

    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "auth")
    private String auth;

    @Builder
    public User(String id, String name, String password, String auth) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return userNo.equals( ((User) obj).getUserNo() );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return userNo != null ? userNo.hashCode() : 0;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
