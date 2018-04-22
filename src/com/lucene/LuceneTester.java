package com.lucene;



import java.io.File;

//CAMINHO PARA PASTA \\smspf0006\Departamentos


import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;



//Essa classe é usada para testar a indexação e busca capacidade da biblioteca Lucene. 
public class LuceneTester {
	String indexDir = "C:\\Users\\cleyton.goulart\\Documents\\Testes\\teste";
	String dataDir = "C:\\Users\\cleyton.goulart\\Documents\\Testes\\A";
	Indexer indexer;
	Searcher searcher;
	
	//
	public static void main(String[] args) {
		LuceneTester tester;
		try {
			
			tester = new LuceneTester();
			tester.createIndex();
			//BUSCA POR PALAVRAS CHAVES 
			tester.search("documento");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 
	   
	    
	}
	private void createIndex() throws IOException{
		indexer = new Indexer(indexDir);
		int numIndexed;
		long startTime = System.currentTimeMillis();
		numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
		long endTime = System.currentTimeMillis();
		indexer.close();
		System.out.println(numIndexed+" Arquivo indexado, tempo gasto:\r" + 
				" "
				+(endTime-startTime)+" ms");
	    
	}
	private void search(String searchQuery) throws IOException, ParseException{
		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search(searchQuery);
		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits +
				"Documentos encontrados. Tempo :\r" + 
				"" + (endTime - startTime));
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("File: "
					+ doc.get(LuceneConstants.FILE_PATH));
		}
		searcher.close();
	}
}
