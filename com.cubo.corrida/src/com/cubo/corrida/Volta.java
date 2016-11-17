package com.cubo.corrida;

import java.time.LocalTime;

public class Volta {
	//Tempo levado para concluir a volta
	private Long duracao;
	//Momento de log do fim da volta
	private LocalTime fimDaVolta;
	
	public Volta(Long duracao, LocalTime momento) {
		this.duracao = duracao;
		this.fimDaVolta = momento;
	}

	public LocalTime getFimDaVolta() {
		return fimDaVolta;
	}

	public Long getDuracao() {
		return duracao;
	}

	@Override
	public String toString() {
		return "Volta [duracao=" + duracao + "]";
	}
}
