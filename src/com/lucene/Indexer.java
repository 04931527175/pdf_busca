package com.lucene;



import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

//classe é usada para indexar os dados brutos para que possamos torná-lo pesquisável usando a biblioteca Lucene. 
public class Indexer {
	private IndexWriter writer;
	@SuppressWarnings("deprecation")
	public Indexer(String indexDirectoryPath) throws IOException{
		// este diretório irá conter os índices 
		Directory indexDirectory =
				FSDirectory.open(new File(indexDirectoryPath));
		// cria o indexador 

		writer = new IndexWriter(indexDirectory,
				new StandardAnalyzer(Version.LUCENE_36),true,
				IndexWriter.MaxFieldLength.UNLIMITED);
	}
	public void close() throws CorruptIndexException, IOException{
		writer.close();
	}
	private Document getDocument(File file) throws IOException{
		Document document = new Document();
		// conteúdo do arquivo de índice 
		Field contentField = new Field(LuceneConstants.CONTENTS,
				new FileReader(file));
		// nome do arquivo de índice 
		Field fileNameField = new Field(LuceneConstants.FILE_NAME,
				file.getName(),
				Field.Store.YES,Field.Index.NOT_ANALYZED);
		// caminho do arquivo de índice 
		Field filePathField = new Field(LuceneConstants.FILE_PATH,
				file.getCanonicalPath(),
				Field.Store.YES,Field.Index.NOT_ANALYZED);
		document.add(contentField);
		document.add(fileNameField);
		document.add(filePathField);
		return document;
	}
	private void indexFile(File file) throws IOException{
		System.out.println("Indexing "+file.getCanonicalPath());
		Document document = getDocument(file);
		writer.addDocument(document);
	}
	public int createIndex(String dataDirPath, FileFilter filter)
			throws IOException{
		// pega todos os arquivos no diretório de dados 
		File[] files = new File(dataDirPath).listFiles();
		for (File file : files) {
			if(!file.isDirectory()
					&& !file.isHidden()
					&& file.exists()
					&& file.canRead()
					&& filter.accept(file)    //olhar
					){
				indexFile(file);
			}
		}
		return writer.numDocs();
	}
}
