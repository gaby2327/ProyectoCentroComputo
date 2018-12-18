package controlador;

import CentroComputo.Software;
import Daos.SoftwareDao;
import CentroComputo.Validador;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author galil y irasema
 */
public class FxmlAgregarSoftwareController implements Initializable {
  @FXML
  private TextField textFieldIdSoftware;
  @FXML
  private TextField textFieldNombre;
  @FXML
  private TextField textFieldOrigen;
  @FXML
  private TextField textFieldObservaciones;
  @FXML
  private TextField textFieldFecha;
  @FXML
  private TextField textFieldTipo;
  @FXML
  private TextField textFieldMarca;
  @FXML
  private TextField textFieldActualizacion;
  @FXML
  private TextField textFieldVersion;
  @FXML
  private TextField textFieldDisponible;
  @FXML
  private TextField textFieldSO;
  @FXML
  private TextField textFieldIdioma;
  @FXML
  private Button buttonGuardar;
  @FXML
  private Button buttonSalir;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

    buttonGuardar.setOnAction(new EventHandler() {
      @Override
      public void handle(Event event) {
        SoftwareDao softwareDao = new SoftwareDao();
        Validador validador = new Validador();

        boolean idValidacion = validador.validaIdSoftware(textFieldIdSoftware.getText());
        if(idValidacion == true) {
          String idSoftware = textFieldIdSoftware.getText();
          String nombreSoftware = textFieldNombre.getText();
          String origen = textFieldOrigen.getText();
          String observaciones = textFieldObservaciones.getText();
          Date fechaAdquisicion = Date.valueOf(textFieldFecha.getText().trim());
          String tipoSoftware = textFieldTipo.getText();
          String marca = textFieldMarca.getText();
          boolean actualizacion = validarActualizacion(textFieldActualizacion.getText());
          Boolean requiereActualizacion = actualizacion;
          Double version = Double.parseDouble(textFieldVersion.getText());
          boolean disponibilidad = validarDisponibilidad(textFieldDisponible.getText());
          Boolean disponible = disponibilidad;
          String sistemaOperativo = textFieldSO.getText();
          String idioma = textFieldIdioma.getText();

          Software software = new Software(idSoftware, nombreSoftware, origen, observaciones,
              fechaAdquisicion, tipoSoftware, marca, requiereActualizacion, version, disponible,
              sistemaOperativo, idioma);

          softwareDao.agregarSoftware(software);

          // agregar ventana emergente---------------------------------------
          Alert alerta = new Alert(Alert.AlertType.INFORMATION);
          alerta.setTitle("Software Guardado");
          alerta.setHeaderText(null);
          alerta.setContentText("Los datos han sido guardados! :D");
          alerta.show();
        }
      }
    });

    buttonSalir.setOnAction((ActionEvent event) -> {
      Stage stage = (Stage) buttonSalir.getScene().getWindow();
      stage.close();
    });


  }

  private boolean validarActualizacion(String respuesta) {
    boolean resultado = false;
    respuesta = textFieldActualizacion.getText();
    if (respuesta.equalsIgnoreCase("si")) {
      resultado = true;
    } else if (respuesta.equalsIgnoreCase("no")) {
      resultado = false;
    }
    return resultado;
  }

  private boolean validarDisponibilidad(String respuesta) {
    boolean resultado = false;
    respuesta = textFieldDisponible.getText();
    if (respuesta.equalsIgnoreCase("si")) {
      resultado = true;
    } else if (respuesta.equalsIgnoreCase("no")) {
      resultado = false;
    }
    return resultado;
  }


}
