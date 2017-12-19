package fr.orsys.biblio.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import fr.orsys.biblio.dao.AdherentDao;
import fr.orsys.biblio.dao.EmpruntDao;
import fr.orsys.biblio.dao.LivreDao;
import fr.orsys.biblio.entity.Adherent;
import fr.orsys.biblio.entity.Emprunt;
import fr.orsys.biblio.entity.Livre;
import fr.orsys.biblio.service.Bibliotheque;

/**
 * Application Lifecycle Listener implementation class BiblioListener
 *
 */
@WebListener
public class BiblioListener implements ServletContextListener {
	
    /**
     * Default constructor. 
     */
    public BiblioListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
	    public void contextDestroyed(ServletContextEvent event)  { 
	   /* 	Bibliotheque biblio = (Bibliotheque)event.getServletContext().getAttribute("biblio");
	    	LivreDao livreDao = biblio.getLivreDao();
	    AdherentDao adherentDao = biblio.getAdherentDao();
	    EmpruntDao empruntDao = biblio.getEmpruntDao();
	    empruntDao.delete(empruntDao.findAll()); 
    		livreDao.delete(livreDao.findAll());  
    		adherentDao.delete(adherentDao.findAll()); */
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    		BeanFactory spring = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    		Bibliotheque biblio = spring.getBean(Bibliotheque.class);
        LivreDao livreDao = biblio.getLivreDao();
        AdherentDao adherentDao = biblio.getAdherentDao();
        EmpruntDao empruntDao = biblio.getEmpruntDao();
        
        empruntDao.delete(empruntDao.findAll()); 
		livreDao.delete(livreDao.findAll());  
		adherentDao.delete(adherentDao.findAll()); 
         
 		Adherent ad1 = new Adherent("Dupond", "Jean", "0234567812", "jean.dupont.@yahoo.fr");
 		Adherent ad2 = new Adherent("Durant", "Jacques", "0223674512", "jacques.durant@free.fr");
 		Adherent ad3 = new Adherent("Martin", "Bernadette", "0138792012", "m.bernadette@gmail.com");
 		
 		adherentDao.save(ad1);
 		adherentDao.save(ad2);
 		adherentDao.save(ad3);
 		Livre l1 = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
 		Livre l2 = new Livre("L'étranger",1942, "Albert Camus");

 		livreDao.save (l1);
 		livreDao.save(l2);
 		livreDao.save(new Livre("Réglez-lui son compte !",1949, "Frédéric Dard"));
 		livreDao.save(new Livre("Tintin au Tibet",1960, "Hergé"));
 		
 		empruntDao.save(new Emprunt(livreDao.findOne(l1.getId()), adherentDao.findOne(ad1.getId())));
		empruntDao.save(new Emprunt(livreDao.findOne(l2.getId()), adherentDao.findOne(ad2.getId())));
 		
 		event.getServletContext().setAttribute("biblio", biblio);							
     
    }
	
}
