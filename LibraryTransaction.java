public interface LibraryTransaction {
    boolean isAvailable();
    void setAvailable(boolean available);
    String getTitle();
    String getAuthor();
    String getISBN();
    int getStock();
    void setStock(int stock);
}
