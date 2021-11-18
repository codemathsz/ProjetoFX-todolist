package com.br.matheus.todolist.io;

import java.io.File;
import java.io.FileWriter;

public class TarefaIO {
	// static POR CAUSA DOS METODOS QUE SÃO static E FINAL POIS NÃO VARIA, É CONSTANTE
	private static final String FOLDER = System.getProperty("user.home")+"/FX_TodoList";// DA O CAMINHO DA PASTA DO ÚSUARIO QUE ESTÁ CONECTADO NA HORA EXECUÇÃO("	USER.HOME")
	private static final String FILE_IDS = FOLDER + "/id.csv";// CSV SEPARA AS INFORMAÇÕES EM ;
	private static final String FILE_TAREFA = FOLDER + "/tarefa.csv";
	
	// MÉTODO PARA CRIAR O ARQUIVO
	public static void createFile() {
		try {
			// PARA MANIPULAR UM ARQUIVO OU PASTA no HD CRIAMOS UM file
			// CRIANDO UMA VÁRIAVEL DO TIPO FILE
			File pasta = new File(FOLDER);
			File arqIDs = new File(FILE_IDS);
			File arqTarefas = new File(FILE_TAREFA);
			
			//pasta.exists(), ESTÁ FALANDO QUE EXISTE, O ! COLOCA UM NÃO, SE NÃO
			if(!pasta.exists()) {
				// CRIAR PASTAS E OS ARQUIVOS
				pasta.mkdir();//CRIAR PASTA
				arqIDs.createNewFile();
				arqTarefas.createNewFile();
				
				FileWriter writer = new FileWriter(arqIDs);// CRIANDO UM ESCRITOR DE ARQUIVOS
				writer.write("1");// ESCREVENDO NO ARQUIVO
				writer.close();// FECHANDO O ARQUIVO DEPOIS DE ESCREVER
			}
			
		} catch (Exception e) {
			e.printStackTrace();// IMPRIMIR O ERRO NO CONSOLE, E COM O JOptionPane NÃO SERIA O CORRETO POIS ESSA CLASSE NÃO DEVE TER CONTATO COM A JANELA
		}
	}
}
