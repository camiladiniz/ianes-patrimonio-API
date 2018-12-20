package br.senai.sp.info.patrimonio.ianes.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.info.patrimonio.ianes.dao.CategoriaPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.dao.ItemPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.dao.MovimentacaoDAO;
import br.senai.sp.info.patrimonio.ianes.dao.PatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.models.CategoriaPatrimonio;
import br.senai.sp.info.patrimonio.ianes.models.ItemPatrimonio;
import br.senai.sp.info.patrimonio.ianes.models.Patrimonio;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;
import br.senai.sp.info.patrimonio.ianes.utils.ConverterBase64;
import br.senai.sp.info.patrimonio.ianes.utils.ProjetoStorage;

@Controller
@RequestMapping("/app")
public class PatrimonioController {
	
	@Autowired
	PatrimonioDAO patrimonioDAO;
	
	@Autowired
	CategoriaPatrimonioDAO categoriaDAO;
	
	@Autowired
	ItemPatrimonioDAO itemDAO;
	
	@Autowired
	private ProjetoStorage storage;
	
	@Autowired
	MovimentacaoDAO movimentacaoDAO;

	@GetMapping("/patrimonio")
	public String abrirListaPatrimonio(Model model) {
		
		model.addAttribute("patrimonios", patrimonioDAO.buscarTodos());
		//model.addAttribute("imgPatrimonio", patrimonioDAO)
		return "patrimonio/lista";
	}
	
	@GetMapping("/adm/patrimonio/novo")
	public String abrirCadastroPatrimonio(@RequestParam(name = "id", required = false) Long idPatrimonio, Model model) {
		
		if(idPatrimonio == null) {
			model.addAttribute("patrimonio", new Patrimonio());
		}else {
			model.addAttribute("patrimonio", patrimonioDAO.buscar(idPatrimonio));
		}
		
		model.addAttribute("categorias", categoriaDAO.buscarTodos());
		return "patrimonio/cadastrarPatrimonio";
	}
	
	@PostMapping("/adm/patrimonio/salvar")
	public String salvar(@Valid Patrimonio patrimonio, BindingResult brPatrimonio, @RequestParam(name = "categoria.id", required = false) String categoria, @RequestParam(name="novaCategoria", required = false) String novaCategoria, Model model, HttpSession session, @RequestPart(name = "imagem", required = false) MultipartFile imagem) throws IOException {
		
		boolean ehNovaCategoria = false;
				
		//Verifica se a opção 'OUTRA_CATEGORIA' foi selecionada...
		if(categoria.equals("OUTRA_CATEGORIA")) {
			if(novaCategoria.length() < 1 || novaCategoria.length() > 30) {
				brPatrimonio.addError(new FieldError("patrimonio", "categoria", "Categoria deve conter de 1 a 30 caracteres!"));
			}else if(categoriaDAO.buscarPorNome(novaCategoria) != null) {
				brPatrimonio.addError(new FieldError("patrimonio", "categoria", "Categoria já existente!"));
			}else {
				CategoriaPatrimonio categoriaASalvar = new CategoriaPatrimonio();
				categoriaASalvar.setNome(novaCategoria);
				categoriaDAO.persistir(categoriaASalvar);
				patrimonio.setCategoria(categoriaASalvar);
				categoria = null;
				ehNovaCategoria = true;
			}
		}
		
		if(patrimonio.getId() == null) {
			if(patrimonioDAO.buscarPorNome(patrimonio.getNome()) != null) {
				brPatrimonio.addError(new FieldError("patrimonio", "nome", "Patrimônio já existente!"));
			}
		}
		
		//Se uma nova categoria estiver sendo cadastrada 
		if(ehNovaCategoria) {
			if(brPatrimonio.getFieldErrors().size() > 1) {
				System.out.println("Uma nova categoria foi cadastrada e houve + de 1 erro");
				System.out.println(brPatrimonio);
				model.addAttribute("categorias", categoriaDAO.buscarTodos());
				return "patrimonio/cadastrarPatrimonio";
			}
		}else {
			if(brPatrimonio.hasErrors()) {
				System.out.println("Uma categoria já existente foi selecionada!");
				System.out.println(brPatrimonio);
				model.addAttribute("categorias", categoriaDAO.buscarTodos());
				return "patrimonio/cadastrarPatrimonio";
			}
		}
		
		//Gerando a base64 da imagem
		//Convertendo multipartFile em file
		/*File foto = new File(imagem.getOriginalFilename());
		foto.createNewFile();
		
		System.out.println("fotoooo" +imagem);
		
		System.out.println(imagem.toString());
		System.out.println(imagem.getOriginalFilename());
		System.out.println(imagem.getBytes());
		
		System.out.println("Imagem multipart content type: " +imagem.getContentType());
		System.out.println("Imagem multipart inputStream: " +imagem.getInputStream());
		System.out.println("Imagem multipart string: " +imagem.toString());
		System.out.println("File:" +foto);
		
		converterBase64.getFotoBase64(patrimonio.getUsuarioCadastrou().getCaminhoFoto());
		
		System.out.println(imagem);
		System.out.println(imagem.toString());*/
		
		//Obtendo o usuário logado
		Usuario autenticado = (Usuario) session.getAttribute("usuarioAutenticado");
		
		patrimonio.setCategoria(categoriaDAO.buscar(patrimonio.getCategoria().getId()));
		System.out.println(patrimonio.getCategoria().getNome());
		
		
		if(patrimonio.getId() == null) {
			patrimonio.setDataCadastro(new Date());
			patrimonio.setUsuarioCadastrou(autenticado);
			patrimonioDAO.persistir(patrimonio);
		}else {
			Patrimonio patrimonioAlterado = patrimonioDAO.buscar(patrimonio.getId());
			patrimonioAlterado.setNome(patrimonio.getNome());
			patrimonioAlterado.setCategoria(patrimonio.getCategoria());
			
			patrimonioDAO.alterar(patrimonioAlterado);
		}
		//Armazenando a imagem
		try {
			storage.armazenarImagemPatrimonio("imagem_" +patrimonio.getId(), imagem.getBytes());
			patrimonio.setCaminhoImagem(storage.getCaminhoProjeto()+"imagem_" +patrimonio.getId());
		}catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Caminho da FOTOOO" +patrimonio.getCaminhoImagem());
		return "redirect:/app/patrimonio";
		
	}
	
	@GetMapping("/adm/patrimonio/deletar")
	public String deletarPatrimonio(@RequestParam(name = "id", required = true) Long id) {
		
		Patrimonio patrimonio = patrimonioDAO.buscar(id);
		movimentacaoDAO.deletarPorPatrimonio(id);
		itemDAO.deletarPorPatrimonio(id);
		patrimonioDAO.deletar(patrimonio);
		
		return "redirect:/app/patrimonio";
		
	}
	
	
}
