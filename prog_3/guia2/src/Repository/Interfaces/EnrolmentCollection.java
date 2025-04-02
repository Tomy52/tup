package Repository.Interfaces;

public interface EnrolmentCollection {
    void enrol(String name);
    void delete(String name);
    boolean isEnrolled(String name);
}