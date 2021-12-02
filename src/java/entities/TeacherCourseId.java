/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Aitor Ruiz de Gauna
 */
/**
 * Class that contains the id's for the TeacherCourse.
 */
@Embeddable
public class TeacherCourseId implements Serializable{
    //Id of the teacher.
    private Long idTeacher;
    //Id of the subject.
    private Long idSubject;
    /**
     * Method that return the id of the teacher.
     * @return idTeacher.
     */
    public long getIdTeacher() {
        return idTeacher;
    }
    /**
     * Method that set the value to the idTeacher.
     * @param idTeacher 
     */
    public void setIdTeacher(Long idTeacher) {
        this.idTeacher = idTeacher;
    }
    /**
     * Method that return the id of the subject.
     * @return idSubject.
     */
    public long getIdSubject() {
        return idSubject;
    }
    /**
    * Method that set the value to the idSubject.
    * @param idSubject 
    */
    public void setIdSubject(Long idSubject) {
        this.idSubject = idSubject;
    }
    /**
     * Integer representation for TeacherCourseId instance.
     * @return 
     */
    @Override    
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.idTeacher);
        hash = 41 * hash + Objects.hashCode(this.idSubject);
        return hash;
    }

    /**
     * Method that compares if two objects of TeacherCourseId are equals.
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
        final TeacherCourseId other = (TeacherCourseId) obj;
        if (this.idTeacher != other.idTeacher) {
            return false;
        }
        if (this.idSubject != other.idSubject) {
            return false;
        }
        return true;
    }
    /**
     * Method that return a String of the parameters of TeacherCourseId.
     * @return String
     */
    @Override
    public String toString() {
        return "TeacherCourseId{" + "idTeacher=" + idTeacher + ", idSubject=" + idSubject + '}';
    }
    
    
}
