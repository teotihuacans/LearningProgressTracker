package tracker;

import java.util.Scanner;

public class Controller {
    private boolean isAdd = false;
    private boolean isPoints = false;
    private boolean isFind = false;
    private Boolean isStat = false;
    private String result = "";
    private Model model;
    private View view;
    private Boolean isCycle = true;
    private StudentUtils studentUtility;

    public Controller(Model md) {
        model = md;
    }

    public Controller() {
        model = new Model();
        view = new View();
        studentUtility = new StudentUtils(model);
    }

    public Boolean getIsCycle() {
        return isCycle;
    }

    private Boolean checkCommand(String command) {
        switch (command) {
            case "add students" : return isAdd || !checkAllFalse();
            case "add points" : return isPoints || !checkAllFalse();
            case "find" : return isFind || !checkAllFalse();
            case "statistics" : return isStat || !checkAllFalse();
        }
        return false;
    }

    private Boolean checkAllFalse() {
        //should return false if there are no active commands cycles
        return isAdd || isPoints || isFind || isStat;
    }

    public String commandProcessor(String command) {
        command = command.replaceAll("\t", " ").strip(); //delete initial spaces
        if ("back".equals(command)) {
            if (isAdd) {
                result = "Total " + model.getAccountsSize() + " students have been added.\n";
            } else {
                result = "Enter 'exit' to exit the program.";
            }
            isAdd = false;
            isPoints = false;
            isFind = false;
            isStat = false;
        } else if (("add students".equals(command) && !checkAllFalse()) || isAdd) {
            if (!isAdd) {
                result = "Enter student credentials or 'back' to return:";
                isAdd = true;
            } else if (command.split("\\s+").length < 3) {
                result = "Incorrect credentials.";
            } else {

                String[] mas = command.split("\\s+");
                String firstName = mas[0].strip();
                String email = mas[mas.length - 1].strip();
                String lastName = command.substring(firstName.length() + 1, command.length() - (email.length() + 1)).strip();

                //System.out.println(firstName + "\n" + lastName + "\n" + email);

                if (!firstName.matches("[A-Za-z][A-Za-z\\-']*[A-Za-z]") ||
                        firstName.matches(".*((\\-\\-+)|(''+)|(\\-+'+)|('+\\-+)).*")) {
                    result = "Incorrect first name.";
                } else if (!lastName.matches("[A-Za-z][A-Za-z\\s\\-']*[A-Za-z]") ||
                        lastName.matches(".*((\\-\\-+)|(''+)|(\\-+'+)|('+\\-+)).*")) {
                    result = "Incorrect last name.";
                } else if (!email.matches("[A-z_a-z0-9\\.]*@[A-z_a-z0-9\\.]*\\.[A-z_a-z0-9]*")) {
                    result = "Incorrect email.";
                } else if (model.getAccounts().stream().map(Student::getEmail).anyMatch(email::equalsIgnoreCase)) {
                    result = "This email is already taken.";
                } else {
                    model.addStudent(new Student(model.getAccountsSize(), email, firstName, lastName));
                    result = "The student has been added.";
                }
            }

        } else if (("add points".equals(command) && !checkAllFalse()) || isPoints) {
            if (!isPoints) {
                isPoints = true;
                result = "Enter an id and points or 'back' to return:";
            } else if (studentUtility.checkPointsFormat(command)) {
                result = "Incorrect points format.";
            } else if (studentUtility.checkIdIfExists(command)) {
                result = String.format("No student is found for id=%s.",
                        studentUtility.parsePointsFormat(command)[0]);
            } else {
                String[] tempMas = studentUtility.parsePointsFormat(command);
                Student st = studentUtility.getStudent(tempMas[0]);
                studentUtility.checkNotify(st, tempMas); //should be run before updating points
                st.setAllScores(tempMas);
                result = "Points updated.";
            }
        } else if (("find".equals(command) && !checkAllFalse()) || isFind) {
            if (!isFind) {
                result = "Enter an id or 'back' to return:";
                isFind = true;
            } else if (studentUtility.checkIdIfExists(command)) {
                result = String.format("No student is found for id=%s.", command);
            } else {
                result = studentUtility.getStudent(command).pointsInfo();
            }

        } else if (("statistics".equals(command) && !checkAllFalse()) || isStat) {
            if (!isStat) {
                result = "Type the name of a course to see details or 'back' to quit:\n";
                result += studentUtility.generalStatistics();
                isStat = true;
            } else if (!model.getSUBJECTLIST().contains(command)) {
                result = "Unknown course.";
            } else {
                result = studentUtility.subjectInfo(command);
            }
        } else if ("exit".equals(command)) {
            result = "Bye!";
            isCycle = false;
        } else if ("list".equals(command)) {
            if (model.getAccounts().size() == 0) {
                result = "No students found.";
            } else {
                result = "Students:";
                model.getAccounts().stream().map(Student::getUniqueID).forEach(e -> result = result + "\n" + e);
            }
        } else if ("notify".equals(command)) {
            result = studentUtility.getNotifications();
        } else if (command.isEmpty() || command.isBlank()) {
            result = "No input";
        } else {
            result = "Unknown command!";
        }

        view.display(result);
        return result;
    }
}
