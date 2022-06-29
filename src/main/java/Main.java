import model.*;
import service.BookService;
import service.LibraryService;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws Exception {


        Library library  = new Library(new LinkedList<>());
        LibraryService libraryService = new LibraryService(library, new BookService());
        libraryService.createRack(1, 3);
        libraryService.createRack(2, 1);
       // libraryService.createRack(3, 3);
        User userA = new User("ram");
        User userB = new User("Sam");
        Book bookA = libraryService.createBook("titleA", "authorA", "pubA");
        Book bookB = libraryService.createBook("titleA", "authorB", "pubB");
        String idA = libraryService.addBook(bookA, BookStatus.AVAILABLE, BookCover.HARD_COVER);
        String idB = libraryService.addBook(bookB, BookStatus.AVAILABLE, BookCover.HARD_COVER);

        String idA1 = libraryService.addBook(bookA, BookStatus.AVAILABLE, BookCover.HARD_COVER);
        String idB1 = libraryService.addBook(bookB, BookStatus.AVAILABLE, BookCover.HARD_COVER);
        String idA2 = libraryService.addBook(bookA, BookStatus.AVAILABLE, BookCover.HARD_COVER);
        String idB2 = libraryService.addBook(bookB, BookStatus.AVAILABLE, BookCover.HARD_COVER);

        String bookCpyIdA = libraryService.borrowBook(userA,bookA);
        String bookCpyIdB = libraryService.borrowBook(userA,bookB);

        userA.showBorrowedBookId();
        libraryService.returnBook(userA,bookCpyIdA);
        userA.showBorrowedBookId();
        System.out.println( libraryService.search("TITLE", "titleA") );
        System.out.println( libraryService.search("AUTHOR", "authorB") );
    }
}
