/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.logging.Logger;
import entities.Course;
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
 * Class that manages course
 * @author Miguel 
 */
@Stateless
@Path("entities.course")
public class CourseFacadeREST extends AbstractFacade<Course> {

    private static final Logger LOGGER = Logger.getLogger(CourseFacadeREST.class.getName());
    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;

    /**
     *  Constructor.
     */
    public CourseFacadeREST() {
        super(Course.class);
    }

    /**
     *  entity managers create method implementation.
     * @param entity that would be course.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Course entity) {
        super.create(entity);
    }

    /**
     *  entity managers edit method implementation.
     * @param id id of the entity.
     * @param entity that would be course.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Course entity) {
        super.edit(entity);
    }

    /**
     * entity managers remove method implementation.
     * @param id id of the entity.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    
    /**
     *
     * @param from from
     * @param to to
     * @return range
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Course> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     *  entity managers count method implementation.
     * @return total number of the existing records
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    //custom queries

    /**
     *
     * @param id id of the entity
     * @return an object of the course based on its id
     */
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

    /**
     *
     * @param name name of the course to use in query
     * @return an object of the course based on the name of the course
     */
    @GET
    @Path("course/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Course findCourseByName(@PathParam("name") String name) {
        Course course = null;
        try {
            course = (Course) em.createNamedQuery("findCourseByName").setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return course;
    }

    /**
     * method that find all the records of the course.
     * @return a collection of course.
     */
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

    /**
     *
     * @param id id of the student.
     * @return an object of the course based on the id of the student.
     */
    @GET
    @Path("student/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Course findCourseByStudent(@PathParam("id") Long id) {
        Course course = null;
        try {
            course = (Course) em.createNamedQuery("findCourseByStudent")
                    .setParameter("idUser", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return course;
    }

    /**
     *
     * @param id id of the subject
     * @return a collection of the course based on the id of the subject.
     */
    @GET
    @Path("subject/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Course> findCoursesBySubject(@PathParam("id") Long id) {
        Set<Course> courses = null;
        try {
            courses = new HashSet<>(em.createNamedQuery("findCoursesBySubject").setParameter("idSubject", id).getResultList());
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return courses;
    }

    /**
     *
     * @return an object of the entity manager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
