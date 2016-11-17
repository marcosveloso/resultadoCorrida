package com.cubo.corrida;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.stream.Stream;

public class ResultadoDaCorrida {
	
	//Constantes de indice para cada campo na linha do arquivo
	// o indice 2 corresponde ao hifen que separa o codigo do nome do piloto
	private static final int ROTULO_LOG = 0; 
	private static final int COD_PILOTO = 1; 
	private static final int NOME_PILOTO = 3; 
	private static final int NR_VOLTA = 4; 
	private static final int TEMPO_VOLTA = 5; 
	private static final int VELOCIDADE = 6;
	private static LocalTime menorTempo = LocalTime.MAX;
	private static LocalTime maiorTempo = LocalTime.MIN;
	
	
	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Digite:\nResultadoDaCorrida <log-da-corrida>\n");
			System.exit(1);
		}

		String arquivo = args[0];
		
		//resultado final da corrida
		HashMap<String,Piloto> rankingFinal = new  HashMap<String,Piloto>();

		try {
			//adcicionar cada linha no ranking.
			Stream<String> linhas = Files.lines(Paths.get(arquivo));
			
			linhas.skip(1).forEach(s -> {
				try {
					interpretaLinha(s.split("\\s+"), rankingFinal);
				} catch (ParseException e) {
					System.out.println("Arquivo contém informações fora do formato esperado.");
				}
			} );
			
			int[] posicao = {1};
			System.out.println("POSICAO |  PILOTO\t|\tNumero de Voltas");
			// Imprime o ranking com a posicao
			rankingFinal.values().stream().sorted()
				.forEachOrdered( elem -> System.out.println("" + posicao[0]++  + ":\t" + elem));
			
			Duration tempoProva = Duration.between(menorTempo, maiorTempo);
			
			LocalTime temp = LocalTime.MIDNIGHT.plus(tempoProva);
			
			System.out.println("\n\nTempo total de Prova: \t" +
					DateTimeFormatter.ofPattern("m:ss.SSS").format(temp));
		    linhas.close();
		} catch (IOException e) {
			System.out.println("Erro ao ler do arquivo");
		}
		
	}

	private static void interpretaLinha(String[] linha, HashMap<String,Piloto> ranking)
			throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");  
		LocalTime logTime = LocalTime.parse(linha[ROTULO_LOG], formatter);
		
		
		String codigo = linha[COD_PILOTO];
		String nome = linha[NOME_PILOTO];
		Integer numeroDaVolta = Integer.parseInt(linha[NR_VOLTA]);

		String mascara = "PT" + linha[TEMPO_VOLTA].replace(":", "M") + "S";
		Duration tempoEmSegundos = Duration.parse(mascara);
		Long tempoDaVolta = tempoEmSegundos.toMillis();
		
		if(maiorTempo.compareTo(logTime) < 0) maiorTempo = logTime;
		if(menorTempo.compareTo(logTime) > 0) {
			// Inicio da corrida eh o menor tempo, subtraido do tempo da primeira volta completa.
			menorTempo = logTime.minus(tempoEmSegundos);
		}
		
		Piloto piloto = new Piloto(codigo, nome);
		piloto.adicionaVolta(numeroDaVolta, tempoDaVolta, logTime);
		if(ranking.containsValue(piloto)) {
			Piloto elemento = ranking.get(piloto.getCodigo());
			elemento.adicionaVolta(numeroDaVolta, tempoDaVolta, logTime);
		}
		else {
			ranking.put(piloto.getCodigo(), piloto);
		}
	}
}
