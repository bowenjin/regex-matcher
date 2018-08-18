package com.bowenjin.regex;

public class InvalidRegexException extends Exception{
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
