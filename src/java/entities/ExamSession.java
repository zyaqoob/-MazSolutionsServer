/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Entity representing ExamSession.
 *
 * @author Zeeshan Yaqoob
 */
@Entity
@Table(name="exam_session",schema="maz_solutions")
public class ExamSession implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Field that identify ExamSession.
     */
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ExamSessionId examSessionId;

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
    private LocalDateTime dateTimeStart;

    /**
     * Data and time end of the examSession.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeEnd;

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
    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    /**
     * Data and time start of the examSession.
     *
     * @param dateTimeStart the dateTimeStart to set
     */
    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    /**
     *
     * @return dateTimeEnd.
     */
    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    /**
     * Data and time end of the examSession.
     *
     * @param dateTimeEnd the dateTimeEnd to set
     */
    public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    /**
     *
     * @return examSessionId
     */
    public ExamSessionId getExamSessionId() {    
        return examSessionId;
    }

    /**
     * an object of  ExamSessionId.
     * @param examSessionId the examSessionId to set.
     */
    public void setExamSessionId(ExamSessionId examSessionId) {
        this.examSessionId = examSessionId;
    }

    /**
     * Interger representation of examSession instance.
     * @return
     */
    @Override   
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.examSessionId);
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
     * @param object the other object of ExamSession to compare.
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
        if (!Objects.equals(this.examSessionId, other.examSessionId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExamSession{" + "examSessionId=" + examSessionId + ", mark=" + mark + ", exam=" + exam + ", student=" + student + ", dateTimeStart=" + dateTimeStart + ", dateTimeEnd=" + dateTimeEnd + '}';
    }

}
