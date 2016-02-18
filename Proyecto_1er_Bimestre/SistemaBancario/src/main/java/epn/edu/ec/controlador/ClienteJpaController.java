/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epn.edu.ec.controlador;

import epn.edu.ec.controlador.exceptions.IllegalOrphanException;
import epn.edu.ec.controlador.exceptions.NonexistentEntityException;
import epn.edu.ec.controlador.exceptions.PreexistingEntityException;
import epn.edu.ec.entities.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import epn.edu.ec.entities.Transaccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author josemiguel
 */
public class ClienteJpaController implements Serializable {

	public ClienteJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Cliente cliente) throws PreexistingEntityException, Exception {
		if (cliente.getTransaccionList() == null) {
			cliente.setTransaccionList(new ArrayList<Transaccion>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Transaccion> attachedTransaccionList = new ArrayList<Transaccion>();
			for (Transaccion transaccionListTransaccionToAttach : cliente.getTransaccionList()) {
				transaccionListTransaccionToAttach = em.getReference(transaccionListTransaccionToAttach.getClass(), transaccionListTransaccionToAttach.getIdTransaccion());
				attachedTransaccionList.add(transaccionListTransaccionToAttach);
			}
			cliente.setTransaccionList(attachedTransaccionList);
			em.persist(cliente);
			for (Transaccion transaccionListTransaccion : cliente.getTransaccionList()) {
				Cliente oldIdClienteOfTransaccionListTransaccion = transaccionListTransaccion.getIdCliente();
				transaccionListTransaccion.setIdCliente(cliente);
				transaccionListTransaccion = em.merge(transaccionListTransaccion);
				if (oldIdClienteOfTransaccionListTransaccion != null) {
					oldIdClienteOfTransaccionListTransaccion.getTransaccionList().remove(transaccionListTransaccion);
					oldIdClienteOfTransaccionListTransaccion = em.merge(oldIdClienteOfTransaccionListTransaccion);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findCliente(cliente.getIdCliente()) != null) {
				throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
//			Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
//			List<Transaccion> transaccionListOld = persistentCliente.getTransaccionList();
//			List<Transaccion> transaccionListNew = cliente.getTransaccionList();
//			List<String> illegalOrphanMessages = null;
//			for (Transaccion transaccionListOldTransaccion : transaccionListOld) {
//				if (!transaccionListNew.contains(transaccionListOldTransaccion)) {
//					if (illegalOrphanMessages == null) {
//						illegalOrphanMessages = new ArrayList<String>();
//					}
//					illegalOrphanMessages.add("You must retain Transaccion " + transaccionListOldTransaccion + " since its idCliente field is not nullable.");
//				}
//			}
//			if (illegalOrphanMessages != null) {
//				throw new IllegalOrphanException(illegalOrphanMessages);
//			}
//			List<Transaccion> attachedTransaccionListNew = new ArrayList<>();
//
//			for (Transaccion transaccionListNewTransaccionToAttach : transaccionListNew) {
//				transaccionListNewTransaccionToAttach = em.getReference(transaccionListNewTransaccionToAttach.getClass(), transaccionListNewTransaccionToAttach.getIdTransaccion());
//				attachedTransaccionListNew.add(transaccionListNewTransaccionToAttach);
//			}
//			transaccionListNew = attachedTransaccionListNew;
//			cliente.setTransaccionList(transaccionListNew);
			em.merge(cliente);
//			for (Transaccion transaccionListNewTransaccion : transaccionListNew) {
//				if (!transaccionListOld.contains(transaccionListNewTransaccion)) {
//					Cliente oldIdClienteOfTransaccionListNewTransaccion = transaccionListNewTransaccion.getIdCliente();
//					transaccionListNewTransaccion.setIdCliente(cliente);
//					transaccionListNewTransaccion = em.merge(transaccionListNewTransaccion);
//					if (oldIdClienteOfTransaccionListNewTransaccion != null && !oldIdClienteOfTransaccionListNewTransaccion.equals(cliente)) {
//						oldIdClienteOfTransaccionListNewTransaccion.getTransaccionList().remove(transaccionListNewTransaccion);
//						oldIdClienteOfTransaccionListNewTransaccion = em.merge(oldIdClienteOfTransaccionListNewTransaccion);
//					}
//				}
//			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = cliente.getIdCliente();
				if (findCliente(id) == null) {
					throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Cliente cliente;
			try {
				cliente = em.getReference(Cliente.class, id);
				cliente.getIdCliente();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Transaccion> transaccionListOrphanCheck = cliente.getTransaccionList();
			for (Transaccion transaccionListOrphanCheckTransaccion : transaccionListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Transaccion " + transaccionListOrphanCheckTransaccion + " in its transaccionList field has a non-nullable idCliente field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(cliente);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Cliente> findClienteEntities() {
		return findClienteEntities(true, -1, -1);
	}

	public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
		return findClienteEntities(false, maxResults, firstResult);
	}

	private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Cliente.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Cliente findCliente(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Cliente.class, id);
		} finally {
			em.close();
		}
	}

	public int getClienteCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Cliente> rt = cq.from(Cliente.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
