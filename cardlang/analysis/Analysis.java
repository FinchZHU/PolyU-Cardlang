/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.analysis;

import cardlang.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAProgram(AProgram node);
    void caseAGame(AGame node);
    void caseAScreen(AScreen node);
    void caseAValid(AValid node);
    void caseANextid(ANextid node);
    void caseAWinnerid(AWinnerid node);
    void caseAStmtblock(AStmtblock node);
    void caseAShuffleStmt(AShuffleStmt node);
    void caseAShiftStmt(AShiftStmt node);
    void caseAForStmt(AForStmt node);
    void caseAWhileStmt(AWhileStmt node);
    void caseAIfStmt(AIfStmt node);
    void caseABreakStmt(ABreakStmt node);
    void caseAContinueStmt(AContinueStmt node);
    void caseABaseShift(ABaseShift node);
    void caseARecurShift(ARecurShift node);
    void caseAFor(AFor node);
    void caseAWhile(AWhile node);
    void caseASingleIf(ASingleIf node);
    void caseAElseIf(AElseIf node);
    void caseAElsestmtblock(AElsestmtblock node);
    void caseABaseExpr(ABaseExpr node);
    void caseAStringExpr(AStringExpr node);
    void caseAAndExpr(AAndExpr node);
    void caseAOrExpr(AOrExpr node);
    void caseABaseFactor(ABaseFactor node);
    void caseANotFactor(ANotFactor node);
    void caseABaseCompexpr(ABaseCompexpr node);
    void caseALargerCompexpr(ALargerCompexpr node);
    void caseALargerequalCompexpr(ALargerequalCompexpr node);
    void caseALessCompexpr(ALessCompexpr node);
    void caseALessequalCompexpr(ALessequalCompexpr node);
    void caseAEqualCompexpr(AEqualCompexpr node);
    void caseAInequalCompexpr(AInequalCompexpr node);
    void caseABaseNumexpr(ABaseNumexpr node);
    void caseAPlusNumexpr(APlusNumexpr node);
    void caseAMinusNumexpr(AMinusNumexpr node);
    void caseAMultiNumexpr(AMultiNumexpr node);
    void caseADivNumexpr(ADivNumexpr node);
    void caseAModNumexpr(AModNumexpr node);
    void caseAObjVal(AObjVal node);
    void caseANumVal(ANumVal node);
    void caseATrueVal(ATrueVal node);
    void caseAFalseVal(AFalseVal node);
    void caseAParenVal(AParenVal node);
    void caseAIdObj(AIdObj node);
    void caseAFieldObj(AFieldObj node);
    void caseALitId(ALitId node);
    void caseAGameId(AGameId node);
    void caseAScreenId(AScreenId node);
    void caseAValidId(AValidId node);
    void caseAWinneridId(AWinneridId node);
    void caseANextidId(ANextidId node);
    void caseASubscriptId(ASubscriptId node);
    void caseALengthId(ALengthId node);

    void caseTKwgame(TKwgame node);
    void caseTKwscreen(TKwscreen node);
    void caseTKwvalid(TKwvalid node);
    void caseTKwnextid(TKwnextid node);
    void caseTKwwinnerid(TKwwinnerid node);
    void caseTKwshuffle(TKwshuffle node);
    void caseTKwfor(TKwfor node);
    void caseTKwfrom(TKwfrom node);
    void caseTKwto(TKwto node);
    void caseTKwwhile(TKwwhile node);
    void caseTKwbreak(TKwbreak node);
    void caseTKwcontinue(TKwcontinue node);
    void caseTKwif(TKwif node);
    void caseTKwthen(TKwthen node);
    void caseTKwelse(TKwelse node);
    void caseTKwtrue(TKwtrue node);
    void caseTKwfalse(TKwfalse node);
    void caseTKwnot(TKwnot node);
    void caseTKwand(TKwand node);
    void caseTKwor(TKwor node);
    void caseTLeftbrace(TLeftbrace node);
    void caseTRightbrace(TRightbrace node);
    void caseTLeftparen(TLeftparen node);
    void caseTRightparen(TRightparen node);
    void caseTPoint(TPoint node);
    void caseTLeftsqr(TLeftsqr node);
    void caseTRightsqr(TRightsqr node);
    void caseTLine(TLine node);
    void caseTOptshift(TOptshift node);
    void caseTOptlargerequal(TOptlargerequal node);
    void caseTOptlessequal(TOptlessequal node);
    void caseTOptinequal(TOptinequal node);
    void caseTOptlarger(TOptlarger node);
    void caseTOptless(TOptless node);
    void caseTOptequal(TOptequal node);
    void caseTOptplus(TOptplus node);
    void caseTOptminus(TOptminus node);
    void caseTOptmulti(TOptmulti node);
    void caseTOptdiv(TOptdiv node);
    void caseTOptmod(TOptmod node);
    void caseTLiteral(TLiteral node);
    void caseTNumeral(TNumeral node);
    void caseTString(TString node);
    void caseTBlank(TBlank node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}