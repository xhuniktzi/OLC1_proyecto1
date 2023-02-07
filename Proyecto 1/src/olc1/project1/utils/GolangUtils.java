/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.utils;

import olc1.project1.enums.EnumOperations;
import olc1.project1.enums.EnumTerminals;
import olc1.project1.enums.EnumTypes;
import olc1.project1.enums.EnumUnitaryOperations;

/**
 *
 * @author Xhunik
 */
public class GolangUtils {
        public static String golangSymbolBinaryOperators(EnumOperations op) {
        switch (op) {
            case ADD -> {
                return "+";
            }
            case AND -> {
                return "&&";
            }
            case DIVISION -> {
                return "/";
            }
            case EQUALS -> {
                return "==";
            }
            case MAJOR -> {
                return ">";
            }
            case MAJOREQUALS -> {
                return ">=";
            }
            case MINOR -> {
                return "<";
            }
            case MINOREQUALS -> {
                return "<=";
            }
            case MODULE -> {
                return "%";
            }
            case MULTIPLY -> {
                return "*";
            }
            case NOTEQUALS -> {
                return "!=";
            }
            case OR -> {
                return "or";
            }
            case SUBSTRACT -> {
                return "-";
            }
            default -> throw new AssertionError();
        }
    }
        
    public static String golangTerminals(String value, EnumTerminals type) {
        switch (type) {
            case BOOLEAN -> {
                if ("verdadero".equals(value)) {
                    return "true";
                } else if ("falso".equals(value)) {
                    return "false";
                }
            }
            case CHAR -> {
                return "'" + value + "'";
            }
            case ID -> {
                return value;
            }
            case NUM -> {
                return value;
            }
            case STR -> {
                boolean contains = value.contains("\n");
                if (!contains)
                    return "\"" + value + "\"";
                else
                    return "`" + value + "`";
            }
            default -> throw new AssertionError();
        }
        return null;
    }
    
    public static String golangSymbolUnitaryOperators(EnumUnitaryOperations op) {
        switch (op) {
            case NEGATIVE -> {
                return "-";
            }
            case NOT -> {
                return "!";
            }
            default -> throw new AssertionError();
        }
    }
    
    public static String golangViewTypes(EnumTypes type){
        switch (type) {
            case BOOLEAN -> {
                return "bool";
            }
            case CHAR -> {
                return "byte";
            }
            case NUM -> {
                return "float64";
            }
            case STR -> {
                return "string";
            }
            default -> throw new AssertionError();
        }
    }
}
