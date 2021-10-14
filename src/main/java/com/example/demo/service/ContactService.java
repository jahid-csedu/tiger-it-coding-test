package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	ContactRepository contactRepository;
	
	public List<Contact> getAllContact() {
		return contactRepository.findAll();
	}
	
	public Contact addContact(Contact contact) {
		if(contact.getName() == null || contact.getName().equals("")) {
			return null;
		}
		if(contact.getEmail()==null || contact.getEmail().equals("")) {
			return null;
		}
		if(getContactByEmail(contact.getEmail()) != null) {
			return null;
		}
		if(contact.getPhoneNumber() == null || contact.getPhoneNumber().equals("")) {
			return null;
		}
		if(getContactByPhoneNumber(contact.getPhoneNumber()) != null) {
			return null;
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
