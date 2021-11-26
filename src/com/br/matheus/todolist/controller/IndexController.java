package com.br.matheus.todolist.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.br.matheus.todolist.io.TarefaIO;
import com.br.matheus.todolist.model.Importancia;
import com.br.matheus.todolist.model.StatusTarefa;
import com.br.matheus.todolist.model.Tarefa;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class IndexController implements Initializable, ChangeListener<Tarefa>{

	@FXML
	private MenuBar menu;

	@FXML
	private DatePicker dateLimitBox;

	@FXML
	private TextField boxNameTf, statusTf;

	@FXML
	private TextArea boxComent;

	@FXML
	private Button btnSave, btnList, btnCalendar, btnClean, btnDump;// Dump = JOGAR FORA

	// VARIÁVEL PARA GUARDAR A TAREFA
	private Tarefa tarefa;
	// VARIÁVEL PARA GUARDAR A LISTA DE TAREFAS
	private List<Tarefa> tarefas;
	
	@FXML
	private ChoiceBox<Importancia> choiceImportancia;
	@FXML
	private TableView<Tarefa> tvTarefa;
	@FXML
	private TableColumn<Tarefa, LocalDate> tcData;
	@FXML
	private TableColumn<Tarefa, String> tcTarefa;
	@FXML
	private TableColumn<Tarefa, StatusTarefa> tcStatus;
	@FXML
	private TableColumn<Tarefa, Importancia> tcImportancia;
	
	@FXML
	public void btnCalendarClick() {

	}

	@FXML
	public void btnCleanClick() {
		clearCamp();
	}

	@FXML
	public void btnDumpClick() {

	}

	@FXML
	public void btnListClick() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {// FAZENDO NO initialize "POR QUE O COMPORTAMENTO É UMA VEZ SÓ"
		choiceImportancia.setItems(FXCollections.observableArrayList(Importancia.values()));
		// DEFINE OS PARÂMETROS PARA AS COLUNAS DO TableView
		tcData.setCellValueFactory(new PropertyValueFactory<>("dataLimiteTf"));// MÉTODO DEFINE COMO É FABRICADO O VALOR DA CELULA
								   // VARIÁVEL ANÔNIMA 		  ACESSANDO o getDataLimite
		tcTarefa.setCellValueFactory(new PropertyValueFactory<>("descricaoDaTf"));
		
		tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		tcImportancia.setCellValueFactory(new PropertyValueFactory<>("classifImportancia"));
					//	FOI POSSIVEL DA UM NEW NA 'INTERFACE', POIS ELE CRIOU UMA CLASSE ANÔNIMA, ENTÃO NÃO SERIA NA INTERFACE E SIM NESSA CLASSE ANÔNIMA QUE ELE CRIOU
		
		// FORMATANDO A DATA NA COLUNA
		tcData.setCellFactory(call -> {
			
				//	CRIANDO UMA MODIFICAÇÃO DA TableCell ANÔNIMA	
				return new TableCell<Tarefa, LocalDate>() {// ABRINDO A CHAVE, DEVOLVENDO UMA INSTÂNCIA DA TableCell MODIFICADA
					
					@Override	
					protected void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);// ESTÁ CHAMANDO O updateItem DA SUPER CLASSE
						// FORAMTANDO A CELULA, TEXTO
						DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
						
						if(!empty) {
							setText(item.format(fmt));
						}else {
							setText("");
						}
						
					}
				};
		
		});
		
		//	EVENTO DE SELEÇÃO DE UM ITEM NA TableView
		tvTarefa.getSelectionModel().selectedItemProperty().addListener(this);
		
		
		
		carregarTarefas();
	}

	private void carregarTarefas() {
		try {
			// BUSCANDO AS TAREFAS NO BANCO DE DADOS E GUARDANDO NA VARIÁVEL TAREFAS
			tarefas = TarefaIO.readTarefas();
			// CONVERTENDO A LISTA PARA ObservableList e ASSOCIANDO AO TableView
			tvTarefa.setItems(FXCollections.observableArrayList(tarefas));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Erro ao buscar as tarefas", "Erro", 0);
			e.printStackTrace();
		}
	}
	
	@FXML
	public void btnSaveClick() {

		// VALIDAÇÃO DE CAMPOS
		if (dateLimitBox.getValue() == null) {
			dateLimitBox.setStyle("-fx-background-color:red");
			dateLimitBox.setPromptText("Informe a Data Limite*");
			// dateLimitBox.requestFocus();// PARA DATEPICKER FICAR SELECIONADO DEPOIS DA
			// MSG
		} else if (boxNameTf.getText().isEmpty()) {
			boxNameTf.setStyle("-fx-border-color: red");
			boxNameTf.setPromptText("Informe o nome da Tarefa*");
			//JOptionPane.showMessageDialog(null, "Informe a descrição da tarefa", "Campo da Descrição da Tarefa Vazio",0);
			//boxNameTf.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG

		} else if (boxComent.getText().isEmpty()) {
			boxComent.setStyle("-fx-background-color:red");
			boxComent.setPromptText("Adicione um comentário*");
			//JOptionPane.showMessageDialog(null, "Informe os comentários da tarefa", "campo de comentários vazio", 0);
			//boxComent.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG

		} else if (boxNameTf.getLength() <= 5) {
			// VERIFICANDO SE O TAMANHO DO NOME DA TAREFA É VALIDO
			JOptionPane.showMessageDialog(null, "Nome da Tarefa muito curto, min(5 caracteres)",
					"Nome da Terefa inválido", 0);
			boxNameTf.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG

		} else if (choiceImportancia.getValue() == null) {
			JOptionPane.showMessageDialog(null, "Selecione uma opção, nível de improtância",
					"Campo vazio,nível de importância", 0);
			choiceImportancia.requestFocus();
		} else {
			// VENDO SE A DATA NÃO É UMA DATA PASSADA, ANTERIOR A ATUAL
			if (dateLimitBox.getValue().isBefore(LocalDate.now())) {

				JOptionPane.showMessageDialog(null, "Data Limite inválida", "Data inválida", 0);
				dateLimitBox.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG
			} else {
				// INSTACIANDO A TAREFA
				tarefa = new Tarefa();
				// POPULAR TAREFA
				tarefa.setDataCriacaoTf(LocalDate.now());
				tarefa.setStatus(StatusTarefa.ABERTA);// CLASSIFICANDO O STATUS DA TAREFA COMO ABERTA
				tarefa.setDataLimiteTf(dateLimitBox.getValue());
				tarefa.setDescricaoDaTf(boxNameTf.getText());
				tarefa.setComentarioTf(boxComent.getText());
				tarefa.setClassifImportancia(choiceImportancia.getValue());
				// SALVAR NO BANCO DE DADOS
				try {// NÃO TRATAMOS NA CLASSE TAREFAIO, USAMOS O THROWS, ENTÃO ESTAMOS TRATANDO AQUI

					TarefaIO.insert(tarefa);
					// LIMPAR OS CAMPOS DO FORMULÁRIO
					clearCamp();
				} catch (FileNotFoundException e) {
					
					JOptionPane.showMessageDialog(null, "Arquivo não encontrado:"+e.getMessage(),"Erro",0);// MSG, PENSANDO NO ÚSUARIO
					e.printStackTrace();// MSG PENSANDO NO DEV
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"Erro de I/O:"+e.getMessage(),"Erro",0);
					e.printStackTrace();
				}

				

			}
		}
	}

	private void clearCamp() {
		tarefa = null;// 'MATANDO' O OBJ TAREFA PARA A PROXIMA TAREFA
		boxNameTf.setText(null);
		dateLimitBox.setValue(null);
		statusTf.setText(null);
		boxComent.setText(null);
		choiceImportancia.setValue(null);// LIMPANDO O "VALOR"
		btnList.setDisable(true);
		btnCalendar.setDisable(true);
		btnDump.setDisable(true);
		dateLimitBox.setDisable(false);
		tvTarefa.getSelectionModel().clearSelection();
		dateLimitBox.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG
	}

	@Override
	public void changed(ObservableValue<? extends Tarefa> observable, Tarefa oldValue, Tarefa newValue) {
		
		// PASSAR A REFERÊNCIA DA VARIÁVEL LOCAL PARA A TAREFA GLOBAL
		tarefa = newValue;
		
		// SE HOUVER UMA TAREFA SELECIONADA
		if (tarefa != null) {
			// LEVANDO AS INFORMAÇÕES DA TAREFA PARA O FORMULARIO
			
			
			
			statusTf.setText(tarefa.getStatus()+"");
			boxNameTf.setText(tarefa.getDescricaoDaTf());
			boxComent.setText(tarefa.getComentarioTf());
			choiceImportancia.setValue(tarefa.getClassifImportancia());
			dateLimitBox.setValue(tarefa.getDataLimiteTf());
			dateLimitBox.setDisable(true);
			btnList.setDisable(false);
			btnCalendar.setDisable(false);
			btnDump.setDisable(false);
			
		}
		
	}
}
