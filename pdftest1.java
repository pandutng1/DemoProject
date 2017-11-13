package com.test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class pdftest1 {
static String pdftoText(String fileName) throws IOException {
PDFParser parser;
String parsedText = null;
//PDFTextStripper pdfStripper = null;
PDFTextStripper pdfStripper = new PDFTextStripper(“UTF-8”);
PDDocument pdDoc = null;
COSDocument cosDoc = null;
File file = new File(fileName);
if (!file.isFile()) {
System.out.println(“File ” + fileName + ” does not exist.”);
return null;
}
try {
parser = new PDFParser(new FileInputStream(file));
} catch (IOException e) {
System.out.println(“Unable to open PDF Parser”+ e.getMessage());
return null;
}
try {
parser.parse();
cosDoc = parser.getDocument();
pdfStripper = new PDFTextStripper();
pdDoc = new PDDocument(cosDoc);
pdfStripper.setStartPage(1);
pdfStripper.setEndPage(5);
parsedText = pdfStripper.getText(pdDoc);
} catch (Exception e) {
e.printStackTrace();
System.out.println(“An exception occured in parsing the PDF Document.”+ e.getMessage());
} finally {
try {
if (cosDoc != null)
cosDoc.close();
if (pdDoc != null)
pdDoc.close();
} catch (Exception e) {
e.printStackTrace();
}
}
return parsedText;
}
public static void main(String args[]) throws IOException{
System.out.println(pdftoText(“C:/Users/AB850924/Desktop/AbdulData/PDFData/Hindi.pdf”));
}
}