package com.bowenjin.regex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * An implementation of the UNIX grep program in Java.
 */
public class Grep{
  public static void main(String [] args) throws IOException{
    if(args.length != 2){
      System.err.println("Usage: grep [pattern] [file]");
      return;
    }
    String regex = args[0];
    String fileName = args[1];
    BufferedReader bufferedReader;
    GrepMatcher matcher;
    try{
      bufferedReader = new BufferedReader(new FileReader(fileName));
    }catch(IOException e){
      System.err.println(e.getMessage());
      return;
    }
    //try{
      matcher = new GrepMatcher(regex);
    /*}catch(InvalidRegexException e){
      System.err.println("Invalid regex: " + regex);
      System.err.println(e.getMessage());
      return;
    }*/

    String line;
  nextLine:
    while((line = bufferedReader.readLine()) != null){
      for(int i = 0; i < line.length(); i++){
        int endIndex;
        if((endIndex = matcher.matchIndex(line.substring(i))) != -1){
          System.out.println(line.substring(0, i) + "<match>" + line.substring(i, endIndex) + "</match>" + line.substring(endIndex));
          continue nextLine;
        } 
      } 
    }
  }
}
