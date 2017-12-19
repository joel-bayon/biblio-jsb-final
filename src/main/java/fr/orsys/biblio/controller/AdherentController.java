package fr.orsys.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.orsys.biblio.entity.Adherent;
import fr.orsys.biblio.service.Bibliotheque;
import fr.orsys.biblio.service.BusinessException;

@Controller
@RequestMapping("/adherents")
public class AdherentController {
	
	@Autowired
	Bibliotheque biblio;
	
	@RequestMapping("/all")
	public String getAdherentAll(Model model) {
		model.addAttribute("adherents", biblio.getAdherentDao().findAll());
		return "adherents";
	}
	
	@RequestMapping("/{id}")
	public String getAdherent(@PathVariable int id, Model model) {
		Adherent adherent = new Adherent();
		if(id != 0 ) 
			adherent = biblio.getAdherentDao().findOne(id);
		model.addAttribute("adherent", adherent);
		return "adherent";
	}
	
	@RequestMapping("/action")
	public String doActionLivre(Integer id, String nom, String prenom, String tel, String email, String action ) {
		Adherent adherent = null;
		switch (action) {
			case "update":
				adherent = biblio.getAdherentDao().findOne(id);
				adherent.setNom(nom);;
				adherent.setPrenom(prenom);
				adherent.setTel(tel);
				adherent.setEmail(email);
				biblio.getAdherentDao().update(adherent);
				break;
			case "delete":
				biblio.retirerAdherent(id);
				break;
			case "create":
				adherent = new Adherent(nom, prenom, tel, email);
				biblio.ajouterAdherent(adherent);
				break;
		}
		
		return "redirect:all";
	}
	
	@ExceptionHandler(BusinessException.class)
	public String traiterErreur(BusinessException e, Model model) {
		model.addAttribute("erreur", e.getMessage());
		return "erreur";
	}

}
