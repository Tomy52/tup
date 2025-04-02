package Ui.Interfaces;

import Repositories.Interfaces.Repository;

public interface Menu<T extends Repository> {
    public void display();
}
