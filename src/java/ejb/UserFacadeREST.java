
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import crypto.Crypto;
import entities.LastSignIn;
import entities.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
import javax.ws.rs.WebApplicationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *  Class that manages UserFacadeREST
 * @author 2dam
 */
@Stateless
@Path("entities.user")
public class UserFacadeREST extends AbstractFacade<User> {
    
     private Logger LOGGER = Logger.getLogger(SubjectFacadeREST.class.getName());
    @PersistenceContext(unitName = "MazSolutionsServerPU")
    private EntityManager em;
    private String mensaje;

    /**
     *
     */
    public UserFacadeREST() {
        super(User.class);
    }

    /**
     *entity managers create method implementation.
     * @param entity user
     * @throws WebApplicationException if user exists.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) throws WebApplicationException{
        
            String password = Crypto.descifrar(entity.getPassword());
            password = Crypto.hashPassword(password);
            entity.setPassword(password);
            if (findExistingUser(entity.getEmail(), entity.getLogin()) != null) {
                throw new WebApplicationException(Response.Status.CONFLICT);
            }
            if (!em.contains(entity)) {
                em.merge(entity);
            }
            em.flush();
        

    }

    /**
     *entity managers edit method implementation.
     * @param id id of the user
     * @param entity user
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, User entity) {
        super.edit(entity);
    }

    /**
     *entity managers remove method implementation.
     * @param id id of the user
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * method to find user
     * @param id id of the user
     * @return an object of the user based on id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("id") Long id) {
        User userChild = super.find(id);
        User user = new User();
        user.setFullName(userChild.getFullName());
        user.setEmail(userChild.getEmail());
        user.setLogin(userChild.getLogin());
        user.setPassword(userChild.getPassword());
        user.setLastPasswordChange(userChild.getLastPasswordChange());
        user.setLastSignIn(userChild.getLastSignIn());
        user.setIdUser(userChild.getIdUser());
        user.setPrivilege(userChild.getPrivilege());
        user.setTelephone(userChild.getTelephone());
        user.setBirthDate(userChild.getBirthDate());
        user.setStatus(userChild.getStatus());
        return user;
    }

    /**
     * method to find all existing users
     * @return a collection of the users,
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAll() {
        return super.findAll();
    }

    /**
     *  method to find user by email
     * @param email email of the user.
     * @return an obejct of the the user.
     */
    @GET
    @Path("email/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public User findUserByEmail(@PathParam("email") String email) {
        User user = null;
        String password;
        String hashedPassword;
        String charLow[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String charUpper[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String number[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        //Hashear la password
        //Enviar password hasheada a la db
        //Enviar password sin hashear al usuario
        password = charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)] + charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)];
        //byte[] array = new byte[10];
        //new Random().nextBytes(array);
        try {
            hashedPassword = Crypto.hashPassword(password);
            User userChild = (User) em.createNamedQuery("findUserByEmail").setParameter("email", email).getSingleResult();
            user = new User();
            user.setFullName(userChild.getFullName());
            user.setEmail(userChild.getEmail());
            user.setLogin(userChild.getLogin());
            user.setPassword(password);
            user.setLastPasswordChange(userChild.getLastPasswordChange());
            user.setLastSignIn(userChild.getLastSignIn());
            user.setIdUser(userChild.getIdUser());
            user.setPrivilege(userChild.getPrivilege());
            user.setTelephone(userChild.getTelephone());
            user.setBirthDate(userChild.getBirthDate());
            user.setStatus(userChild.getStatus());
            if (em.contains(userChild)) {
                userChild.setPassword(hashedPassword);
                sendPasswordToUser(password, userChild.getEmail());
            } else {
                em.merge(userChild);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return user;
    }

    /**
     * method ton change password of the user
     * @param login login of the user
     * @param password old password of the user
     * @param newPassword new password of the user
     * @return an obejct of the user.
     */
    @GET
    @Path("password/{login}/{password}/{newPassword}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
    public User findUserByPassword(@PathParam("login") String login, @PathParam("password") String password, @PathParam("newPassword") String newPassword) {
        User user = null;
        try {
            password = Crypto.descifrar(password);
            String hashedPassword = Crypto.hashPassword(password);
            User userChild = (User) em.createNamedQuery("findUserByPassword").
                    setParameter("password", hashedPassword).
                    setParameter("login", login).
                    getSingleResult();
            user = new User();
            user.setFullName(userChild.getFullName());
            user.setEmail(userChild.getEmail());
            user.setLogin(userChild.getLogin());
            user.setPassword(userChild.getPassword());
            user.setLastPasswordChange(userChild.getLastPasswordChange());
            user.setLastSignIn(userChild.getLastSignIn());
            user.setIdUser(userChild.getIdUser());
            user.setPrivilege(userChild.getPrivilege());
            user.setTelephone(userChild.getTelephone());
            user.setBirthDate(userChild.getBirthDate());
            user.setStatus(userChild.getStatus());
            if (em.contains(userChild)) {
                newPassword= Crypto.descifrar(newPassword);
                newPassword = Crypto.hashPassword(newPassword);
                userChild.setPassword(newPassword);
                sendPasswordToUser("Password change correctly", userChild.getEmail());
            } else if (!em.contains(userChild)) {
                em.merge(userChild);
            }
        }catch (NotAuthorizedException e){
            throw new NotAuthorizedException(e);
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return user;
    }

    /**
     *  Method to signin to app-
     * @param login login ofn the user
     * @param password password of the user
     * @return an object of the user.
     */
    @GET
    @Path("login/{login}/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public User login(@PathParam("login") String login, @PathParam("password") String password) {
        User user = null;
        try {

            password = Crypto.descifrar(password);
            password = Crypto.hashPassword(password);
            User userChild = (User) em.createNamedQuery("findUserByPassword").
                    setParameter("password", password).
                    setParameter("login", login).
                    getSingleResult();
            user = new User();
            user.setFullName(userChild.getFullName());
            user.setEmail(userChild.getEmail());
            user.setLogin(userChild.getLogin());
            user.setPassword(userChild.getPassword());
            user.setLastPasswordChange(userChild.getLastPasswordChange());
            user.setLastSignIn(userChild.getLastSignIn());
            user.setIdUser(userChild.getIdUser());
            user.setPrivilege(userChild.getPrivilege());
            user.setTelephone(userChild.getTelephone());
            user.setBirthDate(userChild.getBirthDate());
            user.setStatus(userChild.getStatus());

            List<LastSignIn> lastSignIns = new ArrayList<>();
            lastSignIns = (ArrayList) em.createNamedQuery("findByUserLogin").setParameter("user", user).getResultList();
            if (lastSignIns.size() < 10) {
                LastSignIn lastSignIn = new LastSignIn();
                lastSignIn.setId(null);
                lastSignIn.setLastSignIn(Calendar.getInstance());
                lastSignIn.setUser(user);
                em.persist(lastSignIn);
            } else {
                LastSignIn lis = lastSignIns.get(0);
                lis.setLastSignIn(Calendar.getInstance());
                em.merge(lis);
            }

        } catch (NotAuthorizedException e) {
            throw new NotAuthorizedException(e);
        }
        return user;
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
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     *
     * @return total number of the user,
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     *
     * @return an object of the entity manager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Method to reset the password of the user.
     * @param password new generated password
     * @param correo email of the user
     */
    private void sendPasswordToUser(String password, String correo) {
        try {
            String email, emailPassword;
            email = Crypto.descifrarEmail(ResourceBundle.getBundle("crypto.CipherKey").getString("KEY"));
            emailPassword = Crypto.descifrarPassword(ResourceBundle.getBundle("crypto.CipherKey").getString("KEY"));
            Properties properties = new Properties();
            //Que tipo de email
            properties.put("mail.smtp.host", "smtp.gmail.com");
            //Puerto
            properties.put("mail.smtp.port", "465");
            //Seguridad?? preguntar duda
            properties.put("mail.smtp.ssl.enable", "true");
            //necesario autenticarse
            properties.put("mail.smtp.auth", "true");

            //aquí hacemos la autentificacion
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, emailPassword);
                }
            });
            Message message = new MimeMessage(session);
            //email que manda el mensaje
            message.setFrom(new InternetAddress("mazsolutionsgi2@gmail.com"));
            //a quien se manda el mensaje
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
            //asunto
            message.setSubject("Password Change");

            Multipart multipart = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(password, "text/html");
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (AddressException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Method to find existing user
     * @param email email of the user
     * @param login login of the user
     * @return an object of the user.
     */
    @GET
    @Path("existing/{email}/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public User findExistingUser(@PathParam("email") String email, @PathParam("login") String login) {
        User user = null;

        user = (User) em.createNamedQuery("findExistingUser").setParameter("login", login).setParameter("email", email).getSingleResult();
       
        return user;
    }
}
