package com.bowenjin.regex;

import java.io.IOException;

class Parser{
  private Tokenizer tokenizer;
  private Token currentToken;

  Parser(Tokenizer tokenizer){
    this.tokenizer = tokenizer;
    try{
      currentToken = tokenizer.nextToken();
    }catch(IOException e){
      throw new RuntimeException(e);
    }
  }

  private void advance(){
    try{
      currentToken = tokenizer.nextToken();
    }catch(IOException e){
      throw new RuntimeException(e);
    }
  }

  private RuntimeException exception(String expected, String found){
    return new RuntimeException("Expected " + expected + ", but found " + found);
  }

  private RuntimeException exception(Token.Type... expectedTokenTypes){
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < expectedTokenTypes.length; i++){
      sb.append(expectedTokenTypes[i].name());
      if(i != expectedTokenTypes.length - 1){
        sb.append(" or ");
      }
    }
    return exception(sb.toString(), currentToken.type.name());
  }

  private void consume(Token.Type t){
    if(currentToken.type == t){
      advance();
    }
    exception(t);
  }

  boolean parse(){
    try{
      expr();
    }catch(Exception e){
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }

  private void expr(){
    term();
    termList();
  }

  private void term(){
    factor();
    factorList();
  }
  
  private void termList(){
    if(currentToken.type == Token.Type.OR){
      advance();
      term();
      termList();
    } 
  }

  private void factor(){
    switch(currentToken.type){
      case CHAR:
      case DOT:
        advance();
        factorTail();
        break;
      case LEFTPAREN:
        advance();
        expr();
        consume(Token.Type.RIGHTPAREN);
        factorTail();
        break;
      default:
        throw exception(Token.Type.CHAR, Token.Type.DOT, Token.Type.LEFTPAREN);
    }   
  }
  
  private void factorList(){ 
    switch(currentToken.type){
      case CHAR:
      case DOT:
      case LEFTPAREN:
        factor();
        factorList();
    }   
  }

  private void factorTail(){
    if(currentToken.type == Token.Type.STAR){
      advance();
      factorTail();
    }
  }

}
