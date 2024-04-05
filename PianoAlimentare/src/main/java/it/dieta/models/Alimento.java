package it.dieta.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="Alimenti")
public class Alimento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int IdCibo;
	
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
	private String Categoria;
	
	@Transient
	private List<String> listaCategorie = new ArrayList<>(List.of("cereale","frutta/verdura","altro","dolci","latticini","carne","pesce"));
	
	
	
	

	public Alimento(int idCibo, String nome, double calorie, double carboidrati, double grassi, double proteine,
			String categoria, List<String> listaCategorie) {
		super();
		IdCibo = idCibo;
		Nome = nome;
		Calorie = calorie;
		Carboidrati = carboidrati;
		Grassi = grassi;
		Proteine = proteine;
		Categoria = categoria;
		this.listaCategorie = listaCategorie;
	}
	
	

	public Alimento() {
		super();
	}



	public int getIdCibo() {
		return IdCibo;
	}

	public void setIdCibo(int idCibo) {
		IdCibo = idCibo;
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

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public List<String> getListaCategorie() {
		return listaCategorie;
	}

	public void setListaCategorie(List<String> listaCategorie) {
		this.listaCategorie = listaCategorie;
	}
	
	
}
