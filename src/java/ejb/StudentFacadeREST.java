/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import entities.Course;
import entities.Student;
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
@Path("entities.student")
public class StudentFacadeREST extends AbstractFacade<Student> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;

    public StudentFacadeREST() {
        super(Student.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Student entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Student entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

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
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Student> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Student> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    //custom querys
    
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
    
    @GET
    @Path("course/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Student>findStudentsByCourse(@PathParam("id") Long id){
        Set<Student>students=null;
        try{
            students=new HashSet<>(em.createNamedQuery("findStudentsByCourse").setParameter("idCourse",id).getResultList());
        }catch(Exception e){
            throw new InternalServerErrorException(e);
        }
        return students;
    }
    
    @GET
    @Path("examSession/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Student findStudentByExSes(@PathParam("id") Long id) {
        Student student = null;
        try {
            student = (Student) em.createNamedQuery("findStudentByExSes")
                    .setParameter("idExamSession",id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return student;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
