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
 *Embeddable class that contains the ID's of exam and student.
 * @author Zeeshan Yaqoob.
 */
@Embeddable
public class ExamSessionId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Field that identify Exam.
     */
    private Long idExam;
    /**
     * Field that identify Student.
     */
    private Long idStudent;

    /**
     *
     * @return idExam
     */
    public Long getExamId() {
        return idExam;
    }

    /**
     * Field that represent exam id.
     * @param idExam the examId to set.
     */
    public void setExamId(Long idExam) {
        this.idExam = idExam;
    }

    /**
     *
     * @return idStudent
     */
    public Long getStudentId() {
        return idStudent;
    }

    /**
     *  Field that represent studentId.
     * @param idStudent
     */
    public void setStudentId(Long idStudent) {
        this.idStudent = idStudent;
    }
/**
 * Integer representation of examId and studentId instance. 
 * @return 
 */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.idExam);
        hash = 29 * hash + Objects.hashCode(this.idStudent);
        return hash;
    }
/**
 * compares two objects of examId's and examSession's.
 * @param obj the other object to compare
 * @return true incase they are equal.
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
        final ExamSessionId other = (ExamSessionId) obj;
        if (!Objects.equals(this.idExam, other.idExam)) {
            return false;
        }
        if (!Objects.equals(this.idStudent, other.idStudent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExamSessionId{" + "examId=" + idExam + ", studentId=" + idStudent + '}';
    }
    
}
