package com.bowenjin.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

class NFAState{
  NFAState child1 = null;
  NFAState child2 = null;
  boolean isLambda = false;
  char edge;
  boolean isStart = false;
  //only valid if isStartNode == true
  NFAState end = null;
  boolean isAccept = false;


  boolean traverse(String str){
    if(!this.isStart){
      throw new IllegalStateException("Tried to call traverse on a non-start state");
    }
    Set<NFAState> stateSet = new HashSet<NFAState>(5);
    stateSet.add(this);
    for(int i = 0 ; i < str.length(); i++){
      stateSet = lambdaClosure(stateSet);
      char c = str.charAt(i);
      stateSet = transition(c, stateSet);
    }
    for(NFAState state: stateSet){
      if(state.isAccept){
        return true;
      }
    }
    return false;
  }

  private void traverseLambda(Set<NFAState> stateSet){
    stateSet.add(this);
    if(this.child1 != null && this.isLambda && !stateSet.contains(this.child1)){
      this.child1.traverseLambda(stateSet);
    }
    if(this.child2 != null && !stateSet.contains(this.child2)){
      this.child2.traverseLambda(stateSet);
    }
  }
  
  private static Set<NFAState> lambdaClosure(Set<NFAState> stateSet){
    Set<NFAState> newStateSet = new HashSet<NFAState>(5);
    for(NFAState state: stateSet){
      state.traverseLambda(newStateSet);
    }
    return newStateSet;
  }

  private static Set<NFAState> transition(char c, Set<NFAState> stateSet){
    Set<NFAState> newStateSet = new HashSet<NFAState>(5);
    for(NFAState state: stateSet){
      if(state.edge == c || state.edge == '.'){
        newStateSet.add(state.child1);
      }
    }
    return newStateSet;
  }
}
