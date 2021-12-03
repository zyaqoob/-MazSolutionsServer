/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *  Entity representing Student of the application. It contains the following fields:
 * year, sessions and courser.
 * @author Miguel Ángel Sánchez
 */
@Entity
@Table(name="student",schema="maz_solutions")
@XmlRootElement
public class Student extends User implements Serializable{
    
    /**
     * Year when the Student is registered.
     */
    @Temporal(TemporalType.DATE)
    private Date year;
    /**
     * Examn sessions where the student are being evaluated.
     */
    @OneToMany(cascade=ALL, mappedBy="student")
    private List<ExamSession> sessions;
    /**
     * Course where the student is registered.
     */
    @ManyToOne
    private Course course;
    /**
     * 
     * @return This method returns the year of the student.
     */
    public Date getYear() {
        return year;
    }
    /**
     * 
     * @param year This method set the year of the student.
     */
    public void setYear(Date year) {
        this.year = year;
    }
    /**
     * 
     * @return This method returns a List with the exam sessions of the student.
     */
    @XmlTransient
    public List<ExamSession> getSessions() {
        return sessions;
    }
    /**
     * 
     * @param sessions This method set the sessions of the student.
     */
    public void setSessions(List<ExamSession> sessions) {
        this.sessions = sessions;
    }
    /**
     * 
     * @return This method returns a course.
     */
    public Course getCourse() {
        return course;
    }
    /**
     * 
     * @param course This method set a course.
     */
    public void setCourse(Course course) {
        this.course = course;
    }
    /**
     * 
     * @return Integer representation for Student instance.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.getUserId());
        hash = 53 * hash + Objects.hashCode(this.year);
        hash = 53 * hash + Objects.hashCode(this.sessions);
        hash = 53 * hash + Objects.hashCode(this.course);
        return hash;
    }
    /**
     * Compares two Student objects for equality. This method consider a Student 
     * equal to another Student if their id fields have the same value. 
     * @param obj The other Student to compare to
     * @return Returns true or false depending if the fields are equals.
     */
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
        final Student other = (Student) obj;
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.sessions, other.sessions)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.getUserId(), other.getUserId())) {
            return false;
        }
        return true;
    }
    /**
     * Obtains a string representation of the Student.
     * @return The String representing the Student.
     */
    @Override
    public String toString() {
        return "Student{" + "year=" + year + ", sessions=" + sessions + ", course=" + course + '}';
    }
    
    
    

    
    
}
