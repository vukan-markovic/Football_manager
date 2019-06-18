package rva.reps;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Tim;

public interface TimRepository extends JpaRepository<Tim, Integer> {
	Collection<Tim> findByNazivContainingIgnoreCase(String naziv);
}