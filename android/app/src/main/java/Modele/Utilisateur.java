package Modele;

public class Utilisateur {
    //Cette classe permet de représenter un Utilisateur

    private String identifiant;
    private Boolean admin;

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
