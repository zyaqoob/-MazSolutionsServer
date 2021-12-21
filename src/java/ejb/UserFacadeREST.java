/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import crypto.Crypto;
import entities.User;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
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
@Path("entities.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, User entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("email/{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
    public User findUserByEmail(@PathParam("email") String email) {
        User user = null;
        String password;
        String hashedPassword;
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[10];
        random.nextBytes(bytes);
        password = new String(bytes);
        //Hashear la password
        //Enviar password hasheada a la db

        //Enviar password sin hashear al usuario
        //password = characterLowCase[(int) (Math.random() * 9)] + characterCapital[(int) (Math.random() * 9)] + numbers[(int) (Math.random() * 9)] + characterLowCase[(int) (Math.random() * 9)] + characterCapital[(int) (Math.random() * 9)] + numbers[(int) (Math.random() * 9)];
        byte[] array = new byte[10];
        new Random().nextBytes(array);
        try {
            hashedPassword = new String(Crypto.cifrar(password));
            hashedPassword = Crypto.descifrar(hashedPassword);
            user = (User) em.createNamedQuery("findUserByEmail").setParameter("email", email).getSingleResult();
            if (user != null && em.contains(user)) {
                user.setPassword(hashedPassword);
            } else if (!em.contains(user)) {
                em.merge(user);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return user;
    }

    @GET
    @Path("password/{login}/{password}/{newPassword}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
    public User findUserByPassword(@PathParam("login") String login, @PathParam("password") String password, @PathParam("newPassword") String newPassword) {
        User user = null;
        try {
            user = (User) em.createNamedQuery("findUserByPassword").setParameter("password", password).setParameter("login", login).getSingleResult();
            if (user != null && em.contains(user)) {
                user.setPassword(newPassword);
            } else if (!em.contains(user)) {
                em.merge(user);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return user;
    }

    @GET
    @Path("login/{login}/{password}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
    public User login(@PathParam("login") String login, @PathParam("password") String password) {
        User user = null;
        try {
            user = (User) em.createNamedQuery("findUserByPassword").setParameter("password", password).setParameter("login", login).getSingleResult();
            if (user == null) {
                throw new NotAuthorizedException("Authentication failure");
            }
        } catch (NotAuthorizedException e) {
            throw new NotAuthorizedException(e);
        }
        return user;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
