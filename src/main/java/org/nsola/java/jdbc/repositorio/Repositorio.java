package org.nsola.java.jdbc.repositorio;

import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {
    List<T> listar();
    T porId(Long id) throws SQLException;
    void guardar(T t);
    void eliminar(Long id);
}
