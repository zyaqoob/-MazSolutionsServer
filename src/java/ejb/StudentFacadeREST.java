/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;


import crypto.Crypto;
import entities.Student;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Class that manages StudentFacadeREST
 * @author Miguel
 */
@Stateless
@Path("entities.student")
public class StudentFacadeREST extends AbstractFacade<Student> {
private static final Logger LOGGER = Logger.getLogger(StudentFacadeREST.class.getName());
    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;

    /**
     *  Constructor
     */
    public StudentFacadeREST() {
        super(Student.class);
    }

    /**
     *entity managers create method implementation.
     * @param entity student
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Student entity) {
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
     * @param id id of the student
     * @param entity student
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Student entity) {
        try {
            super.edit(entity);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    /**
     *  entity managers remove method implementation.
     * @param id id of the student
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        
        try{
            super.remove(super.find(id));
        }catch(Exception e){
            
            throw new InternalServerErrorException(e);
            
        }
    }

    /**
     * method to find a student
     * @param id id of the student
     * @return an object of the student based on the id.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Student find(@PathParam("id") Long id) {
        Student student = null;
        try {
            student = (Student) em.createNamedQuery("findStudentById").setParameter("idUser", id).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

        return student;
    }

    /**
     *  method to find student for a teacher
     * @param name teacher of the name
     * @return a collection of the student based on the teacher name.
     */
    @GET
    @Path("teacher/{fullname}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Student> find(@PathParam("fullname") String name) {
        Set<Student> students = null;
        try {
            students = new HashSet<>(em.createNamedQuery("findStudentsByTeacher").setParameter("full_name", name).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

        return students;
    }

    /**
     *  method to find student by email.
     * @param email email of the student
     * @return an object of the student based on the email of the student
     */
    @GET
    @Path("student/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public Student findStudentByEmail(@PathParam("email") String email) {
        Student student = null;
        try {
            student = (Student) em.createNamedQuery("findStudentByEmail").setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

        return student;
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
    public List<Student> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     *entity managers count method implementation.
     * @return the total number of the existing records of student.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    //custom querys

    /**
     *  method to find all the student.
     * @return a collection of all the existing student.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Set<Student> findAllStudents() {
        Set<Student> students = null;
        try {
            students = new HashSet<>(em.createNamedQuery("findAllStudents").getResultList());
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return students;
    }

    /**
     *  method to find student by course
     * @param id id of the course
     * @return a collection of the student based on the course.
     */
    @GET
    @Path("course/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Student> findStudentsByCourse(@PathParam("id") Long id) {
        Set<Student> students = null;
        try {
            students = new HashSet<>(em.createNamedQuery("findStudentsByCourse").setParameter("idCourse", id).getResultList());
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return students;
    }

    /**
     * method to find student by exam session
     * @param id id of the exam session
     * @return an object of the student based on the exam session
     */
    @GET
    @Path("examSession/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Student findStudentByExSes(@PathParam("id") Long id) {
        Student student = null;
        try {
            student = (Student) em.createNamedQuery("findStudentByExSes")
                    .setParameter("idExamSession", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return student;
    }

    /**
     *  
     * @return an object of the entity manager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     *  method to find existing student 
     * @param email email of the student
     * @param login login of the student
     * @return confirmation if the student exists or not,
     */
    @GET
    @Path("existing/{email}/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public Integer findExistingStudent(@PathParam("email") String email, @PathParam("login") String login) {
           
        return em.createNamedQuery("findExistingStudent").setParameter("login", login).setParameter("email", email).getResultList().size();
    }

}
