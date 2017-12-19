package fr.orsys.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.orsys.biblio.entity.Livre;
import fr.orsys.biblio.service.Bibliotheque;
import fr.orsys.biblio.service.BusinessException;

@Controller
@RequestMapping("/livres")
public class LivreController {
	
	@Autowired
	Bibliotheque biblio;
	
	@RequestMapping("/all")
	public String getLivreAll(Model model) {
		model.addAttribute("livres", biblio.listerLivre());
		return "livres";
	}
	
	@RequestMapping("/{id}")
	public String getLivre(@PathVariable int id, Model model) {
		Livre livre = new Livre();
		if(id != 0 ) 
			livre = biblio.getLivreDao().findOne(id);
		model.addAttribute("livre", livre);
		return "livre";
	}
	
	@RequestMapping("/action")
	public String doActionLivre(@RequestParam(defaultValue = "0") Integer id, @RequestParam(defaultValue = "0")String titre, @RequestParam(defaultValue = "0")String auteur, int parution, @RequestParam(defaultValue = "0")String action) {
		Livre livre = null;
		switch (action) {
			case "update":
				livre = biblio.getLivreDao().findOne(id);
				livre.setTitre(titre);
				livre.setAuteur(auteur);
				livre.setParution(parution);
				biblio.getLivreDao().update(livre);
				break;
			case "delete":
				biblio.retirerLivre(id);
				break;
			case "create":
				livre = new Livre (titre, parution, auteur);
				biblio.ajouterLivre(livre);
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
