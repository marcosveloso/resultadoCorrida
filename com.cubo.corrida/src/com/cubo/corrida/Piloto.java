package com.cubo.corrida;

import java.time.LocalTime;
import java.util.HashMap;

public class Piloto implements Comparable<Piloto>{
	private String codigo;

	private String nome;
	private HashMap<Integer, Volta> voltas;
	private Long tempoTotal;

	public Piloto(String codigoPiloto, String nomePiloto) {
		this.codigo = codigoPiloto;
		this.nome = nomePiloto;
		this.voltas = new HashMap<>();
		this.tempoTotal = 0L;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public Long getTempoTotal() {
		return tempoTotal;
	}

	@Override
	public int compareTo(Piloto o) {
		return this.tempoTotal.compareTo(o.tempoTotal);
	}
	
	private void calculaTempoTotal() {
		this.tempoTotal = voltas.values().stream().mapToLong(Volta::getDuracao).sum();
	}
	
	public void adicionaVolta(Integer ordem, Long duracao, LocalTime fimDaVolta){
		this.voltas.put(ordem, new Volta(duracao, fimDaVolta));
		calculaTempoTotal();
	}
	
	public int numeroDeVoltas(){
		return this.voltas.size();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!( o instanceof Piloto))
			  return false;
		return this.codigo.equals(((Piloto)o).getCodigo());
	}

	@Override
	public String toString() {
		return " " + codigo + " - " + nome +
				"; \tvoltas completadas=" + voltas.size();
	}
}
