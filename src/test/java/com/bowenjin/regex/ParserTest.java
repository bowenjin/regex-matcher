package com.bowenjin.regex;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ParserTest{
  @Test
  public void test1() throws IOException{
    String [] validInputs = {"abc", ".", ".*", ".*a*", "c*.*.*a*"};  
    for(String s: validInputs){
      Tokenizer tokenizer = new Tokenizer(new ByteArrayInputStream(s.getBytes()));
      Parser parser = new Parser(tokenizer);
      assertTrue(parser.parse());
    }
  }

  @Test
  public void test2() throws IOException{
    String [] invalidInputs = {"|", "*"};
    for(String s: invalidInputs){
      Tokenizer tokenizer = new Tokenizer(new ByteArrayInputStream(s.getBytes()));
      Parser parser = new Parser(tokenizer);
      assertFalse(parser.parse());
    }
  }
}
