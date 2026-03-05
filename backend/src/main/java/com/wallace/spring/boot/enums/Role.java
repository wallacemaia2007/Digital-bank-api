package com.wallace.spring.boot.enums;

import static com.wallace.spring.boot.enums.Permission.ADMIN_DELETE;
import static com.wallace.spring.boot.enums.Permission.ADMIN_READ;
import static com.wallace.spring.boot.enums.Permission.ADMIN_WRITE;
import static com.wallace.spring.boot.enums.Permission.USER_READ;
import static com.wallace.spring.boot.enums.Permission.USER_WRITE;
import static com.wallace.spring.boot.enums.Permission.ADMIN_UPDATE; 
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    USER(Set.of(USER_READ, USER_WRITE)),
    ADMIN(Set.of(
        USER_READ, 
        USER_WRITE,
        ADMIN_READ, 
        ADMIN_WRITE, 
        ADMIN_UPDATE, 
        ADMIN_DELETE
    ));
    
    private final Set<Permission> permissions;
    
    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }
    
    public Set<Permission> getPermissions() {
        return permissions;
    }
    
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}