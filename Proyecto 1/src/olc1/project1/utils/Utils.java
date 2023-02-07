/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package olc1.project1.utils;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import olc1.project1.analizadores.Lexico;
import olc1.project1.analizadores.Sintactico;
import olc1.project1.enums.EnumTypes;
import olc1.project1.instructions.Statement;

/**
 *
 * @author Xhunik
 */
public class Utils {
    int id = 0;
    public static AnalyzerResult loadFile(String input) throws Exception{
        Lexico scanner = new Lexico(new StringReader(input));
        Sintactico parser = new Sintactico(scanner);
        try {
            parser.parse();
        } catch (Exception ex) {
            throw ex;
        }
        return new AnalyzerResult(parser.AST, scanner.lexicalErrors, parser.errors);
    }
    
    public static String translateGolang(LinkedList<Statement> ast){
        StringBuilder str = new StringBuilder();
        
        str.append("package main\n");
        str.append("import \"fmt\"\n");
        str.append("import \"math\"\n");
        
        LinkedList<Statement> methods = new LinkedList<>();
        LinkedList<Statement> mainStatements = new LinkedList<>();
        
        for (Statement statement : ast) {
            if (statement != null){
                String className = statement.getClass().getSimpleName();
                if ( "Procedure".equals(className) || "Function".equals(className)){
                    methods.add(statement);
                } else {
                    mainStatements.add(statement);
                }
            }
        }
        
        for (Statement method : methods) {
            str.append(method.translateGolang()).append("\n");
        }
        
        str.append("func main(){\n");
        for (Statement mainStatement : mainStatements) {
            str.append(addTabs(mainStatement.translateGolang())).append("\n");
        }
        str.append("}\n");
        
        return str.toString();
    }
            
    public static String translatePython(LinkedList<Statement> ast){
        StringBuilder str = new StringBuilder();
        
        LinkedList<Statement> methods = new LinkedList<>();
        LinkedList<Statement> mainStatements = new LinkedList<>();
        
        for (Statement statement : ast) {
            if (statement != null){
                String className = statement.getClass().getSimpleName();
                if ( "Procedure".equals(className) || "Function".equals(className)){
                    methods.add(statement);
                } else {
                    mainStatements.add(statement);
                }
            }
        }
        
        for (Statement method : methods) {
            str.append(method.translatePython()).append("\n");
        }
        
        
        str.append("def main():\n");        
        for (Statement statement : mainStatements) {
            str.append(addTabs(statement.translatePython())).append("\n");
        }
        str.append("if __name__ == '__main__':\n");
        str.append("\tmain()");
        return str.toString();
    }
    
    public static String graph(LinkedList<Statement> ast){
        StringBuilder str = new StringBuilder();
        
        str.append("digraph G {\n");
        str.append("rootNode [label=\"Raiz\"];\n");
        str.append("node[shape=\"rectangle\"];\n");
        str.append("splines=polyline;\n");
        str.append("concentrate=true;\n");
        
        for (Statement statement : ast) {
            if (statement != null){
                str.append("rootNode ->").append("T_").append(statement.getGuid()).append(";\n");
                str.append(statement.traverse());
            }
             
        }
        
        str.append("}");
        
        return str.toString();
    }
    public static String generateGuid(){
        return UUID.randomUUID().toString().replaceAll("-", "").replaceFirst("[0-9]+", "");
    }
    public static EnumTypes checkTypes(String type){
        type = type.toLowerCase();
         switch (type) {
            case "numero" -> {
                return EnumTypes.NUM;
            }
            case "cadena" -> {
                return EnumTypes.STR;
            }
            case "boolean" -> {
                return EnumTypes.BOOLEAN;
            }
            case "caracter" -> {
                return EnumTypes.CHAR;
            }
            default -> throw new AssertionError();
        }
    }
    
    public static String viewTypes(EnumTypes type){
        switch (type) {
            case BOOLEAN -> {
                return "bool";
            }
            case CHAR -> {
                return "str";
            }
            case NUM -> {
                return "float";
            }
            case STR -> {
                return "str";
            }
            default -> throw new AssertionError();
        }
    }

    /**
     *
     * @param input the value of input
     * @return
     */
    public static String addTabs(String input) {
        StringBuilder str = new StringBuilder();
        List<String> lines = input.lines().toList();
        for (String line : lines) {
            str.append("\t").append(line).append("\n");
        }
        return str.toString();
    }
}
