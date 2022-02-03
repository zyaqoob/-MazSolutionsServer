/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.LastSignIn;
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

/**
 *Class that manages LastSignInFacadeREST
 * @author Aitor, Miguel
 */
@Stateless
@Path("entities.lastsignin")
public class LastSignInFacadeREST extends AbstractFacade<LastSignIn> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;

    /**
     * Constructor
     */
    public LastSignInFacadeREST() {
        super(LastSignIn.class);
    }

    /**
     * entity managers create method implementation.
     *
     * @param entity lastSignIn
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(LastSignIn entity) {
        super.create(entity);
    }

    /**
     * entity managers edit method implementation.
     *
     * @param id of the lastSignIn
     * @param entity lastsignin
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, LastSignIn entity) {
        super.edit(entity);
    }

    /**
     *entity managers remove method implementation.
     * @param id id the lastSignIn
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     *  entity managers find method implementation.
     * @param id of the last sign in
     * @return an obejct of the lastsignin based on the id.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public LastSignIn find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     *  entity managers findAll method implementation.
     * @return a collection of all the existing lastsignin record.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<LastSignIn> findAll() {
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<LastSignIn> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     *entity managers count method implementation.
     * @return total number of the existing lastsignin records.
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
