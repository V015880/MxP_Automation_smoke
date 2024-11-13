package com.united;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.Assert;

public class PDFRead {
    public static void main(String[] args) {
        try {
            extractTextFromPDF("file:///C:/Users/V015880.v/Downloads/BAMWebDVX_UAL_BA78960D-97F3-46C8-8432-6C189E8DFFA6-BAMShiftHandoverReport-BAMShiftHandoverReport-d1db7196-a580-47e1-a090-4f2101aa82aa.pdf");
        } catch (Exception e) {
            e.printStackTrace(); // Better error handling could be added here
        }
    }

    public static void extractTextFromPDF(String url) throws Exception {
        URL pdfURL = new URL(url);
        URLConnection urlConnection = pdfURL.openConnection();
        urlConnection.addRequestProperty("User-Agent", "Chrome");

        try (InputStream ip = urlConnection.getInputStream();
             BufferedInputStream bf = new BufferedInputStream(ip);
             PDDocument pDDocument = PDDocument.load(bf)) {

            int pageCount = pDDocument.getNumberOfPages();
            System.out.println("Page Count: " + pageCount + "\n");

            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfFullText = pdfTextStripper.getText(pDDocument);
            System.out.println(pdfFullText);

            Assert.assertTrue(pdfFullText.contains("B787-MP-0215-0708-REQ-787"));
        } catch (IOException e) {
            throw new Exception("I/O error while extracting text from PDF: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("Failed to extract text from PDF: " + e.getMessage(), e);
        }
    }
}
