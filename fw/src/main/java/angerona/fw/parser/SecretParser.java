/* Generated By:JavaCC: Do not edit this line. SecretParser.java */
package angerona.fw.parser;
import java.util.*;
import angerona.fw.logic.Secret;
import java.io.*;
import net.sf.tweety.logics.firstorderlogic.parser.FolParser;
import net.sf.tweety.logics.firstorderlogic.syntax.*;
import angerona.fw.util.Pair;

@SuppressWarnings("all")

public class SecretParser implements SecretParserConstants {

  private FolSignature signature;

   public SecretParser(String expr, FolSignature signature)
  {
    this(new StringReader(expr));
    this.signature = signature;
  }

  public static void main(String args []) throws ParseException
  {
        String expr = "(Boss, java.class.irgendwas, !who_argued(husband_of(mary)))";
    System.out.println("Using expresion :" + expr);

        SecretParser parser = new SecretParser(expr, null);
    try
    {
          Secret lst = parser.secret();
          System.out.println("Parsing done...");
          System.out.println(lst.toString());
    }
    catch (Exception e)
    {
      System.out.println("NOK.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    catch (Error e)
    {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

/** Root production. */
  final public Set<Secret > Input() throws ParseException {
  String agent = "";
  Token token;
  Secret temp;
  Set<Secret > reval = new HashSet<Secret >();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LPARANTHESS:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      temp = secret();
      reval.add(temp);
    }
        {if (true) return reval;}
    throw new Error("Missing return statement in function");
  }

  final public Secret secret() throws ParseException {
  boolean openWasZero = false;
  int open = 0;
  String name = "";
  String className = "";
  String formula = "";
  String temp = "";
  Token token;
  Map<String, String > parameters = new HashMap<String, String >();
    jj_consume_token(LPARANTHESS);
    open += 1;
    label_2:
    while (true) {
      token = jj_consume_token(TEXT_CHAR);
          name += token.image;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TEXT_CHAR:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
    }
    jj_consume_token(COMMA);
    className = java_cls(parameters);
    jj_consume_token(COMMA);
    label_3:
    while (true) {
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LPARANTHESS:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_4;
        }
        token = jj_consume_token(LPARANTHESS);
        formula += token.image;
        open += 1;
      }
      temp = logic_identifier();
          formula += temp;
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case RPARANTHESS:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_5;
        }
        token = jj_consume_token(RPARANTHESS);
            formula += token.image;
            open -= 1;
            if(open == 0)
            {
              formula = formula.substring(0, formula.length()-1);
          System.out.println(formula);
          FolParser folparser = new FolParser();
                  FolFormula folform = null;
          try {
                StringReader reader = new StringReader(formula);
                folparser.setSignature(signature);
                folform = (FolFormula)folparser.parseFormula(reader);
          } catch (IOException ex) {
                {if (true) throw new ParseException("Internal Exception: " + ex.getMessage());}
          }
          {if (true) return new Secret(name, folform, className, parameters);}
            }
      }
      if (jj_2_1(2)) {
        ;
      } else {
        break label_3;
      }
    }
        {if (true) throw new ParseException(
          open > 0 ? "missin closing )" : "missing opening (");}
    throw new Error("Missing return statement in function");
  }

  final public String logic_identifier() throws ParseException {
  Token token;
  String reval = "";
  boolean endWithSymbol = false;
  String temp = "";
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LOGIC_UNARY_SYMBOL:
      token = jj_consume_token(LOGIC_UNARY_SYMBOL);
      reval += token.image;
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    label_6:
    while (true) {
      token = jj_consume_token(TEXT_CHAR);
            reval += token.image;
            endWithSymbol = false;
      if (jj_2_2(2)) {
        ;
      } else {
        break label_6;
      }
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LOGIC_BINARY_SYMBOL:
      token = jj_consume_token(LOGIC_BINARY_SYMBOL);
            reval += token.image;
            endWithSymbol = true;
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
        if(endWithSymbol) {
          {if (true) throw new ParseException("Expression cannot end with an symbol like '||' or '&&'");}
        }
        {if (true) return reval;}
    throw new Error("Missing return statement in function");
  }

  final public String java_cls(Map<String, String > toFill) throws ParseException {
  Token token;
  String reval = "";
  List<Pair<String,String > > temp;
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TEXT_CHAR:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_7;
      }
      token = jj_consume_token(TEXT_CHAR);
      reval += token.image;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACE:
      jj_consume_token(LBRACE);
      temp = pair_list();
      for(Pair<String, String > p : temp) {
        toFill.put(p.first, p.second);
      }
      jj_consume_token(RBRACE);
      break;
    default:
      jj_la1[7] = jj_gen;
      ;
    }
    {if (true) return reval;}
    throw new Error("Missing return statement in function");
  }

  final public List<Pair<String, String > > pair_list() throws ParseException {
  List<Pair<String,String > > reval = new LinkedList<Pair<String, String > >();
  Pair<String, String > temp;
    temp = pair();
    reval.add(temp);
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SEMICOLON:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_8;
      }
      jj_consume_token(SEMICOLON);
      temp = pair();
      reval.add(temp);
    }
    {if (true) return reval;}
    throw new Error("Missing return statement in function");
  }

  final public Pair<String, String > pair() throws ParseException {
  Token token;
  String key = "";
  String value = "";
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TEXT_CHAR:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_9;
      }
      token = jj_consume_token(TEXT_CHAR);
      key += token.image;
    }
    jj_consume_token(EQUAL);
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TEXT_CHAR:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_10;
      }
      token = jj_consume_token(TEXT_CHAR);
      value += token.image;
    }
    {if (true) return new Pair<String, String >(key, value);}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_3R_13() {
    if (jj_scan_token(RPARANTHESS)) return true;
    return false;
  }

  private boolean jj_3_2() {
    if (jj_scan_token(TEXT_CHAR)) return true;
    return false;
  }

  private boolean jj_3R_14() {
    if (jj_scan_token(LOGIC_UNARY_SYMBOL)) return true;
    return false;
  }

  private boolean jj_3R_15() {
    if (jj_scan_token(LOGIC_BINARY_SYMBOL)) return true;
    return false;
  }

  private boolean jj_3R_11() {
    if (jj_scan_token(LPARANTHESS)) return true;
    return false;
  }

  private boolean jj_3_1() {
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_11()) { jj_scanpos = xsp; break; }
    }
    if (jj_3R_12()) return true;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_13()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  private boolean jj_3R_12() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_14()) jj_scanpos = xsp;
    if (jj_3_2()) return true;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_2()) { jj_scanpos = xsp; break; }
    }
    xsp = jj_scanpos;
    if (jj_3R_15()) jj_scanpos = xsp;
    return false;
  }

  /** Generated Token Manager. */
  public SecretParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[11];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40,0x20,0x40,0x80,0x2000,0x4000,0x20,0x100,0x400,0x20,0x20,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[2];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public SecretParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public SecretParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new SecretParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public SecretParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new SecretParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public SecretParser(SecretParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(SecretParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[15];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 11; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 15; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
