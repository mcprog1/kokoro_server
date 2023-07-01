/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Entidades;

/**
 *
 * @author nicol
 */
public enum Generos {
    M("Masculino"),
    F("Femenino");
    /*
    Contado("Contado"),
    Credito("Crédito"),
    Debito("Débito"),
    CreditoCasa("Crédito de la casa");
    */
    public final String label;

    private Generos(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label ;
    }
}
