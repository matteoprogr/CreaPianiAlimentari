package it.dieta.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import it.dieta.models.Alimento;
import it.dieta.models.Ricetta;
import it.dieta.models.Utente;
import it.dieta.services.LogicaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Ricetta")
public class RicetteController {

	@Autowired
	private LogicaService ls;
	
	@GetMapping
	public String creaRicetta(Model model,HttpSession sessione,HttpServletRequest request) {
		
		List<Alimento> lista = ls.loadAlimenti();
		model.addAttribute("listaAlimenti",lista);
		
		Utente user=(Utente) sessione.getAttribute("Username");
		String nomericetta=(String) sessione.getAttribute("nomeRicetta");
		boolean flag=true;
		if(user!=null) {
			flag=false;
		}
		model.addAttribute("nomeRicetta",nomericetta);
		model.addAttribute("mostraTrue",flag);
		request.getSession().removeAttribute("nomeRicetta");
		
		return "ricetta";
	}
	@ResponseBody
	@GetMapping("/addSelectAlimento")
	public List<Alimento> addSelect() {
		
		List<Alimento> lista = ls.loadAlimenti();
		
		return lista;
	}
	@ResponseBody
	@GetMapping("/addSelectRicetta")
	public List<Ricetta> addSelectRicetta() {
		
		List<Ricetta> lista = ls.loadRicette();
		
		return lista;
	}
	
	@PostMapping("/saveRicetta")
	public RedirectView saveRicetta(String nome,HttpSession sessione,Model model) {
		String ricetta="";
		
		//sessione= request.getSession();
		List<Alimento> lista =  (List<Alimento>) sessione.getAttribute("ricetta");
		List<String> grammi = (List<String>) sessione.getAttribute("grammi");
		
		int i=0;
		for(var v: lista) {
			if(i<grammi.size()){
				ricetta+=v.getNome()+" "+grammi.get(i)+"gr, ";
			}
			i++;
		}
		Alimento al=new Alimento();
		al=lista.get(lista.size()-1);
		
		Ricetta rtt = new Ricetta();
		rtt.setNome(nome);
		rtt.setAlimenti_e_Grammatura(ricetta);
		rtt.setCalorie(al.getCalorie());
		rtt.setCarboidrati(al.getCarboidrati());
		rtt.setGrassi(al.getGrassi());
		rtt.setProteine(al.getProteine());
		if(nome.equals("")) {
			sessione.setAttribute("nomeRicetta","La ricetta deve avere un nome");
			
		}else {
			ls.saveRicetta(rtt);
		}
		
		
		return  new RedirectView("/Ricetta");
	}
	
	@ResponseBody
	@PostMapping("/tabellaRicetta")
	public List<Alimento> tabellaRicetta(@RequestBody HashMap<String, String> mappa,HttpSession sessione){
		
		List<Alimento> listaFiltrata = new ArrayList<>();
		List<Alimento> lista = ls.loadAlimenti();
		List<String> grammi= new ArrayList<>();
		
		String alimento="";
		int gr=0;
		for(var v : mappa.entrySet()) {
			alimento=v.getKey();
			gr=Integer.parseInt(v.getValue());
			Alimento al=new Alimento();
				for(int i=0;i<lista.size();i++) {
					if(alimento.equals(lista.get(i).getNome())){
						al=lista.get(i);
						al.setCalorie(al.getCalorie()/100*gr);
						al.setCarboidrati(0.01+(al.getCarboidrati()/100*gr));
						al.setGrassi(0.01+(al.getGrassi()/100*gr));
						al.setProteine(0.01+(al.getProteine()/100*gr));
						listaFiltrata.add(al);
						grammi.add(gr+"");
					}
			}
		}
		double Calorie = 0;
		double Carboidrati = 0;
		double Grassi = 0;
		double Proteine=0;
		
		Alimento somma=new Alimento();
		somma.setNome("Totale");
		
		for(var v: listaFiltrata) {
			Calorie+=v.getCalorie();
			Carboidrati+=v.getCarboidrati();
			Grassi+=v.getGrassi();
			Proteine+=v.getProteine();
		}
		somma.setCalorie(Calorie);
		somma.setCarboidrati(Carboidrati);
		somma.setGrassi(Grassi);
		somma.setProteine(Proteine);
		listaFiltrata.add(somma);
		
		//sessione = request.getSession();
		sessione.setAttribute("ricetta", listaFiltrata);
		sessione.setAttribute("grammi", grammi);
		
		return listaFiltrata;
		
	}
}












