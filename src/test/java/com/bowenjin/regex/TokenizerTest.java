package com.bowenjin.regex;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.ByteArrayInputStream;
/**
 * Test for Regex Tokenizer
 */
public class TokenizerTest 
{
    @Test
    public void test1() throws IOException{
        String input = ".a*b()|";
        Tokenizer tokenizer = new Tokenizer(new ByteArrayInputStream(input.getBytes()));
        assertEquals(tokenizer.nextToken().type, Token.Type.DOT);
        assertEquals(tokenizer.nextToken().type, Token.Type.CHAR);
        assertEquals(tokenizer.nextToken().type, Token.Type.STAR);
        assertEquals(tokenizer.nextToken().type, Token.Type.CHAR);
	assertEquals(tokenizer.nextToken().type, Token.Type.LEFTPAREN);
        assertEquals(tokenizer.nextToken().type, Token.Type.RIGHTPAREN);
        assertEquals(tokenizer.nextToken().type, Token.Type.OR);
        assertEquals(tokenizer.nextToken().type, Token.Type.EOI);
        assertEquals(tokenizer.nextToken().type, Token.Type.EOI);
    }
    
    @Test
    public void test2() throws IOException{
      String input = "a^d";
      Tokenizer tokenizer = new Tokenizer(new ByteArrayInputStream(input.getBytes()));
      Token firstToken = tokenizer.nextToken();
      assertEquals(firstToken.type, Token.Type.CHAR); 
      assertEquals(firstToken.value, "a");
      Token secondToken = tokenizer.nextToken();
      assertEquals(secondToken.type, Token.Type.CHAR);
      assertEquals(secondToken.value, "^");    
      Token thirdToken = tokenizer.nextToken();
      assertEquals(thirdToken.type, Token.Type.CHAR);
      assertEquals(thirdToken.value, "d");  
      assertEquals(tokenizer.nextToken().type, Token.Type.EOI);
    }
}
