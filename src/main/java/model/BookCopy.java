package model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BookCopy {
    private final String id;
    private BookStatus bookStatus;
    private List<History> historyList;
    private final BookCover bookCover;
    private final Book book;

    public BookCopy(String id, Book book,BookCover bookCover) {
        this.book = book;
        this.bookCover = bookCover;
        this.id = id;
        historyList = new LinkedList<>();
        bookStatus = BookStatus.AVAILABLE;
    }
}
