package br.com.formatter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Formatador de texto, dados localização do arquivo
 * @author Massao
 */
public class TextFormatter {

	/**
	 * Metodo de formatação dado tamanho de linha, arquivo de origem e arquivo de destino
	 * @param lineLength
	 * @param originPath
	 * @param destinyFile
	 * @throws IOException 
	 */
	public void formartText(final int lineLength, String originPath, String destinyFile ) throws IOException {
		StringBuilder input = new StringBuilder();
		try {
			originPath = originPath.replaceAll("/", File.pathSeparator);
			destinyFile = destinyFile.replaceAll("/", File.pathSeparator);
			
			Path path = Paths.get(originPath);
			List<String> lines = Files.readAllLines(path, Charset.forName("ISO-8859-1"));
			lines.forEach(line -> input.append(line));

			System.out.println("Formatando texto");
			StringBuilder output = putLimitOnLine(lineLength, input);

			if(!destinyFile.endsWith("txt"))
				destinyFile = destinyFile + ".txt";

			Path destinyPath = Paths.get(destinyFile);
			Files.write(destinyPath, justifyText(lineLength, output.toString()).toString().getBytes());

			System.out.println("Salvo");
		} catch (IOException e) {
			Logger.getLogger(TextFormatter.class.getSimpleName()).log(Level.SEVERE, "Erro ao carregar/salvar arquivo", e);
			throw e;
		} catch(Exception e) {
			Logger.getLogger(TextFormatter.class.getSimpleName()).log(Level.SEVERE, "Erro na formatação do arquivo", e);
			throw e;
		}
	}

	/**
	 * formata a linha, respeitando o limite de caracteres inserida pelo usuário
	 * @param maxLineLength
	 * @param input
	 * @return
	 */
	public StringBuilder putLimitOnLine(final int maxLineLength, final StringBuilder input) {
		StringTokenizer stringTokenizer = new StringTokenizer(input.toString(), " ");
		StringBuilder output = new StringBuilder();

		int lineLength = 0;

		while(stringTokenizer.hasMoreTokens()) {
			String word = stringTokenizer.nextToken();

			//Caso a palavra seje maior que a linha
			while(word.length() > maxLineLength) {
				output.append(word.substring(0, maxLineLength - lineLength) + "\n");
				word = word.substring(maxLineLength-lineLength);
				lineLength=0;
			}

			if(lineLength + word.length() > maxLineLength) {
				output.append("\n");
				lineLength=0;
			}
			output.append(word + " ");
			lineLength+=word.length() + 1;
		}
		return output;
	}

	/**
	 * Justifica o texto
	 * @param maxLineLength
	 * @param text
	 * @return
	 */
	public StringBuilder justifyText(final int maxLineLength, final String text) {
		StringBuilder output = new StringBuilder();
		try {
			String[] numLines = text.split("\n");

			for(String line : numLines) {
				if(line.length() < maxLineLength) {
					int nbackspaceToAdd = maxLineLength - line.length();
					String[] words =  line.split(" ");
					int nWords = words.length;

					if(nWords<=0)
						continue;

					Map<Integer, String> mapWords = new HashMap<>();

					for(int indexWord =0; indexWord<nWords; indexWord++) {
						mapWords.put(indexWord, words[indexWord]);
					}

					while(nbackspaceToAdd > 0) {
						if(nWords == 1) {
							String newWord = null;
							String word = mapWords.get(0);
							newWord = " " + word;
							mapWords.put(0, newWord);
							nbackspaceToAdd--;
						}else {
							for(int indexWord=1; indexWord<nWords; indexWord++) {
								if(nbackspaceToAdd==0)
									break;

								String newWord = null;
								String word = mapWords.get(indexWord);
								newWord = " " + word;
								mapWords.put(indexWord, newWord);
								nbackspaceToAdd--;
							}
						}
					}
					
					for(int indexWord=0; indexWord<=mapWords.values().size(); indexWord++) {
						if(indexWord==mapWords.values().size())
							output.append(mapWords.get(indexWord));
						else
							output.append(mapWords.get(indexWord) + " ");
						output.append("\n");
					}
				}else {
					output.append(line + "\n");
				}
			}
		}catch(Exception e) {
			Logger.getLogger(TextFormatter.class.getSimpleName()).log(Level.SEVERE, "Erro na justificação do textoo", e);
		}
		return output;
	}
}