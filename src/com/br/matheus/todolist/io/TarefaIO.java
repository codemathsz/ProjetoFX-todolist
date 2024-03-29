package com.br.matheus.todolist.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.br.matheus.todolist.model.Importancia;
import com.br.matheus.todolist.model.StatusTarefa;
import com.br.matheus.todolist.model.Tarefa;

public class TarefaIO {
	// static POR CAUSA DOS METODOS QUE S�O static E FINAL POIS N�O VARIA, �
	// CONSTANTE
	private static final String FOLDER = System.getProperty("user.home") + "/FX_TodoList";// DA O CAMINHO DA PASTA DO
																							// �SUARIO QUE EST�
																							// CONECTADO NA HORA
																							// EXECU��O(" USER.HOME")
	private static final String FILE_IDS = FOLDER + "/id.csv";// CSV SEPARA AS INFORMA��ES EM ;
	private static final String FILE_TAREFA = FOLDER + "/tarefa.csv";

	// M�TODO PARA CRIAR O ARQUIVO
	public static void createFile() {
		try {
			// PARA MANIPULAR UM ARQUIVO OU PASTA no HD CRIAMOS UM file
			// CRIANDO UMA V�RIAVEL DO TIPO FILE
			File pasta = new File(FOLDER);
			File arqIDs = new File(FILE_IDS);
			File arqTarefas = new File(FILE_TAREFA);

			// pasta.exists(), EST� FALANDO QUE EXISTE, O ! COLOCA UM N�O, SE N�O
			if (!pasta.exists()) {
				// CRIAR PASTAS E OS ARQUIVOS
				pasta.mkdir();// CRIAR PASTA
				arqIDs.createNewFile();
				arqTarefas.createNewFile();							

				FileWriter writer = new FileWriter(arqIDs);// CRIANDO UM ESCRITOR DE ARQUIVOS
				writer.write("1");// ESCREVENDO NO ARQUIVO
				writer.close();// FECHANDO O ARQUIVO DEPOIS DE ESCREVER
			}

		} catch (Exception e) {
			e.printStackTrace();// IMPRIMIR O ERRO NO CONSOLE, E COM O JOptionPane N�O SERIA O CORRETO POIS ESSA
								// CLASSE N�O DEVE TER CONTATO COM A JANELA
		}
	}

	// M�TODO QUE INSERE UMA TAREFA         throws MANDA PARA "CIMA" O TIPO DE EXCEPTION
	public static void insert(Tarefa tarefa) throws FileNotFoundException, IOException {
		File arqTarefas = new File(FILE_TAREFA);
		File arqIDs = new File(FILE_IDS);

		// LER O �LTIMO ID NO FILE_IDS
		Scanner leitor = new Scanner(arqIDs);// USAMOS THOROWS(MANDAR O ERRO PARA QUEM CHAMOU, REPASSANDO UMA EXCEPTION)
												// POE QUE ESTAMOS MEXENDO COM ARQUIVO E O JAVA N�O DAIXA EXECUTAR SEM
												// UM TRY CATCH OU THROWS
		tarefa.setId(leitor.nextLong());
		leitor.close();

		// GRAVA A TAREFA NO ARQUIVO
		FileWriter writer = new FileWriter(arqTarefas, true);
		writer.append(tarefa.formatToSave());// ESCREVENDO/SALVANDO O AQUIVO,A TAREFA
		writer.close();

		// GRAVA O NOVO "PR�XIMO ID" NO ARQUIVO DE IDS
		writer = new FileWriter(arqIDs);
		writer.write((tarefa.getId() + 1) + "");// CONCATENANDO A STRING, PARA VIRAR UMA STRING, POIS SE COLOCASSE O INT
												// ELE IRIA ASSOCIAR A TABELA ASCII
												// OUTRO JEITO DE FAZER
												// Long proxId = tarefa.getId() + 1;
												// writer = new FileWrite(arqIds);
												// writer.write(proxId+"");
		writer.close();

	}

