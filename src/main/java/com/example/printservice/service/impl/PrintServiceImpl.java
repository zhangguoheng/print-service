package com.example.printservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.printservice.service.PrintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class PrintServiceImpl implements PrintService {


    public static void main(String[] args) {
        new PrintServiceImpl().printA4("张国衡", "001154", "123123", "午餐", "员工");
    }
    private boolean printA4(String name, String code, String no, String mealType, String type) {
        if (PrinterJob.lookupPrintServices().length > 0) {
            /*
             * 打印格式
             */
            PageFormat pageFormat = new PageFormat();
            // 设置打印起点从左上角开始，从左到右，从上到下打印
            pageFormat.setOrientation(PageFormat.PORTRAIT);
            /*
             * 打印页面格式设置
             */
            Paper paper = new Paper();
            // 设置打印宽度（固定，和具体的打印机有关）和高度（跟实际打印内容的多少有关）
            paper.setSize(190, 450);
            // 设置打印区域 打印起点坐标、打印的宽度和高度
            paper.setImageableArea(0, 0, 190, 450);

            pageFormat.setPaper(paper);
            // 创建打印文档
            Book book = new Book();
            book.append(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat,
                                 int pageIndex) throws PrinterException {
                    if (pageIndex > 0) {
                        return NO_SUCH_PAGE;
                    }
                    Graphics2D graphics2D = (Graphics2D) graphics;
                    Font font = new Font("宋体", Font.BOLD, 15);
//                    graphics2D.setFont(font);
//                    drawString(graphics2D, "//",
//                            10, 17, 119, 8);
                    graphics2D.setFont(font);
                    int yIndex = 30;
                    int lineHeight = 10;
                    int lineWidth = 180;
                    Color defaultColor = graphics2D.getColor();
                    Color grey = new Color(145, 145, 145);

                    yIndex = drawString(graphics2D, "HYC 华兴食堂餐票", 30,
                            yIndex, lineWidth, lineHeight);
                    // 餐票
                    Stroke stroke = new BasicStroke(0.5f, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_BEVEL, 0, new float[]{4, 4}, 0);
                    graphics2D.setStroke(stroke);
                    graphics2D.drawRect(5, 10, 180, yIndex);
                    // 商品名称
                    lineWidth = 180;
                    lineHeight = 12;
                    graphics2D.setFont(new Font("宋体", Font.PLAIN, 10));
                    graphics2D.setColor(defaultColor);
                    yIndex = drawString(graphics2D, "发放时间："
                            + getCurrDate(), 5, yIndex + lineHeight + 20, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "申 请 人：      " + name, 5, yIndex
                            + lineHeight, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "工    号：      " + code, 5, yIndex
                            + lineHeight, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "编    号：   " + no, 5, yIndex
                            + lineHeight, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "用餐类型：       " + mealType, 5,
                            yIndex + lineHeight, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "类    型：       " + type, 5,
                            yIndex + lineHeight, lineWidth, lineHeight);
                    yIndex = yIndex + 20;
                    graphics2D.drawLine(0, yIndex, 180, yIndex);
                    graphics2D.setFont(new Font("宋体", Font.PLAIN, 8));
                    yIndex = drawString(graphics2D, "地址：华兴总部大楼D3食堂", 5,
                            yIndex + 12, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "备注：1.有效期仅限于发放当天使用", 5,
                            yIndex + 12, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "      2.本餐票不能晚晚餐使用", 5,
                            yIndex + 12, lineWidth, lineHeight);
                    return PAGE_EXISTS;
                }

                private String getCurrDate() {
                    Date currDate = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    return sdf.format(currDate);
                }
            }, pageFormat);
            // 获取默认打印机
            javax.print.PrintService[] printServices = PrinterJob.lookupPrintServices();
            javax.print.PrintService ps = null;
            for (int i = 0; i < printServices.length; i++) {
                javax.print.PrintService printService = printServices[i];
                log.info(printService.getName());
                if ("Toshiba-e-Studio-162".equals(printService.getName())) {
                    ps = printService;
                    break;
                }
            }
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPageable(book);
            try {
                if (ps == null) {
                    printerJob.setPrintService(ps);
                }
                printerJob.print();
                return true;
            } catch (PrinterException e) {
                e.printStackTrace();
                log.error("打印异常:" + e.getMessage());
            }
        } else {
            log.error("没法发现打印机服务");
        }
        return false;

    }



    private boolean printDefault(String name, String code, String no, String mealType, String type) {
        if (PrinterJob.lookupPrintServices().length > 0) {
            /*
             * 打印格式
             */
            PageFormat pageFormat = new PageFormat();
            // 设置打印起点从左上角开始，从左到右，从上到下打印
            pageFormat.setOrientation(PageFormat.PORTRAIT);
            /*
             * 打印页面格式设置
             */
            Paper paper = new Paper();
            // 设置打印宽度（固定，和具体的打印机有关）和高度（跟实际打印内容的多少有关）
            paper.setSize(190, 450);
            // 设置打印区域 打印起点坐标、打印的宽度和高度
            paper.setImageableArea(0, 0, 190, 450);

            pageFormat.setPaper(paper);
            // 创建打印文档
            Book book = new Book();
            book.append(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat,
                                 int pageIndex) throws PrinterException {
                    if (pageIndex > 0) {
                        return NO_SUCH_PAGE;
                    }
                    Graphics2D graphics2D = (Graphics2D) graphics;
                    Font font = new Font("宋体", Font.BOLD, 15);
//                    graphics2D.setFont(font);
//                    drawString(graphics2D, "//",
//                            10, 17, 119, 8);
                    graphics2D.setFont(font);
                    int yIndex = 30;
                    int lineHeight = 10;
                    int lineWidth = 180;
                    Color defaultColor = graphics2D.getColor();
                    Color grey = new Color(145, 145, 145);

                    yIndex = drawString(graphics2D, "HYC 华兴食堂餐票", 30,
                            yIndex, lineWidth, lineHeight);
                    // 餐票
                    Stroke stroke = new BasicStroke(0.5f, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_BEVEL, 0, new float[]{4, 4}, 0);
                    graphics2D.setStroke(stroke);
                    graphics2D.drawRect(5, 10, 180, yIndex);
                    // 商品名称
                    lineWidth = 180;
                    lineHeight = 12;
                    graphics2D.setFont(new Font("宋体", Font.PLAIN, 10));
                    graphics2D.setColor(defaultColor);
                    yIndex = drawString(graphics2D, "发放时间："
                            + getCurrDate(), 5, yIndex + lineHeight + 20, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "申 请 人：      " + name, 5, yIndex
                            + lineHeight, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "工    号：      " + code, 5, yIndex
                            + lineHeight, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "编    号：   " + no, 5, yIndex
                            + lineHeight, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "用餐类型：       " + mealType, 5,
                            yIndex + lineHeight, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "类    型：       " + type, 5,
                            yIndex + lineHeight, lineWidth, lineHeight);
                    yIndex = yIndex + 20;
                    graphics2D.drawLine(0, yIndex, 180, yIndex);
                    graphics2D.setFont(new Font("宋体", Font.PLAIN, 8));
                    yIndex = drawString(graphics2D, "地址：华兴总部大楼D3食堂", 5,
                            yIndex + 12, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "备注：1.有效期仅限于发放当天使用", 5,
                            yIndex + 12, lineWidth, lineHeight);
                    yIndex = drawString(graphics2D, "      2.本餐票不能晚晚餐使用", 5,
                            yIndex + 12, lineWidth, lineHeight);
                    return PAGE_EXISTS;
                }

                private String getCurrDate() {
                    Date currDate = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    return sdf.format(currDate);
                }
            }, pageFormat);
            // 获取默认打印机
            javax.print.PrintService[] printServices = PrinterJob.lookupPrintServices();
            javax.print.PrintService ps = null;
            for (int i = 0; i < printServices.length; i++) {
                javax.print.PrintService printService = printServices[i];
                log.info(printService.getName());
                if ("POS80".equals(printService.getName())) {
                    ps = printService;
                    break;
                }
            }
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPageable(book);
            try {
                if (ps == null) {
                    printerJob.setPrintService(ps);
                }
                printerJob.print();
                return true;
            } catch (PrinterException e) {
                e.printStackTrace();
                log.error("打印异常:" + e.getMessage());
            }
        } else {
            log.error("没法发现打印机服务");
        }
        return false;

    }

    /**
     * 字符串输出
     *
     * @param graphics2D 画笔
     * @param text       打印文本
     * @param x          打印起点 x 坐标
     * @param y          打印起点 y 坐标
     * @param lineWidth  行宽
     * @param lineHeight 行高
     * @return 返回终点 y 坐标
     */
    private int drawString(Graphics2D graphics2D, String text, int x,
                           int y, int lineWidth, int lineHeight) {
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        if (fontMetrics.stringWidth(text) < lineWidth) {
            graphics2D.drawString(text, x, y);
            return y;
        } else {
            char[] chars = text.toCharArray();
            int charsWidth = 0;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < chars.length; i++) {
                if ((charsWidth + fontMetrics.charWidth(chars[i])) > lineWidth) {
                    graphics2D.drawString(sb.toString(), x, y);
                    sb.setLength(0);
                    y = y + lineHeight;
                    charsWidth = fontMetrics.charWidth(chars[i]);
                    sb.append(chars[i]);
                } else {
                    charsWidth = charsWidth + fontMetrics.charWidth(chars[i]);
                    sb.append(chars[i]);
                }
            }
            if (sb.length() > 0) {
                graphics2D.drawString(sb.toString(), x, y);
                y = y + lineHeight;
            }
            return y - lineHeight;
        }
    }


    @Override
    public String print(JSONObject body) {
        JSONObject result = new JSONObject();
        String name = body.getString("name");
        String code = body.getString("code");
        String no = body.getString("no");
        String mealType = body.getString("mealType");
        String type = body.getString("type");

        //String name, String code, String no, String mealType,String type
        if (printDefault(name, code, no, mealType, type)) {
            result.put("result", 1);
            result.put("resultInfo", "成功");
        } else {
            result.put("result", 0);
            result.put("resultInfo", "成功");
        }
        return result.toString();
    }

    @Override
    public String printPdf(JSONObject body) {

        String no =

        return null;
    }


}
