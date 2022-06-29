package model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class Book {
    private final String title;
    private final String author;
    private final String publisher;
    private final Map<String, BookCopy> bookCopies;

    public Book(String title, String author, String publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        bookCopies = new HashMap<>();
    }
}
