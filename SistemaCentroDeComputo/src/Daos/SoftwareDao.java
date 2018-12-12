/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import CentroComputo.Licencia;
import CentroComputo.Software;
import ccExcepciones.NoExisteRegistroException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Irasema Caicero
 * @since 8-12-18
 * @version 1.0
 */
public class SoftwareDao implements InterfaceSoftwareDao {
    private String consulta;
    private List<Software> listaSoftware;
    

    @Override
    public boolean agregarSoftware(Software software) {
        
        consulta= "insert into software values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement consultaParametrizada = AccesoDataBase.obtenerConexionBaseDatos().prepareStatement(consulta)) {
            consultaParametrizada.setString(1, software.getIdSoftware());
            consultaParametrizada.setString(2, software.getNombre());            
            consultaParametrizada.setString(3, software.getOrigen());
            consultaParametrizada.setString(4, software.getObservaciones());
            consultaParametrizada.setDate(5, software.getFechaAdquisicion());
            consultaParametrizada.setString(6, software.getTipoSoftware());
            consultaParametrizada.setString(7, software.getMarca());
            consultaParametrizada.setBoolean(8, software.isRequiereActualizacion());
            consultaParametrizada.setDouble(9, software.getVersion());
            consultaParametrizada.setBoolean(10, software.isDisponible());
            consultaParametrizada.setString(11, software.getSistemaOperativo());
            consultaParametrizada.setString(12, software.getIdioma());
            consultaParametrizada.executeUpdate();                       
        } catch (SQLException ex) {
            Logger.getLogger(SoftwareDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
           AccesoDataBase.cerrarConexion();
        }
        return true;
    }

    @Override
    public boolean actualizarSoftware(Software software) {
        if(software.getIdSoftware().isEmpty()){
            try {
                throw new NoExisteRegistroException("El software que ingresó no existe.");
            } catch (NoExisteRegistroException ex) {
                Logger.getLogger(SoftwareDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        consulta = "UPDATE Software set" +
                "'nombreSoftware'= ?, 'origen'=?, 'Observaciones'=?, 'fechaAdquisicion'=?, 'tipoSoftware'=?," +
                "'marca'=?, 'requiereActualizacion'=?, 'version'=?, 'disponible'=?," + 
                "'sistemaOperativo'=?, 'idioma'=? where idSoftware=? ";
        
        try(PreparedStatement consultaParametrizada = AccesoDataBase.obtenerConexionBaseDatos().prepareStatement(consulta)){
            consultaParametrizada.setString(1, software.getIdSoftware());
            consultaParametrizada.setString(2, software.getNombre());            
            consultaParametrizada.setString(3, software.getOrigen());
            consultaParametrizada.setString(4, software.getObservaciones());
            consultaParametrizada.setDate(5, software.getFechaAdquisicion());
            consultaParametrizada.setString(6, software.getTipoSoftware());
            consultaParametrizada.setString(7, software.getMarca());
            consultaParametrizada.setBoolean(8, software.isRequiereActualizacion());
            consultaParametrizada.setDouble(9, software.getVersion());
            consultaParametrizada.setBoolean(10, software.isDisponible());
            consultaParametrizada.setString(11, software.getSistemaOperativo());
            consultaParametrizada.setString(12, software.getIdioma());
            consultaParametrizada.executeUpdate();                 
        } catch (SQLException ex) {
            Logger.getLogger(SoftwareDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            AccesoDataBase.cerrarConexion();
        }
        return true;
    }

    @Override
    public List<Software> obtenerListaSoftware() {
        consulta = "select * from Software";
        listaSoftware = new ArrayList <>();
        
        try{
            PreparedStatement consultaParametrizada = AccesoDataBase.obtenerConexionBaseDatos().prepareStatement(consulta);
            ResultSet resultado = consultaParametrizada.executeQuery();
            while (resultado.next()){
                Software software = new Software();
                software.setNombre(resultado.getString("nombre"));
                software.setIdSoftware(resultado.getString("clave"));
                software.setMarca(resultado.getString("marca"));
                software.setVersion(resultado.getDouble("version"));
                software.setOrigen(resultado.getString("origen"));
                software.setTipoSoftware(resultado.getString("tipo Software"));
                software.setFechaAdquisicion(resultado.getDate("fecha adquisicion"));
                software.setIdioma(resultado.getString("idioma"));
                software.setSistemaOperativo(resultado.getString("sistemaOperativo"));
                software.setRequiereActualizacion(resultado.getBoolean("actualizacion"));
                software.setObservaciones(resultado.getString("observaciones"));
                software.setDisponible(resultado.getBoolean("disponible"));
                listaSoftware.add(software);   
            }
        } catch (SQLException ex) {
            Logger.getLogger(SoftwareDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            AccesoDataBase.cerrarConexion();
        }
        return null;
    }

    @Override
    public Software buscarSoftware(String idSoftware) {
        consulta = "select * from software where idSoftware = ?";
        Software software = new Software();
        try{
            PreparedStatement consultaParametrizada = AccesoDataBase.obtenerConexionBaseDatos().prepareStatement(consulta);
            consultaParametrizada.setString(1, idSoftware);
            ResultSet resultado = consultaParametrizada.executeQuery();
            if(resultado.next()){
                software.setNombre(resultado.getString("nombre"));
                software.setIdSoftware(resultado.getString("clave"));
                software.setMarca(resultado.getString("marca"));
                software.setVersion(resultado.getDouble("version"));
                software.setOrigen(resultado.getString("origen"));
                software.setTipoSoftware(resultado.getString("tipo Software"));
                software.setFechaAdquisicion(resultado.getDate("fecha adquisicion"));
                software.setIdioma(resultado.getString("idioma"));
                software.setSistemaOperativo(resultado.getString("sistemaOperativo"));
                software.setRequiereActualizacion(resultado.getBoolean("actualizacion"));
                software.setObservaciones(resultado.getString("observaciones"));
                software.setDisponible(resultado.getBoolean("disponible"));
                return software;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SoftwareDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            AccesoDataBase.cerrarConexion();
        }
       return null;
    }

    @Override
    public boolean eliminarSoftware(Software software) {      
        consulta= "update software set disponible=false where idSoftware= ?";
        try{
            PreparedStatement consultaParametrizada = AccesoDataBase.obtenerConexionBaseDatos().prepareStatement(consulta);
            consultaParametrizada.setBoolean(10, software.isDisponible()==false);
            consultaParametrizada.executeUpdate();                   
        } catch (SQLException ex) {
            Logger.getLogger(SoftwareDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            AccesoDataBase.cerrarConexion();
        }
       return true;
    }
    
    
    
}
