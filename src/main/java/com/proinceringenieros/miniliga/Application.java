package com.proinceringenieros.miniliga;

import com.proinceringenieros.miniliga.services.DataStore2;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        // 1) Cargar datos persistidos ANTES de crear controllers/FXML
        DataStore2.load();

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        stage.setTitle("LOGIN SCREEN");
        stage.setScene(scene);

        // 2) Guardar al cerrar ventana
        stage.setOnCloseRequest(e -> DataStore2.save());

        stage.show();
    }

    @Override
    public void stop() {
        // 3) Seguridad extra: JavaFX llama a stop() al cerrar la app
        DataStore2.save();
    }
}





