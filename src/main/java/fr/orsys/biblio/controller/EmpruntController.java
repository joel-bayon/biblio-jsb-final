package fr.orsys.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.orsys.biblio.entity.Emprunt;
import fr.orsys.biblio.service.Bibliotheque;
import fr.orsys.biblio.service.BusinessException;

@Controller
@RequestMapping("/emprunts")
public class EmpruntController {
	
	@Autowired
	Bibliotheque biblio;
	
	@RequestMapping("/all")
	public String getEmpruntsAll(Model model) {
		model.addAttribute("emprunts", biblio.getEmpruntDao().findAll());
		return "emprunts";
	}
	
	@RequestMapping("/{id}")
	public String getEmprunt(@PathVariable int id, Model model) {
		Emprunt emprunt = null;
		if(id != 0 ) 
			emprunt = biblio.getEmpruntDao().findOne(id);
		model.addAttribute("emprunt", emprunt);
		return "emprunt";
	}
	
	@RequestMapping("/action")
	public String doActionEmprunt(Integer id, Integer idLivre, Integer idAdherent, String action) {
		switch (action) {
			case "restituer":
				biblio.restituerLivre(idLivre, idAdherent);
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
