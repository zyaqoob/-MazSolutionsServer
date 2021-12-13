/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dam
 */
@Entity
@Table(name="TeacherCourseSubject",schema="maz_solutions")
@XmlRootElement
public class TeacherCourseSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private TeacherCourseSubjectId teacherCourseSubjectId;
    private float totalHours;
    @ManyToOne
    @JoinColumn(name = "idTeacherCourse", updatable = false, insertable = false)
    private TeacherCourse teacherCourse;
    @ManyToOne
    @JoinColumn(name = "idSubject", updatable = false, insertable = false)
    private Subject subject;

    public TeacherCourseSubjectId getTeacherCourseSubjectId() {
        return teacherCourseSubjectId;
    }

    public void setTeacherCourseSubjectId(TeacherCourseSubjectId teacherCourseSubjectId) {
        this.teacherCourseSubjectId = teacherCourseSubjectId;
    }

    public float getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }

    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }

    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse = teacherCourse;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.teacherCourseSubjectId);
        hash = 53 * hash + Float.floatToIntBits(this.totalHours);
        hash = 53 * hash + Objects.hashCode(this.teacherCourse);
        hash = 53 * hash + Objects.hashCode(this.subject);
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
        final TeacherCourseSubject other = (TeacherCourseSubject) obj;
        if (Float.floatToIntBits(this.totalHours) != Float.floatToIntBits(other.totalHours)) {
            return false;
        }
        if (!Objects.equals(this.teacherCourseSubjectId, other.teacherCourseSubjectId)) {
            return false;
        }
        if (!Objects.equals(this.teacherCourse, other.teacherCourse)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeacherCourseSubject{" + "teacherCourseSubjectId=" + teacherCourseSubjectId + ", totalHours=" + totalHours + ", teacher=" + teacherCourse + ", subject=" + subject + '}';
    }
    
}
