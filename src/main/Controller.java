package main;

import database.Book;

public class Controller {

    protected Controller main_controller;

    public void setMainController(Controller controller){
        main_controller = controller;
    }

    public void removeBookFromTable(Book book) { }
}
