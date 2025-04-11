package br.ufscar.dc.compiladores.alguma.lexico;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class Principal {
    public static void main( String args[]) throws IOException{
        
        try{
        CharStream cs = CharStreams.fromFileName(args[0]);
        AlgumaLexico lex = new AlgumaLexico(cs);
        Token t = null;
        
        while ((t = lex.nextToken()).getType() != Token.EOF) {
            System.out.println("<" + AlgumaLexico.VOCABULARY.getDisplayName(t.getType()) + "," + t.getText() + ">");
        }

    }catch (IOException ex){
    }

    }
}
