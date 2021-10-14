package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	public Contact findByEmail(String email);
	public Contact findByPhoneNumber(String phoneNumber);
}
