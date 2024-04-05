package it.dieta.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.dieta.models.Alimento;
import it.dieta.models.PianoAlimentare;
import it.dieta.models.Ricetta;
import it.dieta.models.Utente;
import it.dieta.services.LogicaService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/piano")
public class PianoAlimentareController {

	@Autowired
	private LogicaService ls;
	
	@GetMapping
	public String index(Model model,HttpSession sessione) {
		
		List<Alimento>allAlimenti= ls.loadAlimenti();
		List<Ricetta>allRicette = ls.loadRicette();
		model.addAttribute("allAlimenti",allAlimenti);
		model.addAttribute("allRicette",allRicette);
		List<String> giorni = PianoAlimentare.GiorniSettimana;
		model.addAttribute("giorni",giorni);
		
		
		Utente user=(Utente) sessione.getAttribute("Username");
		boolean flag=true;
		if(user!=null) {
			flag=false;
			model.addAttribute("fabbisogno","Fabbisogno Calorico: "+user.getFabbisogno());
		}
		
		model.addAttribute("mostraTrue",flag);
		
		return "PianoAlimentare";
	}
	
	@ResponseBody
	@PostMapping("/tabellaPiano")
	public List<Alimento> tabellaPiano(@RequestBody HashMap<String, String> mappa){
		
		return ls.creatabella(mappa);
		
	}
	
	@ResponseBody
	@PostMapping("/savePiano")
	public String salvaPiano(@RequestBody Map<String, String> mappa,HttpSession sessione) {
		PianoAlimentare piano=new PianoAlimentare();
		
		Utente user = (Utente) sessione.getAttribute("Username");
		
		piano.setGiorno(mappa.get("Giorno"));
		piano.setColazione(mappa.get("Colazione"));
		piano.setSpuntinoMattina(mappa.get("SpuntinoM"));
		piano.setPranzo(mappa.get("Pranzo"));
		piano.setSpuntinoPomeriggio(mappa.get("SpuntinoP"));
		piano.setCena(mappa.get("Cena"));
		piano.setTotale(mappa.get("Totale"));
		piano.setUtente(user);
		
		sessione.setAttribute("piano", piano);
		String controllo= ls.salvaPianoMetodo(user, piano, sessione);
		return controllo;
		
	}
	@ResponseBody
	@PutMapping("/confermaPiano")
	public String confermaPiano(HttpSession sessione) {
		
		PianoAlimentare piano = (PianoAlimentare) sessione.getAttribute("piano");
		
		
		ls.savePiano(piano);
		
		return "successo";
		
	}
	
}
