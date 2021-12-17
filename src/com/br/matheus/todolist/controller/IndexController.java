package com.br.matheus.todolist.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.br.matheus.todolist.io.TarefaIO;
import com.br.matheus.todolist.model.Importancia;
import com.br.matheus.todolist.model.StatusTarefa;
import com.br.matheus.todolist.model.Tarefa;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class IndexController implements Initializable, ChangeListener<Tarefa>{

	@FXML
	private MenuBar menu;

	@FXML
	private DatePicker dateLimitBox;

	@FXML
	private TextField boxNameTf, statusTf, viewCodeField;

	@FXML
	private TextArea boxComent;
	
	
	// EXPORTS
	@FXML
	private MenuItem exportConcluida;
	@FXML
	private MenuItem exportAdiada;
	@FXML 
	private MenuItem exportAberta;
	
	
	
	@FXML
	private Button btnSave, btnList, btnCalendar, btnClean, btnDump;// Dump = JOGAR FORA
	@FXML
	private Label labelDate;
	// VARIÁVEL PARA GUARDAR A TAREFA
	private Tarefa tarefa;
	// VARIÁVEL PARA GUARDAR A LISTA DE TAREFAS
	private List<Tarefa> tarefas;
	
	@FXML
	private MenuItem menuItemExportar;
	@FXML
	private MenuItem menuItemSair;
	@FXML
	private MenuItem menuItemAbout;
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
		if(tarefa != null) {
			// PARA NÃO ADIAR SE A TAREFA NÃO ESTIVER SELECIONADA, O BOTÃO JÁ ESTÁ DESHABILITADO, MAIS SÓ POR SEGURANÇA
			int days = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantos dias você deseja Adiar", "Informe quantos dias", JOptionPane.QUESTION_MESSAGE));
			LocalDate newDate = tarefa.getDataLimiteTf().plusDays(days);//  CRIANDO UMA NOVA DATA DE CONCLUSÃO
			
			//if(newDate.equals(tarefa.getDataLimiteTf())) {}
			
			tarefa.setDataLimiteTf(newDate);
			tarefa.setStatus(StatusTarefa.ADIADA);
			try {
				TarefaIO.atualizarTarefas(tarefas);
				// AVISAR AO USUÁRIO QUE A TAREFA FOI ADIADA COM SUCESSO | E A SUA NOVA DATA LIMITE
				DateTimeFormatter fmt =DateTimeFormatter.ofPattern("dd/MM/yyyy");
				JOptionPane.showMessageDialog(null, "Tarefa adiada com sucesso.\n nova data:"+newDate.format(fmt));
				carregarTarefas();
				clearCamp();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao atualizar as tarefas", "Erro", 0);
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void btnCleanClick() {
		clearCamp();
	}

	
	@FXML
	public void btnDumpClick() {
		
		if(tarefa != null) {
			// O JOptionPAne DEVOLVE UM VALOR INTEIRO DO BOTÃO QUE FOI CRIADO
			int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir a tarefa "+tarefa.getId()+"?","Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);//  VAI APARECER UMA CAIXINHA PERGUNTANDO SER QUER EXCLUIR
			if(resposta == 0) {
				tarefas.remove(tarefa);// REMOVE SÓ FUNCIONA SE O OBJ(TAREFA) FOR IGUAL AO O OUTRO(ID DA TAREFA), DEU CERTO POR QUE É O MSM OBJ, MÉTODO CHANGED
				
				try {
					TarefaIO.atualizarTarefas(tarefas);
					clearCamp();
					carregarTarefas();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "ocorreu um erro ao excluir a tarefa", "Erro ao excluir", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				
			}
			 

		}
	}
	

	@FXML
	public void btnListClick() {// MÉTODO CONCLUIR
		if(tarefa != null) {
			
			tarefa.setStatus(StatusTarefa.CONCLUIDA);
			tarefa.setDataFinalizadaTf(LocalDate.now());
			
			DateTimeFormatter fmt =DateTimeFormatter.ofPattern("dd/MM/yyyy");
			JOptionPane.showMessageDialog(null, "Tarefa concluída!!\n Data de conclusão:"+tarefa.getDataFinalizadaTf().format(fmt));
			
			try {
				TarefaIO.atualizarTarefas(tarefas);
				// AVISAR AO USUÁRIO QUE A TAREFA FOI ADIADA COM SUCESSO | E A SUA NOVA DATA LIMITE
				carregarTarefas();
				clearCamp();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao concluir a tarefa ", "Erro ao concluir", 0);
				e.printStackTrace();
			}
		}
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
		
		
		tvTarefa.setRowFactory(call -> new TableRow<Tarefa>() {
			@Override
			public void updateItem(Tarefa item, boolean empty) {
				super.updateItem(item, empty);
				if(item == null) {
					setStyle("");
				}else if(item.getStatus() == StatusTarefa.CONCLUIDA) {
					setStyle("-fx-background-color:#0f0");
					
				}else if(item.getDataLimiteTf().isBefore(LocalDate.now())) {
					// SE A DATA LIMITE ESTIVER ATRASADA
					setStyle("-fx-background-color: tomato");
					
				}else if(item.getStatus() == StatusTarefa.ADIADA) {
					
				}else {
					setStyle("-fx-background-color: #CFF0CC");
					
				}
			}
		});
		
		//tvTarefa.setRowFactory(new Callback<TableView<Tarefa>, TableRow<Tarefa>>() {
			//@Override
			//public TableRow<Tarefa> call(TableView<Tarefa> param) {
				// TODO Auto-generated method stub
				//return TableRow<Tarefa>(){
					//@Override
					//public void updateIndex(int i) {
						// TODO Auto-generated method stub
						//super.updateIndex(i);
					//}
				//}
			//}
		//})
		
		
		
		viewCodeField.setDisable(true);
		
		
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

		} else if (boxNameTf.getLength() <= 3) {
			
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
				
				// VERIFICA SE É UMA TAREFA NOVA OU JÁ EXSTENTE, SE FOR EXISTENTE VAI APENAS RESCREVER A TAREFA E NÃO CRIAR UMA NOVA 
				if(tarefa == null) {
					// INSTACIANDO A TAREFA
					tarefa = new Tarefa();
					tarefa.setDataCriacaoTf(LocalDate.now());
					tarefa.setStatus(StatusTarefa.ABERTA);// CLASSIFICANDO O STATUS DA TAREFA COMO ABERTA
				}
				
				
				// POPULAR TAREFA
				tarefa.setDataLimiteTf(dateLimitBox.getValue());
				tarefa.setDescricaoDaTf(boxNameTf.getText());
				tarefa.setComentarioTf(boxComent.getText());
				tarefa.setClassifImportancia(choiceImportancia.getValue());
				// SALVAR NO BANCO DE DADOS
				
				
				try {// NÃO TRATAMOS NA CLASSE TAREFAIO, USAMOS O THROWS, ENTÃO ESTAMOS TRATANDO AQUI
					// DECIDE ENTRE INSERIR OU ATUALIZAR A TAREFA
					if(tarefa.getId() == 0) {// UMA TAREFA NOVA AINDA NÃO TEM O SEU ID NO BANCO DE DADOS ENTÃO PASSAMOS COMO PARÂMNETRO O ID
						TarefaIO.insert(tarefa);
					}else {
						TarefaIO.atualizarTarefas(tarefas);
					}
					carregarTarefas();
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
		// CAIXAS DE 'TEXTO'
		tarefa = null;// 'MATANDO' O OBJ TAREFA PARA A PROXIMA TAREFA
		boxNameTf.clear();
		dateLimitBox.setValue(null);
		statusTf.clear();
		boxComent.clear();
		choiceImportancia.setValue(null);// LIMPANDO O "VALOR"
		// ESTAVA LIMPANDO OS IDs NO BANCO DE DADOS, E DANDO ERRO QUANDO INSERIA UMA NOVA TAREFA
		viewCodeField.clear();
		
		
		// BOTÕES
		btnList.setDisable(true);
		btnCalendar.setDisable(true);
		btnDump.setDisable(false);
		
		// DESHABILITANDO CAIXAS DE 'TEXTOS' E BOTÕES
		dateLimitBox.setDisable(false);
		boxNameTf.setEditable(true);
		boxComent.setEditable(true);
		choiceImportancia.setDisable(false);
		btnSave.setDisable(false);
		// TIRANDO A BORDA E TEXTPROMPT DAS CAIXAS
		boxNameTf.setStyle("-fx-border-color: none");
		boxNameTf.setPromptText("");
		dateLimitBox.setStyle("-fx-background-color:none");
		dateLimitBox.setPromptText("");
		boxComent.setStyle("-fx-background-color:none");
		boxComent.setPromptText("");
		
		
		labelDate.setText("Data de conclusão:");
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
			
			//  MOSTRANDO O CÓDIGO NA TABELA
			viewCodeField.setText(tarefa.getId()+"");
			viewCodeField.setDisable(true);
			viewCodeField.setOpacity(.8);
			
			if(tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
				boxNameTf.setEditable(false);
				choiceImportancia.setDisable(true);
				choiceImportancia.setOpacity(.8);
				
				btnSave.setDisable(true);
				boxComent.setEditable(false);
				btnList.setDisable(true);
				btnCalendar.setDisable(true);
				btnCalendar.setOpacity(.8);
				
				labelDate.setText("Data de conclusão:");
				dateLimitBox.setValue(tarefa.getDataFinalizadaTf());
			}else {
				boxNameTf.setEditable(true);
				choiceImportancia.setDisable(false);
				btnSave.setDisable(false);
				boxComent.setEditable(true);
				btnList.setDisable(false);
				btnCalendar.setDisable(false);
				
				labelDate.setText("Data para a realização:");
				dateLimitBox.setValue(tarefa.getDataCriacaoTf());
			}

			
		}
		
	}
	// MÉTODO QUE CRIA UM ARRAY DE TAREFA, E DEPOIS ELE REMOVE UM STATUS DE UMA TAREFA QUE SERÁ PASSADA QUANDO CHAMAR ELE
	private List<Tarefa> filtrar(StatusTarefa status){
		List<Tarefa> tarefasFiltradas = null;
		try {
			
			tarefasFiltradas = TarefaIO.readTarefas();
			tarefasFiltradas.removeIf(tarefa -> tarefa.getStatus() != status);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tarefasFiltradas;	
		
		
	}
	
	// MÉTODOS EXPORT
	@FXML
	public void exportConcluidaClick() {
		
		
			// CRIAR O TIPO DO ARQUIVO QUE NO CASO É HTML
			FileFilter filter = new FileNameExtensionFilter("Arquivos HTML", "html");
			
			//JFileChooser, CAIXINHA PARA SELECIONAR UM ARQUIVO
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			chooser.showSaveDialog(null);//showSaveDialog, PARA APARECER TANTO NO MODO OPEN OU SAVE( ABRIR UM ARQUIVO OU SALVAR), null PARA ABRIR NO MEIO DA TELA
			// VERIFICANDO SE AO ABRIR UM ARUIVO FOI ESCOLHIDO
			if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File arqSelecionado = chooser.getSelectedFile();
				arqSelecionado = new File(arqSelecionado+".html");
				try {
									
					
					TarefaIO.exportHtml(filtrar(StatusTarefa.CONCLUIDA), arqSelecionado);
					
					// TENTATIVAS
					//Desktop desktop = Desktop.getDesktop(); ABRI O AQUIVO, SE O ARQUIVO ESTIVER COMO PADRÃO PARA NÃO ABRIR NO CHROME ELE ABRE EM OUTRA COISA E COMO QUERIAMOS ABRIR NO CHROME COLOCAMOS O ÚLTIMO
					//desktop.open(arqSelecionado);
					//Runtime.getRuntime().exec("notepad "+arqSelecionado.getAbsolutePath());
					//Runtime.getRuntime().exec("start chrome "+arqSelecionado.getAbsolutePath()); 
					Runtime.getRuntime().exec("cmd /c start chrome "+arqSelecionado.getAbsolutePath());
					JOptionPane.showConfirmDialog(null, "Deseja Abrir o Arquivo? ", "Abrir", JOptionPane.YES_NO_CANCEL_OPTION);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"Erro ao exportar o arquivo","Erro", 0);
					e.printStackTrace();
				}
			}
		
	}
	@FXML
	public void exportAdiadaClick() {
		// CRIAR O TIPO DO ARQUIVO QUE NO CASO É HTML
					FileFilter filter = new FileNameExtensionFilter("Arquivos HTML", "html");
					
					//JFileChooser, CAIXINHA PARA SELECIONAR UM ARQUIVO
					JFileChooser chooser = new JFileChooser();
					chooser.setFileFilter(filter);
					chooser.showSaveDialog(null);//showSaveDialog, PARA APARECER TANTO NO MODO OPEN OU SAVE( ABRIR UM ARQUIVO OU SALVAR), null PARA ABRIR NO MEIO DA TELA
					// VERIFICANDO SE AO ABRIR UM ARUIVO FOI ESCOLHIDO
					if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						File arqSelecionado = chooser.getSelectedFile();
						arqSelecionado = new File(arqSelecionado+".html");
						try {
											
							
							TarefaIO.exportHtml(filtrar(StatusTarefa.ADIADA), arqSelecionado);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,"Erro ao exportar o arquivo","Erro", 0);
							e.printStackTrace();
						}
					}
	}
	@FXML
	public void exportAbertaClick() {
		// CRIAR O TIPO DO ARQUIVO QUE NO CASO É HTML
		FileFilter filter = new FileNameExtensionFilter("Arquivos HTML", "html");
		
		//JFileChooser, CAIXINHA PARA SELECIONAR UM ARQUIVO
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		chooser.showSaveDialog(null);//showSaveDialog, PARA APARECER TANTO NO MODO OPEN OU SAVE( ABRIR UM ARQUIVO OU SALVAR), null PARA ABRIR NO MEIO DA TELA
		// VERIFICANDO SE AO ABRIR UM ARUIVO FOI ESCOLHIDO
		if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File arqSelecionado = chooser.getSelectedFile();
			arqSelecionado = new File(arqSelecionado+".html");
			try {
								
				
				TarefaIO.exportHtml(filtrar(StatusTarefa.ABERTA), arqSelecionado);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Erro ao exportar o arquivo","Erro", 0);
				e.printStackTrace();
			}
		}
	}
	
	
	
	@FXML
	public void menuItemSairClick() {
		
	}
	@FXML
	public void menuItemAboutClick() {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/br/matheus/todolist/view/About.fxml"));
			Scene scene = new Scene(root, 400,500);
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("About ToDoList");//TITULO
			stage.setResizable(false);//IMPEDIR QUE A JANELA  REDIMENCIONE
			stage.initStyle(StageStyle.UNDECORATED);//TITANDO A BORDA DA JANELA
			stage.initModality(Modality.APPLICATION_MODAL);// MODAL, É AS JANELAS QUE ABREM E SE VOCÊ NÃO CLICAR(FECHAR) NELA VOCÊ NÃO CONSEGUE CLICAR NO QUE ESTÁ ATRÁS DESTA JANELA, "ELA ROUBA A ATENÇÃO PRA ELA"
			stage.showAndWait();// ESPERAR, DEPOIS DE FECHAR A JANELA O CÓDIGO QUE ESTIVER DEPOIS DESSA LINHA SERÁ EXECUTADO, SE ESTIVER ABERTA NÃO SERÁ EXECUTADO
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
