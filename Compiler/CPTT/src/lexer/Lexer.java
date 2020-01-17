package lexer;
import java.io.*;
import java.util.*;

public class Lexer {
    public int line = 1;
    private char peek = ' ';
    private Hashtable<String, Word> words = new Hashtable<String, Word>();

    void reserve(Word word) {
        words.put(word.lexeme, word);
    }

    public Lexer() {
        reserve(new Word(Tag.TRUE, "true"));
        reserve(new Word(Tag.FALSE, "false"));
    }

    public Token scan() throws IOException {
        // skip space, tab, and newline characters
        for (;; peek = (char) System.in.read()) {
            if (peek == ' ' || peek == '\t' || peek == '\r') ;
            else if (peek == '\n') line = line + 1;
            else break;
        }

        if (Character.isDigit(peek)) {  // handle integers
            int val = 0;
            do {
                val = val * 10 + Character.digit(peek, 10);
                peek = (char) System.in.read();
            } while (Character.isDigit(peek));
            return new Num(val);
        }
        else if (Character.isLetter(peek)) {    // handle keywords and identifiers
            StringBuffer buf = new StringBuffer();
            do {
                buf.append(peek);
                peek = (char) System.in.read();
            } while (Character.isLetterOrDigit(peek));
            String str = buf.toString();
            Word word = words.get(str);
            if (word != null) return word;
            word = new Word(Tag.ID, str);
            reserve(word);
            return word;
        }
        else {  // handle single character operators
            Token token = new Token(peek);
            peek = ' ';
            return token;
        }
    }


    public static void main(String[] args){
        System.out.println("start");
        Lexer lexer = new Lexer();
        try {
            while (true) {
                var token = lexer.scan();
                System.out.println(token);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        System.out.println("end");
    }
}