package lexer;

public class Token {
    public final int tag;
    public Token(int _tag) { tag = _tag; }

    public String toString() {
        //return super.toString() + " tag = " + tag + (tag < 256 ? '(' + (char) tag + ')': "");
        return String.format("%s tag = %d%s", super.toString(), tag,
                tag < 256 ? String.format("(%c)", tag) : "");
    }
}