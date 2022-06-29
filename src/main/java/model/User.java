package model;


import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;

public class User {
    @Getter
    private final String id;
    private String name;
    @Getter
    private final HashSet<String> bookIdList;

    public User(String id) {
        this.id = id;
        this.bookIdList = new HashSet<>();
    }

    public void showBorrowedBookId() {
       bookIdList.forEach(id -> System.out.println("book Id : " + id));
    }
}
