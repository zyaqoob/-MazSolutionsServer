/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * Entity manager class
 * @author 2dam
 * @param <T> GenericType to manage entities.
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    /**
     *
     * @param entityClass entity that is being processed in petition.
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     *
     * @return an object of entity manager.
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Method that handle the create of an entity.
     * @param entity entity that is being processed.
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     *  Method that handle the edit of an entity.
     * @param entity entity that is being processed.
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     *  Method that handle the delete of an entity.
     * @param entity entity that is being processed.
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     *  Method that handle the find of an entity.
     * @param id id of the entity that would be use find the record.
     * @return an object of the entity that was being processed.
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     *  Method that handle the get all records petition of an entity.
     * @return an list of the entity.
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     *
     * @param range range
     * @return result
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Method that count the records of the entity.
     * @return the total number number of the record.
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
