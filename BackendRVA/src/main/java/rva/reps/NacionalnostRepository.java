package rva.reps;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Nacionalnost;

public interface NacionalnostRepository extends JpaRepository<Nacionalnost, Integer> {
	Collection<Nacionalnost> findByNazivContainingIgnoreCase(String naziv);
}