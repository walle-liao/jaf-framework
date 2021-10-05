package com.jaf.framework.components.utils.document;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码图片生成工具类
 *
 * @author: liaozhicheng
 * @date: 2018-11-02
 * @since: 1.0
 */
public final class QRCodeUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    private QRCodeUtil() {
    }

    public static void generateQrCode(String content, File file) throws IOException {
        FileUtils.writeByteArrayToFile(file, generateQrCode(content));
    }

    public static byte[] generateQrCode(String content) {
        return imageToBytes(createQrCode(content, DEFAULT_CHARSET, DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    public static byte[] generateQrCode(String content, int w, int h) {
        return imageToBytes(createQrCode(content, DEFAULT_CHARSET, w, h));
    }

    /**
     * 生成二维码图片
     *
     * @param content 存储内容
     * @return
     */
    private static BufferedImage createQrCode(String content, String charset, int w, int h) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, charset == null ? DEFAULT_CHARSET : charset);
        hints.put(EncodeHintType.MARGIN, 0); // 外边距

        // init context
        final MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        // create data
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, w, h, hints);
        } catch (Exception e) {
            throw new RuntimeException("生成二维码出错", e);
        }

        //create image
        final int black = 0xFF000000;
        final int white = 0xFFFFFFFF;
        final BufferedImage image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < bitMatrix.getWidth(); x++) {
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? black : white);
            }
        }
        return image;
    }

    private static byte[] imageToBytes(BufferedImage image) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", out);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
