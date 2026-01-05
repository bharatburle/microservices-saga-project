package com.bharat.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String name;

	@NotNull
	private Character gender;

	@NotNull
	private LocalDate dateOfBirth;

	@NotNull
	private LocalDate dateOfRegistration;

	@NotNull
	@Email
	@Column(unique = true)
	private String email;

	@NotNull
	@Min(value = 1, message = "Age must be greater than 0")
	@Max(value = 100, message = "Age must not be more than 100")
	private Integer age;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;
}
