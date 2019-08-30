import java.io.*;
import java.util.*; // included to use List<string>

//  SP18 CMPSC461 section1 programming assignment #1
//  FILE: Parser.java
//  NAME: SEUNGJOO KIM
//  PSU EMAIL: SQK5588
//  PSU ID: 971482254
//  OS: WINDOWS 10
//  Java Version: JAVA 1.8
//  Description: a syntactic analyzer that reads Tokens and outputs Abstract Syntax Tree

class Parser{

	// variables used
    Lexer lexer;
    Token token;
    String tab = "  "; // use for indentation
	
	List<String> manlist = new ArrayList<String>(); // array List to check grammar
	
	//initialize variables and statements
    public Parser(String s){ 
        lexer = new Lexer(s + "$");
        token = lexer.nextToken();
    }

	// function used to run functions below
    public void run () { 
        webpage();
    }

	// Performs WEBPAGE instruction in E-BNF
    public void webpage() {
		// if first token doesn't match specifically with <body>, then return error message
		if(token.getTokenValue().equals("<body>")) {
			System.out.println(token.getTokenValue());
			do{

				token = lexer.nextToken();
				text();
			}while(token.getTokenValue().equals("</body>")); // while loop to perform <body> { TEXT } </body>
		}
		else {
			System.out.println("Syntax error: expecting <body>, but saw token " + token.getTokenValue());
		}
		grammarCheck(); // check for any grammar mistake; if it does have an error , print error msg.
		token = lexer.nextToken();
        match(Token.TokenType.EOI); // check whether statement reached end of instructions
        System.out.println("</body>");
    }

	// Performs TEXT instruction in E-BNF
    public void text() {
		
		// each if statements test whether TOKEN is valid TEXT
		// each checks for string value; if it matches then print TOKEN value 
		// and moves to next Token
		// and add value to arraylist to be used for grammar checks
		
		if(token.getTokenValue().equals("<b>")) {
			System.out.println(tab + "<b>");
			manlist.add(token.getTokenValue());
			token = lexer.nextToken();
			tab += "  "; // add indentation
			text();	
		}
		else if(token.getTokenValue().equals("<i>")) {
			System.out.println(tab + "<i>");
			manlist.add(token.getTokenValue());
			token = lexer.nextToken();
			tab += "  ";
			text();
		}
		else if(token.getTokenValue().equals("<ul>")) {
			System.out.println(tab + "<ul>");
			manlist.add(token.getTokenValue());
			token = lexer.nextToken();
			tab += "  ";
			listitem();
		}
		//however if token is STRING, not added to arrayList as it is not used for grammar checks
		else if(token.getTokenType().equals(Token.TokenType.STRING)) { 
			System.out.println(tab + token.getTokenValue());
			token = lexer.nextToken();
			text();
		}
		else if(token.getTokenValue().contains("/")){
			tab = tab.substring(0,tab.length() - 2); // minus indentation for proper grammar
			if(token.getTokenValue().equals("</b>")){	
				System.out.println(tab + "</b>");
				manlist.add(token.getTokenValue());
				token = lexer.nextToken();
				text();
			}
			else if(token.getTokenValue().equals("</i>")){
				System.out.println(tab + "</i>");
				manlist.add(token.getTokenValue());
				token = lexer.nextToken();
				text();
			}
			else if(token.getTokenValue().equals("</ul>")){ // performs <ul> { LISTITEM } </ul>
				System.out.println(tab + "</ul>");
				manlist.add(token.getTokenValue());
				token = lexer.nextToken();
				text();
			}
			else if(token.getTokenValue().equals("</li>")){
				listitem();
			}			
		}
		else if(token.getTokenValue().equals("</body>")){
			return;
		}
		else if(token.getTokenType().equals(Token.TokenType.EOI)) {
			return;
		}
		// returns error message for invalid type and value
		else{
			System.out.println("Syntax error: expecting Token value <b>,</b>,<i>,</i>,<ul> or </ul>, but saw token " 
								+ token.getTokenValue() + "  " + token.getTokenType());
            System.exit(1);
		}
		
    }

    public void listitem () {
		// have same logic as TEXT() function does
        if (token.getTokenValue().equals("<li>")) {
            System.out.println(tab + "<li>");
			manlist.add(token.getTokenValue());
			token = lexer.nextToken();
			tab += "  ";
			text();
        }
		else if(token.getTokenValue().contains("/")){
			if (token.getTokenValue().equals("</li>")) {
				System.out.println(tab + "</li>");
				manlist.add(token.getTokenValue());
				token = lexer.nextToken();
				text();
			}
		}
		// error message for invalid value
		else {
            System.out.println("Syntax error: expecting Token format <li> text </li>, but saw token " + token.getTokenValue());
            System.exit(1);
        }
        token = lexer.nextToken();
		text(); // go back to text functio
    }

	// check if input value matches with current TOKEN value
    private String match(Token.TokenType tp){
        String value = token.getTokenValue();
        if (token.getTokenType() == tp)
            token = lexer.nextToken();
        else error(tp);
        return value;
    }
	
	// helper function; this function checks for correct grammar
	// e.g) <b>
	//         hi
	//      </b>
	// <b> and </b> needs to have same indentation and needs to be parallel		
	public void grammarCheck() {
		
		int size = manlist.size(); // get the size of arraylist
		
		// for loop that checks whether TOKEN matches with their pair e.g) <b> pairs with </b>
		for(int i = 0; i < size/2 + 1; i++){
				// get two values of arraylist for comparing
				String case1 = manlist.get(i); 
				String case2 = manlist.get(size-1);
				// go through each TOKEN that needs to be matched and paired
				if (case1.equals("<b>")) {
					if (!case2.equals("</b>"))
						grammar();
				}
				else if (case1.equals("<i>")) {
					if (!case2.equals("</i>"))
						grammar();
				}
				else if (case1.equals("<ul>")) {
					if (!case2.equals("</ul>")) 
						grammar();
				}
				else if (case1.equals("<li>")) {
					if (!case2.equals("</li>")) 
						grammar();
				}
				size--;
		}
	}
	
	// prints out error message if there is a grammar error
	public void grammar() {
		System.out.println("Grammar error: wrong grammar found");
		System.exit(1);
	}
	// if match function fails, then prints out error message 
    private void error(Token.TokenType tp){
		System.out.println("\t" + token.getTokenValue());
		System.out.println("\t" + token.getTokenValue());
		System.out.println("\t" + token.getTokenValue());
        System.err.println("Syntax error: expecting: " + Token.typeToString(tp)
                           + "; saw: "
                           + Token.typeToString(token.getTokenType()));
        System.exit(1);
    }

}