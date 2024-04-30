package com.products.service;

import com.products.entidad.authEntidades.Permisions;
import com.products.entidad.authEntidades.Rol;
import com.products.entidad.authEntidades.RoleEnum;
import com.products.entidad.authEntidades.Usuario;
import com.products.repository.PermissionRepository;
import com.products.repository.RolRepository;
import com.products.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceIMP implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("el usuario no existe"));

        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();

        usuario.getRoles()
                .forEach(rol -> authoritiesList.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRoleEnum().name()))));

        usuario.getRoles()
                .stream().flatMap(rol -> rol.getPermisions().stream())
                .forEach(at -> authoritiesList.add(new SimpleGrantedAuthority(at.getNombre())));


        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled()
                , usuario.isAccountNoExpired(), usuario.isCredentialNoExpired(), usuario.isAccountNoLocked(),
                authoritiesList);

    }





    //METODOS ENTIDAD USUARIO

    public List<Usuario> listarUsuario() {
        return userRepository.findAll();
    }

    public Usuario crearUsuario(Usuario usuario) {
        return userRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        return userRepository.findById(idUsuario).orElseThrow(null);
    }

    public void deleteUsuario(Integer usuarioID) {
        userRepository.deleteById(usuarioID);
    }

    public Usuario actualizarUsuarioPorRol(Usuario usuario) {

        return userRepository.save(usuario);
    }


    //METODOS ENTIDAD ROL

    public Rol actualizarRolconPermision(Rol rol) {

        return rolRepository.save(rol);
    }

    public Rol buscarRolPorId(Integer rolId) {
        return rolRepository.findById(rolId).orElseThrow(null);
    }

    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    //METODOS ENTIDAD PERMISSION

    public Permisions buscarPermissionPorId(Integer permissionId) {
        return permissionRepository.findById(permissionId).orElseThrow(null);
    }

}
