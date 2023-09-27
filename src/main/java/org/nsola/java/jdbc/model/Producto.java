package org.nsola.java.jdbc.model;

import java.util.Date;

public class Producto {
        private Long id;
        private String nombre;

        public Producto() {

        }

        @Override
        public String toString() {
            return  id +
                    " | " +
                    nombre +
                    " | " +
                    precio +
                    " | " +
                    fecha_registro;
        }

        public Producto(Long id, String nombre, Integer precio, Date fecha_registro) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
            this.fecha_registro = fecha_registro;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Integer getPrecio() {
            return precio;
        }

        public void setPrecio(Integer precio) {
            this.precio = precio;
        }

        public Date getFecha_registro() {
            return fecha_registro;
        }

        public void setFecha_registro(Date fecha_registro) {
            this.fecha_registro = fecha_registro;
        }

        private Integer precio;
        private Date fecha_registro;
    }

