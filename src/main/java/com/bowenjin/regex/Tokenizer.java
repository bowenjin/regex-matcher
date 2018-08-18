package com.bowenjin.regex;

import java.io.Reader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.InputStream;
import java.io.IOException;

/**
 * Regex Tokenizer
 */
class Tokenizer 
{
  Reader input;
  Tokenizer(String input){
    this.input = new StringReader(input);
  }
  
  //catch the IOException, should never be thrown because we read from a String
  private int readInput(){
    try{
      return input.read();
    }catch(IOException e){
      throw new RuntimeException(e);
    }
  }

  Token nextToken(){
    Token.Type type;
    int c;
    switch(c = readInput()){
      case -1:
        type = Token.Type.EOI;
        break;
      case '|':
        type = Token.Type.OR;
        break;
      case '*':
        type = Token.Type.STAR;
        break;
      case '.':
        type = Token.Type.DOT;
        break;
      case '(':
        type = Token.Type.LEFTPAREN;
        break;
      case ')':
        type = Token.Type.RIGHTPAREN;
        break;
      case '+':
        type = Token.Type.PLUS;
        break;
      case '?':
        type = Token.Type.QUESTION;
        break;
      default:
        type = Token.Type.CHAR;       
    }
    return new Token(type, (char)c);
  }
}
