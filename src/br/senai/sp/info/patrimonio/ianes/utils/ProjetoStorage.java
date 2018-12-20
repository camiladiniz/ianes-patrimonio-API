package br.senai.sp.info.patrimonio.ianes.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Grava arquivos dentro do próprio projeto
 * @author 32653246805
 *
 */
@Component
public class ProjetoStorage {

	@Autowired
	private ServletContext context;
	
	public String getCaminhoProjeto() {
		return context.getRealPath("");
	}
	
	/**
	 * Cria um novo arquivo para a imagem do patrimônio em
	 * <pastaDoProjeto>/assets/patrimonioImagens/<nomeDoArquivo>
	 * @param nomeDoArquivo
	 * @param dadosDoArquivo
	 * @throws IOException
	 */
	public void armazenarImagemPatrimonio(String nomeDoArquivo, byte[] dadosDoArquivo) throws IOException {
		
		String caminhoImagens = getCaminhoProjeto() +"assets/patrimonioImagens";
		String caminhoArquivo = caminhoImagens +"/" +nomeDoArquivo;
		
		//Cria a pasta de fotos caso ela não exista
		File pastaImagem = new File(caminhoImagens);
		
		if(!pastaImagem.exists()) {
			pastaImagem.mkdirs(); //Cria a pasta
		}
		
		//Cria o arquivo na pasta
		File arquivoImg = new File(caminhoArquivo);
		
		if(arquivoImg.exists()) {
			arquivoImg.delete(); //Deleta a foto caso ela já exista	
		}
		
		arquivoImg.createNewFile();
		
		//Salva o conteúdo no arquivo
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(arquivoImg));
		bos.write(dadosDoArquivo);
		bos.close();
		
	}
	
	public void armazenarFotoDePerfil(String nomeDoArquivo, byte[] dadosDoArquivo) throws IOException {
		//Pega o caminho para a pasta /assets/fotos
		String caminhoFotos = getCaminhoProjeto() +"assets/usuariosFotos";//pasta
		String caminhoArquivo = caminhoFotos +"/" + nomeDoArquivo;
		
		//Criar a pasta de fotos caso ela não exista
		File pastaFoto = new File(caminhoFotos);
		
		if(!pastaFoto.exists()) {
			pastaFoto.mkdirs(); //Cria a pasta
		}
		
		//Cria o arquivo na pata
		File arquivoFoto = new File(caminhoArquivo);
		if(arquivoFoto.exists()) {
			arquivoFoto.delete(); //Deleta a foto caso ela já exista	
		}
		
		arquivoFoto.createNewFile();
		
		//Salva o conteúdo no arquivo
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(arquivoFoto));
		bos.write(dadosDoArquivo);
		bos.close();
	}
	
}
