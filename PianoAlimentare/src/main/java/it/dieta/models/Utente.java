package it.dieta.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="Utenti")
public class Utente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int IdUtente;
	
	@Column(unique = true,nullable = false)
	private String Username;
	@Column(nullable = false)
	private String Password;
	@Column(nullable = false)
	private double Peso;
	@Column(nullable = false)
	private String Obiettivo;
	@Column(nullable = false)
	private String Fabbisogno="";
	
	public String getFabbisogno() {
		return Fabbisogno;
	}


	public void setFabbisogno(String fabbisogno) {
		Fabbisogno = fabbisogno;
	}

	@OneToMany
	private List<PianoAlimentare> listaPiani = new ArrayList<>();
	
	@Transient
	private static List<String> ListaObiettivi= new ArrayList<>(List.of("Perdere Peso","Mantenere Peso","Aumentare Peso"));
	
	

	
	

	public Utente(int idUtente, String username, String password, double peso, String obiettivo,
			List<PianoAlimentare> listaPiani) {
		super();
		IdUtente = idUtente;
		Username = username;
		Password = password;
		Peso = peso;
		Obiettivo = obiettivo;
		this.listaPiani = listaPiani;
	}


	public Utente() {
		super();
	}


	public int getIdUtente() {
		return IdUtente;
	}

	public void setIdUtente(int idUtente) {
		IdUtente = idUtente;
	}

	

	public String getUsername() {
		return Username;
	}


	public void setUsername(String username) {
		Username = username;
	}


	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		Password = password;
	}


	public double getPeso() {
		return Peso;
	}

	public void setPeso(double peso) {
		Peso = peso;
	}

	public String getObiettivo() {
		return Obiettivo;
	}

	public void setObiettivo(String obiettivo) {
		Obiettivo = obiettivo;
	}

	public List<PianoAlimentare> getListaPiani() {
		return listaPiani;
	}

	public void setListaPiani(List<PianoAlimentare> listaPiani) {
		this.listaPiani = listaPiani;
	}

	public static List<String> getListaObiettivi() {
		return ListaObiettivi;
	}

	public void setListaObiettivi(List<String> listaObiettivi) {
		ListaObiettivi = listaObiettivi;
	}
	
	
}
