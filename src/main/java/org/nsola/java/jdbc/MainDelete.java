package org.nsola.java.jdbc;

import org.nsola.java.jdbc.model.Producto;
import org.nsola.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.nsola.java.jdbc.repositorio.Repositorio;
import org.nsola.java.jdbc.utils.ConexionDB;

import java.sql.Connection;
import java.sql.SQLException;


public class MainDelete {
    public static void main(String[] args) throws SQLException {

        //Para evitar anidamientos definimos las sentencias dentro del try y de esa manera se cerrarán de forma automática (Auto closed)
        try (Connection conn = ConexionDB.getInstance()){
            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("============= Listar =============");
            repositorio.listar().forEach(System.out::println);

            System.out.println("============= Buscar por ID =============");
            System.out.println(repositorio.porId(2L));

            System.out.println("============= Actualizar Producto =============");
            Producto producto = new Producto();
            producto.setId(3L);
            producto.setNombre("Radio Bluetooth");
            producto.setPrecio(90);
            repositorio.guardar(producto);
            System.out.println("Producto actualizado");
            repositorio.listar().forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
