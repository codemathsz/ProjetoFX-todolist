package com.br.matheus.todolist.model;

public enum StatusTarefa {
	// CRIANDO ENUMERAÇÃO DOS STATUS POSSIVEIS DA TAREFA, TEM QUE SER TUDO MAIÚSCULO E COM AS REGRAS DE VARIÁVEIS
	ABERTA("Aberta"), CONCLUIDA("Concluída"), ADIADA("Adiada");

	String descricaoEnum;
	
	private StatusTarefa(String desc) {
		this.descricaoEnum = desc;
		
	}
	@Override
	public String toString() {
		
		return descricaoEnum;
	}
}
