/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 *
 * @author Aitor Ruiz de Gauna
 */
/**
 *Entity that has the info of the courses of the Teacher.
 */
@Entity
@Table(name="teacher_course",schema="maz_solutions")
public class TeacherCourse implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    //Object that contains the two id's of the class TeacherCourse.
    private TeacherCourseId idTeacherCourseId;
    // Date when the TeacherCourse starts. 
    private Date dateStart;
    // Date when the TeacherCourse ends.
    private Date dateEnd;
    //Collection of the subject that the teacher has.
    @ManyToMany(mappedBy="teacherCourses", fetch=FetchType.EAGER)
    private Set<Subject>subjects;
    //Teacher of the TeacherCourse
    @ManyToOne
    private Teacher teacher;
    /**
     * Method that returns the class that contains the id's of TeacherCourse.
     * @return idTeacherCourseId;
     */
    public TeacherCourseId getIdTeacherCourseId() {
        return idTeacherCourseId;
    }
    /**
     * Method that set the value of the object of the class TeacherCourseId that contains the id's of TeacherCourse.
     * @param idTeacherCourseId 
     */
    public void setIdTeacherCourseId(TeacherCourseId idTeacherCourseId) {
        this.idTeacherCourseId = idTeacherCourseId;
    }
    /**
     * Method that returns the date start of the TeacherCourse.
     * @return dateStart
     */
    public Date getDateStart() {
        return dateStart;
    }
    /**
     * Method that set the value of the dateStart of the TeacherCourse.
     * @param dateStart 
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    /**
     * Method that return the value of the dateEnd of TeacherCourse.
     * @return 
     */
    public Date getDateEnd() {
        return dateEnd;
    }
    /**
     * Method that set the value of the dateEnd of TeacherCourse.
     * @param dateEnd 
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    /**
     * Method that return the value of the collection of subjects of TeacherCourse.
     * @return 
     */
    public Set<Subject> getSubjects() {
        return subjects;
    }
    /**
     * Method that set the value of the collection of subjects of TeacherCourse.
     * @param subjects 
     */
    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
    /**
     * Method that return the value of the collection of teachers of TeacherCourse.
     * @return teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }
    /**
     * Method that set the value of the collection of subjects of TeacherCourse.
     * @param teacher 
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    /**
     * Integer representation for TeacherCourse instance.
     * @return 
     */
    @Override   
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idTeacherCourseId);
        hash = 67 * hash + Objects.hashCode(this.dateStart);
        hash = 67 * hash + Objects.hashCode(this.dateEnd);
        hash = 67 * hash + Objects.hashCode(this.subjects);
        hash = 67 * hash + Objects.hashCode(this.teacher);
        return hash;
    }

    /**
     * Method that compares if two objects of TeacherCourse are equals.
     * @param obj
     * @return boolean
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
        final TeacherCourse other = (TeacherCourse) obj;
        if (!Objects.equals(this.idTeacherCourseId, other.idTeacherCourseId)) {
            return false;
        }
        return true;
    }
    /**
     * Method that return a String of the parameters of TeacherCourse.
     * @return String
     */
    @Override
    public String toString() {
        return "TeacherCourse{" + "idTeacherCourseId=" + idTeacherCourseId + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", subjects=" + subjects + ", teacher=" + teacher + '}';
    }
}
