package com.nati.algumaLex;


public class Token {
    public tipoToken nome;
    public String lexema;

    public Token(tipoToken nome, String lexema){
        this.nome  = nome;
        this.lexema = lexema;
    }

    @Override
    public String toString(){
        return "<"+nome+","+lexema+">";
    }
}
