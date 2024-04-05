package it.dieta.models;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.groovy.transform.sc.ListOfExpressionsExpression;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="PianiAlimentare")
public class PianoAlimentare {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int IdPiano;
	
	@Column
	private String Giorno;
	@Column
	private String Colazione;
	@Column
	private String SpuntinoMattina;
	@Column
	private String Pranzo;
	@Column
	private String SpuntinoPomeriggio;
	@Column
	private String Cena;
	@Column
	private String Totale;
	
	@ManyToOne
	private Utente utente;
	
	@Transient
	public static List<String> GiorniSettimana=new ArrayList<>(List.of("Lunedì","Martedì","Mercoledì","Giovedì","Venerdì","Sabato","Domenica"));
	
	
	public PianoAlimentare() {
		super();
	}
	
	public PianoAlimentare(int idPiano, String giorno, String colazione, String spuntinoMattina, String pranzo,
			String spuntinoPomeriggio, String cena, String totale, Utente utente) {
		super();
		IdPiano = idPiano;
		Giorno = giorno;
		Colazione = colazione;
		SpuntinoMattina = spuntinoMattina;
		Pranzo = pranzo;
		SpuntinoPomeriggio = spuntinoPomeriggio;
		Cena = cena;
		Totale = totale;
		this.utente = utente;
	}

	public String getGiorno() {
		return Giorno;
	}

	public void setGiorno(String giorno) {
		Giorno = giorno;
	}

	public String getTotale() {
		return Totale;
	}

	public void setTotale(String totale) {
		Totale = totale;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public static List<String> getGiorniSettimana() {
		return GiorniSettimana;
	}

	public int getIdPiano() {
		return IdPiano;
	}
	public void setIdPiano(int idPiano) {
		IdPiano = idPiano;
	}
	public String getColazione() {
		return Colazione;
	}
	public void setColazione(String colazione) {
		Colazione = colazione;
	}
	public String getSpuntinoMattina() {
		return SpuntinoMattina;
	}
	public void setSpuntinoMattina(String spuntinoMattina) {
		SpuntinoMattina = spuntinoMattina;
	}
	public String getPranzo() {
		return Pranzo;
	}
	public void setPranzo(String pranzo) {
		Pranzo = pranzo;
	}
	public String getSpuntinoPomeriggio() {
		return SpuntinoPomeriggio;
	}
	public void setSpuntinoPomeriggio(String spuntinoPomeriggio) {
		SpuntinoPomeriggio = spuntinoPomeriggio;
	}
	public String getCena() {
		return Cena;
	}
	public void setCena(String cena) {
		Cena = cena;
	}
	
	
}
