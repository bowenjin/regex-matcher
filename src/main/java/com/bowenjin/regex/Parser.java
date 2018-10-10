package com.bowenjin.regex;

import java.io.IOException;

class Parser{
  private Tokenizer tokenizer;
  private Token currentToken;

  Parser(Tokenizer tokenizer){
    this.tokenizer = tokenizer;
    advance();
  }

  private void advance(){
    currentToken = tokenizer.nextToken();
  }
  private RuntimeException exception(String message){
    return new RuntimeException(message);
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
    }else {
      throw exception(t);
    }
  }

  NFAState parse() throws InvalidRegexException{
    try{
      NFAState start = expr();
      consume(Token.Type.EOI);
      return start;
    }catch(Exception e){
      throw new InvalidRegexException(e);
    }
  }

  private NFAState expr(){
    switch(currentToken.type){
      
      case RIGHTPAREN: 
      case EOI:
        return NFAState.regexChar((char)0);
      
      default:
        NFAState newState = term();
        return termList(newState);
    }
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
      newState = NFAState.or(left, right);
      return termList(newState);
    } 
    return left;
  }

  private NFAState factor(){
    NFAState newState, ret;
    switch(currentToken.type){
      case CHAR:
      case DOT:
        newState = NFAState.regexChar(currentToken.value);
        advance();
        ret = factorTail(newState);
        break;
      case LEFTPAREN:
        advance();
        newState = expr();
        consume(Token.Type.RIGHTPAREN);
        ret = factorTail(newState);
        break;
      case LEFTBRACKET:
        advance();
        newState = charClass();
        consume(Token.Type.RIGHTBRACKET);
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
      case LEFTBRACKET:
        right = factor();
        newState = NFAState.concat(left, right);
        return factorList(newState);
    }
    return left;    
  }

  private NFAState factorTail(NFAState left){
    NFAState newState = left;
    switch(currentToken.type){
      case STAR:
        advance();
        newState = NFAState.star(left);
        break;
      case PLUS:
        advance();
        newState = NFAState.plus(left);
        break;
      case QUESTION:
        advance();
        newState = NFAState.question(left);
        break;
    } 
    return newState;
  }

  private NFAState charClass(){
    NFAState left = element();
    return elementTail(left, left.label1);   
  }
  
  /**
   * Assumes the currentToken is not of type Token.Type.DASH or of type Token.Type.RIGHTBRACKET;
   */
  private NFAState element(){
    if(currentToken.type == Token.Type.RIGHTBRACKET || currentToken.type == Token.Type.DASH){
      throw new RuntimeException("Expected any token except " + Token.Type.RIGHTBRACKET + 
        " and " + Token.Type.DASH + " but found " + currentToken.type); 
    }
    NFAState ret = NFAState.regexChar(currentToken.value);
    advance();
    return ret;
  }
 
  private NFAState elementTail(NFAState left, char prevChar){
    if(currentToken.type == Token.Type.RIGHTBRACKET){
      return left;
    }else if(currentToken.type == Token.Type.DASH){
      advance();
      char start = (char)(prevChar + 1);
      char end = currentToken.value;
      NFAState newState = left;
      if(start > end){
        throw new RuntimeException(String.format("Starting range character '%s' must be <= ending character '%s'", start + "", end + "")); 
      }
      for(char c = start; c <= end; c++){
        NFAState right = NFAState.regexChar(c);
        newState = NFAState.or(newState, right);
      } 
      advance();
      if(currentToken.type == Token.Type.DASH){
        throw new RuntimeException("Expected non DASH token, but found DASH token");
      }
      return elementTail(newState, (char)0); //the prevChar will never be used   
    }else{
      NFAState right = element();
      NFAState newState = NFAState.or(left, right);
      return elementTail(newState, right.label1);
    }
  }
}
