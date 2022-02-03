/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;



import entities.Exam;
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
 * Class that manages ExamFacadeREST
 * @author Zeeshan
 */
@Stateless
@Path("entities.exam")
public class ExamFacadeREST extends AbstractFacade<Exam> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;
   private static final Logger LOGGER = Logger.getLogger(ExamFacadeREST.class.getName());

    /**
     *
     */
    public ExamFacadeREST() {
        super(Exam.class);
    }

    /**
     * entity managers create method implementation.
     * @param entity exam.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Exam entity) {
        try {
            super.create(entity);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * entity managers edit method implementation.
     * @param id of the the exam
     * @param entity exam.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Exam entity) {
        try {
            super.edit(entity);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    /**
     *  entity managers remove method implementation.
     * @param id id of the exam.
     */
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

    /**
     * 
     * @param id id of the exam.
     * @return an object of the exam based on the id of the exam.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Exam find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

   

    /**
     * Method that find all the existing exams.
     * @return a collection of exam.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Set<Exam> findAllExam() {
        Set<Exam> exams = new HashSet<>();
        try {
            exams = new HashSet<>(em.createNamedQuery("findAllExam").getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return exams;
    }

    /**
     *  Method that find exam for subject.
     * @param subject_name name of the subject.
     * @return a collection of the exam based on the subject name.
     */
    @GET
    @Path("subject/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Exam> findExamsBySubject(@PathParam("name") String subject_name) {
        Set<Exam> exams = null;
        try {
            exams = new HashSet<>(em.createNamedQuery("findExamsBySubject").setParameter("subject_name", subject_name).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return exams;
    }

    /**
     *  method that find exams for one student
     * @param name student name.
     * @return a collection of the exam based on the student name.
     */
    @GET
    @Path("student/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Exam> findExamsByStudent(@PathParam("name") String name) {
        Set<Exam> exams = null;
        try {
            exams = new HashSet<>(em.createNamedQuery("findExamsByStudent").setParameter("student_name", name).getResultList());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return exams;
    }

    /**
     * method to find the exam for exam session.
     * @param id id of the exam session
     * @return an object of the exam based on the exam session.
     */
    @GET
    @Path("examSession/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Exam findExamByExamSession(@PathParam("id") Long id) {
        Exam exam = null;
        try {
            exam = (Exam) em.createNamedQuery("findExamByExamSession").setParameter("idExam", id).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return exam;
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
    public List<Exam> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * entity managers count method implementation.
     * @return the total number of the existing exam.
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
     * @return an object of the entity manager.
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

}
