package tracker;

public class Notify {
    private Student student;
    private Subject subject;

    public Notify(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public Subject getSubject() {
        return subject;
    }
}
