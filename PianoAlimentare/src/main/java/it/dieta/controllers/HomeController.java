package it.dieta.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import it.dieta.models.Alimento;
import it.dieta.models.PianoAlimentare;
import it.dieta.models.Utente;
import it.dieta.services.LogicaService;
import jakarta.servlet.http.HttpSession;

@Controller("/")
public class HomeController {
	
	
	
	@Autowired
	private LogicaService ls;

	@GetMapping
	public String index(HttpSession sessione,Model model) {
		
		Utente user=(Utente) sessione.getAttribute("Username");
		boolean flag=true;
		if(user!=null) {
			flag=false;
			model.addAttribute("user","Benvenuto "+user.getUsername());
		}
		
		model.addAttribute("mostraTrue",flag);
		
		return "Index";
	}
	
	@GetMapping("/tabella")
	public String tabella2(Model model,HttpSession sessione) {
		
		List<Alimento> lista = ls.loadAlimenti();
		
		Utente user=(Utente) sessione.getAttribute("Username");
		boolean flag=true;
		if(user!=null) {
			flag=false;
		}
		Alimento al=new Alimento();
		List<String> categorie= al.getListaCategorie();
		
		model.addAttribute("mostraTrue",flag);
		model.addAttribute("categorie",categorie);
		
		
		return "tabellaJavascript";
		
	}
	
	@ResponseBody
	@GetMapping("/nuovaTab")
	public List<Alimento> tabella(Model model,HttpSession sessione) {
		
		List<Alimento> lista = ls.loadAlimenti();
		
		
	
		
		Utente user=(Utente) sessione.getAttribute("Username");
		boolean flag=true;
		if(user!=null) {
			flag=false;
		}
		Alimento al=new Alimento();
		List<String> categorie= al.getListaCategorie();
		
		model.addAttribute("mostraTrue",flag);
		
		
		//return "tabella";
		return lista;
	}
	
	@GetMapping("/paginaRegistrazione")
	public String pagReg(Model model) {
	
	    List<String> obiettivi= Utente.getListaObiettivi();
	    model.addAttribute("obiettivi",obiettivi);
	   
		return "registrazione";
	}
	
	@PostMapping("/registrazione")
	public RedirectView registrazione(@RequestParam("Nome") String Nome, @RequestParam("Password") String Password,
			                    @RequestParam("Peso") double Peso, @RequestParam("Obiettivo") String Obiettivo,@RequestParam("Fabbisogno") String Fabbisogno,HttpSession sessione) {
		
		PianoAlimentare piano= (PianoAlimentare) sessione.getAttribute("piano");
		
		Utente ut=new Utente();
		ut.setUsername(Nome);
		ut.setPassword(Password);
		ut.setPeso(Peso);
		ut.setObiettivo(Obiettivo);
		ut.setFabbisogno(Fabbisogno);
		
		if(piano!=null) {
			ls.saveUtente(ut);
			piano.setUtente(ut);
			ls.salvaPianoMetodo(ut, piano, sessione);
		}else {
			ls.saveUtente(ut);
		}
		
		return new RedirectView("/login");
	}
	
}
