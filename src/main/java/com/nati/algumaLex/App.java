package com.nati.algumaLex;

public class App 
{
    public static void main( String[] args )
    {
        algumaLexico lex = new algumaLexico(args[0]);
        Token t = null;
        while ((t = lex.proximoToken()).nome != tipoToken.Fim) {
             System.out.println(t);
        }
       
    }
}
