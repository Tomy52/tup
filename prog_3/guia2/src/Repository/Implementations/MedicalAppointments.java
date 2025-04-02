package Repository.Implementations;

import Model.Implementations.Patient;

import java.util.Queue;

public class MedicalAppointments {
    private final Queue<Patient> appointments;

    public MedicalAppointments(Queue<Patient> appointments) {
        this.appointments = appointments;
    }

    public void add(Patient patient) {
        appointments.add(patient);
    }

    public void remove(Patient patient) {
        appointments.remove(patient);
    }

    public Patient peek() {
        return appointments.peek();
    }

    @Override
    public String toString() {
        return "MedicalAppointments{" +
                "appointments=" + appointments +
                '}';
    }
}