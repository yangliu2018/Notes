package symbols;
import java.util.*;

public class Env {
    private Hashtable<String, Symbol> m_table;
    private Env m_prev;

    public Env(Env prev) {
        m_table = new Hashtable<String, Symbol>();
        m_prev = prev;
    }

    public void put(String str, Symbol sym) {
        m_table.put(str, sym);
    }

    public Symbol get(String str) {
        for (Env env = this; env != null; env = env.m_prev) {
            Symbol sym = env.table.get(str);
            if (sym != null) break;
        }
        return sym;
    }
}