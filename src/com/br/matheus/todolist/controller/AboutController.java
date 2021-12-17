package com.br.matheus.todolist.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {
	@FXML
	private Button btnOk;
	
	@FXML
	public void btnOkClick() {
		// WINDOW DEVOLVE UMA JANELA E TEM QUE CONVERTER PRA STAGE
		Stage stage = (Stage) btnOk.getScene().getWindow();
		stage.close();// FECHANDO A JANELA QUANDO CLICAR NO BOTÃO OK
	}
}
