package com.bowenjin.regex;

import static org.junit.Assert.*;
import org.junit.Test;

public class MatcherTest{
  @Test
  public void test0() throws InvalidRegexException{
   final String regex = "a";
   final String [] validStrs = {"a"};
   final String [] invalidStrs = {"b"};
   testRegexOnStrings(regex, validStrs, invalidStrs); 
  }
  
  /**
   * Tests from the book Algorithms 4th edition by Robert Sedgewick, in Chapter 5.4 page 789
   */
  @Test
  public void sedgewickTests() throws InvalidRegexException{
    testRegexOnStrings("(A|B)(C|D)", new String[]{"AC", "AD", "BC", "BD"}, new String[]{"AB", "BA", "CD", "DC"});
    testRegexOnStrings("A(B|C)*D", new String[]{"AD", "ABD", "ACD", "ABCCBD"}, new String[]{"BCD", "ADD", "ABCBC"});
    testRegexOnStrings("A*|(A*BA*BA*)*", new String[]{"AAA", "BBAABB", "BABAAA"}, new String[]{"ABA", "BBB", "BABBAAA"});
  }

  @Test
  public void test1() throws InvalidRegexException{
   final String regex = "abc";
   Matcher matcher = new Matcher(regex);
   assertTrue(matcher.match("abc"));
   assertFalse(matcher.match(""));
   assertFalse(matcher.match("a"));
   assertFalse(matcher.match("ab"));
   assertFalse(matcher.match("abcc"));

   matcher = new Matcher("");
   assertTrue(matcher.match(""));
   assertFalse(matcher.match("a"));
  
   matcher = new Matcher("()");
   assertTrue(matcher.match(""));
   assertFalse(matcher.match("a"));
  }

  @Test
  public void test2() throws InvalidRegexException{
   final String regex = "a*";
   Matcher matcher = new Matcher(regex);
   assertTrue(matcher.match(""));
   assertTrue(matcher.match("a"));
   assertTrue(matcher.match("aa"));
   assertFalse(matcher.match("b"));
   assertFalse(matcher.match("bb"));
   assertFalse(matcher.match("ab"));
  }

  @Test
  public void test3() throws InvalidRegexException{
    final String regex = "a*b*c*";
    final String [] validStrs = {"", "a", "b", "c", "ab", "bc", "abc", "aa", "bb", "cc", "aabbcc"}; 
    final String [] invalidStrs = {"d", "dabc", "adbc", "abdc", "abcd", "ad", "bd", "cd", "aad", "bbd", "ccd", "bac", "cab"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }

  @Test
  public void test4() throws InvalidRegexException{
    final String regex = "...";
    final String [] validStrs = {"abc", "%^&", "xyz"};
    final String [] invalidStrs = {"a", "ab", "abcd"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }
  
  @Test
  public void test5() throws InvalidRegexException{
    final String regex = "abc|dbc|xyz";
    final String [] validStrs = {"abc", "dbc", "xyz"};
    final String [] invalidStrs = {"bc", "xy", "ab", "db","abd", "aaa", "ddd", "xxx", "dba", "xyd"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }

  @Test
  public void test6() throws InvalidRegexException{
    final String regex = "a*|b*|c*";
    final String [] validStrs = {"", "a", "b", "c", "aa", "bb", "cc"};
    final String [] invalidStrs = {"ab", "bc", "ac"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }
  
  @Test
  public void testPlus() throws InvalidRegexException{
    Matcher matcher1 = new Matcher("a+");
    assertTrue(matcher1.match("a"));
    assertTrue(matcher1.match("aa"));
    assertTrue(matcher1.match("aaa"));
    assertFalse(matcher1.match(""));
    assertFalse(matcher1.match("ab"));
    assertFalse(matcher1.match("ba"));
   
    Matcher matcher2 = new Matcher(".+"); 
    assertTrue(matcher2.match("a"));
    assertTrue(matcher2.match("ab"));
    assertFalse(matcher2.match(""));
    
    Matcher matcher3 = new Matcher("(a+b+c+)+"); 
    assertTrue(matcher3.match("abc"));
    assertTrue(matcher3.match("aabbcc"));
    assertTrue(matcher3.match("abcabcaabbcc"));
    assertFalse(matcher3.match("abcabcaabbccb"));
  }
  
  @Test
  public void testQuestion() throws InvalidRegexException{
    Matcher matcher1 = new Matcher("a?");
    assertTrue(matcher1.match(""));
    assertTrue(matcher1.match("a")); 
    assertFalse(matcher1.match("aa"));
    assertFalse(matcher1.match("b"));
   
    Matcher matcher2 = new Matcher("a.?"); 
    assertTrue(matcher2.match("a"));
    assertTrue(matcher2.match("ab"));
    assertFalse(matcher2.match(""));
    assertFalse(matcher2.match("aaa"));
    
    Matcher matcher3 = new Matcher("a?b?c?"); 
    assertTrue(matcher3.match("abc"));
    assertTrue(matcher3.match(""));
    assertTrue(matcher3.match("bc"));
    assertFalse(matcher3.match("aac"));
  }

  @Test
  public void testSet() throws InvalidRegexException{
    Matcher matcher1 = new Matcher("[abc]+");
    assertFalse(matcher1.match(""));
    assertTrue(matcher1.match("a"));
    assertTrue(matcher1.match("b"));
    assertTrue(matcher1.match("c"));
    assertFalse(matcher1.match("d"));
    assertTrue(matcher1.match("abc"));
    assertFalse(matcher1.match("abcd"));

    Matcher matcher2 = new Matcher("[(a)*?+()*|]");
    assertTrue(matcher2.match("a"));
    assertTrue(matcher2.match("?"));
    assertTrue(matcher2.match("+"));
    assertTrue(matcher2.match("("));
    assertTrue(matcher2.match(")"));
    assertTrue(matcher2.match("*"));
    assertTrue(matcher2.match("|"));
    assertFalse(matcher2.match("aa"));
  }
  
  @Test
  public void testRange() throws InvalidRegexException{
    Matcher matcher1 = new Matcher("[a-z]");
    for(char c = 'a'; c <= 'z'; c++){
      assertTrue(matcher1.match("" + c));
    }
    assertFalse(matcher1.match("A"));
    assertFalse(matcher1.match("0"));
    assertFalse(matcher1.match("aa"));
   
    Matcher matcher2 = new Matcher("[0-9]*");
    assertTrue(matcher2.match(""));
    for(int i = 0; i <= 9; i++){
      assertTrue(matcher2.match("" + i));
    }
    assertTrue(matcher2.match("901"));
    assertFalse(matcher2.match("a"));
    
    Matcher matcher3 = new Matcher("[0-Z][a-z]");
    assertTrue(matcher3.match("Za"));
    assertTrue(matcher3.match("9z"));
    assertTrue(matcher3.match("Ao"));
    assertFalse(matcher3.match(""));
    assertFalse(matcher3.match("A"));
    assertFalse(matcher3.match("AA"));
  }

  private static void testRegexOnStrings(String regex, String [] validStrs, String [] invalidStrs) throws InvalidRegexException{
    Matcher matcher = new Matcher(regex);
    for(String validStr: validStrs){
      assertTrue(matcher.match(validStr));
    }
    for(String invalidStr: invalidStrs){
      assertFalse(matcher.match(invalidStr));
    }
  }  
}
