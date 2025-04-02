package Repository.Implementations;

import Model.Implementations.Task;
import Repository.Interfaces.TaskCollection;

import java.util.List;

public class Tasks implements TaskCollection {
    private final List<Task> tasks;

    public Tasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean add(Task task) {
        this.tasks.add(task);
        return true;
    }

    @Override
    public boolean modify(int taskId, String description) {
        this.tasks.get(taskId - 1).modifyDescription(description);
        return true;
    }

    @Override
    public void markAsComplete(int taskId) {
        this.tasks.get(taskId - 1).complete();
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder("");

        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).isComplete()) {
                resultado.append("-- Tarea ").append(i + 1).append("\n").append(tasks.get(i)).append("\n");
            }
        }

        return resultado.toString();
    }
}