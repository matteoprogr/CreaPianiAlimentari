package it.dieta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dieta.models.Alimento;

@Repository
public interface AlimentiRepository extends JpaRepository<Alimento, Integer> {

}
