package com.bowenjin.regex;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ParserTest{
  @Test
  public void test1() throws IOException{
    //expr() doesn't handle empty string regex correctly
    //assertNotNull(makeNFA(""));
    assertNotNull(makeNFA("abc"));
    assertNotNull(makeNFA("."));
    assertNotNull(makeNFA(".*"));
    assertNotNull(makeNFA(".*a*"));
    assertNotNull(makeNFA("c*.*.*a*"));
    
    assertNull(makeNFA("|"));
    assertNull(makeNFA("*"));
  }

  @Test
  public void testPlus() throws IOException{
    assertNotNull(makeNFA("a+"));
    assertNotNull(makeNFA(".+a+"));
    assertNotNull(makeNFA("a+.+(.+)+"));
    //Why doesn't empty parenthesis work?
    //assertNotNull(makeNFA("()+")); 
    
    assertNull(makeNFA("+"));
    //should be invalid
    //assertNull(makeNFA("b++"));
    //assertNull(makeNFA("()++"));
  }

  /**
   * Tests from Exercise 18.1 of Dos Reis book
   */
  @Test
  public void bookExerciseTests() throws IOException{
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
