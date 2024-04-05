package it.dieta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dieta.models.Ricetta;

@Repository
public interface RicetteRepository extends JpaRepository<Ricetta, Integer> {

}
