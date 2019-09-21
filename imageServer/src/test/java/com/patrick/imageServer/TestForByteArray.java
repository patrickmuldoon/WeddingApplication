package com.patrick.imageServer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestForByteArray {

	@Test
	public final void testArray() throws IOException {
		File img  = new File("src/main/resources/img/defaultProfileImg.jpg");
		BufferedImage image = ImageIO.read(img);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      ImageIO.write(image, "jpg", bos );
	      String encodedImage = Base64.getEncoder().encodeToString(bos.toByteArray());
	      byte [] data = bos.toByteArray();
	      System.out.println(data);
	      System.out.println(encodedImage);
	}
}
