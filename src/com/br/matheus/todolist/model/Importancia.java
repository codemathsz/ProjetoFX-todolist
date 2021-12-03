package com.br.matheus.todolist.model;

public enum Importancia {
	URGENTE("Urgente"), IMPORTANTE("Importante"), DE_BOA("De boa");
	
	String descricaoImpor;
	
	private Importancia( String desc) {
		this.descricaoImpor = desc;
	}
	@Override
	public String toString() {
		
		return descricaoImpor;
	}
}
