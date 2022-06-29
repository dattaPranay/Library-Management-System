package service;

import lombok.Data;
import model.*;

import java.util.*;
import java.util.stream.IntStream;

@Data
public class LibraryService {
    private final Library library;
    private final BookService bookService;

    public boolean createRack(int id, int size) {
        library.getRacks().add(new Rack(id, size));
        return true;
    }

    private Rack findFreeRack() {
        Rack rack = library.getRacks()
                .stream()
                .filter(rack1 -> rack1.getBooksMap().size() != rack1.getSize()).findFirst().get();
        return rack;
    }

    public Book createBook(String title, String author, String publisher) {
        Book book = new Book(title, author, publisher);
        bookService.createBook(book);
        return book;
    }

    public String addBook(Book book, BookStatus bookStatus, BookCover bookCover) {
        Rack rack = findFreeRack();
        OptionalInt optionalInt = IntStream.range(0, rack.getSize())
                .filter(idx -> rack.getRack()[idx] == null)
                .findFirst()
                ;
        if (optionalInt.isEmpty()) {
            System.out.println("Librery is full, Please add more racks to add more books ");
            return null;
        }
        int freeIndex = optionalInt.getAsInt();
        BookCopy bookCpy = bookService.addBookCopy(book, bookStatus, bookCover);
        rack.getRack()[freeIndex] = bookCpy;
        return bookCpy.getId();
    }

    public List<Book> search(String attribute, String attributeValue) throws Exception {
        
        return bookService.findBook(attribute, attributeValue);
    }

    public String borrowBook(User user, Book book) {
        Optional<String> bookCpyIdOpt = book.getBookCopies()
                .keySet()
                .stream()
                .filter(bookId-> book.getBookCopies().get(bookId).getBookStatus()
                        == BookStatus.AVAILABLE).findFirst();
        if(bookCpyIdOpt.isEmpty()){
            System.out.println("No books copy available for this book " + book.toString());
            return null;
        }
        String bookCpyId = bookCpyIdOpt.get();
        BookCopy bookCopy = bookService.getBookCopy(bookCpyId);
        if(bookCopy == null) {
            System.out.println("invalid book copy id given " + bookCpyId);
            return null;
        }

        bookCopy.setBookStatus(BookStatus.BORROWED);
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, Config.BORROW_DAYS_THRESHOLD);
        bookCopy.getHistoryList().add(new History(user.getId(), currentDate, c.getTime(), bookCpyId));
        user.getBookIdList().add(bookCpyId);
        return bookCpyId;
    }

    public boolean returnBook(User user, String bookCpyId) {
        BookCopy bookCopy = bookService.getBookCopy(bookCpyId);
        if(bookCopy == null || !user.getBookIdList().contains(bookCpyId)) {
            System.out.println("invalid book copy id given " + bookCpyId);
            return false;
        }
        user.getBookIdList().remove(bookCpyId);
        Rack rack = findFreeRack();
        OptionalInt optionalInt = IntStream.range(0, rack.getSize())
                .filter(idx -> rack.getRack()[idx] == null)
                .findFirst()
               ;
        if (optionalInt.isEmpty()) {
            System.out.println("Cant able to return books at this moment as the library is full. Please contact admin");
            return false;
        }
        int freeIndex = optionalInt.getAsInt();
        rack.getRack()[freeIndex] = bookCopy;
        List<History> historyList = bookCopy.getHistoryList();
        History history = historyList.get(historyList.size() -1);
        Date currentDate = new Date();
        history.setReturnDate(currentDate);
        if (history.getExpiryDate().compareTo(currentDate) < 0) {
            System.out.println("You have to pay fine for this ....");
        }
        System.out.println("Successfully returned book iD " + bookCpyId);
        return true;
    }

}
