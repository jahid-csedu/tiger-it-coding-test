package com.example.demo.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/api")
public class ContactController {

	@Autowired
	ContactService contactService;
	
	Logger logger = (Logger) LoggerFactory.getLogger(ContactController.class);
	
	@PostMapping("/contacts")
	@Secured("ROLE_ADMIN")
	public String addContact(@RequestBody Contact contact) throws JsonProcessingException, ValidationException {
		ObjectMapper mapper = new ObjectMapper();
		Contact addedContact = contactService.addContact(contact);
		logger.info("New Contact Added" + addedContact.toString());
		return mapper.writeValueAsString(addedContact);
	}
	
	@GetMapping("/contacts")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String getContacts() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<Contact> contactList = contactService.getAllContact();
		return mapper.writeValueAsString(contactList);
	}

}
