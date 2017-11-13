package com.test;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ReadPDF {
	
	WebDriver driver;
	
	@BeforeClass
	public void setUp() {
		driver = new FirefoxDriver();
	}
	
	/**
	 * To verify PDF content in the pdf document
	 */
	@Test
	public void testVerifyPDFTextInBrowser() {
		
		driver.get("http://www.princexml.com/samples/invoice/invoicesample.pdf");
		//driver.findElement(By.linkText("PDF flyer")).click();
		Assert.assertTrue(verifyPDFContent(driver.getCurrentUrl(), "Organic Items"));
	}

	/**
	 * To verify pdf in the URL 
	 */
	@Test
	public void testVerifyPDFInURL() {
		driver.get("http://www.princexml.com/samples/invoice/invoicesample.pdf");
	//	driver.findElement(By.linkText("Organic Items")).click();
		String getURL = driver.getCurrentUrl();
		Assert.assertTrue(getURL.contains(".pdf"));
	}

	
public boolean verifyPDFContent(String strURL, String reqTextInPDF) {
		
		boolean flag = false;
		RandomAccessRead  input = null;
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = null;
		PDFParser parser = null;

		try {
			URL url = new URL(strURL);
			BufferedInputStream file = new BufferedInputStream(url.openStream());
			//PDFParser parser = new PDFParser(file);
			//parser = new PDFParser((RandomAccessRead) file);
			parser = new PDFParser(new RandomAccessFile(file,”r”));		
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();	
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(1);
			
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (MalformedURLException e2) {
			System.err.println("URL string could not be parsed "+e2.getMessage());
		} catch (IOException e) {
			System.err.println("Unable to open PDF Parser. " + e.getMessage());
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		
		System.out.println("+++++++++++++++++");
		System.out.println(parsedText);
		System.out.println("+++++++++++++++++");

		if(parsedText.contains(reqTextInPDF)) {
			flag=true;
		}
		
		return flag;
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}