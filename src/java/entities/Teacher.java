/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Entity representing Teacher that extends from user.
 *
 * @author Zeeshan Yaqoob
 */
@Entity
@Table(name="teacher",schema="maz_solutions")
public class Teacher extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Field that identify Teacher.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTeacher;

    /**
     * Field that represent salary of the teacher.
     */
    private float salary;

    /**
     * An object of TeacherCourse.
     */
    @OneToMany(cascade = ALL, mappedBy = "teacher")
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
     * @return teacherCourse.
     */
    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }

    /**
     * An object of TeacherCourse.
     *
     * @param teacherCourse the teacherCourse to set.
     */
    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse = teacherCourse;
    }

    /**
     *
     * @return idTeacher.
     */
    public Long getIdTeacher() {
        return idTeacher;
    }

    /**
     * Field that identify Teacher.
     *
     * @param idTeacher the id to set.
     */
    public void setIdTeacher(Long idTeacher) {
        this.idTeacher = idTeacher;
    }

    /**
     * Integer representation of Teacher instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idTeacher);
        hash = 53 * hash + Float.floatToIntBits(this.salary);
        hash = 53 * hash + Objects.hashCode(this.teacherCourse);
        return hash;
    }

    /**
     * Compares two Teacher objects. This method consider a Teacher equal to
     * another Teacher if their id fields have the same value.
     *
     * @param object the other Teacher object to compare.
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
        if (!Objects.equals(this.idTeacher, other.idTeacher)) {
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
        return "Teacher{" + "idTeacher=" + idTeacher + ", salary=" + salary + ", teacherCourse=" + teacherCourse + '}';
    }

}
