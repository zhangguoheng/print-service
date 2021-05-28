package com.example.printservice.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.itextpdf.text.pdf.qrcode.BitMatrix;
import com.itextpdf.text.pdf.qrcode.QRCodeWriter;
import com.itextpdf.text.pdf.qrcode.WriterException;

import java.io.IOException;
import java.nio.file.FileSystems;

public class QRCodeGenerator {


    public static void main(String[] args) {
        QrCodeUtil.generate("123321", 300, 300, FileUtil.file("/root/temp/qrcode.jpg"));
    }


}
