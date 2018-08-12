package com.bowenjin.regex;

import static org.junit.Assert.*;
import org.junit.Test;

public class MatcherTest{
  @Test
  public void test0(){
   final String regex = "a";
   final String [] validStrs = {"a"};
   final String [] invalidStrs = {"b"};
   testRegexOnStrings(regex, validStrs, invalidStrs); 
  }

  @Test
  public void test1(){
   final String regex = "abc";
   Matcher matcher = new Matcher(regex);
   assertTrue(matcher.match("abc"));
   assertFalse(matcher.match(""));
   assertFalse(matcher.match("a"));
   assertFalse(matcher.match("ab"));
   assertFalse(matcher.match("abcc"));
  }

  @Test
  public void test2(){
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
  public void test3(){
    final String regex = "a*b*c*";
    final String [] validStrs = {"", "a", "b", "c", "ab", "bc", "abc", "aa", "bb", "cc", "aabbcc"}; 
    final String [] invalidStrs = {"d", "dabc", "adbc", "abdc", "abcd", "ad", "bd", "cd", "aad", "bbd", "ccd", "bac", "cab"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }

  @Test
  public void test4(){
    final String regex = "...";
    final String [] validStrs = {"abc", "%^&", "xyz"};
    final String [] invalidStrs = {"a", "ab", "abcd"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }
  
  @Test
  public void test5(){
    final String regex = "abc|dbc|xyz";
    final String [] validStrs = {"abc", "dbc", "xyz"};
    final String [] invalidStrs = {"bc", "xy", "ab", "db","abd", "aaa", "ddd", "xxx", "dba", "xyd"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }

  @Test
  public void test6(){
    final String regex = "a*|b*|c*";
    final String [] validStrs = {"", "a", "b", "c", "aa", "bb", "cc"};
    final String [] invalidStrs = {"ab", "bc", "ac"};
    testRegexOnStrings(regex, validStrs, invalidStrs);
  }

  private static void testRegexOnStrings(String regex, String [] validStrs, String [] invalidStrs){
    Matcher matcher = new Matcher(regex);
    for(String validStr: validStrs){
      assertTrue(matcher.match(validStr));
    }
    for(String invalidStr: invalidStrs){
      assertFalse(matcher.match(invalidStr));
    }
  }  
}
