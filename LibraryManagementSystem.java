import java.time.LocalDate;
import java.util.*;

public class LibraryManagementSystem {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);
    private static final String ADMIN_PASSWORD = "iamanadmin";
    private static String loggedInUser = null;

    public static void main(String[] args) {
        System.out.println("Welcome to the Library Management System!");

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Login as Admin");
            System.out.println("2. Login as User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    if (adminLogin()) {
                        adminMenu();
                    } else {
                        System.out.println("Incorrect password. Access denied.");
                    }
                    break;
                case 2:
                    userLogin();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Thank you for using the Library Management System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private static boolean adminLogin() {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        return ADMIN_PASSWORD.equals(password);
    }

    private static void adminMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add a book");
            System.out.println("2. Search for a book");
            System.out.println("3. remove a book");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    displayAvailableBooks();
                    removeBook();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void userLogin() {
        System.out.print("Enter your username: ");
        loggedInUser = scanner.nextLine();
        userMenu();
    }

    private static void userMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Buy a book");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. View borrowed books");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    buyBook();
                    break;
                case 2:
                    displayAvailableBooks();
                    borrowBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    viewBorrowedBooks();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void addBook() {
        System.out.println("Enter Book type\n1. Pyhsical \n2. Digital");
        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String ISBN = scanner.nextLine();
       
        if(type ==  1){
            System.out.println("Enter stock:");
            int stock = scanner.nextInt();
            scanner.nextLine();
            Book newBook = new Book(title, author, ISBN, stock);
            library.addBook(new Book(title, author, ISBN, stock));
            System.out.println("You have added \"" + newBook.toString() + "\"");

        } else if (type == 2) {
            System.out.println("Enter download Link:");
            String downloadLink = scanner.nextLine();   
            DigitalBook newDigitalBook = new DigitalBook(title, author, ISBN, downloadLink);
            library.addBook(new DigitalBook(title,author, ISBN, downloadLink));
            System.out.println("You have added \"" + newDigitalBook.toString() + "\"");

        } else{
            System.out.println("Invalid choice!");
        }

    }

    private static void removeBook(){
        System.out.println("Enter title or ISBN to remove book");
        String identifier = scanner.nextLine();

        LibraryTransaction bookByTitle = library.searchBookByTitle(identifier);
        LibraryTransaction bookByISBN = library.searchBookByISBN(identifier);

        if (bookByTitle != null) {
            if (library.removeBook(identifier)) {
                System.out.println("You have remove\" " + bookByTitle.toString()   + "\".");
            }
        } else if (bookByISBN != null) {
            if (library.removeBook(identifier)) {
                System.out.println("You have bought\" " + bookByISBN.toString() +  "\".");
            }
        } else{
            System.out.println("Book is not found");
        }
    }

    private static void searchBook() {
        System.out.println("Enter title or ISBN to search:");
        String query = scanner.nextLine();

        LibraryTransaction bookByTitle = library.searchBookByTitle(query);
        LibraryTransaction bookByISBN = library.searchBookByISBN(query);

        if (bookByTitle != null) {
            System.out.println("Book found by title:" + bookByTitle);
        } else if(bookByISBN != null) {
            System.out.println("Book found by ISBN:" + bookByISBN);

        } else{
            System.out.println("Book not found.");
        }
       
    }

    private static void buyBook() {
        List<LibraryTransaction> booksForSale = library.getBooksForSale();
        if (booksForSale.isEmpty()) {
            System.out.println("No books are available for sale");
        } else {

            System.out.println("Available books for sale:");

            for (int i = 0; i < booksForSale.size(); i++) {
                System.out.println((i + 1) + ". " + booksForSale.get(i).toString() );
            }
        
            System.out.println("Enter title or ISBN of the book to buy");
            String query = scanner.nextLine();

            LibraryTransaction bookByTitle = library.searchBookByTitle(query);
            LibraryTransaction bookByISBN = library.searchBookByISBN(query);

            if (bookByTitle != null) {
                if (library.buyBook(loggedInUser, bookByTitle)) {
                    System.out.println("You have bought\" " + bookByTitle.toString() + " " + " for 50$" + "\".");
                }
            } else if (bookByISBN != null) {
                if (library.buyBook(loggedInUser, bookByISBN)) {
                    System.out.println("You have bought\" " + bookByISBN.toString()+ " " + " for 50$"  + "\".");
                }
            } else {
                System.out.println("invalid choice or book is out of stock");
            }
        }
        
    }

    private static void displayAvailableBooks() {
        List<LibraryTransaction> availableBooks = library.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("No books are available.");
        } else{
            for (int i = 0; i < availableBooks.size(); i++) {
                System.out.println((i + 1) + ". " + availableBooks.get(i).toString());
            }
        } 

       
    }

    private static void borrowBook() {
    System.out.print("Enter title or ISBN of the book to borrow: ");
    String query = scanner.nextLine();

    LibraryTransaction bookByTitle = library.searchBookByTitle(query);
    LibraryTransaction bookByISBN = library.searchBookByISBN(query);

    if (bookByTitle != null) {
        if (library.borrowBook(loggedInUser, bookByTitle)) {
            BorrowedBook borrowedBook = new BorrowedBook(bookByTitle, LocalDate.now());
            System.out.println("You have borrowed: " + borrowedBook.toString());
        }
    } else if (bookByISBN != null) {
        if (library.borrowBook(loggedInUser, bookByISBN)) {
            BorrowedBook borrowedBook = new BorrowedBook(bookByISBN, LocalDate.now());
            System.out.println("You have borrowed: " + borrowedBook.toString());
        }
    } else {
        System.out.println("Invalid choice or book is out of stock.");
    }
}


    private static void returnBook() {
        System.out.print("Enter book title  to return: ");
        String title = scanner.nextLine();

        List<BorrowedBook> borrowedBooks = library.getUserBorrowedBooks(loggedInUser);
        for(BorrowedBook borrowedBook : borrowedBooks) {
            if (borrowedBook.getBook().getTitle().equalsIgnoreCase(title)) {
                LibraryTransaction book = borrowedBook.getBook();
                if (library.returnBook(loggedInUser, title)) {
                    System.out.println("You have returned\"" + book.toString() + "\".");
                    return;
                }
            }
        }
        System.out.println("Book is not found");
    }

    private static void viewBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = library.getUserBorrowedBooks(loggedInUser);
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
        } else {
            System.out.println("Your borrowed books:");
            for (BorrowedBook borrowedBook : borrowedBooks) {
                System.out.println(borrowedBook);
            }
        }
    }
}