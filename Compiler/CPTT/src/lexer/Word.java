package lexer;

// reserved keywords and identifiers
public class Word extends Token {
    public final String lexeme;
    public Word(int tag, String _lexeme) {
        super(tag);
        lexeme = _lexeme;
    }

    public String toString() {
        return super.toString() + " lexeme = " + lexeme;
    }
}