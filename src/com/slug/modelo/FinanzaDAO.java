package com.slug.modelo;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FinanzaDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conexion conectar = new Conexion();

    public List listar() {
        List<Finanza> datos = new ArrayList<>();
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement("select * from finanza");
            rs = ps.executeQuery();
            while (rs.next()) {
                Finanza f = new Finanza();
                f.setId(rs.getString(1));
                f.setMonto(rs.getString(2));
                f.setFecha(rs.getString(3));
                f.setMetodoPago(rs.getString(4));
                f.setTipo(rs.getString(5));
                f.setComentarios(rs.getString(6));
                datos.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datos;
    }
    
    public int agregar(Finanza fin) {  
    int r = 0;
    String sql = "insert into finanza(monto, fecha, metodoPago, tipo, comentarios) values(?,?,?,?,?)";
    try {
        con = conectar.getConnection();
        ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Solicita las claves generadas
        ps.setString(1, fin.getMonto());
        ps.setString(2, fin.getFecha());
        ps.setString(3, fin.getMetodoPago());
        ps.setString(4, fin.getTipo());
        ps.setString(5, fin.getComentarios());
        r = ps.executeUpdate(); 

        // Obtiene la clave generada (id autoincremental)
        if (r == 1) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                fin.setId(String.valueOf(generatedKeys.getInt(1))); // Actualiza el ID en el objeto Finanza
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }  
    return r;
}

    
    public int actualizar(Finanza fin) {  
        int r=0;
        String sql="update finanza set monto=?, fecha=?, metodoPago=?, tipo=?, comentarios=? where id=?";        
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);            
            ps.setString(1, fin.getMonto());
            ps.setString(2, fin.getFecha());
            ps.setString(3, fin.getMetodoPago());
            ps.setString(4, fin.getTipo());
            ps.setString(5, fin.getComentarios());
            ps.setString(6, fin.getId());
            r=ps.executeUpdate();    
            if(r==1){
                return 1;
            }
            else{
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
        return r;
    }
    
    public int eliminar(String id){
        int r=0;
        String sql="delete from finanza where id=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, id);
            r= ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
}
