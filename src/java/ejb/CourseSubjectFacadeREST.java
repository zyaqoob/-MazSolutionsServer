/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.CourseSubject;
import entities.CourseSubjectId;
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
 * Class that manages CourseSubjectFacadeREST
 * @author Miguel
 */
@Stateless
@Path("entities.coursesubject")
public class CourseSubjectFacadeREST extends AbstractFacade<CourseSubject> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;

    private CourseSubjectId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;idCourse=idCourseValue;idSubject=idSubjectValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.CourseSubjectId key = new entities.CourseSubjectId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> idCourse = map.get("idCourse");
        if (idCourse != null && !idCourse.isEmpty()) {
            key.setIdCourse(new java.lang.Long(idCourse.get(0)));
        }
        java.util.List<String> idSubject = map.get("idSubject");
        if (idSubject != null && !idSubject.isEmpty()) {
            key.setIdSubject(new java.lang.Long(idSubject.get(0)));
        }
        return key;
    }

    /**
     * Constructor.
     */
    public CourseSubjectFacadeREST() {
        super(CourseSubject.class);
    }

    /**
     *  entity managers create method implementation.
     * @param entity CouseSubject
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(CourseSubject entity) {
        super.create(entity);
    }

    /**
     * entity managers edit method implementation.
     * @param id id of the CourseSubject
     * @param entity CouseSubject
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") PathSegment id, CourseSubject entity) {
        super.edit(entity);
    }

    /**
     *  entity managers remove method implementation.
     * @param id id of the coursesubject.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entities.CourseSubjectId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    /**
     *
     * @param id of the couse subject
     * @return an object of the courseSubject.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public CourseSubject find(@PathParam("id") PathSegment id) {
        entities.CourseSubjectId key = getPrimaryKey(id);
        return super.find(key);
    }

    /**
     *  entity managers find all method implementation.
     * @return a collection of the CourseSubject.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<CourseSubject> findAll() {
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
    public List<CourseSubject> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     *
     * @return total number of the records of the coursesubject.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
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
