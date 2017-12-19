package fr.orsys.biblio.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import fr.orsys.biblio.dao.EmpruntDao;
import fr.orsys.biblio.entity.Emprunt;

@Repository
@Primary
@Transactional
public class EmpruntDaoJpa implements EmpruntDao {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public Emprunt save(Emprunt entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public void update(Emprunt entity) {
		//seule la date de fin peut changer sur un emprunt ...
		Emprunt old = findOne(entity.getId());
		old.setFin(new Date());
	}

	@Override
	public Emprunt findOne(Integer primaryKey) {
		// TODO Auto-generated method stub
		return em.find(Emprunt.class, primaryKey);
	}

	@Override
	public List<Emprunt> findAll() {
		return em.createQuery("select e from Emprunt e", Emprunt.class)
				.getResultList();
	}

	@Override
	public void delete(Emprunt entity) {
		delete(entity.getId());

	}

	@Override
	public void delete(Integer id) {
		em.remove(findOne(id));
	}

	@Override
	public void delete(Iterable<Emprunt> entities) {
		for(Emprunt l : entities)
			delete(l);

	}

	@Override
	public List<Emprunt> getAllByLivre(int livreId) {
		String requete = "select e from Emprunt e where e.livre.id = :livreId";
		return em.createQuery(requete, Emprunt.class)
				.setParameter("livreId", livreId)
				.getResultList();
	}

	@Override
	public Emprunt getEmpruntEnCoursByLivre(int livreId) {
		String requete = "select e from Emprunt e where e.livre.id = :livreId and e.fin=null";
		List<Emprunt> le = em.createQuery(requete, Emprunt.class)
				.setParameter("livreId", livreId)
				.getResultList();
		return le.size() == 0 ? null : le.get(0);
	}

	@Override
	public List<Emprunt> getAllByAdherent(int adherentId) {
		String requete = "select e from Emprunt e where e.adherent.id = :adherentId";
		return em.createQuery(requete, Emprunt.class)
				.setParameter("adherentId", adherentId)
				.getResultList();
	}

	@Override
	public List<Emprunt> getEmpruntsEnCoursByAdherent(int adherentId) {
		String requete = "select e from Emprunt e where e.adherent.id = :adherentId and e.fin = null";
		return em.createQuery(requete, Emprunt.class)
				.setParameter("adherentId", adherentId)
				.getResultList();
	}


}
