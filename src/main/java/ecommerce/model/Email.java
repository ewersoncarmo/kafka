package ecommerce.model;

public class Email {

    private String assunto;
    private String corpo;

    public Email(String assunto, String corpo) {
        this.assunto = assunto;
        this.corpo = corpo;
    }

    @Override
    public String toString() {
        return "Email{" +
                "assunto='" + assunto + '\'' +
                ", corpo='" + corpo + '\'' +
                '}';
    }
}
