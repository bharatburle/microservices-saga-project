package com.bharat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharat.dto.PatientRequestDTO;
import com.bharat.dto.PatientResponseDTO;
import com.bharat.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name="Patient", description ="API for managing patients")
public class PatientController {

	private final PatientService patientService;

	@PostMapping("/save")
	@Operation(summary = "Save a new patient")
	public ResponseEntity<PatientResponseDTO> savePatient(@Valid @RequestBody PatientRequestDTO dto) {
		PatientResponseDTO savePatient = patientService.savePatient(dto);
		return ResponseEntity.ok().body(savePatient);
	}

	@GetMapping("/all")
	@Operation(summary = "Get all patients")
	public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
		List<PatientResponseDTO> allPatients = patientService.getAllPatients();
		return ResponseEntity.ok().body(allPatients);
	}

	@GetMapping("/findById")
	public ResponseEntity<PatientResponseDTO> getPatientById(@RequestParam Integer id) {
		PatientResponseDTO patientById = patientService.getPatientById(id);
		return ResponseEntity.ok().body(patientById);
	}

	@PutMapping("/updatePatient")
	@Operation(summary = "Update a patient")
	public ResponseEntity<PatientResponseDTO> updatePatient(@RequestParam Integer id,
			@RequestBody PatientRequestDTO dto) {
		PatientResponseDTO updatedPatient = patientService.updatePatient(id, dto);
		return ResponseEntity.ok().body(updatedPatient);
	}

	@DeleteMapping("/deletePatient/{id}")
	@Operation(summary = "Delete a patient")
	public ResponseEntity<PatientResponseDTO> deletePatient(@PathVariable Integer id) {
		patientService.deletePatientById(id);
		return ResponseEntity.noContent().build();
	}
}
