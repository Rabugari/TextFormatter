package br.com.formatter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Informado arquivo, o algoritmo irá formatar suas linhas e justifica-las de acordo com o número de caracteres inserido pelo usuário
 * @author Massao
 */
public class TextFormatter {

	public static void main(String[] args) {

		final int MAX_LENGTH = 40;

		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o local de origem do arquivo: ");
		String originPath = scanner.nextLine();
		
		System.out.println("Digite a quantidade de caracteres por linha:");
		String ncharacters = scanner.nextLine();
		
		while((ncharacters==null || "".equals(ncharacters))) {
			
		}
		
		formartText(MAX_LENGTH, originPath);	
	}

	private static void formartText(final int MAX_LENGTH, String originPath ) {
		StringBuilder input = new StringBuilder();

		if(originPath.contains("/"))
			originPath = originPath.replaceAll("/", "\\");

		try {
			Path path = Paths.get(originPath);
			List<String> lines = Files.readAllLines(path, Charset.forName("ISO-8859-1"));
			lines.forEach(line -> input.append(line));
			
			System.out.println("Formatando texto");
			StringBuilder output = formatLine(MAX_LENGTH, input);
			
			System.out.println("Digite o nome do novo arquivo formatado: ");
			String newFileJustified = scanner.nextLine();
			
			if(!newFileJustified.endsWith("txt"))
				newFileJustified = newFileJustified + ".txt";
			
			Path destinyPath = Paths.get(newFileJustified);
			Files.write(destinyPath, justifyLine(MAX_LENGTH, output.toString()).toString().getBytes());
			
			System.out.println("Salvo");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static StringBuilder formatLine(final int maxLineLength, StringBuilder input) {
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

	private static StringBuilder justifyLine(final int maxLineLength, String line) {
		StringBuilder output = new StringBuilder();
		String[] numLines = line.split("\n");

		for(String linha : numLines) {
			if(linha.length() < maxLineLength) {
				int nbackspaceToAdd = maxLineLength - linha.length();
				String[] words =  linha.split(" ");
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

				mapWords.values().forEach(word -> output.append(word + " "));
				output.append("\n");
			}else {
				output.append(linha + "\n");
			}
		}
		return output;
	}
}