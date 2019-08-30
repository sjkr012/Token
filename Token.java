
import java.lang.String;
import java.io.*;
import java.util.*;

//  SP18 CMPSC461 section1 programming assignment #1
//  FILE: Token.java
//  NAME: SEUNGJOO KIM
//  PSU EMAIL: SQK5588
//  PSU ID: 971482254
//  OS: WINDOWS 10
//  Java Version: JAVA 1.8
//  Description: a basic building blocks of program that stores value and category of any token

public class Token{
    public enum TokenType {STRING, KEYWORD, EOI, INVALID}

    private TokenType type;
    private String val;

	// construct functions that set up values and categories for each token
    Token(TokenType t, String v) {
        type = t; val = v;
    }
	// get a type of current token
    TokenType getTokenType() {
		return type;
	}
	// get a value of current token
    String getTokenValue() {
		return val;
	}

	// prints out category of each token (string,keyword -> val; EOI->""; INVALID->"INVALID)
    void print () {
        String s = "";
        switch (type) {
        case STRING: 
		case KEYWORD: 
			s = val; 
			break;  // case for string and keyword
        case EOI: 
			s = "";
        case INVALID: 
			s = "invalid"; 
			break;
        }
        System.out.print(s);
    }
	// convert each token to string
    public static String typeToString (TokenType tp) {
        String s = "";
        switch (tp) {
        case STRING: s = "String"; break;
        case KEYWORD: s = "Keyword"; break;
        case INVALID: s="Invalid"; break;
        }
        return s;
    }
    
}



