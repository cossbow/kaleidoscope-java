package com.jjj.lexer;

import com.jjj.lexer.ast.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.jjj.lexer.Constants.*;

public class ExprParser {
    private final TokenParser tokenParser;

    public ExprParser(CharSource source) {
        this(new TokenParser(source));
    }

    public ExprParser(TokenParser tokenParser) {
        this.tokenParser = Objects.requireNonNull(tokenParser);
    }


    private int currentToken;


    //

    short getCurrentPrecedence() {
        return binaryOperatorPrecedence((char) currentToken);
    }


    int getNextToken() {
        return currentToken = tokenParser.getToken();
    }


    //

    ExprAST parseNumberExpr() {
        var result = new NumberExprAST(tokenParser.number());
        getNextToken();
        return result;
    }

    ExprAST parseParenExpr() {
        getNextToken();
        var expr = parseExpression();
        getNextToken();
        return expr;
    }

    ExprAST parseIdentifierExpr() {
        var id = tokenParser.identifier();
        getNextToken();
        if (currentToken != '(') {
            return new VariableExprAST(id);
        } else {
            getNextToken();
            var args = new ArrayList<ExprAST>();
            while (currentToken != ')') {
                args.add(parseExpression());
                if (currentToken == ')') {
                    break;
                } else {
                    getNextToken();
                }
            }
            getNextToken();
            return new CallExprAST(id, args);
        }
    }

    ExprAST parsePrimary() {
        switch (currentToken) {
            case TOKEN_IDENTIFIER:
                return parseIdentifierExpr();
            case TOKEN_NUMBER:
                return parseNumberExpr();
            case '(':
                return parseParenExpr();
            default:
                return null;
        }
    }

    ExprAST parseBinaryOperatorRhs(int min_precedence, ExprAST lhs) {
        while (true) {
            var current_precedence = getCurrentPrecedence();
            if (current_precedence < min_precedence) {
                return lhs;
            }
            var binOp = currentToken;
            getNextToken();
            var rhs = parsePrimary();
            var next_precedence = getCurrentPrecedence();
            if (current_precedence < next_precedence) {
                rhs = parseBinaryOperatorRhs(current_precedence + 1, rhs);
            }
            lhs = new BinaryExprAST((char) binOp, lhs, rhs);
        }
    }

    ExprAST parseExpression() {
        var lhs = parsePrimary();
        return parseBinaryOperatorRhs(0, lhs);
    }

    PrototypeAST parsePrototype() {
        var function = tokenParser.identifier();
        getNextToken();
        var parameters = new ArrayList<String>();
        while (getNextToken() == TOKEN_IDENTIFIER) {
            parameters.add(tokenParser.identifier());
        }
        getNextToken();
        return new PrototypeAST(function, parameters);
    }

    FunctionAST parseDefinition() {
        getNextToken();
        var proto = parsePrototype();
        var expr = parseExpression();
        return new FunctionAST(proto, expr);
    }

    PrototypeAST parseExtern() {
        getNextToken();
        return parsePrototype();
    }

    FunctionAST parseTopLevelExpr() {
        var expr = parseExpression();
        return new FunctionAST(new PrototypeAST("", List.of()), expr);
    }


    void testParse() {
        getNextToken();
        while (true) {
            switch (currentToken) {
                case TOKEN_EOF:
                    return;
                case TOKEN_DEF:
                    parseDefinition();
                    System.out.println("parsed a function definition");
                    break;
                case TOKEN_EXTERN:
                    parseExtern();
                    System.out.println("parsed a extern");
                    break;
                default:
                    var expr = parseTopLevelExpr();
                    if (null == expr) {
                        System.err.println("parse top level expr null!");
                        return;
                    }
                    System.out.println("parsed a top level expr");
                    break;
            }
        }
    }

    Callable parseCallable() {
        getNextToken();
        return parseExpression();
    }

}
