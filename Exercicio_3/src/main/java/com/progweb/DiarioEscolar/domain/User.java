package com.progweb.DiarioEscolar.domain;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.progweb.DiarioEscolar.domain.enums.Authority;

public class User implements UserDetails {
    private static final long serialVerionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> Authorities;
    public Long getId(){
        return id;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
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
    public User(Long id, String username, String password, Set<Authority> authority) {
        this.id = id;
        this.username = username;
        this.password = password;
        Authorities = authority.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toSet());
    }   
}