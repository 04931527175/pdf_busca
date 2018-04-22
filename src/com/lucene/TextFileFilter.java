package com.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;


// classe é usada como uma. TXT filtro de arquivo. 
public class TextFileFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		
		return pathname.getName().endsWith(".pdf") ||pathname.getName().endsWith(".PDF") 
			   || pathname.getName().toLowerCase().endsWith(".txt")
			   || pathname.getName().toLowerCase().endsWith(".doc");
		

	}

}
