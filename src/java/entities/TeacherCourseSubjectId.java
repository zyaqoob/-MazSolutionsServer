/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author 2dam
 */
public class TeacherCourseSubjectId implements Serializable{
    private Long idTeacherCourse;
    private Long idSubject;

    public Long getIdTeacherCourse() {
        return idTeacherCourse;
    }

    public void setIdTeacherCourse(Long idTeacherCourse) {
        this.idTeacherCourse = idTeacherCourse;
    }

    public Long getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Long idSubject) {
        this.idSubject = idSubject;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idTeacherCourse);
        hash = 59 * hash + Objects.hashCode(this.idSubject);
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
        final TeacherCourseSubjectId other = (TeacherCourseSubjectId) obj;
        if (!Objects.equals(this.idTeacherCourse, other.idTeacherCourse)) {
            return false;
        }
        if (!Objects.equals(this.idSubject, other.idSubject)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeacherCourseSubjectId{" + "idTeacher=" + idTeacherCourse + ", idSubject=" + idSubject + '}';
    }
    
}
