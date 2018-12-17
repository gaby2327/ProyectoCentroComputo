package Daos;

import CentroComputo.Hardware;
import ccExcepciones.NoExisteRegistroException;
import centroDeComputo.Validador;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * clase que se conecta con la base de datos para realizar los metodos de 
 * administracion (CRUD)
 *
 * @author Irasema Caicero
 * @since 11/12/2018
 * @version 1.0
 */
public class HardwareDao implements InterfaceHardwareDao {
    private String consulta;
    private List <Hardware> listaHardware;
    private Connection conection;

    /**
     * registra un nuevo hardware
     * 
     * @param hardware a guardar
     * @return true
     */
    @Override
    public boolean agregarHardware(Hardware hardware) {
         Validador validador = new Validador();
         validador.validarNoInventarioHardware(hardware.getNoInventarioUv());
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
         validador.validarFechaMaxima(sdf.format(hardware.getFechaAdquisicion()));
        consulta = "insert into hardware values (?, ?, ?, ?, ?, ?, ?)";
        try{
            conection = AccesoDataBase.obtenerConexionBaseDatos();
            PreparedStatement consultaParametrizada = conection.prepareStatement(consulta);
            consultaParametrizada.setString(1, hardware.getNoInventarioUv());
            consultaParametrizada.setString(2, hardware.getMarca());
            consultaParametrizada.setString(3, hardware.getModelo());
            consultaParametrizada.setString(4, hardware.getEstado());
            consultaParametrizada.setString(5, hardware.getTipoDispositivo());
            consultaParametrizada.setDate(6, hardware.getFechaAdquisicion());
            consultaParametrizada.setInt(7, hardware.getNumeroSerie());
            consultaParametrizada.executeUpdate();           
        } catch (SQLException ex) {
            Logger.getLogger(HardwareDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            AccesoDataBase.cerrarConexion();
        }
        return true;        
    }

    /**
     * modifica los datos de un hardware existente
     * @param hardware a modificar
     * @return true
     */
    @Override
    public boolean actualizarHardware(Hardware hardware) {
        if(hardware.getNoInventarioUv().isEmpty()){
            try{
                throw new NoExisteRegistroException("El hardware que ingresó no existe");
            } catch (NoExisteRegistroException ex) {
                Logger.getLogger(HardwareDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        consulta = "UPDATE hardware set 'marca'= ?, 'modelo' = ?, 'estado' = ?, "
                + "'tipoDispositivo' = ?, 'fechaAdquisicion' = ?, 'numeroSerie' = ?";
        try{
            conection = AccesoDataBase.obtenerConexionBaseDatos();
            PreparedStatement consultaParametrizada = conection.prepareStatement(consulta);
            consultaParametrizada.setString(1, hardware.getNoInventarioUv());
            consultaParametrizada.setString(2, hardware.getMarca());
            consultaParametrizada.setString(3, hardware.getModelo());
            consultaParametrizada.setString(4, hardware.getEstado());
            consultaParametrizada.setString(5, hardware.getTipoDispositivo());
            consultaParametrizada.setDate(6, hardware.getFechaAdquisicion());
            consultaParametrizada.setInt(7, hardware.getNumeroSerie());
            consultaParametrizada.executeUpdate();              
        } catch (SQLException ex) {
            Logger.getLogger(HardwareDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            AccesoDataBase.cerrarConexion();
        }     
        return true;
    }

    /**
     * obtiene la lista existente de equipos registrados
     * @return 
     */
    @Override
    public List<Hardware> obtenerListaHardware() {
        consulta = "select * from hardware";
        List<Hardware> listaHardware = new ArrayList<>();
        try{
            conection = AccesoDataBase.obtenerConexionBaseDatos();
            PreparedStatement consultaParametrizada = conection.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaParametrizada.executeQuery();
            while(resultadoConsulta.next()) {
                Hardware hardware = new Hardware();
                hardware.setNoInventarioUv(resultadoConsulta.getString("noInventarioUv"));
                hardware.setMarca(resultadoConsulta.getString("marca"));
                hardware.setModelo(resultadoConsulta.getString("modelo"));
                hardware.setEstado(resultadoConsulta.getString("estado"));
                hardware.setTipoDispositivo(resultadoConsulta.getString("tipoDispositivo"));
                hardware.setFechaAdquisicion(resultadoConsulta.getDate("fechaAdquisicion"));
                hardware.setNumeroSerie(resultadoConsulta.getInt("noSerie"));
                listaHardware.add(hardware);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HardwareDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            AccesoDataBase.cerrarConexion();
        }
        return listaHardware;
    }

    @Override
    public Hardware buscarHardware(String noInventarioUv) {
        consulta = "select * from hardware where noInventarioUv = ? ";
        Hardware hardware = new Hardware();
        try{
            conection = AccesoDataBase.obtenerConexionBaseDatos();
            PreparedStatement consultaParametrizada = conection.prepareStatement(consulta);
            consultaParametrizada.setString(1, noInventarioUv);
            ResultSet resultadoConsulta = consultaParametrizada.executeQuery();
            resultadoConsulta.next();
            hardware.setNoInventarioUv(resultadoConsulta.getString("noInventarioUv"));
            hardware.setMarca(resultadoConsulta.getString("marca"));
            hardware.setModelo(resultadoConsulta.getString("modelo"));
            hardware.setEstado(resultadoConsulta.getString("estado"));
            hardware.setTipoDispositivo(resultadoConsulta.getString("tipoDispositivo"));
            hardware.setFechaAdquisicion(resultadoConsulta.getDate("fechaAdquisicion"));
            hardware.setNumeroSerie(resultadoConsulta.getInt("noSerie"));
            
        } catch (SQLException ex) {
            Logger.getLogger(HardwareDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            AccesoDataBase.cerrarConexion();
        }
        return hardware;
    }

    /**
     * deshabilita el hardware seleccionado
     * @param hardware a dehabilitar
     * @return true
     */
    @Override
    public boolean eliminarHardware(Hardware hardware) {
        consulta = "update software set estado = deshabilitado  where no.InventarioUv = ?";
        try{
            PreparedStatement consultaParametrizada = AccesoDataBase.obtenerConexionBaseDatos().prepareStatement(consulta);
            consultaParametrizada.setString(1, hardware.getNoInventarioUv());
            consultaParametrizada.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            AccesoDataBase.cerrarConexion();
        }
        return true;
    }   
}
