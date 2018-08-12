package com.bowenjin.regex;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ParserTest{
  @Test
  public void test1() throws IOException{
    assertNotNull(makeNFA("abc"));
    assertNotNull(makeNFA("."));
    assertNotNull(makeNFA(".*"));
    assertNotNull(makeNFA(".*a*"));
    assertNotNull(makeNFA("c*.*.*a*"));
    
    assertNull(makeNFA("|"));
    assertNull(makeNFA("*"));
  }

  /**
   * Tests from the Exercise section of the book
   */
  @Test
  public void test2() throws IOException{
    assertNotNull(makeNFA("b"));
    assertNotNull(makeNFA("bc"));
    assertNotNull(makeNFA("b|c"));
    assertNotNull(makeNFA("b*"));
    assertNotNull(makeNFA("((b))"));
    assertNotNull(makeNFA("bc|b*"));
    
    assertNull(makeNFA("b|"));
    assertNull(makeNFA("*b"));
    assertNull(makeNFA("b)"));     
  }

  static NFAState makeNFA(String regex){
    return new Parser(new Tokenizer(new ByteArrayInputStream(regex.getBytes()))).parse(); 
  }
}
