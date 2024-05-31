public class DigitalBook extends Book  {
    private String downloadLink;

    public DigitalBook(String title, String author, String ISBN, String downloadLink){
        super(title, author, ISBN, Integer.MAX_VALUE);
        this.downloadLink = downloadLink;
    }

    public String getDownloadLink(){
        return downloadLink;
    }

    @Override
    public String toString(){
        return "Digital book:" + " " + super.toString() + " ," + "download link:" + " " +  getDownloadLink();
    }
}

