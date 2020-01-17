package lexer;

public class Num extends Token {
    public final int value;
    public Num(int _value) {
        super(Tag.NUM);
        value = _value;
    }

    public String toString() {
        return super.toString() + " value = " + value;
    }
}