/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity representing Course of the application. It contains the following fields:
 * name, dateStart, dateEnd, subjects, students.
 * @author Miguel Angel Sanchez
 */
@Entity
@Table(name="course",schema="maz_solutions")
public class Course implements Serializable {
    
    /**
     * Identification field for the course.
     */
    @EmbeddedId CourseId id;
    /**
     * Name field for the course.
     */
    private String name;
    /**
     * The date of the course start.
     */
    private LocalDate dateStart;
    /**
     * The date of the course end.
     */
    private LocalDate dateEnd;
    /**
     * The subjects of the course.
     */
    @ManyToMany(mappedBy="courses",fetch=FetchType.EAGER)
    private List<Subject> subjects;
    /**
     * The students of the course.
     */
    @OneToMany(cascade=ALL, mappedBy="course")
    private List<Student> students;
    /**
     * This method returns the course id.
     * @return 
     */
    public CourseId getId() {
        return id;
    }
    /**
     * This method set the course id.
     * @param id 
     */
    public void setId(CourseId id) {
        this.id = id;
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
    public LocalDate getDateStart() {
        return dateStart;
    }
    /**
     * This method set the date start of the course.
     * @param dateStart 
     */
    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }
    /**
     * This method returns the date end of the course.
     * @return 
     */
    public LocalDate getDateEnd() {
        return dateEnd;
    }
    /**
     * This method set the date end of the course.
     * @param dateEnd 
     */
    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }
    /**
     * This method return a list of the subjects from the course.
     * @return 
     */
    public List<Subject> getSubjects() {
        return subjects;
    }
    /**
     * This method set a list of subjects in the course.
     * @param subjects 
     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
    /**
     * This method get a list of students from the course.
     * @return 
     */
    public List<Student> getStudents() {
        return students;
    }
    /**
     * This method set a list of students in the course.
     * @param students 
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    /**
     * Integer representation for Course instance.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.dateStart);
        hash = 53 * hash + Objects.hashCode(this.dateEnd);
        hash = 53 * hash + Objects.hashCode(this.subjects);
        hash = 53 * hash + Objects.hashCode(this.students);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateStart, other.dateStart)) {
            return false;
        }
        if (!Objects.equals(this.dateEnd, other.dateEnd)) {
            return false;
        }
        if (!Objects.equals(this.subjects, other.subjects)) {
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
        return "Course{" + "id=" + id + ", name=" + name + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", subjects=" + subjects + ", students=" + students + '}';
    }
    
    
    

    

    
    
}
