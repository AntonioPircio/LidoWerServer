package com.unimol.lidowebserver.mobile;

public class UserInfo {
    private String name;
    private String surname;
    private String mail;
    private String ID_affittato;
    private int numero_sedie;
    private int numero_sdraio;
    private int prezzo;

    public UserInfo() {

    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID_Affittato(String ID_Affittato) {
        this.ID_affittato = ID_Affittato;
    }

    public void setNumero_Sedie(int numero_Sedie) {
        this.numero_sedie = numero_Sedie;
    }

    public void setNumero_Sdraio(int numero_Sdraio) {
        this.numero_sdraio = numero_Sdraio;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

}
