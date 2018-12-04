package oberga2.illinois.edu.srpackageprocessing;

public class Package {
    private int id;
    private String recipient;
    private String date;
    private String firm;
    private int count;

    public Package(int id, String recipient, String date, String firm, int count) {
        this.id = id;

        this.recipient = recipient;
        this.date = date;
        this.firm = firm;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {

        return count;
    }

    public void setCount(int count) {

        this.count = count;
    }

    public int getId() {

        return id;
    }

    public String getRecipient() {

        return recipient;
    }

    public void setRecipient(String recipient) {

        this.recipient = recipient;
    }

    public String getFirm() {

        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }
}
