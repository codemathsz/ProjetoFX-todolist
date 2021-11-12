package com.br.matheus.todolist.controller;


import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class IndexController {

    @FXML
    private MenuBar menu;

    @FXML
    private DatePicker dateLimitBox;

    @FXML
    private TextField boxNameTf;

    @FXML
    private TextField statusTf;

    @FXML
    private TextArea boxComent;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnList;

    @FXML
    private Button btnCalendar;

    @FXML
    private Button btnClean;

    @FXML
    private Button btnDump;// Dump = JOGAR FORA	

    @FXML
    public void btnCalendarClick() {
    	
    }

    @FXML
    public void btnCleanClick() {

    }

    @FXML
    public void btnDumpClick() {

    }

    @FXML
    public void btnListClick() {

    }

    @FXML
    public void btnSaveClick() {
    	
    	//VALIDAÇÃO DE CAMPOS
    	if(dateLimitBox.getValue()== null) {
    		JOptionPane.showMessageDialog(null, "informe a data limite", "Informe a data limite Obrigatório* ", JOptionPane.ERROR_MESSAGE);
    		dateLimitBox.requestFocus();// PARA P DATEPICKER FICAR SELECIONADO DEPOIS DA MSG
    		
    	}else if(boxNameTf.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Informe a descrição da tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
    		boxNameTf.requestFocus();
    	}else if(boxComent.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Informe os comentários da tarefa", "campo de comentários vazio", JOptionPane.ERROR_MESSAGE);
    		boxComent.requestFocus();
    	}
    }

}

	

