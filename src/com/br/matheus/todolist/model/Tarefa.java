package com.br.matheus.todolist.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tarefa {

	private long id;
	private LocalDate dataCriacaoTf;// DATA DE CRIAÇÃO DA TAREFA
	private LocalDate dataLimiteTf;// PRAZO, DATA LIMITE PARA A TAREFA SER CONCLUIDA
	private LocalDate dataFinalizadaTf;// DATA PARA FINALIZAR A TAREFA
	private String descricaoDaTf;// COMPLEMENTOS, NOME, DATA
	private String comentarioTf;
	private StatusTarefa status;// ENUMERAÇÃO
	private Importancia classifImportancia;
	
	
	// GETs E SETs
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getDataCriacaoTf() {
		return dataCriacaoTf;
	}
	public void setDataCriacaoTf(LocalDate dataCriacaoTf) {
		this.dataCriacaoTf = dataCriacaoTf;
	}
	public LocalDate getDataLimiteTf() {
		return dataLimiteTf;
	}
	public void setDataLimiteTf(LocalDate dataLimiteTf) {
		this.dataLimiteTf = dataLimiteTf;
	}
	public LocalDate getDataFinalizadaTf() {
		return dataFinalizadaTf;
	}
	public void setDataFinalizadaTf(LocalDate dataFinalizadaTf) {
		this.dataFinalizadaTf = dataFinalizadaTf;
	}
	public String getDescricaoDaTf() {
		return descricaoDaTf;
	}
	public void setDescricaoDaTf(String descricaoDaTf) {
		this.descricaoDaTf = descricaoDaTf;
	}
	public String getComentarioTf() {
		return comentarioTf;
	}
	public void setComentarioTf(String comentarioTf) {
		this.comentarioTf = comentarioTf;
	}
	public StatusTarefa getStatus() {
		return status;
	}
	public void setStatus(StatusTarefa status) {
		this.status = status;
	}
	public Importancia getClassifImportancia() {
		return classifImportancia;
	}
	public void setClassifImportancia(Importancia classifImportancia) {
		 this.classifImportancia = classifImportancia;
	}
	
	
	// MÉTODO "PREPARA A TAREFA PARA SER GRAVADA"
	public String formatToSave() {
		// "CONTRUIR UMA STRING(StringBuilder)"
		StringBuilder builder = new StringBuilder();
		//FORMATADOR DE DATA, DEIXANDO NA ORDEM DE DIA/MÊS/ANO
		DateTimeFormatter fmt =DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		// CONSTRUINDO A STRING ATRAVÉS DO BUILDER
		builder.append(this.getId()+";");
		builder.append(this.getDataCriacaoTf().format(fmt)+";");//FORMATANDO A DATA COM, format(fmt)
		builder.append(this.getDataLimiteTf().format(fmt)+";");
		if(this.getDataFinalizadaTf() != null) {
			builder.append(this.getDataCriacaoTf().format(fmt));
		}
		builder.append(";");// TENDO A DATA OU NÃO TERÁ ;, POR ISSO ESTÁ FORA DO IF
		
		builder.append(this.getDescricaoDaTf()+";");
		builder.append(this.getStatus().ordinal()+";");//GUARDANDO PELA POSIÇÃO DA ENUM
		builder.append(this.getClassifImportancia().ordinal()+";");
		builder.append(this.getComentarioTf()+"\n");//\n PARA A PRÓXIMA TAREFA COMEÇAR NA LINHA DE BAIXO
		
		return builder.toString();
	}
}



