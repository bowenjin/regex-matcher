package com.bowenjin.regex;

import static org.junit.Assert.*;
import org.junit.Test;

public class NFATest{
  @Test
  public void test0(){
   final String regex = "a";
   final String [] validStrs = {"a"};
   final String [] invalidStrs = {"b"};
   testRegexOnStrings(regex, validStrs, invalidStrs); 
  }

  @Test
  public void test1(){
   final String regex = "a*";
   NFAState nfa = new Parser(new Tokenizer(regex)).parse();
   assertTrue(nfa.traverse(""));
   assertTrue(nfa.traverse("a"));
   assertTrue(nfa.traverse("aa"));
   assertFalse(nfa.traverse("b"));
   assertFalse(nfa.traverse("bb"));
   assertFalse(nfa.traverse("ab"));
  }

  @Test
  public void test2(){
    final String regex = "a*b*c*";
    final String [] validStrs = {"", "a", "b", "c", "ab", "bc", "abc", "aa", "bb", "cc", "aabbcc"}; 
    final String [] invalidStrs = {"d", "dabc", "adbc", "abdc", "abcd", "ad", "bd", "cd", "aad", "bbd", "ccd", "bac", "cab"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }

  @Test
  public void test3(){
    final String regex = "...";
    final String [] validStrs = {"abc", "%^&", "xyz"};
    final String [] invalidStrs = {"a", "ab", "abcd"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }
  
  @Test
  public void test4(){
    final String regex = "abc|dbc|xyz";
    final String [] validStrs = {"abc", "dbc", "xyz"};
    final String [] invalidStrs = {"bc", "xy", "ab", "db"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }

  @Test
  public void test5(){
    final String regex = "a*|b*|c*";
    final String [] validStrs = {"", "a", "b", "c", "aa", "bb", "cc"};
    final String [] invalidStrs = {"ab", "bc", "ac"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }

  private static void testRegexOnStrings(String regex, String [] validStrs, String [] invalidStrs){
    NFAState nfa = new Parser(new Tokenizer(regex)).parse();
    for(String validStr: validStrs){
      assertTrue(nfa.traverse(validStr));
    }
    for(String invalidStr: invalidStrs){
      assertFalse(nfa.traverse(invalidStr));
    }
  }  
}
