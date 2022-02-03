/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.logging.Logger;
import crypto.Crypto;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Class that manages TeacherFacadeREST
 *
 * @author Zeeshan
 */
@Stateless
@Path("entities.teacher")
public class TeacherFacadeREST extends AbstractFacade<Teacher> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;
    private Logger LOGGER = Logger.getLogger(SubjectFacadeREST.class.getName());

    /**
     * Constructor
     */
    public TeacherFacadeREST() {
        super(Teacher.class);
    }

    /**
     * entity managers create method implementation.
     *
     * @param entity teacher
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Teacher entity) {
        String password = Crypto.descifrar(entity.getPassword());
        password = Crypto.hashPassword(password);
        entity.setPassword(password);
        try {
            if (!em.contains(entity)) {
                em.merge(entity);
            }
            em.flush();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
    }

    /**
     * entity managers edit method implementation.
     *
     * @param id id of the teacher
     * @param entity teacher
     */
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

    /**
     * entity managers remove method implementation.
     *
     * @param login login of the teacher.
     */
    @DELETE
    @Path("{login}")
    public void remove(@PathParam("login") String login) {
        try {
            Teacher teacher = findTeacherByName(login);
            super.remove(teacher);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

    }

    /**
     * method to find teacher
     *
     * @param id id of the teacher
     * @return an object of the teacher based on the id.
     */
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

    /**
     * method to find all the existing teachers
     *
     * @return a collection of teacher
     */
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

    /**
     * method to find teachers for subject
     *
     * @param subject_name subject name
     * @return a collection of the teachers based on the name of the subject.
     */
    @GET
    @Path("subject/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Teacher> findTeachersBySubject(@PathParam("name") String subject_name) {
        Set<Teacher> teachers = null;
        try {
            teachers = new HashSet<>(em.createNamedQuery("findTeacherBySubject").setParameter("subject_name", subject_name).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teachers;
    }

    /**
     * method to find teacher for login
     *
     * @param login login of the teacher
     * @return an obejct of the teacher based on the login.
     */
    @GET
    @Path("login/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public Teacher findTeacherByName(@PathParam("login") String login) {
        Teacher teacher = null;
        try {
            teacher = (Teacher) em.createNamedQuery("findTeacherByLogin").setParameter("login", login).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teacher;
    }

    /**
     * method to find tecahers for course
     *
     * @param id id of the course
     * @return a collection of the tecaher based on the is of the course
     */
    @GET
    @Path("course/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Teacher> findTeachersByCourse(@PathParam("id") Long id) {
        Set<Teacher> teachers = null;
        try {
            teachers = new HashSet<>(em.createNamedQuery("findTeacherByCourse").setParameter("id_teacher_course", id).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);

        }
        return teachers;
    }

    /**
     * method to find teachers for student
     *
     * @param name name of the student
     * @return a collection of the teachers based on student name.
     */
    @GET
    @Path("student/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Teacher> findTeachersByStudent(@PathParam("name") String name) {
        Set<Teacher> teachers = null;
        try {
            teachers = new HashSet<>(em.createNamedQuery("findTeachersByStudent").setParameter("student_name", name).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return teachers;
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
    public List<Teacher> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    /**
     *
     * @return total number of existing teachers
     */
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

    /**
     *
     * @return an object of entity manager.
     */
    @Override
    protected EntityManager getEntityManager() {
        try {
            return em;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * method to find tecaher for login and email
     *
     * @param email email of teacher
     * @param login login of teacher
     * @return confirmation if the tecaher exists or not.
     */
    @GET
    @Path("existing/{email}/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public Integer findExistingTeacher(@PathParam("email") String email, @PathParam("login") String login) {

        return em.createNamedQuery("findExistingTeacher").setParameter("login", login).setParameter("email", email).getResultList().size();

    }

}
