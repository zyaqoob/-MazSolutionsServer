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
@Table(name="CourseSubject",schema="maz_solutions")
@XmlRootElement
public class CourseSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private CourseSubjectId courseSubjectId;
    
    private float totalHours;
    @ManyToOne
    @JoinColumn(name = "idCourse", updatable = false, insertable = false)
    private Course course;
    @ManyToOne
    @JoinColumn(name = "idSubject", updatable = false, insertable = false)
    private Subject subject;

    public CourseSubjectId getCourseSubjectId() {
        return courseSubjectId;
    }

    public void setCourseSubjectId(CourseSubjectId courseSubjectId) {
        this.courseSubjectId = courseSubjectId;
    }

    public float getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.courseSubjectId);
        hash = 79 * hash + Float.floatToIntBits(this.totalHours);
        hash = 79 * hash + Objects.hashCode(this.course);
        hash = 79 * hash + Objects.hashCode(this.subject);
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
        final CourseSubject other = (CourseSubject) obj;
        if (Float.floatToIntBits(this.totalHours) != Float.floatToIntBits(other.totalHours)) {
            return false;
        }
        if (!Objects.equals(this.courseSubjectId, other.courseSubjectId)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CourseSubject{" + "courseSubjectId=" + courseSubjectId + ", totalHours=" + totalHours + ", course=" + course + ", subject=" + subject + '}';
    }
    
}
