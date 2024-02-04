package com.dev.sphone.mod.server.bdd;

import com.dev.sphone.mod.common.phone.*;
import com.dev.sphone.mod.utils.exceptions.DatabaseException;

import java.sql.Connection;
import java.util.List;

public class EmptyDatabase implements DatabaseType {
    @Override
    public DatabaseType getInstance() throws DatabaseException {
        return null;
    }

    @Override
    public void prepapreDatabase() throws DatabaseException {

    }

    @Override
    public void addContact(int sim, Contact contact) {

    }

    @Override
    public void editContact(Contact contact) {

    }

    @Override
    public void deleteContact(Contact contact) {

    }

    @Override
    public List<Contact> getContacts(int sim) {
        return null;
    }

    @Override
    public List<Note> getNotes(int sim) {
        return null;
    }

    @Override
    public void addNote(int sim, Note note) {

    }

    @Override
    public void editNote(Note note) {

    }

    @Override
    public void deleteNote(Note note) {

    }

    @Override
    public void addMessage(Message message) {

    }

    @Override
    public String getNumero(int sim) {
        return null;
    }

    @Override
    public String getSimFromNum(String num) {
        return null;
    }

    @Override
    public List<Message> getMessages(int sim) {
        return null;
    }

    @Override
    public List<Message> getMessage(int sim, String contactNumber) {
        return null;
    }

    @Override
    public Conversation getConversation(int sim, Contact contact) {
        return null;
    }

    @Override
    public List<Conversation> getConversations(int sim) {
        return null;
    }

    @Override
    public boolean checkNumber(String number) {
        return false;
    }

    @Override
    public boolean checkSim(int sim) {
        return false;
    }

    @Override
    public boolean addSim(int sim, String number) {
        return false;
    }

    @Override
    public void addNews(News news) {

    }

    @Override
    public void editNews(News news) {

    }

    @Override
    public void deleteNews(News news) {

    }

    @Override
    public List<News> getNews() {
        return null;
    }

    @Override
    public boolean isSimExist(int sim) {
        return false;
    }
}
