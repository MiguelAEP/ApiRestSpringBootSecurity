package com.products.service;

import com.products.entidad.authEntidades.*;
import com.products.entidad.dtos.AuthCreateUserRequest;
import com.products.entidad.dtos.ResponseLogin;
import com.products.repository.PermissionRepository;
import com.products.repository.RolRepository;
import com.products.repository.UserRepository;
import com.products.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceIMP implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    //metodo apra obtener los datos del usuario UserDetails de la base de datos
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

    public ResponseLogin login(UserAuthRequestLogin userAuthRequestLogin) {
        String username = userAuthRequestLogin.getNombreUsuario();
        String password = userAuthRequestLogin.getPassword();

        Authentication authentication = this.autenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //generaremos el token
        String accesToken = jwtUtils.createToken(authentication);
        System.out.println("accesToken login imp" + accesToken);

        ResponseLogin authResponse = new ResponseLogin(username, accesToken, true);
        System.out.println("authResponse login imp" + authResponse);

        return authResponse;
    }


    public Authentication autenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        System.out.println("userDetails usuario imp" + userDetails);
        if (userDetails == null) {
            throw new BadCredentialsException("invalido username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalidad  password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public ResponseLogin createUser(AuthCreateUserRequest authCreateUserRequest){

        String username = authCreateUserRequest.getUsername();
        String password = authCreateUserRequest.getPassword();

        List<String> roleRequest = authCreateUserRequest.getRoleListName();

        List<Rol> rolEntity  = rolRepository.findByRoleEnumIn(roleRequest);


        if(rolEntity.isEmpty()){
            throw new IllegalArgumentException("roles especificados no existen");
        }

        Usuario userEntity = Usuario.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(rolEntity)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();

        Usuario userCreated = userRepository.save(userEntity);
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userCreated.getRoles().forEach(role-> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userCreated.getRoles()
                .stream()
                .flatMap(rol -> rol.getPermisions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getNombre())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(),
                userCreated.getPassword(),authorityList);

        String accesToken = jwtUtils.createToken(authentication);

        ResponseLogin authResponse = new ResponseLogin(userCreated.getUsername(),
                accesToken,true);

        return  authResponse;

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
