package Repository.Interfaces;
import Model.Implementations.Task;

public interface TaskCollection {
    boolean add(Task task);
    boolean modify(int taskId, String description);
    void markAsComplete(int taskId);
}