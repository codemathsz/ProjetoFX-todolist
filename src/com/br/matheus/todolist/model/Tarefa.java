package com.br.matheus.todolist.model;

import java.time.LocalDate;

public class Tarefa {

	private long id;
	private LocalDate dataCriacaoTf;// DATA DE CRIAÇÃO DA TAREFA
	private LocalDate dataLimiteTf;// PRAZO, DATA LIMITE PARA A TAREFA SER CONCLUIDA
	private LocalDate dataFinalizadaTf;// DATA PARA FINALIZAR A TAREFA
	private String descricaoDaTf;// COMPLEMENTOS, NOME, DATA
	private String comentarioTf;
	private StatusTarefa status;// ENUMERAÇÃO
	private String classifImportancia;
	
	
	
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
	public String getClassifImportancia() {
		return classifImportancia;
	}
	public void setClassifImportancia(String classifImportancia) {
		this.classifImportancia = classifImportancia;
	}
	
	
	
}
