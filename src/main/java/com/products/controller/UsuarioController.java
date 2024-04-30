package com.products.controller;

import com.products.entidad.authEntidades.Permisions;
import com.products.entidad.authEntidades.Rol;
import com.products.entidad.authEntidades.RoleEnum;
import com.products.entidad.authEntidades.Usuario;
import com.products.entidad.dtos.UsuarioDTO;
import com.products.repository.RolRepository;
import com.products.service.UsuarioServiceIMP;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioServiceIMP usuarioServiceIMP;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> listarUsers = usuarioServiceIMP.listarUsuario();
        System.out.println("lista" + listarUsers);
        return ResponseEntity.ok(listarUsers);
    }

    @PostMapping("/crear")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        String constrasenia = passwordEncoder.encode(usuarioDTO.getPassword());
        usuario.setPassword(constrasenia);
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setRoles(usuarioDTO.getRol());
        usuario.setEnabled(true);
        usuario.setAccountNoLocked(true);
        usuario.setAccountNoExpired(true);
        usuario.setCredentialNoExpired(true);

        Usuario usuarioNEW = usuarioServiceIMP.crearUsuario(usuario);
        UsuarioDTO usuarioDTO1 = new UsuarioDTO();
        usuarioDTO1.setUsername(usuarioNEW.getUsername());
        usuarioDTO1.setPassword(usuarioNEW.getPassword());
        usuarioDTO1.setRol(usuarioNEW.getRoles());

        return ResponseEntity.ok(usuarioDTO1);

    }

    @PostMapping("/crear/rol/{idRol}")
    public ResponseEntity<Rol> crearRolAsociandoPermision(@PathVariable Integer idRol, @RequestBody @Valid Rol rol) {

        Rol rolBuscado = usuarioServiceIMP.buscarRolPorId(idRol);
        if (rolBuscado == null) return ResponseEntity.unprocessableEntity().build();
        rolBuscado.setPermisions(rol.getPermisions());

        return ResponseEntity.ok(usuarioServiceIMP.crearRol(rolBuscado));
    }

    @PutMapping("/actualizar/rol/{idRol}/permision/{idPermission}")
    public ResponseEntity<Rol> actualizarRolaPermision(@PathVariable Integer idRol, @PathVariable Integer idPermission) {

        Rol rolBuscado = usuarioServiceIMP.buscarRolPorId(idRol);

        if (rolBuscado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Permisions permissionBuscado = usuarioServiceIMP.buscarPermissionPorId(idPermission);

        if (permissionBuscado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        rolBuscado.setPermisions(List.of(permissionBuscado));

        List<Permisions> p = new ArrayList<>();
        p.add(permissionBuscado);
        p.get(0).setId(idPermission);

        rolBuscado.setPermisions(p);

        Rol rolActualizado = usuarioServiceIMP.actualizarRolconPermision(rolBuscado);
        return ResponseEntity.ok(rolActualizado);

    }

    @PutMapping("/actualizar/usuario/{idUsuario}/rol/{idRol}")
    public ResponseEntity<Usuario> actualizarUsuarioPorRrol(@PathVariable Integer idUsuario, @PathVariable Integer idRol) {

        Usuario usuarioBuscado = usuarioServiceIMP.buscarUsuarioPorId(idUsuario);

        if (usuarioBuscado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Rol rolBuscado = usuarioServiceIMP.buscarRolPorId(idRol);

        if (rolBuscado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        usuarioBuscado.setRoles(List.of(rolBuscado));

        List<Rol> rol = new ArrayList<>();
        rol.add(rolBuscado);
        rol.get(0).setId(idRol);
        System.out.println("rol " + rol);
        usuarioBuscado.setRoles(rol);

        Usuario usuarioActualizado = usuarioServiceIMP.actualizarUsuarioPorRol(usuarioBuscado);
        return ResponseEntity.ok(usuarioActualizado);

    }


    @DeleteMapping("/eliminar/{usuarioID}")
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Integer usuarioID) {
        usuarioServiceIMP.deleteUsuario(usuarioID);
        return ResponseEntity.noContent().build();
    }

}
