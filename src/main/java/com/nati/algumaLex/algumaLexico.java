package com.nati.algumaLex;

public class algumaLexico {
    leitorArquivosTexto ldat;

    public algumaLexico(String arquivo) {
        ldat = new leitorArquivosTexto(arquivo);
    }

    public Token proximoToken() {
        Token proximo = null;

        espacosEcomentarios();
        ldat.confirmar();
        proximo = fim();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = palavrasChave();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = variavel();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = numeros();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = operadorAritmetico();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = operadorRelacional();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = delimitador();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = parenteses();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = cadeia();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        System.err.println("Erro léxico!");
        System.err.println(ldat.toString());
        return null;
    }

    private Token operadorAritmetico() {
        int caractereLido = ldat.lerProximoCaractere();
        char c = (char) caractereLido;

        if (c == '*') {
            return new Token(tipoToken.OpAriMuilt, ldat.getLexema());
        } else if (c == '-') {
            return new Token(tipoToken.OpArisub, ldat.getLexema());
        } else if (c == '/') {
            return new Token(tipoToken.OpAriDiv, ldat.getLexema());
        } else if (c == '+') {
            return new Token(tipoToken.OpAriSoma, ldat.getLexema());
        } else {
            return null;
        }
    }

    private Token delimitador() {
        int caractereLido = ldat.lerProximoCaractere();
        char c = (char) caractereLido;

        if (c == ':') {
            return new Token(tipoToken.Delim, ldat.getLexema());
        } else {
            return null;
        }
    }

    private Token parenteses() {
        int caractereLido = ldat.lerProximoCaractere();
        char c = (char) caractereLido;

        if (c == '(') {
            return new Token(tipoToken.AbrePar, ldat.getLexema());
        } else if (c == ')') {
            return new Token(tipoToken.FechaPar, ldat.getLexema());
        }else{
            return null;
        }
    }

    private Token operadorRelacional(){
        int caractereLido = ldat.lerProximoCaractere();
        char c = (char) caractereLido;

        if (c == '<') {
                c = (char)ldat.lerProximoCaractere();
                if (c == '>') {
                    return new Token(tipoToken.OpRelDif, ldat.getLexema());
                }else if (c == '=') {
                    return new Token(tipoToken.OpRelMenorIgual, ldat.getLexema());
                }else{
                    ldat.retroceder();
                    return new Token(tipoToken.OpRelMenor, ldat.getLexema());
                }
            }else if (c == '=') {
                return new Token(tipoToken.OpRelIgual, ldat.getLexema());
            }else if (c == '>') {
                c = (char) ldat.lerProximoCaractere();
                if (c == '=') {
                    return new Token(tipoToken.OpRelMaiorIgual, ldat.getLexema());
                }else{
                    ldat.retroceder();
                    return new Token(tipoToken.OpRelMaior, ldat.getLexema());
                }
            }
            return null;
    }
    
    private Token numeros(){
        int estado = 1;

        while (true) {
            char c = (char) ldat.lerProximoCaractere();
            if (estado == 1) {
                if (Character.isDigit(c)) {
                    estado = 2;
                }else{
                    return null;
                }
            }else if (estado == 2) {
                if (c == '.') {
                    c = (char) ldat.lerProximoCaractere();
                    if (Character.isDigit(c)) {
                        estado = 3;
                    }else{
                        return null;
                    }
                }else if (!Character.isDigit(c)) {
                    ldat.retroceder();
                    return new Token(tipoToken.NumInt, ldat.getLexema());
                }
            }else if (estado == 3) {
                if (!Character.isDigit(c)) {
                    ldat.retroceder();
                    return new Token(tipoToken.NumReal, ldat.getLexema());
                }
            }
        }
    }

    private Token variavel(){
        int estado = 1;

        while (true) {
            char c = (char) ldat.lerProximoCaractere();
            if (estado == 1) {
                if (Character.isLetter(c)) {
                    estado = 2;
                }else{
                    return null;
                }
            }else if (estado == 2) {
                if (!Character.isLetterOrDigit(c)) {
                    ldat.retroceder();
                    return new Token(tipoToken.Var, ldat.getLexema());
                }
            }
        }
    }
    
    private Token cadeia(){
        int estado = 1;

        while (true) {
            char c = (char) ldat.lerProximoCaractere();

            if (estado == 1) {
                if (c == '\'') {
                    estado = 2;
                }else{
                    return null;
                }
            }else if (estado == 2) {
                if (c == '\n') {
                    return null;
                }
                if (c == '\'') {
                    return new Token(tipoToken.Cadeia, ldat.getLexema());
                }else if (c == '\\') {
                    estado = 3;
                }
            }else if (estado == 3) {
                if (c == '\n') {
                    return null;
                }else{
                    estado = 2;
                }
            }
        }
    }

    private void espacosEcomentarios(){
        int estado = 1;

        while (true) {
            char c = (char) ldat.lerProximoCaractere();

            if (estado == 1){
                if (Character.isWhitespace(c) || c == ' ') {
                    estado = 2;
                }else if (c == '%') {
                    estado = 3;
                }else{
                    ldat.retroceder();
                    return;
                }
            }else if (estado == 2) {
                if (c == '%') {
                    estado = 3;
                }else if (!(Character.isWhitespace(c) || c == ' ')) {
                    ldat.retroceder();
                    return;
                }
            }else if (estado == 3) {
                if (c == '\n') {
                    return;
                }
            }
        }
    }

    private Token palavrasChave(){
        while (true) {
            char c = (char) ldat.lerProximoCaractere();

            if (!Character.isLetter(c)) {
                ldat.retroceder();
                String lexema = ldat.getLexema();

                if (lexema.equals("DECLARACOES")) {
                    return new Token(tipoToken.PCDeclaracoes, lexema);
                } else if (lexema.equals("ALGORITMO")) {
                    return new Token(tipoToken.PCAlgoritmo, lexema);
                }else if (lexema.equals("INTEIRO")) {
                    return new Token(tipoToken.PCInteiro, lexema);
                }else if (lexema.equals("REAL")) {
                    return new Token(tipoToken.PCReal, lexema);
                }else if (lexema.equals("ATRIBUIR")) {
                    return new Token(tipoToken.PCAtribuir, lexema);
                }else if (lexema.equals("A")) {
                    return new Token(tipoToken.PCA, lexema);
                }else if (lexema.equals("LER")) {
                    return new Token(tipoToken.PCLer, lexema);
                }else if (lexema.equals("IMPRIMIR")) {
                    return new Token(tipoToken.PCImprimir, lexema);
                }else if (lexema.equals("SE")) {
                    return new Token(tipoToken.PCSe, lexema);
                }else if (lexema.equals("ENTAO")) {
                    return new Token(tipoToken.PCEntao, lexema);
                }else if (lexema.equals("ENQUANTO")) {
                    return new Token(tipoToken.PCEnquanto, lexema);
                }else if (lexema.equals("INICIO")) {
                    return new Token(tipoToken.PCInicio, lexema);
                }else if (lexema.equals("FIM")) {
                    return new Token(tipoToken.PCFim, lexema);
                }else if (lexema.equals("E")) {
                    return new Token(tipoToken.OpBoolE, lexema);
                }else if (lexema.equals("OU")) {
                    return new Token(tipoToken.OpBoolOu, lexema);
                }else{
                    return null;
                }
            }
        }
    }

    private Token fim(){
        int caractereLido = ldat.lerProximoCaractere();
        if (caractereLido == -1) {
            return new Token(tipoToken.Fim, "Fim");
        }
        return null;
    }
}
