package Database.Interfaces;

import Database.Entities.Advertisement;

import java.util.ArrayList;

public interface CRUD {

    void insert();

    ArrayList<Advertisement> read();

    void update();

    void delete();
}
