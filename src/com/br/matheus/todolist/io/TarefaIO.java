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
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.br.matheus.todolist.model.Importancia;
import com.br.matheus.todolist.model.StatusTarefa;
import com.br.matheus.todolist.model.Tarefa;

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
	}									// throws MANDA PARA "CIMA" O TIPO DE EXCEPTION
	public static void insert(Tarefa tarefa) throws FileNotFoundException ,IOException {
		File arqTarefas = new File(FILE_TAREFA);
		File arqIDs = new File(FILE_IDS);
		
		// LER O ÚLTIMO ID NO FILE_IDS
		Scanner leitor = new Scanner(arqIDs);// USAMOS THOROWS(MANDAR O ERRO PARA QUEM CHAMOU, REPASSANDO UMA EXCEPTION) POE QUE ESTAMOS MEXENDO COM ARQUIVO E O JAVA NÃO DAIXA EXECUTAR SEM UM TRY CATCH OU THROWS
		tarefa.setId(leitor.nextLong());
		leitor.close();
		// GRAVA A TAREFA NO ARQUIVO
		FileWriter writer = new FileWriter(arqTarefas, true);
		writer.append(tarefa.formatToSave());//ESCREVENDO/SALVANDO O AQUIVO,A TAREFA
		writer.close();
		// GRAVA O NOVO "PRÓXIMO ID" NO ARQUIVO DE  IDS
		writer = new FileWriter(arqIDs);
		writer.write((tarefa.getId()+1)+"");// CONCATENANDO A STRING, PARA VIRAR UMA STRING, POIS SE COLOCASSE O INT ELE IRIA ASSOCIAR A TABELA ASCII
											 // OUTRO JEITO DE FAZER
											// Long proxId = tarefa.getId() + 1;
										   //  writer = new FileWrite(arqIds);
										  //   writer.write(proxId+"");	
		writer.close();
		
		
	}
	// CRIANDO O MÉTODO QUE LER AS TAREFAS			"MANDANDO O ERRO PRA CIMA"
	public static List<Tarefa> readTarefas() throws IOException{// List <"TEM QUE SER DE UMA CLASSE, E PARA USAR OS TIPOS PRIMITIVOS TEM QUE CHAMAR A CLASSE QUE FORAM CRIADOS PARA ELES EX: INT= Integer">
		// LENDO UM ARQUIVO TEXTO
		
		File arqTarefas = new File(FILE_TAREFA);
		List<Tarefa> tarefas = new ArrayList<>();// É OPCIONAL COLOCAR Array< AQUI!> O <Tarefa>  POR QUE JÁ ESTÁ NO List
		FileReader reader = new  FileReader(arqTarefas);// FileReader NÃO LÊ UMA LINHA INTEIRA, É LIMITADO
		BufferedReader buff = new BufferedReader(reader);// O BufferedReader CONSEGUE LER O ARQUIVO MELHOR QUE O FileReader
		
		String linha;// CADA UMA DAS LINHAS DO DOC
		// COLOCAR DENTRO DELA A LINHA QUE FOI LIDA PELO BUFFEREADER linha = buff.readLine();
		while((linha = buff.readLine()) != null) {
			// TRANSFORMO A LINHA EM UM VETOR
			String[] vetor = linha.split(";");// o MÉTODO SPLIT, CRIOU O VETOR DE STRING
			
			// TEMOS QUE CRIAR UMA TEREFA
			Tarefa t = new Tarefa();
			
			System.out.println(linha);
			t.setId(Long.parseLong(vetor[0]));// Long.parseLong, CONVERTENDO O VETOR STRING PARA LONG, POR QUE O Id É DO TIPO long
			
			DateTimeFormatter fmt =DateTimeFormatter.ofPattern("dd/MM/yyyy");// fmt FORMATADOR DE DATA PARA STRING
			
			t.setDataCriacaoTf(LocalDate.parse(vetor[1], fmt));
			
			t.setDataLimiteTf(LocalDate.parse(vetor[2], fmt));
			
			if(!vetor[3].isEmpty()) {
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
		System.out.println(tarefas.size());
		return tarefas;
		
		// FileReader ESTÁ CONECTANDO o BufferedReader AO ARQUIVO, o Buff  é MELHOR QUE o FileReader
	}
}
 




