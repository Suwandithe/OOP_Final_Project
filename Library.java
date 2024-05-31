import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class Library {
    private List<LibraryTransaction> books;
    private Map<String, List<BorrowedBook>> borrowedBooksByUser;

    public Library() {
        books = new ArrayList<>();
        borrowedBooksByUser = new HashMap<>();

        books.add(new Book("Java Programming", "John Doe", "1234567890", 5));
        books.add(new Book("Python for Beginners", "Jane Smith", "0987654321", 3));
        books.add(new Book("Data Structures and Algorithms", "Robert Martin", "1122334455", 4));
        books.add(new DigitalBook("Learn JavaScript", "Mike Taylor", "6678776797", "https://downloadLink.com"));
    
    }

    // Add a book to the library
    public void addBook(LibraryTransaction book) {
        books.add(book);
    }

    public boolean removeBook(String identifier ){
        Iterator<LibraryTransaction> iterator = books.iterator();
        while (iterator.hasNext()) {
            LibraryTransaction book = iterator.next();
            if (book.getTitle().equalsIgnoreCase(identifier) || book.getISBN().equals(identifier)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    // Search for a book by title
    public LibraryTransaction searchBookByTitle(String title) {
        for (LibraryTransaction book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {      
                return book;
            }
        }
        return null;
    }

    // Search for a book by ISBN
    public LibraryTransaction searchBookByISBN(String ISBN) {
        for (LibraryTransaction book : books) {
            if (book.getISBN().equals(ISBN) && book.isAvailable()) {
                return book;
            }
        }
        return null;
    }

    // Borrow a book
    public boolean borrowBook(String user, LibraryTransaction book) {
        if (book != null && book.isAvailable()) {
            book.setStock(book.getStock() - 1);
            borrowedBooksByUser.computeIfAbsent(user, k -> new ArrayList<>()).add(new BorrowedBook(book, LocalDate.now()));
            if (book.getStock() == 0) {
                book.setAvailable(false);
            }
            return true;
        }
        return false;
    }

    // Return a book
    public boolean returnBook(String user, String title) {
        List<BorrowedBook> borrowedBooks = borrowedBooksByUser.get(user);
        if (borrowedBooks != null) {
            for (BorrowedBook borrowedBook : borrowedBooks) {
                if (borrowedBook.getBook().getTitle().equalsIgnoreCase(title)) {
                    LibraryTransaction book = borrowedBook.getBook();
                    book.setStock(book.getStock() + 1);
                    book.setAvailable(true);
                    borrowedBooks.remove(borrowedBook);
                    return true;
                }
            }
        }
        return false;
    }


    // Get borrowed books for a user
    public List<BorrowedBook> getUserBorrowedBooks(String user) {
        return borrowedBooksByUser.getOrDefault(user, new ArrayList<>());
    }

    // Get available books
    public List<LibraryTransaction> getAvailableBooks() {
        List<LibraryTransaction> availableBooks = new ArrayList<>();
        for (LibraryTransaction book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public List<LibraryTransaction> getBooksForSale() {
        List<LibraryTransaction> booksForSale = new ArrayList<>();
        for (LibraryTransaction book : books) {
            if(book.getStock() > 0) {
                booksForSale.add(book);
            }
        }
        return booksForSale;
    }

    public boolean buyBook(String user, LibraryTransaction bookForSale) {
        // List<LibraryTransaction> booksForSale = getBooksForSale();
        if (bookForSale != null && bookForSale.isAvailable()) {
            bookForSale.setStock(bookForSale.getStock() - 1);
            if (bookForSale.getStock() == 0) {
                bookForSale.setAvailable(false);
            }
            return true;
        }
        return false;
    }
}