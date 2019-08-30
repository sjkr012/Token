import java.io.*;
import java.util.*;

//  SP18 CMPSC461 section1 programming assignment #1
//  FILE: Lexer.java
//  NAME: SEUNGJOO KIM
//  PSU EMAIL: SQK5588
//  PSU ID: 971482254
//  OS: WINDOWS 10
//  Java Version: JAVA 1.8
//  Description: a lexical analyzer that reads strings and convert to Token 
//               and pased in to Parser



class Lexer{
    private final String letters = "abcdefghijklmnopqrstuvmxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String digits = "0123456789";

    String stmt;
    int index = 0; // index for nextChar() function
    char ch; // character passed in from String
    String key; // string output value for keyword item
	
	// initialize variables
    public Lexer(String s){ 
        stmt = s; 
		index=0; 
		ch = nextChar();
    }
	
	// this function looping through each characters of strings
	// to return Token and assign TokenType, and TokenValue
	// Tokens split into four: STRING, KEYWORD, EOI, INVALID
    public Token nextToken(){
        do {
			// this if statements check if Token is KEYWORD
			// if first character is <, then concatenate strings and digits and assign values
			if (ch == '<'){ 
				ch = nextChar(); 
				if (Character.isLetter(ch)) {
					String key = concat(letters + digits);
					return new Token(Token.TokenType.KEYWORD, "<" + key);
				}
				// if character contains '/', then concatenate strings and output </sth> 
				else if(ch == '/'){
					ch = nextChar();
					String key = concat(letters + digits);
					return new Token(Token.TokenType.KEYWORD, "<" + "/" + key);
				}
			}
			// if current ch is letter or digit, concatenate letters and digits, and return Token STRING
			if (Character.isLetter(ch) || Character.isDigit(ch) ) {
                String str = concat (letters + digits);
                return new Token(Token.TokenType.STRING, str);
            } 
			else {
				switch (ch) {
					// if character is empty, then just move to next character
					case ' ': 
						ch = nextChar(); 
						break;
					// if character is $, then assign TOKEN EOI
					case '$':
						return new Token(Token.TokenType.EOI, "");
					default:
					// if every steps and statements don't match, set TOKEN to INVALID
						ch = nextChar();
						return new Token(Token.TokenType.INVALID, Character.toString(ch));
				}
			}
        } while (true);
    }
	// this function shifts current character to next character of strings
    private char nextChar() {
        char ch = stmt.charAt(index); index = index+1;
        return ch;
    }

	// this function basically concatenate any values passed into this function
	// until it reaches end of index
	// also if current ch is '>', then just return concatenated values until then.
    private String concat (String set) {
        StringBuffer r = new StringBuffer("");
        do { 
		   r.append(ch); 
		   ch = nextChar();
		   if(ch == ('>')){
			 r.append(ch);
			 ch = nextChar();
			 return r.toString();
		   }
        } while (set.indexOf(ch) >= 0);
        return r.toString();
    }

	// this functions checks whether next character is equal to input value
    private boolean check(char c) {
        ch = nextChar();
        if (ch == c) 
			return true;
        else 
			return false;
    }
	// if it reached error then prints out specific error message
	// i didn't use for this project
    public void error (String msg) {
        System.err.println("\nError: location " + index + " " + msg);
        System.exit(1);
    }

}