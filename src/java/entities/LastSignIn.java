/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *Class that represents LastSignIn entity.
 * @author 2dam
 */

@NamedQueries({
    @NamedQuery(
            name = "findByUserLogin", query = "SELECT l FROM LastSignIn l WHERE l.user =:user ORDER BY l.lastSignIn ASC")
}
)
@Entity
@Table(name="lastSignIn",schema="maz_solutions")
@XmlRootElement
public class LastSignIn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastSignIn;
    
    @ManyToOne
    private User user;
    
    /**
     *
     * @return lastsignin
     */
    public Calendar getLastSignIn() {
        return lastSignIn;
    }

    /**
     *
     * @param lastSignIn lastsignin to set
     */
    public void setLastSignIn(Calendar lastSignIn) {
        this.lastSignIn = lastSignIn;
    }
    
    /**
     *
     * @return user
     */
    @XmlTransient
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LastSignIn other = (LastSignIn) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LastSignIn{" + "id=" + id + ", lastSignIn=" + lastSignIn + ", user=" + user + '}';
    }

    
    
}
