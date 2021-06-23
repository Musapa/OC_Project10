package com.openclassrooms.ocproject10.patients.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "patient")
public class Patient {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "givenName", nullable = false)
    @NotBlank(message="Given name is mandatory.")
    @Size(min=1, max=30, message="Given name need to be between 1-30 characters.")
    private String givenName;

    @Column(name = "familyName", nullable = false)
    @NotBlank(message="Family name is mandatory.")
    @Size(min=1, max=30, message="Family name need to be between 1-30 characters.")
    private String familyName;

    @NotNull(message = "Date of birth is mandatory.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotNull(message = "Gender is mandatory.")
    private char sex;

    @NotBlank(message="Home address is mandatory.")
    private String address;
    
    @NotNull(message="Phone number is mandatory.")
    @DecimalMin("0.01")
    private String phone;

    
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

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
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
