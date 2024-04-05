package it.dieta.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dieta.models.Alimento;
import it.dieta.models.PianoAlimentare;
import it.dieta.models.Ricetta;
import it.dieta.models.Utente;
import it.dieta.repositories.AlimentiRepository;
import it.dieta.repositories.PianiRepository;
import it.dieta.repositories.RicetteRepository;
import it.dieta.repositories.UtenteRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class LogicaService {

	@Autowired
	private AlimentiRepository aliRepo;
	@Autowired
	private RicetteRepository ricRepo;
	@Autowired
	private PianiRepository pianiRepo;
	@Autowired
	private UtenteRepository utRepo;
	
	
	
	public List<Alimento> loadAlimenti(){
		
		return aliRepo.findAll();
	}
	
	public List<Ricetta> loadRicette(){
		return ricRepo.findAll();
	}
	
	public List<PianoAlimentare> loadPianById(int id){
		
	List<PianoAlimentare> lista= pianiRepo.findAll();
	List<PianoAlimentare> listaById= new ArrayList<>();
	for(var v: lista) {
		if(v.getUtente().getIdUtente()==id) {
			listaById.add(v);
		}
	}
	return listaById;
	}
	
	
	public Utente accedi(String nome,String password) throws Exception{
		List<Utente>lista= utRepo.findAll();
		Utente ut=new Utente();
		
			ut=(Utente) lista.stream().filter(u->u.getUsername().equals(nome)).findFirst().orElse(null);
			
		if(ut==null) {
		    throw new Exception("Utente non trovato");
		}else if(ut.getPassword().equals(password)) {
			return ut;
		}else if(ut!=null) {
			throw new Exception("Password errata"); 
			
			}
		return ut;
		
	}
	
	public void saveUtente(Utente ut) {
		 utRepo.save(ut);
	}
	public void saveRicetta(Ricetta rtt){
		ricRepo.save(rtt);
	}
	public void savePiano(PianoAlimentare pal) {
		pianiRepo.save(pal);
	}
	public String salvaPianoMetodo(Utente user,PianoAlimentare piano,HttpSession sessione) {
		if(user!=null) {
			List<PianoAlimentare> pianiUtente= loadPianById(user.getIdUtente());
			for(var v : pianiUtente) {
				if(v.getGiorno().equals(piano.getGiorno())) {
					piano.setIdPiano(v.getIdPiano());
					return "esiste";
				}
			}
			savePiano(piano);
			return "successo";
			
		}else {
			sessione.setAttribute("piano", piano);
			return "login";
		}
	}
	
	public List<Alimento> creatabella(HashMap<String,String> mappa){
		
		List<Alimento> listaUnita=new ArrayList<>();
		List<Alimento>allAlimenti= aliRepo.findAll();
		List<Ricetta>allRicette = ricRepo.findAll();
		
		for(var v: mappa.entrySet()) {
			String alimento=v.getKey();
			Alimento al=new Alimento();
			if(v.getValue()!="") {
			double gr= Integer.parseInt(v.getValue());
			for(int i=0;i<allAlimenti.size();i++) {
				if(alimento.equals(allAlimenti.get(i).getNome())) {
					al=allAlimenti.get(i);
					al.setCalorie(al.getCalorie()/100*gr);
					al.setCarboidrati(0.01+(al.getCarboidrati()/100*gr));
					al.setGrassi(0.01+(al.getGrassi()/100*gr));
					al.setProteine(0.01+(al.getProteine()/100*gr));
					listaUnita.add(al);
				}
			}
			for(int i=0;i<allRicette.size();i++) {
				if(alimento.equals(allRicette.get(i).getNome())) {
					al.setNome(allRicette.get(i).getNome());
					al.setCalorie(allRicette.get(i).getCalorie());
					al.setCarboidrati(allRicette.get(i).getCarboidrati());
					al.setGrassi(allRicette.get(i).getGrassi());
					al.setProteine(allRicette.get(i).getProteine());
					listaUnita.add(al);
				}
			}
		}
		}
		double Calorie = 0;
		double Carboidrati = 0;
		double Grassi = 0;
		double Proteine=0;
		
		Alimento somma=new Alimento();
		somma.setNome("Totale");
		
		for(var v: listaUnita) {
			Calorie+=v.getCalorie();
			Carboidrati+=v.getCarboidrati();
			Grassi+=v.getGrassi();
			Proteine+=v.getProteine();
		}
		somma.setCalorie(Calorie);
		somma.setCarboidrati(Carboidrati);
		somma.setGrassi(Grassi);
		somma.setProteine(Proteine);
		listaUnita.add(somma);
		
		return listaUnita;
	}
}
