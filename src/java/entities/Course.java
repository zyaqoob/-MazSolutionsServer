/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing Course of the application. It contains the following fields:
 * name, dateStart, dateEnd, subjects, students.
 * @author Miguel Angel Sanchez
 */
@NamedQueries({
    @NamedQuery(
        name="findCourseById", query="SELECT c FROM Course c WHERE c.idCourse=:idCourse"
    ),
    @NamedQuery(
        name="findAllCourses", query="SELECT c FROM Course c ORDER BY c.idCourse DESC"
    ),
    @NamedQuery(
        name="findCourseByStudent", query="SELECT s.course FROM Student s WHERE s.idUser=:idUser"
    ),
    @NamedQuery(
        name="findCoursesBySubject",query="SELECT cs.course FROM CourseSubject cs WHERE cs.subject.idSubject=:idSubject"
    )
})
@Entity
@Table(name="course",schema="maz_solutions")
@XmlRootElement
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Identification field for the course.
     */
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCourse;
    /**
     * Name field for the course.
     */
    private String name;
    /**
     * The date of the course start.
     */
    @Temporal(TemporalType.DATE)
    private Date dateStart;
    /**
     * The date of the course end.
     */
    @Temporal(TemporalType.DATE)
    private Date dateEnd;
    /**
     * The subjects of the course.
     */
    @OneToMany(cascade=ALL,mappedBy="course",fetch=FetchType.EAGER)
    private Set<CourseSubject> courseSubjects;
    /**
     * The students of the course.
     */
    @OneToMany(cascade=ALL, mappedBy="course",fetch=FetchType.EAGER)
    private Set<Student> students;
    /**
     * This method returns the course id.
     * @return 
     */
    public Long getIdCourse() {
        return idCourse;
    }
    /**
     * This method set the course id.
     * @param idCourse 
     */
    public void setIdCourse(Long idCourse) {
        this.idCourse = idCourse;
    }
    /**
     * This method returns the course name.
     * @return 
     */
    public String getName() {
        return name;
    }
    /**
     * This method set the course name.
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * This method return the date start of the course.
     * @return 
     */
    public Date getDateStart() {
        return dateStart;
    }
    /**
     * This method set the date start of the course.
     * @param dateStart 
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    /**
     * This method returns the date end of the course.
     * @return 
     */
    public Date getDateEnd() {
        return dateEnd;
    }
    /**
     * This method set the date end of the course.
     * @param dateEnd 
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    /**
     * This method return a set of the subjects from the course.
     * @return 
     */
    @XmlTransient
    public Set<CourseSubject> getCourseSubjects() {
        return courseSubjects;
    }
    /**
     * This method set a set of subjects in the course.
     * @param courseSubjects
     */
    public void setCourseSubject(Set<CourseSubject> courseSubjects) {    
        this.courseSubjects = courseSubjects;
    }
    /**
     * This method get a set of students from the course.
     * @return 
     */
    @XmlTransient
    public Set<Student> getStudents() {
        return students;
    }
    /**
     * This method set a set of students in the course.
     * @param students 
     */
    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    /**
     * Integer representation for Course instance.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idCourse);     
        return hash;
    }
    /**
     * Compares two Courses objects for equality. This method consider a Course 
     * equal to another Course if their id fields have the same value. 
     * @param obj The other Course to compare to
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
        final Course other = (Course) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.idCourse, other.idCourse)) {
            return false;
        }
        if (!Objects.equals(this.dateStart, other.dateStart)) {
            return false;
        }
        if (!Objects.equals(this.dateEnd, other.dateEnd)) {
            return false;
        }
        if (!Objects.equals(this.courseSubjects, other.courseSubjects)) {
            return false;
        }
        if (!Objects.equals(this.students, other.students)) {
            return false;
        }
        return true;
    }
    /**
     * Obtains a string representation of the Course.
     * @return The String representing the Course.
     */
    @Override
    public String toString() {
        return "Course{" + "id=" + idCourse + ", name=" + name + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", subjects=" + courseSubjects + ", students=" + students + '}';
    }

}