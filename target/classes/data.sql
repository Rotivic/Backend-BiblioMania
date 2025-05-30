-- 🔹 Deshabilitar claves foráneas temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- 🗑 Vaciar todas las tablas


-- 🔹 Habilitar claves foráneas nuevamente
SET FOREIGN_KEY_CHECKS = 1;

-- 👤 Usuarios
CREATE TABLE IF NOT EXISTS user (
    id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('USER', 'ADMIN') NOT NULL,
    verificacion_token VARCHAR(255),
    is_verified BOOLEAN DEFAULT 0,
    activo BOOLEAN DEFAULT 1
);

-- 📚 Libros
CREATE TABLE IF NOT EXISTS book (
    id_libro BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    isbn VARCHAR(20) UNIQUE,
    activo BOOLEAN DEFAULT 1
);

-- ✍️ Reseñas
CREATE TABLE IF NOT EXISTS reviews (
    id_review BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_libro BIGINT NOT NULL,
    comentario TEXT,
    calificacion INT CHECK (calificacion BETWEEN 1 AND 5),
    FOREIGN KEY (id_usuario) REFERENCES user(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_libro) REFERENCES book(id_libro) ON DELETE CASCADE
);

-- 🗂 Listas
CREATE TABLE IF NOT EXISTS listas (
    id_lista BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    usuario_id BIGINT NOT NULL,
    activo BOOLEAN DEFAULT 1,
    FOREIGN KEY (usuario_id) REFERENCES user(id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lista_libro (
    lista_id BIGINT NOT NULL,
    libro_id BIGINT NOT NULL,
    PRIMARY KEY (lista_id, libro_id),
    FOREIGN KEY (lista_id) REFERENCES listas(id_lista) ON DELETE CASCADE,
    FOREIGN KEY (libro_id) REFERENCES book(id_libro) ON DELETE CASCADE
);

-- 📖 Grupos de lectura
CREATE TABLE IF NOT EXISTS grupos_lectura (
    id_grupo BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT 1,
    id_creador BIGINT, 
    FOREIGN KEY (id_creador) REFERENCES user(id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS usuarios_grupos (
    id_usuario BIGINT NOT NULL,
    id_grupo BIGINT NOT NULL,
    fecha_ingreso DATE DEFAULT (CURRENT_DATE),
    PRIMARY KEY (id_usuario, id_grupo),
    FOREIGN KEY (id_usuario) REFERENCES user(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_grupo) REFERENCES grupos_lectura(id_grupo) ON DELETE CASCADE
);

-- 💬 Foros
CREATE TABLE IF NOT EXISTS forum_threads (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    id_grupo BIGINT NOT NULL,
    cerrado BOOLEAN DEFAULT 0,
    FOREIGN KEY (id_grupo) REFERENCES grupos_lectura(id_grupo) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS messages (
    id_message BIGINT PRIMARY KEY AUTO_INCREMENT,
    contenido TEXT NOT NULL,
    fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_usuario BIGINT NOT NULL,
    id_thread BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES user(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_thread) REFERENCES forum_threads(id) ON DELETE CASCADE
);

-- ✅ Libros leídos
CREATE TABLE IF NOT EXISTS libro_leido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    libro_id BIGINT NOT NULL,
    fecha_lectura DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES user(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (libro_id) REFERENCES book(id_libro) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS favorite_books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT,
    libro_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES user(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (libro_id) REFERENCES book(id_libro) ON DELETE CASCADE
);


-- 👤 Usuarios
INSERT INTO user (id_usuario, name, email, password, rol, is_verified, activo, verificacion_token) VALUES
(1, 'Juan Pérez', 'juan@example.com', '$2a$10$NUMT7poYywJRJPjl9zAiS.xeRgPU0YqjgTm6wuIMb4s0r1e9AN44q', 'USER', 1, 1, 'e15f2e2a-5ad0-4b47-b061-e159e02cf97c'),
(2, 'María López', 'maria@example.com', '$2a$10$NUMT7poYywJRJPjl9zAiS.xeRgPU0YqjgTm6wuIMb4s0r1e9AN44q', 'USER', 1, 1, 'e15f2e2a-5ad0-4b47-b061-e159e02cf97c'),
(3, 'Carlos Ruiz', 'carlos@example.com', '$2a$10$NUMT7poYywJRJPjl9zAiS.xeRgPU0YqjgTm6wuIMb4s0r1e9AN44q', 'USER', 1, 1, 'e15f2e2a-5ad0-4b47-b061-e159e02cf97c'),
(4, 'Ana Torres', 'ana@example.com', '$2a$10$NUMT7poYywJRJPjl9zAiS.xeRgPU0YqjgTm6wuIMb4s0r1e9AN44q', 'USER', 1, 1, 'e15f2e2a-5ad0-4b47-b061-e159e02cf97c'),
(5, 'Lucía Gómez', 'lucia@example.com', '$2a$10$NUMT7poYywJRJPjl9zAiS.xeRgPU0YqjgTm6wuIMb4s0r1e9AN44q', 'ADMIN', 1, 1, 'e15f2e2a-5ad0-4b47-b061-e159e02cf97c');

-- 📚 Libros
INSERT INTO book (id_libro, title, author, description, isbn, activo) VALUES
(1, 'Cien Años de Soledad', 'Gabriel García Márquez', 'Realismo Mágico', '1234567890', 1),
(2, 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'Novela', '0987654321', 1),
(3, 'El Principito', 'Antoine de Saint-Exupéry', 'Ficción', '1122334455', 1),
(4, 'Rayuela', 'Julio Cortázar', 'Narrativa experimental', '2233445566', 1),
(5, 'La Sombra del Viento', 'Carlos Ruiz Zafón', 'Misterio', '3344556677', 1);

-- ⭐ Reseñas
INSERT INTO reviews (id_usuario, id_libro, comentario, calificacion) VALUES
(1, 1, 'Una obra maestra', 5),
(2, 2, 'Una novela esencial', 4),
(3, 3, 'Conmovedora y única', 5),
(4, 4, 'Me costó, pero vale la pena', 4),
(5, 5, 'Una historia atrapante', 5);

-- 👥 Grupos de lectura
INSERT INTO grupos_lectura (id_grupo, nombre, descripcion, activo, id_creador) VALUES
(1, 'Club de Lectura García Márquez', 'Amantes de Gabo', 1, 1),
(2, 'Literatura Española', 'Clásicos españoles', 1, 2),
(3, 'Infancia y Fábulas', 'Libros para reflexionar', 1, 3),
(4, 'Realismo Mágico', 'Cortázar, Borges, y más', 1, 4),
(5, 'Bestsellers Modernos', 'Los más leídos del siglo XXI', 1, 5);

-- 👤 Usuarios en grupos
INSERT INTO usuarios_grupos (id_usuario, id_grupo, fecha_ingreso) VALUES
(1, 1, '2024-01-15'),
(2, 1, '2024-02-10'),
(3, 2, '2024-02-20'),
(4, 3, '2024-03-01'),
(5, 5, '2024-03-05'),
(1, 4, '2024-04-01'),
(2, 2, '2024-04-10');

-- 📝 Listas
INSERT INTO listas (id_lista, nombre, usuario_id, activo) VALUES
(1, 'Favoritos de Juan', 1, 1),
(2, 'Clásicos de María', 2, 1),
(3, 'Lecturas 2024', 3, 1),
(4, 'Por leer', 4, 1),
(5, 'Mi colección', 5, 1);

-- 📌 Libros en listas
INSERT INTO lista_libro (lista_id, libro_id) VALUES
(1, 1),
(1, 3),
(2, 2),
(3, 4),
(4, 5),
(5, 1);

-- 💬 Hilos de foro
INSERT INTO forum_threads (id, titulo, id_grupo, cerrado) VALUES
(1, 'Discusión sobre Cien Años de Soledad', 1, false),
(2, 'El impacto de Don Quijote', 2, false),
(3, '¿Qué nos enseña El Principito?', 3, false),
(4, 'Interpretando Rayuela', 4, false),
(5, '¿Vale la pena La Sombra del Viento?', 5, false);

-- 🗨️ Mensajes en hilos
INSERT INTO messages (contenido, fecha_envio, id_usuario, id_thread) VALUES
('Me encanta este libro, especialmente Aureliano.', NOW(), 1, 1),
('¿Creen que Don Quijote estaba loco?', NOW(), 2, 2),
('Este libro me ayudó de niño.', NOW(), 3, 3),
('Rayuela es un rompecabezas literario.', NOW(), 4, 4),
('Lo leí en dos días, atrapante.', NOW(), 5, 5);

-- ✅ Libros leídos
INSERT INTO libro_leido (usuario_id, libro_id, fecha_lectura) VALUES
(1, 1, '2024-01-01T10:00:00'),
(1, 3, '2024-01-15T10:00:00'),
(2, 2, '2024-01-10T10:00:00'),
(3, 4, '2024-01-20T10:00:00'),
(4, 5, '2024-01-25T10:00:00');

INSERT INTO favorite_books (usuario_id, libro_id) VALUES
(1, 1),
(1, 3),
(2, 2),
(3, 4),
(2, 5);

