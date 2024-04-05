package it.dieta.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Ricette")
public class Ricetta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int IdRicetta;
	
	@Column
	private String Nome;
	
	@Column
	private double Calorie;
	@Column
	private double Carboidrati;
	@Column
	private double Grassi;
	@Column
	private double Proteine;
	@Column
	private String Alimenti_e_Grammatura;
	
	

	public Ricetta() {
		super();
	}



	public Ricetta(int idRicetta, String nome, double calorie, double carboidrati, double grassi,
			double proteine, String alimenti_e_Grammatura) {
		super();
		IdRicetta = idRicetta;
		Nome = nome;
		Calorie = calorie;
		Carboidrati = carboidrati;
		Grassi = grassi;
		Proteine = proteine;
		Alimenti_e_Grammatura = alimenti_e_Grammatura;
	}



	public int getIdRicetta() {
		return IdRicetta;
	}



	public void setIdRicetta(int idRicetta) {
		IdRicetta = idRicetta;
	}



	public String getNome() {
		return Nome;
	}



	public void setNome(String nome) {
		Nome = nome;
	}



	public double getCalorie() {
		return Calorie;
	}



	public void setCalorie(double calorie) {
		Calorie = calorie;
	}



	public double getCarboidrati() {
		return Carboidrati;
	}



	public void setCarboidrati(double carboidrati) {
		Carboidrati = carboidrati;
	}



	public double getGrassi() {
		return Grassi;
	}



	public void setGrassi(double grassi) {
		Grassi = grassi;
	}



	public double getProteine() {
		return Proteine;
	}



	public void setProteine(double proteine) {
		Proteine = proteine;
	}



	public String getAlimenti_e_Grammatura() {
		return Alimenti_e_Grammatura;
	}



	public void setAlimenti_e_Grammatura(String alimenti_e_Grammatura) {
		Alimenti_e_Grammatura = alimenti_e_Grammatura;
	}






	
	
}
