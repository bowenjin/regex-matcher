package com.bowenjin.regex.grep;

import com.bowenjin.regex.Matcher;
import com.bowenjin.regex.InvalidRegexException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

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
    try{
      grep(regex, fileName, System.out);
    }catch(IOException e){
      System.err.println(e.getMessage());
      return;
    }catch(InvalidRegexException e){
      System.out.println(e.getMessage());
      return;
    }
  }

  static public void grep(String regex, String fileName, PrintStream out) throws IOException, InvalidRegexException{
    BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
    grep(regex, bufferedReader, out);
  }

  public static void grep(String regex, BufferedReader bufferedReader, PrintStream out) throws IOException, InvalidRegexException{
    Matcher matcher = new Matcher(".*" + regex + ".*");
    String line;
    while((line = bufferedReader.readLine()) != null){
      if(matcher.match(line)){
        out.println(line);
      }
    }
  } 
}
