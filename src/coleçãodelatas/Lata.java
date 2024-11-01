/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coleçãodelatas;

import java.io.Serializable;

/**
 *
 * @author m150886
 */
public class Lata implements Serializable{
    String lataTipo;
    boolean lataPrioritária;
    boolean lataEdiçãoLimitada;
    String nomeDaMarca;
    String descriçãoDoSabor;
    int código;
    int rank;
    
    public String getMarca(){
        return nomeDaMarca;
    }
    
    public String getSabor(){
        return descriçãoDoSabor;
    }
    
    public Lata (String lataTipo, boolean lataPrioritária, boolean lataEdiçãoLimitada, String nomeDaMarca, String descriçãoDoSabor, int código, int rank){
    
    this.lataTipo = lataTipo;
    this.lataPrioritária = lataPrioritária;
    this.lataEdiçãoLimitada = lataEdiçãoLimitada;
    this.nomeDaMarca = nomeDaMarca;
    this.descriçãoDoSabor = descriçãoDoSabor;
    this.código = código;
    this.rank = rank;
    
    }
    
    public void exibirInformacoes(){
        System.out.println("Tipo: " +lataTipo);
        System.out.println("Lata Prioritária? " +lataPrioritária);
        System.out.println("Lata Edição Limitada: " + lataEdiçãoLimitada);
        System.out.println("Nome da Marca: " + nomeDaMarca);
        System.out.println("Descrição do Sabor: "+ descriçãoDoSabor);
        System.out.println("Código: " + código);
        System.out.println("Rank: " + rank);
    }
    
    
}
