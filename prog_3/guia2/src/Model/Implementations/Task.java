package Model.Implementations;

import Model.Interfaces.Completable;

public class Task implements Completable {
    private String description;
    private boolean isComplete;

    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    public void modifyDescription(String newDescription) {
        this.description = newDescription;
    }

    public boolean isComplete(){
        return this.isComplete;
    }

    @Override
    public void complete() {
        this.isComplete = true;
    }

    @Override
    public String toString() {
        return "Descripcion: " + this.description;
    }
}