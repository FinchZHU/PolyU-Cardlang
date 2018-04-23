package cardlang.analysis;

import cardlang.node.*;

import java.io.*;
import java.util.*;

public class SemanticAnalyzer extends DepthFirstAdapter {

    static char TYPE_UNKNOWN='U';
    static char TYPE_INTEGER ='I';
    static char TYPE_STRING ='S';
    static char TYPE_BOOLEAN ='B';
    static char TYPE_DECK = 'D';
    static char TYPE_CARD = 'C';
    static char TYPE_PLAYER = 'P';
    // for variable type card[] and player[], use the head letter of 'Hand' and 'Group'
    static char TYPE_CARD_ARR = 'H';
    static char TYPE_PLAYER_ARR = 'G';


    static char BRANCH_GAME = 'G';
    static char BRANCH_SCREEN = 'S';
    static char BRANCH_VALID = 'V';
    static char BRANCH_WINNERID = 'W';
    static char BRANCH_NEXTID = 'N';
    static char BRANCH_OUTOFSCOPE = 'O';



    // counts the layer of loops the current line is in, including both for and while loops
    int loopLayerCount = 0;

    // records current method, indicated by the BRANCH_* keyword. Are set in the "in" and "out" of methods
    char currentMethodBranch;

    // record the type of each identifier, within 5 different hash tables
    // each hash table records its own set of variables.
    Hashtable<Character, Hashtable> symtable = new Hashtable<>();

    // record the type of each node (no need to modify the "Node" class)
    Hashtable<Node,Character> nodetype = new Hashtable<>();


    // ------ Helper method ------



    // a method that receives a string, and throw an illegalArgumentException with the string as error information.
    public void reportError(String errmsg) {
        throw new IllegalArgumentException(errmsg);
    }


    // receives the node's text, and look up in the hash table of the current method. if found, return type; else return UNKNOWN, which is also a type
    public char getIDType(String id) {
        char type=TYPE_UNKNOWN;
        Object obj = symtable.get(currentMethodBranch).get(id);
        if (obj!=null)
            type= (Character) obj;
        return type;
    }

    // set the data type of a node.
    public void setNodeDataType(Node node, char type) {
        nodetype.put(node, type);
    }


    // get the node's data type. will report error if node with no type is looked up
    public char getNodeDataType(Node node) { // scope checking
        char type=TYPE_UNKNOWN;
        Object obj=nodetype.get(node);
        if (obj!=null) {
            type= (Character) obj;
        }
        else
            reportError("invalid node data type: " + node.toString());
        return type;
    }

    // precondition: P is parent of C
    // let node p be of the same type of node c. note that c must have a type
    public void passUpDataType(Node P, Node C) {
        char type=getNodeDataType(C);
        setNodeDataType(P,type);
    }


    // get the type of a variable. if it should have been declared, set ShouldHaveBeenDeclared to be true;
    // else if it should be un-declared, set it to be false
    public char checkDeclaredID(String id, boolean ShouldHaveBeenDeclared) { // scope checking
        char type=getIDType(id);
        if (ShouldHaveBeenDeclared) {
            if (type==TYPE_UNKNOWN) {
                reportError("undeclared id: " + id);
            }
        }
        else {
            if (type!=TYPE_UNKNOWN)
                reportError("redeclared id: "+ id);
        }
        return type;
    }

