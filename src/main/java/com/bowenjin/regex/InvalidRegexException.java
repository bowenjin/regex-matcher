package com.bowenjin.regex;

class InvalidRegexException extends RuntimeException{
  InvalidRegexException(Exception e){
    super(e);
  }
  InvalidRegexException(String s){
    super(s);
  }
  InvalidRegexException(){
    super();
  }
}
