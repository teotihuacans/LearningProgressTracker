package tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {
    private List<Student> accounts;
    private List<Notify> notifies;
    private List<Subject> subjects;
    private List<String> SUBJECTLIST = Arrays.asList("Java", "DSA", "Databases", "Spring");

    public Model() {
        accounts = new ArrayList<>();
        notifies = new ArrayList<>();
        subjects = new ArrayList<>();
        subjects.add(new Subject(1, "Java", 600));
        subjects.add(new Subject(2, "DSA", 400));
        subjects.add(new Subject(3, "Databases", 480));
        subjects.add(new Subject(4, "Spring", 550));

        //For test purpose only
        /*accounts.add(new Student(1, "1@mail.ru", "First", "First",
                24, 0, 0, 10));
        accounts.add(new Student(2, "2@mail.ru", "Second", "Second",
                12, 4, 0, 10));
        accounts.add(new Student(3, "3@mail.ru", "Third", "Third",
                21, 0, 0, 10));
        accounts.add(new Student(4, "4@mail.ru", "Fourth", "Fourth",
                8, 0, 0, 0));*/

    }

    public Integer getAccountsSize() {
        return accounts.size();
    }

    public List<Student> getAccounts() {
        return accounts;
    }

    public void addStudent(Student input) {
        accounts.add(input);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<String> getSUBJECTLIST() {
        return SUBJECTLIST;
    }

    public void addNotify(Student student, Subject subject) {
        notifies.add(new Notify(student, subject));
    }

    public List<Notify> getNotifies() {
        return notifies;
    }

    public void deleteNotify() {
        notifies.clear();
    }
}
