package com.bowenjin.regex;

class Token{
  enum Type{
    EOI,
    CHAR,
    DOT,
    OR,
    STAR,
    LEFTPAREN,
    RIGHTPAREN,
    PLUS,
    QUESTION,
    LEFTBRACKET,
    RIGHTBRACKET,
    DASH
  }

  Type type;
  char value;
  public Token(Type type, char value){
    this.type = type;
    this.value = value;
  }
}
