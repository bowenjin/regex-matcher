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
    public void testAllTokens() throws IOException{
        String input = ".a*b()|+";
        Tokenizer tokenizer = new Tokenizer(new ByteArrayInputStream(input.getBytes()));
        assertToken(tokenizer.nextToken(), Token.Type.DOT, '.');
        assertToken(tokenizer.nextToken(), Token.Type.CHAR, 'a');
        assertToken(tokenizer.nextToken(), Token.Type.STAR, '*');
        assertToken(tokenizer.nextToken(), Token.Type.CHAR, 'b');
	assertToken(tokenizer.nextToken(), Token.Type.LEFTPAREN, '(');
        assertToken(tokenizer.nextToken(), Token.Type.RIGHTPAREN, ')');
        assertToken(tokenizer.nextToken(), Token.Type.OR, '|');
        assertToken(tokenizer.nextToken(), Token.Type.PLUS, '+');
        assertToken(tokenizer.nextToken(), Token.Type.EOI, (char)-1);
        assertToken(tokenizer.nextToken(), Token.Type.EOI, (char)-1);
    }
    
    private void assertToken(Token token, Token.Type type, char value){
      assertEquals(type, token.type);
      assertEquals(value, token.value);
    }
}
