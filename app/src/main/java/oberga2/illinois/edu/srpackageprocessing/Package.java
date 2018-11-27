package oberga2.illinois.edu.srpackageprocessing;

public class Package {
    private int id;
    private String recipient;
    private String firm;

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

    public Package(int id, String recipient, String firm) {
        this.id = id;

        this.recipient = recipient;
        this.firm = firm;
    }


}
