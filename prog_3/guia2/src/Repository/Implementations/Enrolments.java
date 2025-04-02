package Repository.Implementations;

import Repository.Interfaces.EnrolmentCollection;

import java.util.Set;

public class Enrolments implements EnrolmentCollection {
    private final Set<String> enrolments;

    public Enrolments(Set<String> enrolments) {
        this.enrolments = enrolments;
    }

    @Override
    public void enrol(String name) {
        enrolments.add(name);
    }

    @Override
    public void delete(String name) {
        enrolments.remove(name);
    }

    @Override
    public boolean isEnrolled(String name) {
        return enrolments.contains(name);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (String name: enrolments) {
            result.append(name).append("\n");
        }

        return result.toString();
    }
}