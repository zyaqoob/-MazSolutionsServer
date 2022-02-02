/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.logging.Logger;
import entities.ExamSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
 * @author Zeeshan
 */
@Stateless
@Path("entities.examsession")
public class ExamSessionFacadeREST extends AbstractFacade<ExamSession> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(ExamSessionFacadeREST.class.getName());

    public ExamSessionFacadeREST() {
        super(ExamSession.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(ExamSession entity) {
        try {
             if(!em.contains(entity)){
                em.merge(entity);
            }
            em.flush();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, ExamSession entity) {
        try {
            super.edit(entity);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            super.remove(super.find(id));
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public ExamSession find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<ExamSession> findAll() {
        try {
            return super.findAll();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Set<ExamSession> findAllExamSession() {
        Set<ExamSession> examSessions;
        try {
            examSessions = new HashSet<>(em.createNamedQuery("findAllExamSession").getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return examSessions;
    }

    @GET
    @Path("student/{fullname}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<ExamSession> findExamSessionsByStudent(@PathParam("fullname") String fullname) {
        Set<ExamSession> examSessions = null;
        try {
            examSessions = new HashSet<>(em.createNamedQuery("findExamSessionsByStudent").setParameter("fullName", fullname).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return examSessions;
    }

    @GET
    @Path("subject/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<ExamSession> findExamSessionsBySubject(@PathParam("name") String subject_name) {
        Set<ExamSession> examSessions = null;
        try {
            examSessions = new HashSet<>(em.createNamedQuery("findExamSessionsBySubject").setParameter("subject_name", subject_name).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return examSessions;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<ExamSession> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        try {
            return String.valueOf(super.count());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        try {
            return em;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

}
