package com.bharat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bharat.dto.PatientRequestDTO;
import com.bharat.dto.PatientResponseDTO;
import com.bharat.exception.EmailAlreadyExistException;
import com.bharat.exception.PatientNotFoundException;
import com.bharat.mapper.PatientMapper;
import com.bharat.model.Patient;
import com.bharat.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

	private final PatientRepository repository;

	@Transactional
	public PatientResponseDTO savePatient(PatientRequestDTO dto) {
		Patient patient = PatientMapper.toEntity(dto);
		return PatientMapper.toResponseDTO(repository.save(patient));
	}

	public List<PatientResponseDTO> getAllPatients() {
		List<Patient> allPatients = repository.findAll();
		return allPatients.stream().map(PatientMapper::toResponseDTO).toList();
	}

	public PatientResponseDTO getPatientById(Integer id) {
		Patient patient = repository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
		return PatientMapper.toResponseDTO(patient);
	}

	public PatientResponseDTO updatePatient(Integer id, PatientRequestDTO dto) {
		Patient existPatient = repository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
		if (repository.existsByEmailAndIdNot(dto.getEmail(), id)) {
			throw new EmailAlreadyExistException("A patient with this email already exist {}" + dto.getEmail());
		}
		existPatient.setName(dto.getName());
		existPatient.setEmail(dto.getEmail());
		return PatientMapper.toResponseDTO(repository.save(existPatient));
	}

	public void deletePatientById(Integer id) {
		repository.findById(id)
		.orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
		repository.deleteById(id);
	}

}
