package br.senai.sp.info.patrimonio.ianes.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterBase64 {

	@Autowired
	private ServletContext context;
	
		public String getFotoBase64(String imagem) throws IOException {
				
			//Caminho para salvar a foto
	
			File arquivo = new File(imagem);
				
			System.out.println("img" + imagem);
			System.out.println("rota img" + imagem);
				
			//Pega a extensão da imagem
			MimetypesFileTypeMap mimeType = new MimetypesFileTypeMap();
			String mime = mimeType.getContentType(imagem);
			String tipoImagem = mime.substring(mime.indexOf('/') + 1, mime.length());
			
			System.out.println(tipoImagem);
		
			BufferedImage img = ImageIO.read(arquivo);
			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			
			ImageIO.write(img, tipoImagem, oStream);
			oStream.flush();
			
			byte[] bytesImg = oStream.toByteArray();
			oStream.close();
			
			String base64 = Base64.getEncoder().encodeToString(bytesImg);
			System.out.println(base64);
				
			return  base64;
				
		}
	
}
