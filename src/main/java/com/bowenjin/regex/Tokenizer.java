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
  Tokenizer(InputStream input){
    this.input = new InputStreamReader(input); 
  }
  Tokenizer(String input){
    this.input = new StringReader(input);
  }
  Token nextToken() throws IOException{
    Token.Type type;
    int c;
    switch(c = input.read()){
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
      default:
        type = Token.Type.CHAR;       
    }
    return new Token(type, (char)c);
  }
}
