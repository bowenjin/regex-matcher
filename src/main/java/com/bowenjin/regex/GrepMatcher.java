package com.bowenjin.regex;

/**
 * GrepMatcher is used by Grep inplace of a normal
 * Matcher because Grep defines a line to be a match if
 * any substring in that line matches the regex. Thus if our NFA
 * at any point enters an "acceptance state" or endState, we return
 * <code>true</code> for the string it was iterating our.
 */
class GrepMatcher extends Matcher{
  GrepMatcher(String regex) throws InvalidRegexException{
    super(regex);
  }

  /**
   * @param str String to be searched for matches
   * @return the end index of the match
   */	
  int matchIndex(String str){
    for(int i = 0; i < str.length(); i++){
      lambdaClosure();
      if(currentStates.contains(nfa.endState)){
        return i;
      }
      transition(str.charAt(i));
    }
    lambdaClosure();
    if(currentStates.contains(nfa.endState)){
      return str.length();
    }
    return -1;
  }
 
  @Override
  public boolean match(String str){
    return matchIndex(str) != -1;
  }
}
