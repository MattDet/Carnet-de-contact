package com.example.contactbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public String getAllContacts(Model model) {
        List<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "index";  // Correspond à index.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact_form";  // Correspond à contact_form.html
    }

    @GetMapping("/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));
        model.addAttribute("contact", contact);
        return "contact_form";  // Correspond à contact_form.html pour la modification
    }

    @PostMapping
    public String saveContact(Contact contact) {
        contactRepository.save(contact);
        return "redirect:/contacts";  // Redirige vers la liste des contacts après l'enregistrement
    }

    @PostMapping("/{id}")
    public String updateContact(@PathVariable String id, Contact contact) {
        contact.setId(id);
        contactRepository.save(contact);
        return "redirect:/contacts";  // Redirige après la modification
    }

    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable String id) {
        contactRepository.deleteById(id);
        return "redirect:/contacts";  // Redirige après la suppression
    }
}
