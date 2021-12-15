/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Subject;
import entities.Teacher;
import entities.TeacherCourse;
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
 * @author Aitor Ruiz de Gauna.
 */
@Stateless
@Path("entities.teachercourse")
public class TeacherCourseFacadeREST {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;
    private Logger LOGGER = Logger.getLogger(TeacherCourseFacadeREST.class.getName());

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(TeacherCourse entity) {
        try {
            em.persist(entity);
            em.flush();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(TeacherCourse entity) {
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
    public void remove(TeacherCourse entity) {
        try {
            em.remove(em.merge(entity));
            em.flush();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public TeacherCourse find(@PathParam("id")Long id) {
        TeacherCourse teacherCourse = null;
        try {
            teacherCourse = (TeacherCourse) em.createNamedQuery("findTeacherCourseById").setParameter("idTeacherCourse", id).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teacherCourse;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Set<TeacherCourse> findAll() {
        Set<TeacherCourse> teacherCourses = null;
        try {
            teacherCourses = new HashSet<>(em.createNamedQuery("findAllTeacherCourses").getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teacherCourses;
    }

    @GET
    @Path("teacher/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public TeacherCourse findTeacherCourseByTeacher(Teacher teacher) {
        TeacherCourse teacherCourse = null;
        Long id=teacher.getIdUser();
        try {
            teacherCourse = (TeacherCourse) em.createNamedQuery("findTeacherCourseByTeacher").setParameter("idUser",id).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teacherCourse;
    }

    @GET
    @Path("subject/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<TeacherCourse> findTeacherCoursesBySubject(Subject subject) {
        Set<TeacherCourse> teacherCourses = null;
        Long id=subject.getIdSubject();
        try {
            teacherCourses = new HashSet<>(em.createNamedQuery("findTeacherCoursesBySubject").setParameter("idSubject", id).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teacherCourses;
    }
    /**
     * @GET @Path("{from/{to}")
     * @Produces({MediaType.APPLICATION_XML}) public List<TeacherCourse>
     * findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
     * return super.findRange(new int[]{from, to}); }
     *
     * @GET
     * @Path("count")
     * @Produces(MediaType.TEXT_PLAIN) public String countREST() { return
     * String.valueOf(super.count()); }
     *
     * @Override protected EntityManager getEntityManager() { return em; }*
     */

}
