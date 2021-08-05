package com.openclassrooms.ocproject10.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "givenName", nullable = false)
	@Size(min = 1, max = 30, message = "Given name need to be between 1-30 characters.")
	@NotBlank(message = "Given name is mandatory.")
	private String givenName;

	@Column(name = "familyName", nullable = false)
	@Size(min = 1, max = 30, message = "Family name need to be between 1-30 characters.")
	@NotBlank(message = "Family name is mandatory.")
	private String familyName;

	@NotNull(message = "Date of birth is mandatory.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;

	@NotBlank(message = "Gender is mandatory.")
	@Pattern(regexp = "\\b(Male|Female)\\b")
	private String sex;

	@NotBlank(message = "Home address is mandatory.")
	private String address;

	@NotBlank(message = "Phone number is mandatory.")
	private String phone;

	public Patient() {

	}

	public Patient(Integer id, String givenName, String familyName, LocalDate dob, String sex, String address,
			String phone) {
		this.id = id;
		this.givenName = givenName;
		this.familyName = familyName;
		this.dob = dob;
		this.sex = sex;
		this.address = address;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
