package com.br.matheus.todolist.application;
	
import com.br.matheus.todolist.io.TarefaIO;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TarefaIO.createFile();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/Index.fxml"));
			Scene scene = new Scene(root,490,550);
			scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);//IMPEDIR QUE A JANELA  REDIMENCIONE
			primaryStage.setTitle("To do List");//TITULO DA JANELA
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
