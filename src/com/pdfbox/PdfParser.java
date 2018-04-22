package com.pdfbox;


import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


public class PdfParser{
	
			    public static void main(String args[]) {
			        File file = new File("C:\\Users\\cleyton.goulart\\Documents\\Testes\\A\\pp.pdf");
			        try {
			            PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
			            parser.parse();
			            COSDocument cosDoc = parser.getDocument();
			            PDFTextStripper pdfStripper = new PDFTextStripper();
			            PDDocument pdDoc = new PDDocument(cosDoc);
			            for (int i = 1; i <= pdDoc.getNumberOfPages(); i++) {
			                pdfStripper.setStartPage(i);
			                pdfStripper.setEndPage(i);
			                String parsedText = pdfStripper.getText(pdDoc);
			                System.out.println("Página " + i + ": " + parsedText);
			            }
			        } catch (IOException e) {
			            // Tratar a exceção adequadamente.
			            e.printStackTrace();
			        }
			    }
			}