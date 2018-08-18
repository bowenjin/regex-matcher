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
  char label1 = 0;
  NFAState endState = null;


  static NFAState regexChar(char c){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.endState = end;
    
    start.label1 = c;
    start.child1 =  end;
   
    return start;
  }

  static NFAState or(NFAState left, NFAState right){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.endState = end;
 
    start.child1 = left;
    start.child2 = right;
    left.endState.child1 = end;
    left.endState.label1 = 0;
    right.endState.child1 = end;  
    right.endState.label1 = 0;
   
    return start; 
  }

  static NFAState concat(NFAState left, NFAState right){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.endState = end;
   
    start.child1 = left;
    left.endState.child1 = right;
    left.endState.label1 = 0;

    right.endState.child1 = end;
    right.endState.label1 = 0;
    
    return start;
  }

  static NFAState star(NFAState left){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.endState = end;
    
    start.child1 = end;
    start.label1 = 0;
    start.child2 = left;
    
    left.endState.child1 = start;
    left.endState.label1 = 0; 
    
    return start;
  }
  
  static NFAState plus(NFAState left){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.endState = end;
    
    start.child1 = left;
    start.label1 = 0;
    
    left.endState.child1 = start;
    left.endState.label1 = 0; 
    left.endState.child2 = end;
  
    return start;
  }

  static NFAState question(NFAState left){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.endState = end; 

    start.child1 = left;
    start.label1 = 0;
    start.child2 = end;
    
    left.endState.child1 = end;
    left.endState.label1 = 0;

    return start;
  }
}
