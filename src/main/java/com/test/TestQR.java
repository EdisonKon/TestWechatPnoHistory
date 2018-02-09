package com.test;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.EnumMap;

/**
 * @Author : kaifa
 * @Description :
 * @Date : Create in 9:31 2018/2/7
 * @modified : kaifa
 */
public class TestQR {
    /**
     * 解析二维码图片
     * @param filePath 图片路径
     */
    public static String decodeQR(String filePath) {
        if ("".equalsIgnoreCase(filePath) && filePath.length() == 0) {
            return "二维码图片不存在!";
        }
        String content = "";
        EnumMap<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        // 指定编码方式,防止中文乱码
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(binaryBitmap, hints);
            content = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) {
        System.out.println(decodeQR("D://loginqrcode4.jpg"));
    }
}
