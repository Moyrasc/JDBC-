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


        return productos;
    }

    @Override
    public Producto porId(Long id) {
        Producto producto = null;
        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM productos WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    producto = crearProducto(res);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) {
        String sql;
        //Actualiza producto existence
        if (producto.getId() != null && producto.getId() > 0 ) {
            sql = "UPDATE productos SET nombre=?, precio=? WHERE id=?";
        //Crea y guarda producto nuevo
        } else {
            sql = "INSERT INTO productos(nombre,precio,fecha_registro) VALUES (?,?,?)";
        }
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getPrecio());

            //Verificacion adicional ya que el 3 parametro corresponde al id y se necesita comprobar si existe
            if (producto.getId() != null && producto.getId() > 0 ) {
                stmt.setLong(3, producto.getId());
            }else {
                //tenemos que convertir el java util en java sql
                stmt.setDate(3, new Date(producto.getFecha_registro().getTime()));
            }
            //sentencia de ejecución
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(Long id) {
        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM productos WHERE id= ?")) {
            stmt.setLong(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

