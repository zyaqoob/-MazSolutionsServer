/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.TeacherCourseSubject;
import entities.TeacherCourseSubjectId;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *  Class that manages TeacherCourseSubjectFacadeREST
 * @author 2dam
 */
@Stateless
@Path("entities.teachercoursesubject")
public class TeacherCourseSubjectFacadeREST extends AbstractFacade<TeacherCourseSubject> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;

    private TeacherCourseSubjectId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;idTeacherCourse=idTeacherCourseValue;idSubject=idSubjectValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.TeacherCourseSubjectId key = new entities.TeacherCourseSubjectId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> idTeacherCourse = map.get("idTeacherCourse");
        if (idTeacherCourse != null && !idTeacherCourse.isEmpty()) {
            key.setIdTeacherCourse(new java.lang.Long(idTeacherCourse.get(0)));
        }
        java.util.List<String> idSubject = map.get("idSubject");
        if (idSubject != null && !idSubject.isEmpty()) {
            key.setIdSubject(new java.lang.Long(idSubject.get(0)));
        }
        return key;
    }

    /**
     *
     */
    public TeacherCourseSubjectFacadeREST() {
        super(TeacherCourseSubject.class);
    }

    /**
     *entity managers create method implementation.
     * @param entity teacherCourseSubject
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(TeacherCourseSubject entity) {
        super.create(entity);
    }

    /**
     *entity managers edit method implementation.
     * @param id id of the teacherCourseSubject
     * @param entity teachercoursesubject
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") PathSegment id, TeacherCourseSubject entity) {
        super.edit(entity);
    }

    /**
     *entity managers remove method implementation.
     * @param id id of the teacherCOurseSubject
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entities.TeacherCourseSubjectId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    /**
     *  method to find teachercoursesubject
     * @param id id of the tecahercoursesubject
     * @return an object of teachercoursesubject based on id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public TeacherCourseSubject find(@PathParam("id") PathSegment id) {
        entities.TeacherCourseSubjectId key = getPrimaryKey(id);
        return super.find(key);
    }

    /**
     *  method to find all the existing tecahercoursesubject
     * @return a collection of teachercoursesubject
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<TeacherCourseSubject> findAll() {
        return super.findAll();
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
    public List<TeacherCourseSubject> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     *
     * @return total number of teachercoursesubject
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     *
     * @return an object of entity manager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
