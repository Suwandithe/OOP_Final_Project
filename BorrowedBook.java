import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * BorrowedBook
 */
public class BorrowedBook {

    private LibraryTransaction book;
    private LocalDate borrowDate;

    public BorrowedBook(LibraryTransaction book, LocalDate borrowDate) {
        this.book = book;
        this.borrowDate = borrowDate;
    }

    public LibraryTransaction getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public long getRemainingDays() {
        return 14 - ChronoUnit.DAYS.between(borrowDate, LocalDate.now());
    }

    @Override
    public String toString(){
        return "Title: " + book.getTitle() + ",borrowed on: " + borrowDate + ",remaining days to return:" + getRemainingDays();
    }
}