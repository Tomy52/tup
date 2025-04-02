package Model.Implementations;

public class Patient implements Comparable<Patient> {
    private final String name;
    private final int priorityLevel;

    public Patient(String name, int priorityLevel) {
        this.name = name;
        this.priorityLevel = priorityLevel;
    }

    public String getName() {
        return name;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }


    @Override
    public int compareTo(Patient otherPatient) {
        int result = 0;

        if (this.priorityLevel < otherPatient.getPriorityLevel()) result = -1;
        if (this.priorityLevel > otherPatient.getPriorityLevel()) result = 1;

        return result;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", priorityLevel=" + priorityLevel +
                '}';
    }
}