    public class Book implements LibraryTransaction {
        private String title;
        private String author;
        private String ISBN;
        private boolean isAvailable;
        private int stock;

        public Book(String title, String author, String ISBN, int stock) {
            this.title = title;
            this.author = author;
            this.ISBN = ISBN;
            this.isAvailable = stock > 0;
            this.stock = stock;
        }

        @Override
        public String getTitle() {
            return title;
        }


        @Override
        public String getAuthor() {
            return author;
        }

        @Override
        public String getISBN() {
            return ISBN;
        }

        @Override
        public boolean isAvailable() {
            return isAvailable  ;

        }

        @Override
        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        @Override
        public int getStock() {
            return stock;
        }

        @Override
        public void setStock(int stock){
            this.stock = stock;
            this.isAvailable = stock > 0;
        }

        @Override
        public String toString() {
            return title + " " + "by" + " "  + author + " " + "(ISBN: " + ISBN + ")" + " " + "stock:" + " " + stock;
        
        }
    }
