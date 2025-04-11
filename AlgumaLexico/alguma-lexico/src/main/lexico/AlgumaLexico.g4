lexer grammar AlgumaLexico;

Programa: Declaracoes 'algoritmo' Corpo 'fim_algoritmo';
Declaracoes: {Decl_local_global};
Decl_local_global: Declaracao_local | Declaracao_global;
Declaracao_local: 'declare' Variavel | 'constante' IDENT ':' Tipo_basico '=' Valor_constante | 'tipo' IDENT ':' Tipo;
Variavel: Identificador {',' Identificador} ':' Tipo;
Identificador: IDENT {'.' IDENT} Dimensao;
Dimensao: {'[', Exp_aritmetica ']' };
WS : ( ' ' | '\t' | '\r' | '\n' )+ -> skip;
Tipo: Registro | Tipo_estendido;
Tipo_basico: 'literal' | 'inteiro' | 'real' | 'logico';
Tipo_basico_ident: Tipo_basico | IDENT;
Tipo_estendido: ['^'] Tipo_basico_ident;
Valor_constante: CADEIA | NUM_INT | 'verdadeiro' | 'falso';
Registro: 'registro' {Variavel} 'fim_registro';
Declaracao_global: 'procedimento' IDENT '(' [Parametros] ')' {Declaracao_local} {Cmd} 'fim_procedimento'
                    | 'funcao' IDENT '(' [Parametros] ')' ':' Tipo_estendido {Declaracao_local} {cmd} 'fim_funcao';
Parametro: ['var'] Identificador {',' Identificador} ':' Tipo_estendido;
Parametros: Parametro {',' Parametro};
Corpo: {Declaracao_local} {Cmd};
Cmd: CmdLeia | CmdEscreva | CmdSe | CmdCaso | CmdPara | CmdEnquanto
    | CmdFaca | CmdAtribuicao | CmdChamada | CmdRetorne;
CmdLeia: 'leia' '(' ['^'] Identificador {',' ['^'] Identificador} ')';
CmdEscreva: 'escreva' '(' Expressao {',' Expressao} ')';
CmdSe: 'se' Expressao 'entao' {Cmd} ['senao {Cmd}] 'fim_se';
CmdCaso: 'caso' Exp_aritmetica 'seja' Selecao ['senao' {Cmd}] 'fim_caso;
CmdPara: 'para' IDENT  '<-' Exp_aritmetica 'ate' Exp_aritmetica 'faca' {Cmd} 'fim_para';
CmdEnquanto: 'enquanto' Expressao 'faca' {Cmd} 'fim_enquanto';
CmdFaca: 'faca' {Cmd} 'ate' Expressao;
CmdAtribuicao: ['^'] Identificador '<-' Expressao;
CmdChamada: IDENT '(' Expressao {',' Expressao} ')';
CmdRetorne: 'retorne' Expressao;
Selecao: {Item_selecao};
Item_selecao: Constantes ':' {Cmd};
Constantes: Numero_intervalo {',' Numero_intervalo};
Numero_intervalo: [Op_unario] NUM_INT ['..' [Op_unario] NUM_INT];
Op_unario: '-';
Exp_aritmetica: Termo {Op1 Termo}*;
Termo: Fator {Op2 Fator};
Fator: Parcela {Op3 Parcela};
Op1: '+' | '-';
Op2: '*' | '/';
Op3: '%';
Parcela: [Op_unario] Parcela_unario | Parcela_nao_unario;
Parcela_unario: ['^'] Identificador
                | IDENT '(' Expressao {',' Expressao} ')'
                | NUM_INT
                | NUM_REAL
                | '(' Expressao ')';
Parcela_nao_unario: '&' Identificador | CADEIA;
Exp_relacional: Exp_aritmetica [Op_relacional Exp_aritmetica];
Op_relacional: '=' | '<>' | '<=' | '>' | '<';
Expressao: Termo_Logico {Op_logico_1 Termo_Logico};
Termo_Logico: Fator_logico {Op_logico_2 Fator_logico};
Fator_logico: ['nao'] Parcela_logica;
Parcela_logica: ('verdadeiro' | 'falso')
                | Exp_relacional;
Op_logico_1: 'ou';
Op_logico_2: 'e';
Comentario: '{' ~('\n')* '}' -> skip;

