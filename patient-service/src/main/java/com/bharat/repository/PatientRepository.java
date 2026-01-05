package com.bharat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bharat.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	@EntityGraph(attributePaths = "address") // to avoid N+1 problem
	List<Patient> findAll();

	public boolean existsByEmailAndIdNot(String email, Integer id);
}
