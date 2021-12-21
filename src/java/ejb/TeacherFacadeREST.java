/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import com.sun.istack.internal.logging.Logger;
import entities.Teacher;
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
 * @author 2dam
 */
@Stateless
@Path("entities.teacher")
public class TeacherFacadeREST extends AbstractFacade<Teacher> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;
    private Logger LOGGER=Logger.getLogger(TeacherFacadeREST.class);

    public TeacherFacadeREST() {
        super(Teacher.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Teacher entity) {
        try {
            super.create(entity);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Teacher entity) {
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
    public Teacher find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (Exception e) {
             LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Set<Teacher> findAllTeacher() {
        Set<Teacher> teachers = new HashSet<>();
        try {
            teachers = new HashSet<>(em.createNamedQuery("findAllTeacher").getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teachers;
    }
    @GET
    @Path("subject/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Teacher> findTeachersBySubject(@PathParam("name") String subject_name){
        Set<Teacher> teachers=null;
        try {
            teachers=new HashSet<>(em.createNamedQuery("findTeacherBySubject").setParameter("subject_name", subject_name).getResultList());
        } catch (Exception e) {
             LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teachers;
    }
    @GET
    @Path("course/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Teacher> findTeachersByCourse(@PathParam("id") Long id){
        Set<Teacher> teachers= null;
        try {
            teachers=new HashSet<>(em.createNamedQuery("findTeacherByCourse").setParameter("id_teacher_course", id).getResultList());
        } catch (Exception e) {
             LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
            
        }
        return teachers;
    }
    @GET
    @Path("student/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Teacher> findTeachersByStudent(@PathParam("name") String name){
        Set<Teacher> teachers=null;
        try {
            teachers=new HashSet<>(em.createNamedQuery("findTeachersByStudent").setParameter("student_name", name).getResultList());
        } catch (Exception e) {
             LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teachers;
    }
    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Teacher> findAll() {
        try {
            return super.findAll();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Teacher> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
