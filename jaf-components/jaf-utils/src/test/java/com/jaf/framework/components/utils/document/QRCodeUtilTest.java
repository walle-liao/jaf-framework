package com.jaf.framework.components.utils.document;

import org.junit.Test;

import java.io.File;

/**
 * @author: liaozhicheng
 * @date: 2021/10/2
 */
public class QRCodeUtilTest {

    @Test
    public void testCreateQrCode() throws Exception {
        File file = new File("./0001.jpg");
        QRCodeUtil.generateQrCode("https://github.com/walle-liao/jaf-framework", file);
    }

}
