package com.bharat.mapper;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import com.bharat.dto.AddressDTO;
import com.bharat.dto.PatientRequestDTO;
import com.bharat.dto.PatientResponseDTO;
import com.bharat.model.Address;
import com.bharat.model.Patient;

public class PatientMapper {

	public static PatientResponseDTO toResponseDTO(Patient patient) {
		PatientResponseDTO dto = new PatientResponseDTO();
		BeanUtils.copyProperties(patient, dto, "dateOfBirth", "address","dateOfRegistration");

		dto.setDateOfBirth(patient.getDateOfBirth().toString());
		dto.setDateOfRegistration(patient.getDateOfRegistration().toString());
		AddressDTO addressDTO = new AddressDTO();
		BeanUtils.copyProperties(patient.getAddress(), addressDTO);
		dto.setAddress(addressDTO);
		return dto;
	}

	public static Patient toEntity(PatientRequestDTO patientDTO) {
		Patient entity = new Patient();
		entity.setName(patientDTO.getName());
		entity.setAge(patientDTO.getAge());
		entity.setGender(patientDTO.getGender());
		entity.setDateOfBirth(LocalDate.parse(patientDTO.getDateOfBirth()));
		entity.setDateOfRegistration(LocalDate.parse(patientDTO.getDateOfRegistration()));
		entity.setEmail(patientDTO.getEmail());
		Address address = new Address();
		address.setCity(patientDTO.getAddress().getCity());
		address.setState(patientDTO.getAddress().getState());
		address.setCountry(patientDTO.getAddress().getCountry());
		entity.setAddress(address);
		return entity;

	}

}
