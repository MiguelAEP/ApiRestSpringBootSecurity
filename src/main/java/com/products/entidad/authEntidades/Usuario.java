package com.products.entidad.authEntidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del usuario debe ser obligatorio")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "El password del usuario debe ser obligatorio")
    private String password;

    @Column(name = "is_enabledd")
    private boolean isEnabled;

    @Column(name = "account_No_Expiredd")
    private boolean accountNoExpired;

    @Column(name = "account_No_Lockedd")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expiredd")
    private boolean credentialNoExpired;

    public Usuario() {
    }

    public Usuario(Integer id, String username, String password, boolean isEnabled, boolean accountNoExpired, boolean accountNoLocked, boolean credentialNoExpired, List<Rol> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.accountNoExpired = accountNoExpired;
        this.accountNoLocked = accountNoLocked;
        this.credentialNoExpired = credentialNoExpired;
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name ="user_roles", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private List<Rol> roles = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isAccountNoExpired() {
        return accountNoExpired;
    }

    public void setAccountNoExpired(boolean accountNoExpired) {
        this.accountNoExpired = accountNoExpired;
    }

    public boolean isAccountNoLocked() {
        return accountNoLocked;
    }

    public void setAccountNoLocked(boolean accountNoLocked) {
        this.accountNoLocked = accountNoLocked;
    }

    public boolean isCredentialNoExpired() {
        return credentialNoExpired;
    }

    public void setCredentialNoExpired(boolean credentialNoExpired) {
        this.credentialNoExpired = credentialNoExpired;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
