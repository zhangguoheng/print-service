package com.example.printservice.service;

import org.apache.fontbox.ttf.OpenTypeScript;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPrintable;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 打印机测试类（58mm）
 * 1、目标打印机必须设置为默认打印机
 * 2、打印页面的宽度和具体的打印机有关，一般为打印纸的宽度，需要配置成系统参数
 * 3、一个汉字的宽度大概是12点
 */
public class PrintTest {
    protected static final String DIAN_MING = "伊晨Bayby童装工厂";
    protected static final String MAIJIA_DIZHI = "A区三街01114号";
    protected static final String MAIJIA_DH = "18660665245";
    protected static final String MAIJIA_WX = "yichenbaby0";

    public static void main(String[] args) {
        try {
            String filePath = "/root/temp/" + System.currentTimeMillis() + ".pdf";
            save(filePath);
            File file = new File(filePath);
//            PDDocument document = PDDocument.load(file);
//            // 加载成打印文件
//            PDFPrintable printable = new PDFPrintable(document);
//            PrinterJob job = PrinterJob.getPrinterJob();
//            job.setPrintable(printable);
//            job.print();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void save(String filePath)throws Exception{
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDDocumentInformation pdd = document.getDocumentInformation();
        pdd.setTitle("领料单");
        pdd.setCreator("pdf examples");
        pdd.setSubject("领料单");
        pdd.setAuthor("张国衡");

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 22);
        // 设置文本前导
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(60, 750);
        String text1 = "研发生产领料单";
        contentStream.showText(text1);
        contentStream.newLine();
        System.out.println("Content added");
        contentStream.close();

        Calendar date = new GregorianCalendar();
        date.set(2020, 4, 8);
        pdd.setCreationDate(date);
        pdd.setKeywords("sample, first example, my pdf");
        try {
            document.save(filePath);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PDFont getDefaultFont(PDDocument doc) {
        String path = "/unicode.ttf";
        PDFont font = null;
        try (InputStream input = OpenTypeScript.class.getResourceAsStream(path)) {
            font = PDType0Font.load(doc, input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return font;
    }

}