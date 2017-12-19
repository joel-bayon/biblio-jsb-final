package fr.orsys.biblio.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import fr.orsys.biblio.dao.AdherentDao;
import fr.orsys.biblio.entity.Adherent;

@Repository
@Primary
@Transactional
public class AdherentDaoJpa implements AdherentDao {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Adherent save(Adherent entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public void update(Adherent entity) {
		em.merge(entity);
	}

	@Override
	public Adherent findOne(Integer primaryKey) {
		return em.find(Adherent.class, primaryKey);
	}

	@Override
	public List<Adherent> findAll() {
		String requeteJPQL = "select a from Adherent a";
		return em.createQuery(requeteJPQL, Adherent.class).getResultList();
	}

	@Override
	public void delete(Adherent entity) {
		// TODO Auto-generated method stub
		delete(entity.getId());
	}

	@Override
	public void delete(Integer id) {
		em.remove(em.find(Adherent.class, id));
	}

	@Override
	public void delete(Iterable<Adherent> entities) {
		// TODO Auto-generated method stub
		for(Adherent a : entities)
			delete(a);
	}

	@Override
	public boolean isPresent(Adherent adherent) {
		String requeteJPQL = "SELECT count(a) FROM Adherent a where nom=:nom and prenom=:prenom and email=:email";
		if (em.createQuery(requeteJPQL, Long.class)
				.setParameter("nom", adherent.getNom())
				.setParameter("prenom", adherent.getPrenom())
				.setParameter("email", adherent.getEmail())
				.getSingleResult() == 0)
			return false;
		else
			return true;
	}
}
