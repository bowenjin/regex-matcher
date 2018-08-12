package com.bowenjin.regex;

import java.util.List;
import java.util.ArrayList; 
public class Matcher{
  List<NFAState> currentStates = new ArrayList<>();
  List<NFAState> nextStates = new ArrayList<>();
  NFAState nfa;
  
  public Matcher(String regex){
    this.nfa = new Parser(new Tokenizer(regex)).parse();
  }
  
  public boolean match(String str){
    currentStates.clear();
    currentStates.add(nfa);
    for(int i = 0; i < str.length(); i++){
      lambdaClosure();
      transition(str.charAt(i));
    }
    lambdaClosure();
    if(currentStates.contains(nfa.endState)){
      return true;
    }
    return false;
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
  
  private void lambdaClosure(){
    int size = currentStates.size();
    for(int i = 0; i < size; i++){
      NFAState state = currentStates.get(i);
      lambdaClosureHelper(state);
    }
  }

  private void transition(char c){
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
