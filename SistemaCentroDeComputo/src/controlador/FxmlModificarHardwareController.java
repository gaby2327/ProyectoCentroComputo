package controlador;

import CentroComputo.Hardware;
import CentroComputo.Validador;
import Daos.HardwareDao;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author irasema caicero
 */
public class FxmlModificarHardwareController implements Initializable {

    //cajas de texto y combo box
    @FXML
    private TextField textFieldNoInventario;
    @FXML
    private TextField textFieldMarca;
    @FXML
    private TextField textFieldResponsable;
    @FXML
    private TextField textFieldTipo;
    @FXML
    private TextField textFieldFecha;
    @FXML
    private TextField textFieldModelo;
    @FXML
    private TextField textFieldNoSerie;
    @FXML
    private TextField textFieldUbicacion;
    @FXML
    private ComboBox<String> comboBoxEstado;
    //botones
    @FXML
    private Button buttonGuardar;
    @FXML
    private Button buttonSalir;
    
    FxmlAdministrarHardwareController controlAdminHardware;
    Hardware hardware;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
      ObservableList<String> estados = FXCollections.observableArrayList();
      estados.addAll("Disponible", "Deshabilitado", "prestado", "En mantenimiento");
      comboBoxEstado.setItems(estados);
      
      comboBoxEstado.setOnAction(e -> System.out.println("selecciona un estado: " + comboBoxEstado.getValue()));
      
      buttonGuardar.setOnAction((ActionEvent event) -> {
          Validador validador = new Validador();
          
          boolean noInventarioValidado = validador.validarNoInventarioHardware(textFieldNoInventario.getText());
          if(noInventarioValidado == true) {
              String noInventarioUv = textFieldNoInventario.getText();
              String marca = textFieldMarca.getText();
              String modelo = textFieldModelo.getText();
              int numerodeSerie = Integer.parseInt(textFieldNoSerie.getText());
              String estado = comboBoxEstado.getValue();
              String tipoDispositivo = textFieldTipo.getText();
              Date fecha = Date.valueOf(textFieldFecha.getText());
              String responsable = textFieldResponsable.getText();
              String ubicacion = textFieldUbicacion.getText();
              
              HardwareDao hardwareDao = new HardwareDao();
              boolean resultadoActualizacion = hardwareDao.actualizarHardware(new
                Hardware(noInventarioUv, marca, modelo, numerodeSerie, estado, 
                        tipoDispositivo, fecha,responsable, ubicacion));
              
              if(resultadoActualizacion == true) {
                  // agregar ventana emergente---------------------------------------
                  Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                  alerta.setTitle("Hardware Guardado");
                  alerta.setHeaderText(null);
                  alerta.setContentText("Los datos han sido guardados! :D /n Recuerda"
                          + "actualizar la tabla!!");
                  alerta.show();
              }
          }else {
          // agregar ventana emergente---------------------------------------
          Alert alerta = new Alert(Alert.AlertType.INFORMATION);
          alerta.setTitle("hardware no Guardado");
          alerta.setHeaderText(null);
          alerta.setContentText("revise que los datos sean correctos");
          alerta.show();
              }  
      });
      
      buttonSalir.setOnAction((ActionEvent event) -> {
          Stage stage = (Stage) buttonSalir.getScene().getWindow();
          stage.close();
      });
  }
  
  public void traerHardware(Hardware auxiliar) {
      textFieldNoInventario.setText(auxiliar.getNoInventarioUv());
      textFieldMarca.setText(auxiliar.getMarca());
      textFieldResponsable.setText(auxiliar.getResponsable());
      textFieldTipo.setText(auxiliar.getTipoDispositivo());
      textFieldFecha.setText(String.valueOf(auxiliar.getFechaAdquisicion()));
      textFieldModelo.setText(auxiliar.getModelo());
      textFieldNoSerie.setText(String.valueOf(auxiliar.getNumeroSerie()));
      textFieldUbicacion.setText(auxiliar.getUbicacion());
      comboBoxEstado.getSelectionModel().select(auxiliar.getEstado());
      
      this.hardware = auxiliar;    
  }

}
