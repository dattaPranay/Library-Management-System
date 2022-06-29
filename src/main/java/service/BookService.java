package service;

import model.Book;
import model.BookCopy;
import model.BookCover;
import model.BookStatus;
;

import java.util.*;

public class BookService {
    private final HashMap<String, List<Book>> searchByAuthor;
    private final HashMap<String, List<Book>> searchByPublisher;
    private final HashMap<String, List<Book>> searchByTitle;
    private final HashMap<String,BookCopy> bookCopyHashMap;

    public BookService() {
        searchByAuthor = new HashMap<>();
        searchByPublisher = new HashMap<>();
        searchByTitle = new HashMap<>();
        bookCopyHashMap = new HashMap<>();

    }

    public BookCopy getBookCopy(String id) {
        return bookCopyHashMap.getOrDefault(id, null);
    }

    public void createBook(Book book){
        if (!searchByTitle.containsKey(book.getTitle())){
            searchByTitle.put(book.getTitle(), new LinkedList<>());
        }
        if (!searchByPublisher.containsKey(book.getPublisher())){
            searchByPublisher.put(book.getPublisher(), new LinkedList<>());
        }
        if (!searchByAuthor.containsKey(book.getAuthor())){
            searchByAuthor.put(book.getAuthor(), new LinkedList<>());
        }
        searchByAuthor.get(book.getAuthor()).add(book);
        searchByTitle.get(book.getTitle()).add(book);
        searchByPublisher.get(book.getPublisher()).add(book);
    }

    public BookCopy addBookCopy(Book book, BookStatus bookStatus, BookCover bookCover){
        String id = UUID.randomUUID().toString();
        BookCopy bookCopy = new BookCopy(id, book, bookCover);
        book.getBookCopies().put(id, bookCopy);
        bookCopyHashMap.put(id,bookCopy);
        return bookCopy;
    }

    public List<Book> findBook(String attribute, String attributeValue) throws Exception {

        return switch (attribute) {
            case Config.AUTHOR -> searchByAuthor.getOrDefault(attributeValue, null);
            case Config.TITLE -> searchByTitle.getOrDefault(attributeValue, null);
            case Config.PUBLISHER -> searchByPublisher.getOrDefault(attributeValue, null);
            default -> throw new Exception("Invalid attribute");
        };
    }
}
