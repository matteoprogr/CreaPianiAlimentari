package it.dieta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import it.dieta.models.PianoAlimentare;
import it.dieta.models.Utente;
import it.dieta.services.LogicaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LogicaService ls;
	
	@GetMapping
	public String index(HttpSession sessione,HttpServletRequest request,Model model) {
		
		String msg=(String) sessione.getAttribute("messaggio");
		
		model.addAttribute("messaggio",msg);
		Utente user=(Utente) sessione.getAttribute("Username");
		boolean flag=true;
		if(user!=null) {
			flag=false;
			
		}
		
		model.addAttribute("mostraTrue",flag);
		
		return "login";
	}
	@ResponseBody
	@PostMapping("/accesso")
	public RedirectView login(HttpSession sessione,@RequestParam("Username") String Username,@RequestParam("Password") String Password,Model model){
		
		String errore="";
		try {
			Utente ut = ls.accedi(Username,Password);
			sessione.setMaxInactiveInterval(300);
			sessione.setAttribute("messaggio",errore);
			sessione.setAttribute("Username",ut);
			PianoAlimentare piano= (PianoAlimentare) sessione.getAttribute("piano");
			
			
			if(piano!=null) {
				ls.salvaPianoMetodo(ut, piano, sessione);
				return new RedirectView("/piano");
			}
			 
			
			return new RedirectView("/");
			
		} catch (Exception e) {
			
			errore= e.getMessage();
			sessione.setAttribute("messaggio",errore);
		}
		
		
		return new RedirectView("/login");
	}
	
	@GetMapping("/logout")
	public RedirectView logout(HttpSession sessione) {
		sessione.invalidate();
		return new RedirectView("/login");
	}
}
