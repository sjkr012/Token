import java.io.*;

//  SP18 CMPSC461 section1 programming assignment #1
//  FILE: test.java
//  NAME: SEUNGJOO KIM
//  PSU EMAIL: SQK5588
//  PSU ID: 971482254
//  OS: WINDOWS 10
//  Java Version: JAVA 1.8
//  Description: test file that tests whether my assignment creates correct output


class Test
{
	public static void main(String args[]) throws IOException
	{
		// test case 1
		// test for basic case
		System.out.println();
		System.out.println("Test 1: <body> google <b><i><b> yahoo</b></i></b></body>" + "\t");
		Parser a = new Parser ("<body> google <b><i><b> yahoo</b></i></b></body>");
		a.run();
		System.out.println();
		
		// test case 2
		// test to find out <ul> { LISTITEM } </ul> works properly
		System.out.println("Test 2: <body> amazon <ul><li><b><ul><li>this</li></ul></b></li></ul></body>" + "\t");
		Parser b = new Parser("<body> amazon <ul><li><b><ul><li>this</li></ul></b></li></ul></body>");
		b.run();
		System.out.println();
		
		// test case 3
		// this should fail as it has wrong grammar
		System.out.println("Test 3: <body><ul><li>test</ul></li></body>" + "\t");
		Parser c = new Parser("<body><ul><li>test</ul></li></body>");
		c.run();
		System.out.println();
		
		// test case 4
		// this should fail as TEXT should be used right after WEBPAGE is called, but LISTITEM is used here
		System.out.println("Test 3: <body><li>test</li></body>" + "\t");
		Parser d = new Parser("<body><li>test</li></body>");
		d.run();
		System.out.println();
	}
	
	
}