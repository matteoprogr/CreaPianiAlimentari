package it.dieta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dieta.models.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer>{

}
