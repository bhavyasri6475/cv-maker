package sample;

import java.util.ArrayList;
import java.util.Scanner;

class Book {
    int id;
    String name;
    String author;
    boolean isIssued;
    int studentId;
    String dueDate;
    double fine;

    public Book(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isIssued = false;
        this.studentId = -1; // -1 indicates not issued to any student
        this.dueDate = "";
        this.fine = 0.0;
    }
}

class Library {
    ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(int bookId) {
        books.removeIf(book -> book.id == bookId);
    }

    public void updateBook(int bookId, String newName, String newAuthor) {
        for (Book book : books) {
            if (book.id == bookId) {
                book.name = newName;
                book.author = newAuthor;
                break;
            }
        }
    }

    public void issueBook(int bookId, int studentId, String dueDate) {
        for (Book book : books) {
            if (book.id == bookId && !book.isIssued) {
                book.isIssued = true;
                book.studentId = studentId;
                book.dueDate = dueDate;
                break;
            }
        }
    }

    public void returnBook(int bookId) {
        for (Book book : books) {
            if (book.id == bookId && book.isIssued) {
                book.isIssued = false;
                book.studentId = -1;
                book.dueDate = "";
                // Calculate fine if applicable
                // Assuming Rs.5/day fine after 15 days
                // For simplicity, not considering current date and actual due date
                // You may need to modify this based on your actual requirements
                book.fine = Math.max(0, 5 * (15 - calculateDaysPastDue(book.dueDate)));
                break;
            }
        }
    }

    public void viewBooksIssuedToStudent(int studentId) {
        for (Book book : books) {
            if (book.isIssued && book.studentId == studentId) {
                System.out.println("Book ID: " + book.id + ", Name: " + book.name + ", Author: " + book.author);
            }
        }
    }

    public void searchBookById(int bookId) {
        for (Book book : books) {
            if (book.id == bookId) {
                System.out.println("Book ID: " + book.id + ", Name: " + book.name + ", Author: " + book.author +
                        ", Status: " + (book.isIssued ? "Issued" : "Available"));
                if (book.isIssued) {
                    System.out.println("Issued to Student ID: " + book.studentId + ", Due Date: " + book.dueDate +
                            ", Fine: Rs." + book.fine);
                }
                return;
            }
        }
        System.out.println("Book not found with ID: " + bookId);
    }

    public void checkBookStatus(int studentId) {
        int issuedBooksCount = 0;
        for (Book book : books) {
            if (book.isIssued && book.studentId == studentId) {
                issuedBooksCount++;
            }
        }
        System.out.println("Number of books issued to Student ID " + studentId + ": " + issuedBooksCount);
    }

    private int calculateDaysPastDue(String dueDate) {
        // This is a placeholder method, you may need to implement the actual logic to calculate days past due
        // based on the current date and due date
        // For simplicity, it's returning a fixed value of 10
        return 10;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Adding some sample books to the library
        library.addBook(new Book(1, "Book1", "Author1"));
        library.addBook(new Book(2, "Book2", "Author2"));
        library.addBook(new Book(3, "Book3", "Author3"));

        // Sample usage
        library.issueBook(1, 101, "2024-02-01");
        library.issueBook(2, 102, "2024-02-10");

        library.returnBook(1);
        library.returnBook(2);

        library.viewBooksIssuedToStudent(101);

        library.searchBookById(1);

        library.checkBookStatus(101);
    }
}