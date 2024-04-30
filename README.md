Hola este proyecto es un api rest de productos y categorias y se le añadio spring security.




AYUDAS : 

una ayuda para poder ver el usuario los roles y que permisos tiene cada uno
usando Mysql

select u.username ,u.password as contrasenia  ,r.role_name , rsion.*, p.nombre
from usuario u
inner join user_roles rp on u.id = rp.usuario_id
inner join rol r on  rp.rol_id = r.id 
inner join rol_permision rsion on rsion.rol_id = r.id
inner join permisions p on p.id = rsion.permisions_id

////////////////////////


METODOS PARA AGREGAR USUARIOS ,ROL :

Agregar usuario
POST: http://localhost:8080/user/crear

{
   "username":"Miguel",
   "password":"1234",
   "rol":[
   ]
}

/////////////////////////////

Agregar el rol al usuario creado:
PUT : http://localhost:8080/user/actualizar/usuario/{idUsuario}/rol/{idRolAsignar}


//////////////////////////

Asociar permisos a un rol: ( ADMINISTRADOR, DEVELOPER , USUARIO , VISITANTE )
POST : http://localhost:8080/user/crear/rol/{idRol}

{
    "role_name":"ADMINISTRADOR",
    "permisions":[
        {
            "id":1,
            "nombre":"CREATE"
        },
        {
            "id":2,
            "nombre":"READ"
        },
        {
            "id":3,
            "nombre":"UPDATE"
        },
        {
            "id":4,
            "nombre":"DELETE"
        }
    ]
}
