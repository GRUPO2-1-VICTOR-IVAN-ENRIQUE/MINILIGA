package com.proinceringenieros.miniliga;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class LoginController {

    private Map<String, String> usuarios = new HashMap<>();
    private final String ARCHIVO_USUARIOS="usuarios.json";
    private final Gson gson = new Gson();

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfPasword;
    @FXML
    private  Button btnLogin;
    @FXML
    private Label lblMensaje;
    @FXML
    private Button btnRegistrar;

    @FXML
    private void initialize() {
        cargarUsuarios();  // Carga usuarios existentes del archivo JSON
    }

    @FXML
    protected void registerClick(ActionEvent event) {

        String usuario = tfUsuario.getText().trim();
        String password = pfPasword.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Rellena todos los campos");
            mostrarMensaje("Rellena todos los campos");
            return;
        }

        if (registrarUsuario(usuario, password)) {
            guardarUsuarios(); // 🔥 AQUÍ se escribe el JSON
            lblMensaje.setText("Usuario registrado correctamente");
        } else {
            lblMensaje.setText("El usuario ya existe");
        }

    }

    @FXML
    protected void loginClick(ActionEvent event) {

        String usuario = tfUsuario.getText().trim();
        String password = pfPasword.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Rellena todos los campos");
            mostrarMensaje("Rellena todos los campos");
            return;
        }
        if (validarLogin(usuario, password)) {
            lblMensaje.setText("Login Correcto");
            lblMensaje.setStyle("-fx-text-fill:green;");
            mostrarMensaje("Login Correcto");
            // Abrir ventana principal
            abrirVentanaLista();
            cerrarVentanaLogin();


        }  else {
            lblMensaje.setText("Usuario o contraeñas incorrectos");
            lblMensaje.setStyle("-fx-text-fill:red;");
            mostrarMensaje("Login no realizado");
        }


    }


    // Metodo cargar usuarios

    private void cargarUsuarios() {
        try (FileReader reader = new FileReader(ARCHIVO_USUARIOS)) {
            Type tipo = new TypeToken<Map<String, String>>(){}.getType();
            usuarios = gson.fromJson(reader, tipo);

            if (usuarios == null) {
                usuarios = new HashMap<>();
            }

        } catch (IOException e) {
            usuarios = new HashMap<>();
            mostrarMensaje("usuarios.json no existe todavía");

        }
    }
    // Metodo para registrar el usuario
    private boolean registrarUsuario (String usuario, String password) {
        if (usuarios.containsKey(usuario)){
            return false;
        }
        usuarios.put(usuario,password);
        return true;
    }

    // Metodo guardar usuarios en archivo Json
    private void guardarUsuarios() {
        try (FileWriter writer = new FileWriter(ARCHIVO_USUARIOS)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Metodo para validar login
    private boolean validarLogin(String usuario, String password){
        return usuarios.containsKey(usuario) && usuarios.get(usuario).equals(password);
    }
    // Metodo abrir ventana

    private void abrirVentanaLista() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("lista.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Aplicación");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Metodo Cerrar ventana
    private void cerrarVentanaLogin() {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    public void mostrarMensaje(String mensaje) {

        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("ATENCIÓN");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