	// CRIANDO O M�TODO QUE LER AS TAREFAS "MANDANDO O ERRO PRA CIMA"
	public static List<Tarefa> readTarefas() throws IOException {// List <"TEM QUE SER DE UMA CLASSE, E PARA USAR OS
																	// TIPOS PRIMITIVOS TEM QUE CHAMAR A CLASSE QUE
																	// FORAM CRIADOS PARA ELES EX: INT= Integer">
		// LENDO UM ARQUIVO TEXTO

		File arqTarefas = new File(FILE_TAREFA);
		List<Tarefa> tarefas = new ArrayList<>();// � OPCIONAL COLOCAR Array< AQUI!> O <Tarefa> POR QUE J� EST� NO List
		FileReader reader = new FileReader(arqTarefas);// FileReader N�O L� UMA LINHA INTEIRA, � LIMITADO
		BufferedReader buff = new BufferedReader(reader);// O BufferedReader CONSEGUE LER O ARQUIVO MELHOR QUE O
															// FileReader

		String linha;// CADA UMA DAS LINHAS DO DOC
		// COLOCAR DENTRO DELA A LINHA QUE FOI LIDA PELO BUFFEREADER linha =
		// buff.readLine();

		while ((linha = buff.readLine()) != null) {
			// TRANSFORMO A LINHA EM UM VETOR
			String[] vetor = linha.split(";");// o M�TODO SPLIT, CRIOU O VETOR DE STRING

			// TEMOS QUE CRIAR UMA TEREFA
			Tarefa t = new Tarefa();

			System.out.println(linha);
			t.setId(Long.parseLong(vetor[0]));// Long.parseLong, CONVERTENDO O VETOR STRING PARA LONG, POR QUE O Id � DO
												// TIPO long

			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");// fmt FORMATADOR DE DATA PARA STRING

			t.setDataCriacaoTf(LocalDate.parse(vetor[1], fmt));

			t.setDataLimiteTf(LocalDate.parse(vetor[2], fmt));

			if (!vetor[3].isEmpty()) {
				t.setDataFinalizadaTf(LocalDate.parse(vetor[3], fmt));
			}

			t.setDescricaoDaTf(vetor[4]);

			int indStatus = Integer.parseInt(vetor[5]);
			t.setStatus(StatusTarefa.values()[indStatus]);

			t.setClassifImportancia(Importancia.values()[Integer.parseInt(vetor[6])]);

			t.setComentarioTf(vetor[7]);

			tarefas.add(t);

		}

		buff.close();
		reader.close();
		// 	ORDENANDO AS TAREFAS
		Collections.sort(tarefas);
		//System.out.println(tarefas.size());
		return tarefas;

		// FileReader EST� CONECTANDO o BufferedReader AO ARQUIVO, o Buff � MELHOR QUE o
		// FileReader
	}

	public static void atualizarTarefas(List<Tarefa> tarefas) throws IOException {

		File arqTarefas = new File(FILE_TAREFA);
		FileWriter writer = new FileWriter(arqTarefas);
		for (Tarefa t : tarefas) {
			writer.append(t.formatToSave());
		}
		writer.close();
	}
	
	
	
	
	public static void exportHtml(List<Tarefa> lista, File arquivo) throws IOException {
		// PARA ESCREVER NO ARQUIVO
		FileWriter writer = new FileWriter(arquivo);
		// PEGANDO A LISTA DE TAREFAS E REESCREVENDO EM HTML
		writer.append("<!DOCTYPE html>\n");
		writer.append("<html>\n");
		writer.append("<body>\n");
		
		writer.append("<h1>Lista de Tarefas</h1>\n");
		writer.append("<ul>\n");
		for (Tarefa tarefa : lista) {
			writer.append("<li>\n");
				writer.append(tarefa.getDescricaoDaTf() +" - "+tarefa.getDataLimiteTf() +" - "+tarefa.getStatus());
			writer.append("</li>\n");
		}
		writer.append("</ul>\n");
		writer.append("</body>\n");
		writer.append("</html>\n");
		writer.close();
	}
}
