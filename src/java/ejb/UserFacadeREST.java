
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import crypto.Crypto;
import entities.User;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
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
        String password=Crypto.descifrar(entity.getPassword());
        password=Crypto.hashPassword(password);
        entity.setPassword(password);
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
    public List<User> findAll() {
        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\z332h\\OneDrive\\Escritorio\\pepe.txt")) {
            String mensaje=Crypto.cifrar("QdQTUliRpfjScPqRCgn4nkFMtes=");
            fos.write(mensaje.getBytes());
            //String mensajeDesc=Crypto.descifrar(mensaje);
            //fos.write(Crypto.descifrar(mensajeDesc).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findAll();
    }

    @GET
    @Path("email/{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
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
            user = (User) em.createNamedQuery("findUserByEmail").setParameter("email", email).getSingleResult();
            if (user != null && em.contains(user)) {
                user.setPassword(hashedPassword);
                sendPasswordToUser(password, user.getEmail());
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
           String  hashedPassword=Crypto.hashPassword(password);
            user = (User) em.createNamedQuery("findUserByPassword").setParameter("password", hashedPassword).setParameter("login", login).getSingleResult();
            if (user != null && em.contains(user)) {
                newPassword=Crypto.hashPassword(newPassword);
                user.setPassword(newPassword);
                sendPasswordToUser("Password change correctly", user.getEmail());
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
            password=Crypto.descifrar(password);
            password=Crypto.hashPassword(password);
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

    private void sendPasswordToUser(String password, String correo) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("mazsolutionsgi2@gmail.com", "abcd*1234");
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mazsolutionsgi2@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
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
}
