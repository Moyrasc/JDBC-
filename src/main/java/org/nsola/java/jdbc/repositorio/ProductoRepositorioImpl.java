package org.nsola.java.jdbc.repositorio;



import org.nsola.java.jdbc.model.Producto;
import org.nsola.java.jdbc.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto> {
    //Conexión BBDD
    private Connection getConnection() throws SQLException {
        return ConexionDB.getInstance();
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        //Procesa una sentencia SQL y obtiene los resultados(Statement)
        //Contiene los resultados de una consulta SQL, por lo que es un conjunto de filas obtenidas desde una base de datos(ResultSet)
        try (Statement stmt = getConnection().createStatement(); ResultSet res = stmt.executeQuery("SELECT * FROM productos")) {

            while (res.next()) {
                Producto prod = crearProducto(res);
                productos.add(prod);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;

        return productos;
    }

    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM productos WHERE id = ?")) {
            stmt.setLong(1, id);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                producto = crearProducto(res);
            }
            res.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) {

    }

    @Override
    public void eliminar(Long id) {

    }

    private static Producto crearProducto(ResultSet res) throws SQLException {
        Producto prod = new Producto();
        prod.setId(res.getLong("id"));
        prod.setNombre(res.getString("nombre"));
        prod.setPrecio(res.getInt("precio"));
        prod.setFecha_registro(res.getDate("fecha_registro"));
        return prod;
    }
}

