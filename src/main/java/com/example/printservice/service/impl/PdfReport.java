package com.example.printservice.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
@Service
public class PdfReport {


    // main测试
    public static void main(String[] args) throws Exception {
        try {
            String imgPath = "/root/temp/test.jpg";
            // 1.新建document对象
            Document document = new Document(PageSize.A4);// 建立一个Document对象
            QrCodeUtil.generate("123321", 300, 300, FileUtil.file(imgPath));
            // 2.建立一个书写器(Writer)与document对象关联
            File file = new File("/root/temp/PDFDemo.pdf");
            file.createNewFile();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new Watermark("HYC 华兴源创"));// 水印
            writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚

            // 3.打开文档
            document.open();
            document.addTitle("免费领料点");// 标题
            document.addAuthor("zhanggh@hyc.cn");// 作者
            document.addSubject("Subject@iText pdf sample");// 主题
            document.addKeywords("Keywords@iTextpdf");// 关键字
            document.addCreator("Creator@umiz`s");// 创建者

            // 4.向文档中添加内容
            new PdfReport().generatePDF(document,new JSONObject(),imgPath,"");

            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // 定义全局的字体静态变量
    private static Font titlefont;
    private static Font headfont;
    private static Font headftextfont;
    private static Font keyfont;
    private static Font textfont;
    // 最大宽度
    private static int maxWidth = 520;

    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 12, Font.BOLD);
            headftextfont = new Font(bfChinese, 10, Font.NORMAL);
            keyfont = new Font(bfChinese, 8, Font.BOLD);
            textfont = new Font(bfChinese, 8, Font.NORMAL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 生成PDF文件
    public void generatePDF(Document document, JSONObject body,String imgPath,String warehouseId) throws Exception {

        // 段落
        Paragraph paragraph = new Paragraph("苏州华兴源创科技股份有限公司", titlefont);
        paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.setIndentationLeft(12); //设置左缩进
        paragraph.setIndentationRight(12); //设置右缩进
        paragraph.setFirstLineIndent(24); //设置首行缩进
        paragraph.setLeading(20f); //行间距
        paragraph.setSpacingBefore(5f); //设置段落上空白
        paragraph.setSpacingAfter(10f); //设置段落下空白

        Paragraph headtext = new Paragraph();
        headtext.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        headtext.setFont(headftextfont);
        headtext.add(new Chunk("申请人:"+body.getString("name")+"         "));
        headtext.add(new Chunk("申请部门:"+body.getString("bmName")+"         "));
        headtext.add(new Chunk("申请日期:"+body.getString("datetime")));

        Paragraph headtext2 = new Paragraph();
        headtext2.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        headtext2.setFont(headftextfont);
        headtext2.add(new Chunk("申请单号:"+body.getString("fromNo")+"         "));
        headtext2.add(new Chunk("单据类型:"+body.getString("type")+"         "));
        headtext2.add(new Chunk("账号别名:"+body.getString("account")));

        Paragraph headtext3 = new Paragraph();
        headtext3.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        headtext3.setFont(headftextfont);
        headtext3.add(new Chunk("备注:"+body.getString("remarks")+"         "));
        headtext3.add(new Chunk("账户别名描述:"+body.getString("accountName")+"         "));
        headtext3.add(new Chunk(""));
        // 添加图片
        Image image = Image.getInstance(imgPath);
        image.setAlignment(Image.ALIGN_RIGHT);
        image.scalePercent(20); //依照比例缩放

        Paragraph headtext4 = new Paragraph();
        headtext4.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        headtext4.setFont(headftextfont);
        headtext4.add(new Chunk("审批:"+body.getString("approve")+"         "));

        Paragraph headtext5 = new Paragraph();
        headtext5.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        headtext5.setFont(headftextfont);
        headtext5.add(new Chunk("领料人:                    "));
        headtext5.add(new Chunk("仓管员:                    "));
        headtext5.add(new Chunk("操作时间:                    "));

        // 直线
        Paragraph p1 = new Paragraph();
        p1.add(new Chunk(new LineSeparator()));




        // 表格
        PdfPTable table = createTable(new float[]{40, 60, 60, 60, 20, 20,20,30,30});
        table.addCell(createCell("物料明细", headfont, Element.ALIGN_CENTER, 9, false));
        table.addCell(createCell("项目号", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("项目描述", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("物料编号", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("物料名称", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("申请数量", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("实际数量", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("仓库", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("货位", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("单位", keyfont, Element.ALIGN_CENTER));
        Integer totalQuantity = 0;
        JSONArray rows = body.getJSONObject("rowMap").getJSONArray(warehouseId);
        for (int i = 0; i < rows.size(); i++) {
            JSONObject row = rows.getJSONObject(i);
            table.addCell(createCell(row.getString("project"), textfont));
            table.addCell(createCell(row.getString("projectName"), textfont));
            table.addCell(createCell(row.getString("code"), textfont));
            table.addCell(createCell(row.getString("name"), textfont));
            table.addCell(createCell(row.getString("count"), textfont));
            table.addCell(createCell("", textfont));
            table.addCell(createCell(row.getString("space"), textfont));
            table.addCell(createCell(row.getString("warehouse"), textfont));
            table.addCell(createCell(row.getString("unit"), textfont));
            totalQuantity++;
        }
//        table.addCell(createCell("总计", keyfont));
//        table.addCell(createCell("", textfont));
//        table.addCell(createCell("", textfont));
//        table.addCell(createCell("", textfont));
//        table.addCell(createCell(String.valueOf(totalQuantity) + "件事", textfont));
//        table.addCell(createCell("", textfont));

        document.add(paragraph);
//        document.add(p1);
        document.add(image);
        document.add(headtext);
        document.add(table);
    }


/**------------------------创建表格单元格的方法start----------------------------*/
    /**
     * 创建单元格(指定字体)
     *
     * @param value
     * @param font
     * @return
     */
    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平..）
     *
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else if (boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     *
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }
/**------------------------创建表格单元格的方法end----------------------------*/


/**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     *
     * @param colNumber
     * @param align
     * @return
     */
    public PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 创建指定列宽、列数的表格
     *
     * @param widths
     * @return
     */
    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 创建空白的表格
     *
     * @return
     */
    public PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }
/**--------------------------创建表格的方法end------------------- ---------*/


}
