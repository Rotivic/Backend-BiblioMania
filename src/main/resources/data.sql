-- Seeders para la tabla usuarios
--INSERT INTO user (id_usuario, name, email, password, rol) VALUES
--(1, 'Juan Pérez', 'juan.perez@example.com', 'password1', 'USUARIO'),
--(2, 'Maria García', 'maria.garcia@example.com', 'password2', 'USUARIO'),
--(3, 'Admin', 'admin@example.com', 'adminpass', 'ADMIN');

-- Seeders para la tabla libros
--INSERT INTO book (id_libro, title, author, description, isbn) VALUES
--(1, 'Cien Años de Soledad', 'Gabriel García Márquez', 'Realismo Mágico', '1234567890'),
--(2, 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'Novela', '0987654321'),
--(3, 'El Principito', 'Antoine de Saint-Exupéry', 'Ficción', '1122334455');

-- Seeders para la tabla reseñas
--INSERT INTO reviews (id_review, id_usuario, id_libro, comentario, calificacion) VALUES
--(1, 1, 1, 'Una obra maestra de la literatura latinoamericana', 5),
--(2, 2, 2, 'Una novela esencial de la literatura española', 4),
--(3, 1, 3, 'Una historia conmovedora y única', 5);

-- Seeders para la tabla listas
--INSERT INTO listas (id_lista, nombre, usuario_id) VALUES
--(1, 'Favoritos de Juan', 1),
--(2, 'Clásicos', 2);

-- Seeders para la tabla lista_libros
--INSERT INTO lista_libro (lista_id, libro_id) VALUES
--(1, 1),
--(1, 3),
--(2, 2);

-- Seeders para la tabla grupos_lectura
--INSERT INTO grupos_lectura (id_grupo, nombre, descripcion) VALUES
--(1, 'Club de Lectura Gabriel García Márquez', 'Un grupo para amantes de García Márquez'),
--(2, 'Literatura Española', 'Grupo para discutir clásicos españoles');

-- Seeders para la tabla usuarios_grupos
--INSERT INTO usuarios_grupos (id_usuario, id_grupo) VALUES
--(1, 1),
--(2, 1),
--(1, 2),
--(3, 2);
