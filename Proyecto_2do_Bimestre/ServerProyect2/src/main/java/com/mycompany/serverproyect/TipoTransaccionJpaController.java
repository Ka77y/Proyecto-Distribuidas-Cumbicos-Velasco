/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproyect;

import com.mycompany.serverproyect.exceptions.IllegalOrphanException;
import com.mycompany.serverproyect.exceptions.NonexistentEntityException;
import entities.TipoTransaccion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Transaccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nathy
 */
public class TipoTransaccionJpaController implements Serializable {

    public TipoTransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTransaccion tipoTransaccion) {
        if (tipoTransaccion.getTransaccionList() == null) {
            tipoTransaccion.setTransaccionList(new ArrayList<Transaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Transaccion> attachedTransaccionList = new ArrayList<Transaccion>();
            for (Transaccion transaccionListTransaccionToAttach : tipoTransaccion.getTransaccionList()) {
                transaccionListTransaccionToAttach = em.getReference(transaccionListTransaccionToAttach.getClass(), transaccionListTransaccionToAttach.getIdTransaccion());
                attachedTransaccionList.add(transaccionListTransaccionToAttach);
            }
            tipoTransaccion.setTransaccionList(attachedTransaccionList);
            em.persist(tipoTransaccion);
            for (Transaccion transaccionListTransaccion : tipoTransaccion.getTransaccionList()) {
                TipoTransaccion oldIdTipoOfTransaccionListTransaccion = transaccionListTransaccion.getIdTipo();
                transaccionListTransaccion.setIdTipo(tipoTransaccion);
                transaccionListTransaccion = em.merge(transaccionListTransaccion);
                if (oldIdTipoOfTransaccionListTransaccion != null) {
                    oldIdTipoOfTransaccionListTransaccion.getTransaccionList().remove(transaccionListTransaccion);
                    oldIdTipoOfTransaccionListTransaccion = em.merge(oldIdTipoOfTransaccionListTransaccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTransaccion tipoTransaccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTransaccion persistentTipoTransaccion = em.find(TipoTransaccion.class, tipoTransaccion.getIdTipo());
            List<Transaccion> transaccionListOld = persistentTipoTransaccion.getTransaccionList();
            List<Transaccion> transaccionListNew = tipoTransaccion.getTransaccionList();
            List<String> illegalOrphanMessages = null;
            for (Transaccion transaccionListOldTransaccion : transaccionListOld) {
                if (!transaccionListNew.contains(transaccionListOldTransaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaccion " + transaccionListOldTransaccion + " since its idTipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Transaccion> attachedTransaccionListNew = new ArrayList<Transaccion>();
            for (Transaccion transaccionListNewTransaccionToAttach : transaccionListNew) {
                transaccionListNewTransaccionToAttach = em.getReference(transaccionListNewTransaccionToAttach.getClass(), transaccionListNewTransaccionToAttach.getIdTransaccion());
                attachedTransaccionListNew.add(transaccionListNewTransaccionToAttach);
            }
            transaccionListNew = attachedTransaccionListNew;
            tipoTransaccion.setTransaccionList(transaccionListNew);
            tipoTransaccion = em.merge(tipoTransaccion);
            for (Transaccion transaccionListNewTransaccion : transaccionListNew) {
                if (!transaccionListOld.contains(transaccionListNewTransaccion)) {
                    TipoTransaccion oldIdTipoOfTransaccionListNewTransaccion = transaccionListNewTransaccion.getIdTipo();
                    transaccionListNewTransaccion.setIdTipo(tipoTransaccion);
                    transaccionListNewTransaccion = em.merge(transaccionListNewTransaccion);
                    if (oldIdTipoOfTransaccionListNewTransaccion != null && !oldIdTipoOfTransaccionListNewTransaccion.equals(tipoTransaccion)) {
                        oldIdTipoOfTransaccionListNewTransaccion.getTransaccionList().remove(transaccionListNewTransaccion);
                        oldIdTipoOfTransaccionListNewTransaccion = em.merge(oldIdTipoOfTransaccionListNewTransaccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTransaccion.getIdTipo();
                if (findTipoTransaccion(id) == null) {
                    throw new NonexistentEntityException("The tipoTransaccion with id " + id + " no longer exists.");
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
            TipoTransaccion tipoTransaccion;
            try {
                tipoTransaccion = em.getReference(TipoTransaccion.class, id);
                tipoTransaccion.getIdTipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTransaccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Transaccion> transaccionListOrphanCheck = tipoTransaccion.getTransaccionList();
            for (Transaccion transaccionListOrphanCheckTransaccion : transaccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoTransaccion (" + tipoTransaccion + ") cannot be destroyed since the Transaccion " + transaccionListOrphanCheckTransaccion + " in its transaccionList field has a non-nullable idTipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoTransaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTransaccion> findTipoTransaccionEntities() {
        return findTipoTransaccionEntities(true, -1, -1);
    }

    public List<TipoTransaccion> findTipoTransaccionEntities(int maxResults, int firstResult) {
        return findTipoTransaccionEntities(false, maxResults, firstResult);
    }

    private List<TipoTransaccion> findTipoTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTransaccion.class));
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

    public TipoTransaccion findTipoTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTransaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTransaccion> rt = cq.from(TipoTransaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
