/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing ExamSession.
 *
 * @author Zeeshan Yaqoob
 */
@NamedQueries({
    @NamedQuery(name = "findAllExamSession", query = "SELECT es FROM ExamSession es")
    ,
    @NamedQuery(name = "findExamSessionsByStudent", query = "SELECT es FROM ExamSession es WHERE es.student.fullName=:fullName")
    ,
    @NamedQuery(name = "findExamSessionsBySubject", query = "SELECT es FROM ExamSession es WHERE es.exam.subject.name=:subject_name")
})
@Entity
@Table(name = "exam_session", schema = "maz_solutions")
@XmlRootElement
public class ExamSession implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Field that identify ExamSession.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idExamSession;
    /**
     * Field that represent marks obtained.
     */
    private int mark;

    /**
     * An object of Exam.
     */
    @ManyToOne
    private Exam exam;

    /**
     * An object of Student.
     */
    @ManyToOne
    private Student student;

    /**
     * Data and time start of the examSession.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTimeStart;

    /**
     * Data and time end of the examSession.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTimeEnd;

    /**
     *
     * @return mark.
     */
    public int getMark() {
        return mark;
    }

    /**
     * Field that represent marks obtained
     *
     * @param mark the mark to set
     */
    public void setMark(int mark) {
        this.mark = mark;
    }

    /**
     *
     * @return exam
     */
    public Exam getExam() {
        return exam;
    }

    /**
     * An object of Exam.
     *
     * @param exam the exam to set
     */
    public void setExam(Exam exam) {
        this.exam = exam;
    }

    /**
     *
     * @return student
     */
    @XmlTransient
    public Student getStudent() {
        return student;
    }

    /**
     * An object of student.
     *
     * @param student the student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     *
     * @return dateTimeStart.
     */
    public Calendar getDateTimeStart() {
        return dateTimeStart;
    }

    /**
     * Data and time start of the examSession.
     *
     * @param dateTimeStart the dateTimeStart to set
     */
    public void setDateTimeStart(Calendar dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    /**
     *
     * @return dateTimeEnd.
     */
    public Calendar getDateTimeEnd() {
        return dateTimeEnd;
    }

    /**
     * Data and time end of the examSession.
     *
     * @param dateTimeEnd the dateTimeEnd to set
     */
    public void setDateTimeEnd(Calendar dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    /**
     *
     * @return examSessionId
     */
    public Long getIdExamSession() {
        return idExamSession;
    }

    /**
     * an object of ExamSessionId.
     *
     * @param idExamSession the examSessionId to set.
     */
    public void setIdExamSession(Long idExamSession) {
        this.idExamSession = idExamSession;
    }

    /**
     * Interger representation of examSession instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idExamSession);
        hash = 79 * hash + this.mark;
        hash = 79 * hash + Objects.hashCode(this.exam);
        hash = 79 * hash + Objects.hashCode(this.student);
        hash = 79 * hash + Objects.hashCode(this.dateTimeStart);
        hash = 79 * hash + Objects.hashCode(this.dateTimeEnd);
        return hash;
    }

    /**
     * compares two objects of ExamSessions.
     *
     * @param obj the other object of ExamSession to compare.
     * @return true incase they are same.
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
        final ExamSession other = (ExamSession) obj;
        if (!Objects.equals(this.idExamSession, other.idExamSession)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExamSession{" + "examSessionId=" + idExamSession + ", mark=" + mark + ", exam=" + exam + ", student=" + student + ", dateTimeStart=" + dateTimeStart + ", dateTimeEnd=" + dateTimeEnd + '}';
    }

}
