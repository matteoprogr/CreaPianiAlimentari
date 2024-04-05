package it.dieta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dieta.models.PianoAlimentare;

@Repository
public interface PianiRepository extends JpaRepository<PianoAlimentare, Integer> {

}
