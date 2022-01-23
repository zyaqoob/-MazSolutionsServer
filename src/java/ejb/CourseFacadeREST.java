/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import entities.Course;
import entities.Student;
import entities.Subject;
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
@Path("entities.course")
public class CourseFacadeREST extends AbstractFacade<Course> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;

    public CourseFacadeREST() {
        super(Course.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Course entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Course entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Course> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Course> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    //custom queries
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Course find(@PathParam("id") Long id) {
        Course course = null;
        try {
            course = (Course) em.createNamedQuery("findCourseById").setParameter("idCourse", id).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return course;
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Set<Course> findAllCourses() {
        Set<Course> courses = new HashSet<>();
        try {
            courses = new HashSet<>(em.createNamedQuery("findAllCourses").getResultList());
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return courses;
    }
    @GET
    @Path("student/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Course findCourseByStudent(@PathParam("id") Long id) {
        Course course = null;
        try {
            course = (Course) em.createNamedQuery("findCourseByStudent")
                    .setParameter("idUser",id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return course;
    }
    @GET
    @Path("subject/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Course>findCoursesBySubject(@PathParam("id") Long id){
        Set<Course>courses=null;
        try{
            courses=new HashSet<>(em.createNamedQuery("findCoursesBySubject").setParameter("idSubject",id).getResultList());
        }catch(Exception e){
            throw new InternalServerErrorException(e);
        }
        return courses;
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
