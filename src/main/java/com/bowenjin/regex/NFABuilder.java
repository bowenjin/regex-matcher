package com.bowenjin.regex;

class NFABuilder{ 
  static NFAState regexChar(char c){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.isStart = true;
    start.end = end;
    end.isAccept = true;
    
    start.edge = c;
    start.child1 =  end;
   
    return start;
  }

  static NFAState or(NFAState left, NFAState right){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.isStart = true;
    start.end = end;
    end.isAccept = true;
    
    left.isStart = false;
    right.isStart = false;
    left.end.isAccept = false;
    right.end.isAccept = false;
    start.end = end;
    start.child1 = left;
    start.child2 = right;
    start.isLambda = true;
    left.end.child1 = end;
    left.end.isLambda = true;
    right.end.child1 = end;  
    right.end.isLambda = true; 
   
    return start; 
  }

  static NFAState concat(NFAState left, NFAState right){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.isStart = true;
    start.end = end;
    end.isAccept = true;
   
    left.isAccept = false;
    right.isAccept = false;
    start.child1 = left;
    start.isLambda = true; 
    left.end.isAccept = false;
    left.end.child1 = right;
    left.end.isLambda = true;
    right.end.isAccept = false;
    right.end.child1 = end;
    right.end.isLambda = true;
   
    return start; 
  }

  static NFAState star(NFAState state){
    NFAState start = new NFAState();
    NFAState end = new NFAState();
    start.isStart = true;
    start.end = end;
    end.isAccept = true;
    
    state.isStart = false;
    start.child1 = state;
    start.isLambda = true;
    start.child2 = end;
    state.end.isAccept = false;
    state.end.child1 = start;
    state.end.isLambda = true;
   
    return start;
  }

}
