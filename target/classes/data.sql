-- 🔹 Deshabilitar claves foráneas temporalmente para evitar errores al truncar
SET FOREIGN_KEY_CHECKS = 0;

-- 🗑 Vaciar todas las tablas
TRUNCATE TABLE messages;
TRUNCATE TABLE forum_threads;
TRUNCATE TABLE usuarios_grupos;
TRUNCATE TABLE grupos_lectura;
TRUNCATE TABLE lista_libro;
TRUNCATE TABLE listas;
TRUNCATE TABLE reviews;
TRUNCATE TABLE book;
TRUNCATE TABLE user;

-- 🔹 Habilitar claves foráneas nuevamente
SET FOREIGN_KEY_CHECKS = 1;

-- 📌 Si las tablas no existen, crearlas
CREATE TABLE IF NOT EXISTS user (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('USER', 'ADMIN') NOT NULL,
    verificacion_token VARCHAR(255),
    is_verified BOOLEAN DEFAULT 0,
    activo BOOLEAN DEFAULT 1
);

CREATE TABLE IF NOT EXISTS book (
    id_libro INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    isbn VARCHAR(20) UNIQUE,
    activo BOOLEAN DEFAULT 1
);

CREATE TABLE IF NOT EXISTS reviews (
    id_review INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_libro INT NOT NULL,
    comentario TEXT,
    calificacion INT CHECK (calificacion BETWEEN 1 AND 5),
    reseñas_order INT NOT NULL AUTO_INCREMENT,
    FOREIGN KEY (id_usuario) REFERENCES user(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_libro) REFERENCES book(id_libro) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS listas (
    id_lista INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    usuario_id INT NOT NULL,
    activo BOOLEAN DEFAULT 1,
    FOREIGN KEY (usuario_id) REFERENCES user(id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lista_libro (
    lista_id INT NOT NULL,
    libro_id INT NOT NULL,
    PRIMARY KEY (lista_id, libro_id),
    FOREIGN KEY (lista_id) REFERENCES listas(id_lista) ON DELETE CASCADE,
    FOREIGN KEY (libro_id) REFERENCES book(id_libro) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS grupos_lectura (
    id_grupo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT 1
);

CREATE TABLE IF NOT EXISTS usuarios_grupos (
    id_usuario INT NOT NULL,
    id_grupo INT NOT NULL,
    fecha_ingreso DATE DEFAULT (CURRENT_DATE),
    grupos_order INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id_usuario, id_grupo),
    FOREIGN KEY (id_usuario) REFERENCES user(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_grupo) REFERENCES grupos_lectura(id_grupo) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS forum_threads (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    id_grupo INT NOT NULL,
    FOREIGN KEY (id_grupo) REFERENCES grupos_lectura(id_grupo) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS messages (
    id_message INT PRIMARY KEY AUTO_INCREMENT,
    contenido TEXT NOT NULL,
    fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    id_thread INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES user(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_thread) REFERENCES forum_threads(id) ON DELETE CASCADE
);

-- 👤 Insertar usuarios de prueba
INSERT INTO user (id_usuario, name, email, password, rol, is_verified, activo, verification_token) VALUES
(1, 'Juan Pérez', 'juan@example.com', '$2a$10$NUMT7poYywJRJPjl9zAiS.xeRgPU0YqjgTm6wuIMb4s0r1e9AN44q', 'USER', 1, 1,'e15f2e2a-5ad0-4b47-b061-e159e02cf97c'),
(2, 'María Gómez', 'maria@example.com', '$2a$10$NUMT7poYywJRJPjl9zAiS.xeRgPU0YqjgTm6wuIMb4s0r1e9AN44q', 'ADMIN', 1, 1, 'e15f2e2a-5ad0-4b47-b061-e159e02cf97c'),
(3, 'Carlos Rodríguez', 'carlos@example.com', '$2a$10$NUMT7poYywJRJPjl9zAiS.xeRgPU0YqjgTm6wuIMb4s0r1e9AN44q', 'USER', 1, 1, 'e15f2e2a-5ad0-4b47-b061-e159e02cf97c');

-- 📚 Insertar libros
INSERT INTO book (id_libro, title, author, description, isbn, activo) VALUES
(1, 'Cien Años de Soledad', 'Gabriel García Márquez', 'Realismo Mágico', '1234567890', 1),
(2, 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'Novela', '0987654321', 1),
(3, 'El Principito', 'Antoine de Saint-Exupéry', 'Ficción', '1122334455', 1);

-- 📝 Insertar reseñas
INSERT INTO reviews (id_usuario, id_libro, comentario, calificacion) VALUES
(1, 1, 'Una obra maestra de la literatura latinoamericana', 5),
(2, 2, 'Una novela esencial de la literatura española', 4),
(1, 3, 'Una historia conmovedora y única', 5);

-- 🔹 Insertar grupos de lectura
INSERT INTO grupos_lectura (id_grupo, nombre, descripcion, activo) VALUES
(1, 'Club de Lectura García Márquez', 'Un grupo para amantes de García Márquez', 1),
(2, 'Literatura Española', 'Grupo para discutir clásicos españoles', 1);

-- 📌 Insertar relaciones usuarios-grupos con fechas correctas
INSERT INTO usuarios_grupos (id_usuario, id_grupo, fecha_ingreso) VALUES
(1, 1, '2024-01-15'),
(2, 1, '2024-02-10'),
(1, 2, '2024-03-05'),
(3, 2, '2024-03-20');

-- 📋 Insertar listas
INSERT INTO listas (id_lista, nombre, usuario_id, activo) VALUES
(1, 'Favoritos de Juan', 1, 1),
(2, 'Clásicos de María', 2, 1);

-- 🔗 Insertar relación entre listas y libros
INSERT INTO lista_libro (lista_id, libro_id) VALUES
(1, 1),
(1, 2),
(2, 3);

-- 📝 Insertar hilos de discusión
INSERT INTO forum_threads (id, titulo, id_grupo) VALUES
(1, 'Discusión sobre Cien Años de Soledad', 1),
(2, 'El impacto de Don Quijote', 2);

-- ✉ Insertar mensajes
INSERT INTO messages (contenido, fecha_envio, id_usuario, id_thread) VALUES
('Me encanta este libro, especialmente el personaje de Aureliano.', NOW(), 1, 1),
('¿Creen que Don Quijote estaba realmente loco?', NOW(), 2, 2);
