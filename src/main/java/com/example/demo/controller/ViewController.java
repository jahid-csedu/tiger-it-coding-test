package com.example.demo.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;

import ch.qos.logback.classic.Logger;

@Controller
public class ViewController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(ViewController.class);
	
	@Autowired
	ContactService contactService;
	
	@GetMapping("/home")
	public String getHomePage() {
		return "home";
	}
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		return "login";
	}
	
	@GetMapping("/add-contact")
	public String getAddContactPage(Model model) {
		model.addAttribute("contact", new Contact());
		return "add-contact";
	}
	
	@PostMapping("/add-contact") 
	public String addContact(@ModelAttribute("contact") Contact contact, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("errorMessage", "Invalid Form Data");
			return "redirect:/add-contact";
		}
		try {
			contactService.addContact(contact);
		} catch (ValidationException e) {
			model.addAttribute("errorMessage", "Invalid Form Data");
			return "redirect:/add-contact";
		}
		
		return getAllContacts(model);
		
	}
	
	@GetMapping("/contacts")
	public String getAllContacts(Model model) {
		List<Contact> contacts = contactService.getAllContact();
		logger.info("List of contacts"+contacts);
		model.addAttribute("contacts", contacts);
		return "contacts";
	}
	
	@GetMapping("/access-denied")
	public String getAccessDeniedPage() {
		return "access-denied";
	}
}
