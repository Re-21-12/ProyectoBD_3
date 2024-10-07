```
-- MySQL

CREATE DATABASE Proyecto3;
USE Proyecto3;

CREATE TABLE empleados (
    dpi VARCHAR(16) PRIMARY KEY,
    primer_nombre VARCHAR(50),
    segundo_nombre VARCHAR(50),
    primer_apellido VARCHAR(50),
    segundo_apellido VARCHAR(50),
    direccion_domiciliar VARCHAR(100),
    telefono_de_casa VARCHAR(15),
    telefono_movil VARCHAR(15),
    salario_base DECIMAL(10, 2),
    bonificacion DECIMAL(10, 2)
);

GRANT SELECT ON Proyecto3.empleados TO 'VictorAdmin'@'localhost';
SHOW GRANTS FOR 'VictorAdmin'@'localhost';
GRANT INSERT, CREATE, SELECT, UPDATE, DELETE ON Proyecto3.* TO 'VictorAdmin'@'localhost';
FLUSH PRIVILEGES;
REPAIR TABLE mysql.db;

-- Trigger

CREATE TABLE bitacora (
    id_bitacora INT AUTO_INCREMENT PRIMARY KEY,
    dpi VARCHAR(20),                  -- El DPI del empleado que fue modificado
    campo_modificado VARCHAR(100),    -- El campo que fue modificado
    valor_anterior TEXT,              -- El valor anterior del campo
    valor_nuevo TEXT,                 -- El valor nuevo del campo
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Fecha de modificación
);

DELIMITER //

CREATE TRIGGER trigger_empleados_update
AFTER UPDATE ON empleados
FOR EACH ROW
BEGIN
    -- Verificar si el 'Primer Nombre' cambió
    IF OLD.primer_nombre <> NEW.primer_nombre THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'primer_nombre', OLD.primer_nombre, NEW.primer_nombre);
    END IF;

    -- Verificar si el 'Segundo Nombre' cambió
    IF OLD.segundo_nombre <> NEW.segundo_nombre THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'segundo_nombre', OLD.segundo_nombre, NEW.segundo_nombre);
    END IF;

    -- Verificar si el 'Primer Apellido' cambió
    IF OLD.primer_apellido <> NEW.primer_apellido THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'primer_apellido', OLD.primer_apellido, NEW.primer_apellido);
    END IF;

    -- Verificar si el 'Segundo Apellido' cambió
    IF OLD.segundo_apellido <> NEW.segundo_apellido THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'segundo_apellido', OLD.segundo_apellido, NEW.segundo_apellido);
    END IF;

    -- Verificar si la 'Dirección Domiciliar' cambió
    IF OLD.direccion_domiciliar <> NEW.direccion_domiciliar THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'direccion_domiciliar', OLD.direccion_domiciliar, NEW.direccion_domiciliar);
    END IF;

    -- Verificar si el 'Teléfono de Casa' cambió
    IF OLD.telefono_de_casa <> NEW.telefono_de_casa THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'telefono_de_casa', OLD.telefono_de_casa, NEW.telefono_de_casa);
    END IF;

    -- Verificar si el 'Teléfono Móvil' cambió
    IF OLD.telefono_movil <> NEW.telefono_movil THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'telefono_movil', OLD.telefono_movil, NEW.telefono_movil);
    END IF;

    -- Verificar si el 'Salario Base' cambió
    IF OLD.salario_base <> NEW.salario_base THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'salario_base', OLD.salario_base, NEW.salario_base);
    END IF;

    -- Verificar si la 'Bonificación' cambió
    IF OLD.bonificacion <> NEW.bonificacion THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'bonificacion', OLD.bonificacion, NEW.bonificacion);
    END IF;
END //

DELIMITER ;

-- PostgreSQL

CREATE TABLE empleados (
    dpi VARCHAR(16) PRIMARY KEY,
    primer_nombre VARCHAR(50),
    segundo_nombre VARCHAR(50),
    primer_apellido VARCHAR(50),
    segundo_apellido VARCHAR(50),
    direccion_domiciliar VARCHAR(100),
    telefono_de_casa VARCHAR(15),
    telefono_movil VARCHAR(15),
    salario_base NUMERIC(10, 2),
    bonificacion NUMERIC(10, 2)
);

-- Trigger

CREATE TABLE bitacora (
    id_bitacora SERIAL PRIMARY KEY,
    dpi VARCHAR(20),                  -- El DPI del empleado que fue modificado
    campo_modificado VARCHAR(100),    -- El campo que fue modificado
    valor_anterior TEXT,              -- El valor anterior del campo
    valor_nuevo TEXT,                 -- El valor nuevo del campo
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Fecha de modificación
);

CREATE OR REPLACE FUNCTION fn_trigger_empleados_update()
RETURNS TRIGGER AS $$
BEGIN
    -- Verificar si el 'Primer Nombre' cambió
    IF OLD.primer_nombre <> NEW.primer_nombre THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'primer_nombre', OLD.primer_nombre, NEW.primer_nombre);
    END IF;

    -- Verificar si el 'Segundo Nombre' cambió
    IF OLD.segundo_nombre <> NEW.segundo_nombre THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'segundo_nombre', OLD.segundo_nombre, NEW.segundo_nombre);
    END IF;

    -- Verificar si el 'Primer Apellido' cambió
    IF OLD.primer_apellido <> NEW.primer_apellido THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'primer_apellido', OLD.primer_apellido, NEW.primer_apellido);
    END IF;

    -- Verificar si el 'Segundo Apellido' cambió
    IF OLD.segundo_apellido <> NEW.segundo_apellido THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'segundo_apellido', OLD.segundo_apellido, NEW.segundo_apellido);
    END IF;

    -- Verificar si la 'Dirección Domiciliar' cambió
    IF OLD.direccion_domiciliar <> NEW.direccion_domiciliar THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'direccion_domiciliar', OLD.direccion_domiciliar, NEW.direccion_domiciliar);
    END IF;

    -- Verificar si el 'Teléfono de Casa' cambió
    IF OLD.telefono_de_casa <> NEW.telefono_de_casa THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'telefono_de_casa', OLD.telefono_de_casa, NEW.telefono_de_casa);
    END IF;

    -- Verificar si el 'Teléfono Móvil' cambió
    IF OLD.telefono_movil <> NEW.telefono_movil THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'telefono_movil', OLD.telefono_movil, NEW.telefono_movil);
    END IF;

    -- Verificar si el 'Salario Base' cambió
    IF OLD.salario_base <> NEW.salario_base THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'salario_base', OLD.salario_base, NEW.salario_base);
    END IF;

    -- Verificar si la 'Bonificación' cambió
    IF OLD.bonificacion <> NEW.bonificacion THEN
        INSERT INTO bitacora (dpi, campo_modificado, valor_anterior, valor_nuevo)
        VALUES (NEW.dpi, 'bonificacion', OLD.bonificacion, NEW.bonificacion);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_empleados_update
AFTER UPDATE ON empleados
FOR EACH ROW
EXECUTE FUNCTION fn_trigger_empleados_update();
