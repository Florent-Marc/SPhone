package com.dev.sphone.mod.server.bdd;

import com.dev.sphone.mod.common.phone.*;
import com.dev.sphone.mod.utils.exceptions.DatabaseException;

import java.util.List;

public interface DatabaseType {
    DatabaseType getInstance() throws DatabaseException;

    void prepapreDatabase() throws DatabaseException;

    void addContact(int sim, Contact contact);

    void editContact(Contact contact);

    void deleteContact(Contact contact);


    List<Contact> getContacts(int sim);

    List<Note> getNotes(int sim);

    void addNote(int sim, Note note);

    void editNote(Note note);

    void deleteNote(Note note);

    void addMessage(Message message);

    String getNumero(int sim);

    String getSimFromNum(String num);


    List<Message> getMessages(int sim);

    List<Message> getMessage(int sim, String contactNumber);


    Conversation getConversation(int sim, Contact contact);


    List<Conversation> getConversations(int sim);

    boolean checkNumber(String number);

    boolean checkSim(int sim);

    boolean addSim(int sim, String number);

    void addNews(News news);

    void editNews(News news);

    void deleteNews(News news);

    List<News> getNews();

    boolean isSimExist(int sim);
}
