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

  NFAState parse(){
    try{
      return expr();
    }catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }
  }

  private NFAState expr(){
    NFAState newState = term();
    return termList(newState);
  }

  private NFAState term(){
    NFAState newState = factor();
    return factorList(newState);
  }
  
  private NFAState termList(NFAState left){
    NFAState right, newState;
    if(currentToken.type == Token.Type.OR){
      advance();
      right = term();
      newState = NFABuilder.or(left, right);
      return termList(newState);
    } 
    return left;
  }

  private NFAState factor(){
    NFAState newState, ret;
    switch(currentToken.type){
      case CHAR:
      case DOT:
        newState = NFABuilder.regexChar(currentToken.value);
        advance();
        ret = factorTail(newState);
        break;
      case LEFTPAREN:
        advance();
        newState = expr();
        consume(Token.Type.RIGHTPAREN);
        ret = factorTail(newState);
        break;
      default:
        throw exception(Token.Type.CHAR, Token.Type.DOT, Token.Type.LEFTPAREN);
    }
    return ret;   
  }
  
  private NFAState factorList(NFAState left){ 
    NFAState right, newState;
    switch(currentToken.type){
      case CHAR:
      case DOT:
      case LEFTPAREN:
        right = factor();
        newState = NFABuilder.concat(left, right);
        return factorList(newState);
    }
    return left;    
  }

  private NFAState factorTail(NFAState left){
    NFAState newState;
    if(currentToken.type == Token.Type.STAR){
      advance();
      newState = NFABuilder.star(left);
      return factorTail(newState);
    }
    return left;
  }

}