    // remove the index between square-brackets of an array, including the second-layer square-brackets
    public static String removeArrIndex(String nodeText) {
        StringBuilder sb = new StringBuilder(nodeText);
        int bracLev = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '[') {
                bracLev += 1;
                if (bracLev == 1){
                    continue;
                }
            }
            else if (sb.charAt(i) == ']') {
                bracLev -= 1;
            }
            if (bracLev >= 1) {
                sb.deleteCharAt(i);
                i--;
            }
        }
        
        return sb.toString();
    }

    // check if a text is valid literal
    public static boolean isValidLit(String text) {
        if (!('a' <= text.charAt(0) && text.charAt(0) <= 'z' || 'A' <= text.charAt(0) && text.charAt(0) <= 'Z')) {
            return false;
        }
        for (int i = 1; i < text.length(); i++) {
            if (!('a' <= text.charAt(i) && text.charAt(i) <= 'z' || 'A' <= text.charAt(i) && text.charAt(i) <= 'Z' || '0' <= text.charAt(i) && text.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }

    
    

    // ------ Node Traversal ------


    // Integer(Unsigned)
    //	String
    //	Boolean
    //	Deck
    //	Card(Two fields): Suit(Integer), Rank(Integer)
    //	Player(Four fields): Cards(Card[]), Name(String), Point(Integer), Flag(Boolean)
    //	Card[];
    //	Player[];




    // when entering methods, all pre-defined variables and their fields should be put into corresponding hash tables
    // and the currentMethodBranch should be set to corresponding method initials
    @Override
    public void inAProgram(AProgram node) {
        final Hashtable<String, Character> outOfScope = new Hashtable<>();
        symtable.put(BRANCH_OUTOFSCOPE, outOfScope);
    }

    // while entering any method, record the method type, and open a hash table to store the variables
    // Game: Players(Player[]), Deck(Deck), Game(Integer)
    @Override
    public void inAGame(AGame node) {
        currentMethodBranch = BRANCH_GAME;
        symtable.put(BRANCH_GAME, new Hashtable<String, Character>());
        symtable.get(BRANCH_GAME).put("Deck", TYPE_DECK);
        symtable.get(BRANCH_GAME).put("Game", TYPE_INTEGER);
        symtable.get(BRANCH_GAME).put("Players", TYPE_PLAYER_ARR);
        symtable.get(BRANCH_GAME).put("Players[]", TYPE_PLAYER);
        symtable.get(BRANCH_GAME).put("Players[].Name", TYPE_STRING);
        symtable.get(BRANCH_GAME).put("Players[].Point", TYPE_INTEGER);
        symtable.get(BRANCH_GAME).put("Players[].Flag", TYPE_BOOLEAN);
        symtable.get(BRANCH_GAME).put("Players[].Cards", TYPE_CARD_ARR);
        symtable.get(BRANCH_GAME).put("Players[].Cards[]", TYPE_CARD);
        symtable.get(BRANCH_GAME).put("Players[].Cards[].Suit", TYPE_INTEGER);
        symtable.get(BRANCH_GAME).put("Players[].Cards[].Rank", TYPE_INTEGER);

    }

    @Override
    //	Screen: Players(Player[]), CurID(Integer), Screen(String)
    public void inAScreen(AScreen node) {
        currentMethodBranch = BRANCH_SCREEN;
        symtable.put(BRANCH_SCREEN, new Hashtable<String, Character>());
        symtable.get(BRANCH_SCREEN).put("CurID", TYPE_INTEGER);
        symtable.get(BRANCH_SCREEN).put("Screen", TYPE_STRING);
        symtable.get(BRANCH_SCREEN).put("Players", TYPE_PLAYER_ARR);
        symtable.get(BRANCH_SCREEN).put("Players[]", TYPE_PLAYER);
        symtable.get(BRANCH_SCREEN).put("Players[].Name", TYPE_STRING);
        symtable.get(BRANCH_SCREEN).put("Players[].Point", TYPE_INTEGER);
        symtable.get(BRANCH_SCREEN).put("Players[].Flag", TYPE_BOOLEAN);
        symtable.get(BRANCH_SCREEN).put("Players[].Cards", TYPE_CARD_ARR);
        symtable.get(BRANCH_SCREEN).put("Players[].Cards[]", TYPE_CARD);
        symtable.get(BRANCH_SCREEN).put("Players[].Cards[].Suit", TYPE_INTEGER);
        symtable.get(BRANCH_SCREEN).put("Players[].Cards[].Rank", TYPE_INTEGER);
    }

    @Override
    //	Valid: Players(Player[]), CurID(Integer), CurPlayCards(Card[]), PrevID(Integer), PrevPlayCards(Card[]), Deck(Deck), Valid(Boolean)
    public void inAValid(AValid node) {
        currentMethodBranch = BRANCH_VALID;
        symtable.put(BRANCH_VALID, new Hashtable<String, Character>());
        symtable.get(BRANCH_VALID).put("CurID", TYPE_INTEGER);
        symtable.get(BRANCH_VALID).put("PrevID", TYPE_INTEGER);
        symtable.get(BRANCH_VALID).put("Deck", TYPE_DECK);
        symtable.get(BRANCH_VALID).put("Valid", TYPE_BOOLEAN);
        symtable.get(BRANCH_VALID).put("CurPlayCards", TYPE_CARD_ARR);
        symtable.get(BRANCH_VALID).put("CurPlayCards[]", TYPE_CARD);
        symtable.get(BRANCH_VALID).put("CurPlayCards[].Suit", TYPE_INTEGER);
        symtable.get(BRANCH_VALID).put("CurPlayCards[].Rank", TYPE_INTEGER);
        symtable.get(BRANCH_VALID).put("PrevPlayCards", TYPE_CARD_ARR);
        symtable.get(BRANCH_VALID).put("PrevPlayCards[]", TYPE_CARD);
        symtable.get(BRANCH_VALID).put("PrevPlayCards[].Suit", TYPE_INTEGER);
        symtable.get(BRANCH_VALID).put("PrevPlayCards[].Rank", TYPE_INTEGER);
        symtable.get(BRANCH_VALID).put("Players", TYPE_PLAYER_ARR);
        symtable.get(BRANCH_VALID).put("Players[]", TYPE_PLAYER);
        symtable.get(BRANCH_VALID).put("Players[].Name", TYPE_STRING);
        symtable.get(BRANCH_VALID).put("Players[].Point", TYPE_INTEGER);
        symtable.get(BRANCH_VALID).put("Players[].Flag", TYPE_BOOLEAN);
        symtable.get(BRANCH_VALID).put("Players[].Cards", TYPE_CARD_ARR);
        symtable.get(BRANCH_VALID).put("Players[].Cards[]", TYPE_CARD);
        symtable.get(BRANCH_VALID).put("Players[].Cards[].Suit", TYPE_INTEGER);
        symtable.get(BRANCH_VALID).put("Players[].Cards[].Rank", TYPE_INTEGER);
    }


    //	WinnerID: Players(Player[]), CurID(Integer), PrevID(Integer), PrevPlayCards(Card[]), Deck(Deck), WinnerID(Integer)
    @Override
    public void inAWinnerid(AWinnerid node) {
        currentMethodBranch = BRANCH_WINNERID;
        symtable.put(BRANCH_WINNERID, new Hashtable<String, Character>());
        symtable.get(BRANCH_WINNERID).put("CurID", TYPE_INTEGER);
        symtable.get(BRANCH_WINNERID).put("PrevID", TYPE_INTEGER);
        symtable.get(BRANCH_WINNERID).put("PrevPlayCards", TYPE_CARD_ARR);
        symtable.get(BRANCH_WINNERID).put("PrevPlayCards[]", TYPE_CARD);
        symtable.get(BRANCH_WINNERID).put("PrevPlayCards[].Suit", TYPE_INTEGER);
        symtable.get(BRANCH_WINNERID).put("PrevPlayCards[].Rank", TYPE_INTEGER);
        symtable.get(BRANCH_WINNERID).put("Deck", TYPE_DECK);
        symtable.get(BRANCH_WINNERID).put("WinnerID", TYPE_INTEGER);
        symtable.get(BRANCH_WINNERID).put("Players", TYPE_PLAYER_ARR);
        symtable.get(BRANCH_WINNERID).put("Players[]", TYPE_PLAYER);
        symtable.get(BRANCH_WINNERID).put("Players[].Name", TYPE_STRING);
        symtable.get(BRANCH_WINNERID).put("Players[].Point", TYPE_INTEGER);
        symtable.get(BRANCH_WINNERID).put("Players[].Flag", TYPE_BOOLEAN);
        symtable.get(BRANCH_WINNERID).put("Players[].Cards", TYPE_CARD_ARR);
        symtable.get(BRANCH_WINNERID).put("Players[].Cards[]", TYPE_CARD);
        symtable.get(BRANCH_WINNERID).put("Players[].Cards[].Suit", TYPE_INTEGER);
        symtable.get(BRANCH_WINNERID).put("Players[].Cards[].Rank", TYPE_INTEGER);
    }

    //	NextID: Players(Player[]), CurID(Integer), PrevID(Integer), PrevPlayCards(Card[]), Deck(Deck), NextID(Integer)
    @Override
    public void inANextid(ANextid node) {
        currentMethodBranch = BRANCH_NEXTID;
        symtable.put(BRANCH_NEXTID, new Hashtable<String, Character>());
        symtable.get(BRANCH_NEXTID).put("CurID", TYPE_INTEGER);
        symtable.get(BRANCH_NEXTID).put("PrevID", TYPE_INTEGER);
        symtable.get(BRANCH_NEXTID).put("PrevPlayCards", TYPE_CARD_ARR);
        symtable.get(BRANCH_NEXTID).put("PrevPlayCards[]", TYPE_CARD);
        symtable.get(BRANCH_NEXTID).put("PrevPlayCards[].Suit", TYPE_INTEGER);
        symtable.get(BRANCH_NEXTID).put("PrevPlayCards[].Rank", TYPE_INTEGER);
        symtable.get(BRANCH_NEXTID).put("NextID", TYPE_INTEGER);
        symtable.get(BRANCH_NEXTID).put("Deck", TYPE_DECK);
        symtable.get(BRANCH_NEXTID).put("Players", TYPE_PLAYER_ARR);
        symtable.get(BRANCH_NEXTID).put("Players[]", TYPE_PLAYER);
        symtable.get(BRANCH_NEXTID).put("Players[].Name", TYPE_STRING);
        symtable.get(BRANCH_NEXTID).put("Players[].Point", TYPE_INTEGER);
        symtable.get(BRANCH_NEXTID).put("Players[].Flag", TYPE_BOOLEAN);
        symtable.get(BRANCH_NEXTID).put("Players[].Cards", TYPE_CARD_ARR);
        symtable.get(BRANCH_NEXTID).put("Players[].Cards[]", TYPE_CARD);
        symtable.get(BRANCH_NEXTID).put("Players[].Cards[].Suit", TYPE_INTEGER);
        symtable.get(BRANCH_NEXTID).put("Players[].Cards[].Rank", TYPE_INTEGER);
    }

    // when exiting a method, set the branch record to be 'O'
    @Override
    public void outANextid(ANextid node) {
        currentMethodBranch = BRANCH_OUTOFSCOPE;
    }

    @Override
    public void outAGame(AGame node) {
        currentMethodBranch = BRANCH_OUTOFSCOPE;
    }

    @Override
    public void outAScreen(AScreen node) {
        currentMethodBranch = BRANCH_OUTOFSCOPE;
    }

    @Override
    public void outAValid(AValid node) {
        currentMethodBranch = BRANCH_OUTOFSCOPE;
    }

    @Override
    public void outAWinnerid(AWinnerid node) {
        currentMethodBranch = BRANCH_OUTOFSCOPE;
    }





    // when exiting a base shift:
    @Override
    public void outABaseShift(ABaseShift node) {

        // Check if the assigned object is not changeable: card.rank or card.suit
        // remove index of array so that it fits the string pattern as the hash table keys
        String objStr = removeArrIndex(node.getObj().toString());
        if (objStr.length() >= 6) {
            String lastField = objStr.substring(objStr.length() - 5);
            // check if the variable is a field of card
            if (getIDType(objStr.substring(0, objStr.length() - 6)) == TYPE_CARD && (lastField.equals(".Suit") || lastField.equals(".Rank"))) {
                reportError("invalid object to be assigned: cannot assign to constant variable: " + objStr);
            }
        }

        // add object type to table if the object is undeclared yet and is only a literal, and set the node's type
        if (getIDType(objStr) == TYPE_UNKNOWN && isValidLit(objStr)) {
            if (getNodeDataType(node.getExpr()) == TYPE_INTEGER) {
                symtable.get(currentMethodBranch).put(objStr, TYPE_INTEGER);
                setNodeDataType(node, TYPE_INTEGER);

                // can assign the type of node.object here, but seems unnecessary.
                // setNodeDataType(node.getObj(), getNodeDataType(node.getExpr()));
            }

            // if the assigned value is not integer and the object is un-defined, report error since user cannot define other types of variables
            else {
                reportError("invalid base shift: invalid expression type: " + node.getExpr().toString() + ": " + getNodeDataType(node.getExpr()));
            }
        }

        // if the object seems to be a field or something other than a plain literal and is not defined, report error
        // changeable fields are all pre-defined so the type will not be unknown
        else if (getIDType(objStr) == TYPE_UNKNOWN && !isValidLit(objStr)) {
            reportError("invalid base shift: invalid object type: " + node.getExpr().toString() + ": " + getNodeDataType(node.getExpr()));
        }

        // if the object is declared as primitive type, check if the types match
        else if (getIDType(objStr) == TYPE_BOOLEAN ||
                getIDType(objStr) == TYPE_INTEGER ||
                getIDType(objStr) == TYPE_STRING) {

            // if the type of expression and object dismatches, report error
            if (getIDType(objStr) != getNodeDataType(node.getExpr())) {
                reportError("invalid base shift: Assigning " + node.getExpr().toString() + " to " + node.getObj().toString());
            }
            // if they match, it is a valid assignment.
            // need not insert it to the hash table because it is already defined and inserted
            else {
                passUpDataType(node, node.getObj());
            }
        }

        // if the object is a card array, then the expression must be of Deck type
        else if (getIDType(objStr) == TYPE_CARD_ARR) {
            if (getNodeDataType(node.getExpr()) != TYPE_DECK) {
                reportError("invalid base shift: Assigning " + node.getExpr().toString() + " to " + node.getObj().toString());
            }
            else {
                setNodeDataType(node, TYPE_CARD_ARR);
            }
        }

        // any other forms of assignments are invalid
        else {
            reportError("invalid base shift: invalid object type: " + node.getExpr().toString() + ": " + getNodeDataType(node.getExpr()));
        }

    }

    // when exiting a recursive shift
    @Override
    public void outARecurShift(ARecurShift node) {

        // the shift and expression must be of same type, or shifting Deck to a card array. else report error
        if (!(getNodeDataType(node.getShift()) == getNodeDataType(node.getExpr()) || (getNodeDataType(node.getShift()) == TYPE_CARD_ARR && getNodeDataType(node.getExpr()) == TYPE_DECK))) {
            reportError("invalid recursive shift: Assigning " + node.getExpr().toString() + " to " + node.getShift().toString());
        }

        // even if the shift and expression are of the same type, the shift must be of the  4 types that is able to be assigned
        if (getNodeDataType(node.getShift()) != TYPE_BOOLEAN &&
                getNodeDataType(node.getShift()) != TYPE_INTEGER &&
                getNodeDataType(node.getShift()) != TYPE_STRING &&
                getNodeDataType(node.getShift()) != TYPE_CARD_ARR) {
            reportError("invalid recursive shift: invalid object type: " + node.getShift().toString() + ": " + getNodeDataType(node.getShift()));
        }

        // if the shift is of card array type, then expression must be of deck type. it cannot be of card array type, too.
        if (getNodeDataType(node.getShift()) == TYPE_CARD_ARR && getNodeDataType(node.getExpr()) != TYPE_DECK) {
            reportError("invalid recursive shift to card array: invalid expression type: " + node.getExpr().toString() + ": " + getNodeDataType(node.getExpr()));
        }

        // if none of the above, est the node type.
        // need not insert it to the hash table because the object is already defined and inserted in the previous shift
        passUpDataType(node, node.getShift());
    }


    // when entering for
    @Override
    public void inAFor(AFor node) {

        // check if the id is declared, and the name of the id is a valid literal for initializing as an integer
        if (checkDeclaredID(node.getId().toString(), false) != TYPE_UNKNOWN || !isValidLit(node.getId().toString())) {
            reportError("invalid ID: " + node.getId().toString());
        }

        // insert the for varible into the hash table
        // it will be deleted at the end of this loop
        symtable.get(currentMethodBranch).put(node.getId().toString(), TYPE_INTEGER);

        // set the counter one layer higher
        loopLayerCount++;
    }

    // when exiting for
    @Override
    public void outAFor(AFor node) {

        // both from expression and to expression should be integers
        if (getNodeDataType(node.getFrom()) != TYPE_INTEGER) {
            reportError("invalid from expression type: " + node.getFrom().toString() + ": " + getNodeDataType(node.getFrom()));
        }
        if (getNodeDataType(node.getTo()) != TYPE_INTEGER) {
            reportError("invalid to expression type: " + node.getFrom().toString() + ": " + getNodeDataType(node.getFrom()));
        }

        // at the end of the loop, delete the for variable from hash table
        symtable.remove(node.getId().toString());

        // and reduce the layer count
        loopLayerCount--;
    }

    // when entering while
    @Override
    public void inAWhile(AWhile node) {

        // update loop count
        loopLayerCount++;
    }

    // when exiting while
    @Override
    public void outAWhile(AWhile node) {

        // the expression must be a boolean
        if (getNodeDataType(node.getExpr()) != TYPE_BOOLEAN) {
            reportError("invalid expression type: " + node.getExpr().toString() + ": " + getNodeDataType(node.getExpr()));
        }
        loopLayerCount--;
    }


    // for both types of if, the expression must be a boolean
    @Override
    public void outASingleIf(ASingleIf node) {
        if (getNodeDataType(node.getExpr()) != TYPE_BOOLEAN) {
            reportError("invalid expression type: " + node.getExpr().toString() + ": " + getNodeDataType(node.getExpr()));
        }
    }
    @Override
    public void outAElseIf(AElseIf node) {
        if (getNodeDataType(node.getExpr()) != TYPE_BOOLEAN) {
            reportError("invalid expression type: " + node.getExpr().toString() + ": " + getNodeDataType(node.getExpr()));
        }
    }

    // for continue and break, the loop count must be greater than 0
    @Override
    public void outABreakStmt(ABreakStmt node) {
        if (!(loopLayerCount >= 0)) {
            reportError("invalid break statement: not enough loop layer count: " + loopLayerCount);
        }
    }
    @Override
    public void outAContinueStmt(AContinueStmt node) {
        if (!(loopLayerCount >= 0)) {
            reportError("invalid continue statement: not enough loop layer count: " + loopLayerCount);
        }
    }


    // the expression type passing are trivial.
    @Override
    public void outABaseExpr(ABaseExpr node) {
        passUpDataType(node, node.getFactor());
    }

    @Override
    public void outAStringExpr(AStringExpr node) {
        setNodeDataType(node, TYPE_STRING);
    }


    // and and or expressions must be applied to boolean values
    @Override
    public void outAAndExpr(AAndExpr node) {
        if (getNodeDataType(node.getExpr()) != TYPE_BOOLEAN || getNodeDataType(node.getFactor()) != TYPE_BOOLEAN) {
            reportError("invalid and expression: " + node.getExpr() + ": " + getNodeDataType(node.getExpr()) + " and " + node.getFactor() + ": " + getNodeDataType(node.getFactor()));
        }
        setNodeDataType(node, TYPE_BOOLEAN);
    }
    @Override
    public void outAOrExpr(AOrExpr node) {
        if (getNodeDataType(node.getExpr()) != TYPE_BOOLEAN || getNodeDataType(node.getFactor()) != TYPE_BOOLEAN) {
            reportError("invalid or expression: " + node.getExpr() + ": " + getNodeDataType(node.getExpr()) + " or " + node.getFactor() + ": " + getNodeDataType(node.getFactor()));
        }
        setNodeDataType(node, TYPE_BOOLEAN);
    }

    // a base factor's type is determined by the comp expression's type
    @Override
    public void outABaseFactor(ABaseFactor node) {
        passUpDataType(node, node.getCompexpr());
    }

    // a not factor must be of boolean type
    @Override
    public void outANotFactor(ANotFactor node) {
        if (getNodeDataType(node.getCompexpr()) != TYPE_BOOLEAN) {
            reportError("invalid not factor: " + node.getCompexpr() + ": " + getNodeDataType(node.getCompexpr()));
        }
        setNodeDataType(node, TYPE_BOOLEAN);
    }

    // a comp expression's type is determined by the Num expression type
    @Override
    public void outABaseCompexpr(ABaseCompexpr node) {
        passUpDataType(node, node.getNumexpr());
    }

    // the mathematical comparison expressions are applied to two integers, and is of boolean type
    @Override
    public void outALargerCompexpr(ALargerCompexpr node) {
        if (getNodeDataType(node.getLexpr()) != TYPE_INTEGER || getNodeDataType(node.getRexpr()) != TYPE_INTEGER) {
            reportError("invalid larger comp expression: " + node.getLexpr() + ": " + getNodeDataType(node.getLexpr()) + " > " + node.getRexpr() + ": " + getNodeDataType(node.getRexpr()));
        }
        setNodeDataType(node, TYPE_BOOLEAN);
    }
    @Override
    public void outALargerequalCompexpr(ALargerequalCompexpr node) {
        if (getNodeDataType(node.getLexpr()) != TYPE_INTEGER || getNodeDataType(node.getRexpr()) != TYPE_INTEGER) {
            reportError("invalid larger equal comp expression: " + node.getLexpr() + ": " + getNodeDataType(node.getLexpr()) + " >= " + node.getRexpr() + ": " + getNodeDataType(node.getRexpr()));
        }
        setNodeDataType(node, TYPE_BOOLEAN);
    }
    @Override
    public void outALessCompexpr(ALessCompexpr node) {
        if (getNodeDataType(node.getLexpr()) != TYPE_INTEGER || getNodeDataType(node.getRexpr()) != TYPE_INTEGER) {
            reportError("invalid less comp expression: " + node.getLexpr() + ": " + getNodeDataType(node.getLexpr()) + " < " + node.getRexpr() + ": " + getNodeDataType(node.getRexpr()));
        }
        setNodeDataType(node, TYPE_BOOLEAN);
    }
    @Override
    public void outALessequalCompexpr(ALessequalCompexpr node) {
        if (getNodeDataType(node.getLexpr()) != TYPE_INTEGER || getNodeDataType(node.getRexpr()) != TYPE_INTEGER) {
            reportError("invalid less comp expression: " + node.getLexpr() + ": " + getNodeDataType(node.getLexpr()) + " <= " + node.getRexpr() + ": " + getNodeDataType(node.getRexpr()));
        }
        setNodeDataType(node, TYPE_BOOLEAN);
    }
    @Override
    public void outAEqualCompexpr(AEqualCompexpr node) {
        if (getNodeDataType(node.getLexpr()) != TYPE_INTEGER || getNodeDataType(node.getRexpr()) != TYPE_INTEGER) {
            reportError("invalid less comp expression: " + node.getLexpr() + ": " + getNodeDataType(node.getLexpr()) + " == " + node.getRexpr() + ": " + getNodeDataType(node.getRexpr()));
        }
        setNodeDataType(node, TYPE_BOOLEAN);
    }
    @Override
    public void outAInequalCompexpr(AInequalCompexpr node) {
        if (getNodeDataType(node.getLexpr()) != TYPE_INTEGER || getNodeDataType(node.getRexpr()) != TYPE_INTEGER) {
            reportError("invalid less comp expression: " + node.getLexpr() + ": " + getNodeDataType(node.getLexpr()) + " != " + node.getRexpr() + ": " + getNodeDataType(node.getRexpr()));
        }
        setNodeDataType(node, TYPE_BOOLEAN);
    }

    @Override
    public void outABaseNumexpr(ABaseNumexpr node) {
        passUpDataType(node, node.getVal());
    }

    // the mathematic operations must be applied to two integers
    @Override
    public void outAPlusNumexpr(APlusNumexpr node) {
        if (getNodeDataType(node.getNumexpr()) != TYPE_INTEGER && getNodeDataType(node.getVal()) != TYPE_INTEGER) {
            reportError("invalid plus num expression: " + node.getNumexpr() + ": " + getNodeDataType(node.getNumexpr()) + " + " + node.getVal() + ": " + getNodeDataType(node.getVal()));
        }
        setNodeDataType(node, TYPE_INTEGER);
    }
    @Override
    public void outAMinusNumexpr(AMinusNumexpr node) {
        if (getNodeDataType(node.getNumexpr()) != TYPE_INTEGER && getNodeDataType(node.getVal()) != TYPE_INTEGER) {
            reportError("invalid plus num expression: " + node.getNumexpr() + ": " + getNodeDataType(node.getNumexpr()) + " - " + node.getVal() + ": " + getNodeDataType(node.getVal()));
        }
        setNodeDataType(node, TYPE_INTEGER);
    }
    @Override
    public void outAMultiNumexpr(AMultiNumexpr node) {
        if (getNodeDataType(node.getNumexpr()) != TYPE_INTEGER && getNodeDataType(node.getVal()) != TYPE_INTEGER) {
            reportError("invalid plus num expression: " + node.getNumexpr() + ": " + getNodeDataType(node.getNumexpr()) + " * " + node.getVal() + ": " + getNodeDataType(node.getVal()));
        }
        setNodeDataType(node, TYPE_INTEGER);
    }
    @Override
    public void outADivNumexpr(ADivNumexpr node) {
        if (getNodeDataType(node.getNumexpr()) != TYPE_INTEGER && getNodeDataType(node.getVal()) != TYPE_INTEGER) {
            reportError("invalid plus num expression: " + node.getNumexpr() + ": " + getNodeDataType(node.getNumexpr()) + " / " + node.getVal() + ": " + getNodeDataType(node.getVal()));
        }
        setNodeDataType(node, TYPE_INTEGER);
    }
    @Override
    public void outAModNumexpr(AModNumexpr node) {
        if (getNodeDataType(node.getNumexpr()) != TYPE_INTEGER && getNodeDataType(node.getVal()) != TYPE_INTEGER) {
            reportError("invalid plus num expression: " + node.getNumexpr() + ": " + getNodeDataType(node.getNumexpr()) + " % " + node.getVal() + ": " + getNodeDataType(node.getVal()));
        }
        setNodeDataType(node, TYPE_INTEGER);
    }

    // a type of object value node is determined by the object
    @Override
    public void outAObjVal(AObjVal node) {
        passUpDataType(node, node.getObj());
    }

    // a numeric value node is of type integer
    @Override
    public void outANumVal(ANumVal node) {
        setNodeDataType(node, TYPE_INTEGER);
    }

    // a true value node is of type boolean
    @Override
    public void outATrueVal(ATrueVal node) {
        setNodeDataType(node, TYPE_BOOLEAN);
    }

    // a false value node is of type boolean
    @Override
    public void outAFalseVal(AFalseVal node) {
        setNodeDataType(node, TYPE_BOOLEAN);
    }

    @Override
    public void outAParenVal(AParenVal node) {
        passUpDataType(node, node.getExpr());
    }

    @Override
    public void outAIdObj(AIdObj node) {
        passUpDataType(node, node.getId());
    }

    @Override
    public void outAFieldObj(AFieldObj node) {
        setNodeDataType(node, getIDType(removeArrIndex(node.getId().toString() + "." + node.getObj().toString())));
    }

    @Override
    public void outALitId(ALitId node) {
        setNodeDataType(node, getIDType(node.getLiteral().toString()));
    }


    // here when a token is identified as the keyword, we check if it is defined in the corresponding method
    // if it is defined, then the node is of its type
    // if it is not defined, then it can be treated as a literal with unknown type
    @Override
    public void outAGameId(AGameId node) {
        setNodeDataType(node, getIDType(node.getKwgame().toString()));
    }
    @Override
    public void outAScreenId(AScreenId node) {
        setNodeDataType(node, getIDType(node.getKwscreen().toString()));
    }
    @Override
    public void outAValidId(AValidId node) {
        setNodeDataType(node, getIDType(node.getKwvalid().toString()));
    }
    @Override
    public void outAWinneridId(AWinneridId node) {
        setNodeDataType(node, getIDType(node.getKwwinnerid().toString()));
    }
    @Override
    public void outANextidId(ANextidId node) {
        setNodeDataType(node, getIDType(node.getKwnextid().toString()));
    }

    // the thing before subscript must be an array
    @Override
    public void outASubscriptId(ASubscriptId node) {
        if (getIDType(node.getId().toString()) != TYPE_CARD_ARR && getIDType(node.getId().toString()) != TYPE_PLAYER_ARR) {
            System.out.println(currentMethodBranch);
            reportError("invalid subscript: not an array: " + node.getId().toString() + ": " + getIDType(node.getId().toString()));
        }
        if (getNodeDataType(node.getExpr()) != TYPE_INTEGER) {
            reportError("invalid subscript: index is not integer: " + node.getExpr().toString() + ": " + getNodeDataType(node.getExpr()));
        }

        // see what the array type is and set the node type to be the corresponding variable type
        if (getIDType(node.getId().toString()) == TYPE_CARD_ARR) {
            setNodeDataType(node, TYPE_CARD);
        }
        else if (getIDType(node.getId().toString()) == TYPE_PLAYER_ARR) {
            setNodeDataType(node, TYPE_PLAYER);
        }
    }

    // the thing between bars must be an array
    @Override
    public void outALengthId(ALengthId node) {
        if (getIDType(node.getObj().toString()) != TYPE_CARD_ARR && getIDType(node.getObj().toString()) != TYPE_PLAYER_ARR) {
            reportError("invalid length: not an array: " + node.getObj().toString() + ": " + getIDType(node.getObj().toString()));
        }
        setNodeDataType(node, TYPE_INTEGER);
    }
}

