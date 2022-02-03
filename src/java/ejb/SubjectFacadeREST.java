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
 * Class that manages SubjectFacadeREST
 * @author Aitor Ruiz de Gauna
 */
@Stateless
@Path("entities.subject")
public class SubjectFacadeREST extends AbstractFacade<Subject>{

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;
    private Logger LOGGER = Logger.getLogger(SubjectFacadeREST.class.getName());

    /**
     *  Constructor
     */
    public SubjectFacadeREST() {
        super(Subject.class);
    }
    /**
     * entity managers create method implementation.
     * @param entity  subject
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Subject entity) {
          super.create(entity);
    }
    /**
     * entity managers edit method implementation.
     * @param entity subject
     */
    @PUT
    @Path("{id}")
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(Subject entity) {
        super.edit(entity);
    }

    /**
     *  entity managers remove method implementation.
     * @param id id of the subject.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }
   
    /**
     *  method to find one subject
     * @param id id of the subject
     * @return an object of subject based on the id of the subject.
     */
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

    

    /**
     * Method to find all the existing subjects.
     * @return a collection of all the existing subjects.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Set<Subject> findAllSubject() {
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
     * method to find subject for student
     * @param fullName full name of the student
     * @return a collection of the subjects based on the full name of the students.
     */
    @GET
    @Path("student/{fullName}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Subject>findSubjectsByStudent(@PathParam("fullName")String fullName){
        Set<Subject>subjects=null;
        try{
            subjects=new HashSet<>(em.createNamedQuery("findSubjectsByStudent").setParameter("fullName",fullName).getResultList());
        }catch(Exception e){
            throw new InternalServerErrorException(e);
        }
        return subjects;
    }

    /**
     * method to find subjects for a course
     * @param name name of the course
     * @return a collection of the subject based on the course name.
     */
    @GET
    @Path("course/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Subject>findSubjectsByCourse(@PathParam("name")String name){
        Set<Subject>subjects=null;
        try{
            subjects=new HashSet<>(em.createNamedQuery("findSubjectsByCourse").setParameter("name",name).getResultList());
        }catch(Exception e){
            throw new InternalServerErrorException(e);
        }
        return subjects;
    }

    /**
     * method to find subjects for the teacher course.
     * @param name name of teacher course
     * @return a collection of the subjects based on the name of the teacher course
     */
    @GET
    @Path("teacherCourse/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Subject>findSubjectsByTeacherCourse(@PathParam("name")Long name){
        Set<Subject>subjects=null;
        try{
            subjects=new HashSet<>(em.createNamedQuery("findSubjectsByTeacherCourse").setParameter("idTeacherCourse",name).getResultList());
        }catch(Exception e){
            throw new InternalServerErrorException(e);
        }
        return subjects;
    }

    /**
     * method to find subject for exam
     * @param id of the exam
     * @return an object of subject based on the id of the exam.
     */
    @GET
    @Path("exam/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Subject findSubjectByExam(@PathParam("id")Long id){
        Subject subject=null;
        try{
            subject=(Subject) em.createNamedQuery("findSubjectByExam").setParameter("idExam",id).getSingleResult();
        }catch(Exception e){
            throw new InternalServerErrorException(e);
        }
        return subject;
    }

    /**
     *
     * @return an object of entity manager,
     */
    @Override
    protected EntityManager getEntityManager() {
       return em;
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
    public List<Subject> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     *entity managers count method implementation.
     * @return total number of all the existing subjects.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
}
