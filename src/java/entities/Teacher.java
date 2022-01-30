/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing Teacher that extends from user.
 *
 * @author Zeeshan Yaqoob
 */
@NamedQueries({
    @NamedQuery(name = "findAllTeacher",
            query = "SELECT t FROM Teacher t")
    ,@NamedQuery(name = "findTeacherByCourse",
            query = "SELECT t FROM Teacher t WHERE t.teacherCourse.idTeacherCourse=:id_teacher_course")
    ,@NamedQuery(name = "findTeacherByLogin", 
            query = "SELECT t from Teacher t WHERE t.login=:login")
    ,@NamedQuery(name = "findExistingTeacher",
            query = "SELECT t from Teacher t WHERE t.login=:login or t.email=:email")
})
@Entity
@Table(name = "teacher", schema = "maz_solutions")
@XmlRootElement
public class Teacher extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Field that represent salary of the teacher.
     */
    private float salary;

    /**
     * A collection of TeacherCourses.
     */
    @ManyToOne
    private TeacherCourse teacherCourse;

    /**
     *
     * @return salary.
     */
    public float getSalary() {
        return salary;
    }

    /**
     * Field that represent salary of the teacher.
     *
     * @param salary the salary to set.
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     *
     * @return teacherCourses.
     */
    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }

    /**
     * A collection of TeacherCourse.
     *
     * @param teacherCourse the teacherCourses to set.
     */
    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse = teacherCourse;
    }

    /**
     * Integer representation of Teacher instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.getIdUser());
        hash = 53 * hash + Float.floatToIntBits(this.salary);
        hash = 53 * hash + Objects.hashCode(this.teacherCourse);
        return hash;
    }

    /**
     * Compares two Teacher objects. This method consider a Teacher equal to
     * another Teacher if their id fields have the same value.
     *
     * @param obj the other Teacher object to compare.
     * @return true in case they are same.
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
        final Teacher other = (Teacher) obj;
        if (!Objects.equals(this.getIdUser(), other.getIdUser())) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Teacher{" + "idTeacher=" + getIdUser() + ", salary=" + salary + ", teacherCourses=" + teacherCourse + '}';
    }

}
