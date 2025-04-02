package Repository.Implementations;

import Repository.Interfaces.MapCollection;

import java.util.Map;

public class Phonebook implements MapCollection<String,String> {
    private final Map<String,String> contacts;

    public Phonebook(Map<String,String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean add(String name, String phoneNumber) {
        boolean added = false;
        if (!contacts.containsKey(name)) {
            contacts.put(name,phoneNumber);
            added = true;
        }
        return added;
    }

    @Override
    public String find(String name) {
        String result;
        result = contacts.getOrDefault(name, "Contacto no encontrado");
        return result;
    }

    @Override
    public boolean modify(String contactName, String newPhoneNumber) {
        boolean modified = false;
        if (contacts.containsKey(contactName)) {
            contacts.put(contactName,newPhoneNumber);
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean delete(String contactName) {
        boolean deleted = false;
        if (contacts.containsKey(contactName)) {
            contacts.remove(contactName);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

}