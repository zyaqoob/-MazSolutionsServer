/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Subject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Aitor Ruiz de Gauna
 */
@Stateless
@Path("entities.subject")
public class SubjectFacadeREST {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;
    private Logger LOGGER = Logger.getLogger(SubjectFacadeREST.class.getName());
    /**
     * 
     * @param entity 
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Subject entity) {
          try {
            em.persist(entity);
            em.flush();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    /**
     * 
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Subject entity) {
        try {
            em.merge(entity);
            em.flush();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }
   
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Subject find(@PathParam("id") Long id) {
        Subject subject=null;
        try{
            subject=(Subject) em.createNamedQuery("findSubjectById").setParameter("id", id).getSingleResult();
        }catch(Exception e){
            throw new InternalServerErrorException(e);
        }
        return subject;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Set<Subject> findAll() {
        Set<Subject> subjects=null;
        try{
            subjects=new HashSet<>(em.createNamedQuery("findAllSubjects").getResultList());
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return subjects;
    }
    /**
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Subject> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    **/
}
