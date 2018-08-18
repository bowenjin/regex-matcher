package com.bowenjin.regex;

import java.util.List;
import java.util.ArrayList; 
public class Matcher{
  protected List<NFAState> currentStates = new ArrayList<>();
  protected List<NFAState> nextStates = new ArrayList<>();
  protected NFAState nfa;
  
  public Matcher(String regex) throws InvalidRegexException{
    this.nfa = new Parser(new Tokenizer(regex)).parse();
  }
  
  /**
   * returns whether the regex that was used to create this matcher
   * matches {@code str}
   * <p>
   * Override matchInternal to change the behavior of how matches are determined.
   * @param str the string to match against this regex
   */
  public final boolean match(String str){
    currentStates.clear();
    currentStates.add(nfa);
    return matchInternal(str);
  }
  
  /**
   * Override this method to re-implement how matches are determined.
   * For example one may wish for the NFA to return a match if at any
   * point while matching {@code str} accept state is arrived at, 
   * rather than only at end, when all characters in {@code str} have been consumed.
   */
  protected boolean matchInternal(String str){
    for(int i = 0; i < str.length(); i++){
      lambdaClosure();
      transition(str.charAt(i));
    }
    lambdaClosure();
    return hasReachedAcceptState();
  }
  
  /**
   * @return whether the current state set contains the accept state
   *  of this NFA.
   */
  protected final boolean hasReachedAcceptState(){
    return currentStates.contains(nfa.endState);
  }

  private void lambdaClosureHelper(NFAState state){
    if(state.child1 != null && state.label1 == 0 && !currentStates.contains(state.child1)){
      currentStates.add(state.child1);
      lambdaClosureHelper(state.child1);
    }
    if(state.child2 != null && !currentStates.contains(state.child2)){
      currentStates.add(state.child2);
      lambdaClosureHelper(state.child2);
    }
  }
  
  /**
   * Takes the lambda closure of the current states. Adds
   * to the current set of states all states reachable via
   * edges labeled with lambda.
   * <p>
   * May be used by methods that extend Matcher.
   */
  protected final void lambdaClosure(){
    int size = currentStates.size();
    for(int i = 0; i < size; i++){
      NFAState state = currentStates.get(i);
      lambdaClosureHelper(state);
    }
  }


  /**
   * Transitions from the current set of states to the next
   * set of the states moving along edges labeled with {@code c}
   * <p>
   * May be used by methods that extend Matcher.
   */
  protected final void transition(char c){
    nextStates.clear();
    for(NFAState state: currentStates){
      if(state.label1 == c || state.label1 == '.'){
        nextStates.add(state.child1);
      }
    }
    currentStates.clear();
    currentStates.addAll(nextStates);
  }

}
