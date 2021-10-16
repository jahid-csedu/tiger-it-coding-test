package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	ContactRepository contactRepository;
	
	public List<Contact> getAllContact() {
		return contactRepository.findAll();
	}
	
	public Contact addContact(Contact contact) throws ValidationException {
		if(contact.getName() == null || contact.getName().equals("")) {
			throw new ValidationException("Name is required");
		}
		if(getContactByEmail(contact.getEmail()) != null) {
			throw new ValidationException("Email exists");
		}
		if(contact.getPhoneNumber() == null || contact.getPhoneNumber().equals("")) {
			throw new ValidationException("Phone Number is required");
		}
		if(getContactByPhoneNumber(contact.getPhoneNumber()) != null) {
			throw new ValidationException("Phone number exists");
		}
		return contactRepository.save(contact);
	}
	
	public Contact getContactByEmail(String email) {
		return contactRepository.findByEmail(email);
	}
	
	public Contact getContactByPhoneNumber(String phoneNumber) {
		return contactRepository.findByPhoneNumber(phoneNumber);
	}
}
