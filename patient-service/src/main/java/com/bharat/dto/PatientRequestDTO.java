package com.bharat.dto;

import lombok.Data;

@Data
public class PatientRequestDTO {

	private String name;
	private String dateOfBirth;
	private Character gender;
	private Integer age;
	private String email;
	private String dateOfRegistration;
	private AddressDTO address;
	
}
