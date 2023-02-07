/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import java.util.LinkedList;
import olc1.project1.utils.Utils;
/**
 *
 * @author Xhunik
 */
public class If implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    Operation expr;
    LinkedList<Statement> statements;
    LinkedList<Elif> elifs;
    LinkedList<Statement> else_statements;
    
    public If(Operation expr, LinkedList<Statement> statements){
        this.expr = expr;
        this.statements = statements;
    }
    
    public If(Operation expr, LinkedList<Statement> statements, LinkedList<Statement> else_statements){
        this.expr = expr;
        this.statements = statements;
        this.else_statements = else_statements;
    }
    
    public If(Operation expr, LinkedList<Statement> statements, LinkedList<Elif> elifs, LinkedList<Statement> else_statements){
        this.expr = expr;
        this.statements = statements;
        this.elifs = elifs;
        this.else_statements = else_statements;
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root to expr
        str.append("T_").append(guid).append("[label=\"T_If\"];\n");
        
        
        // reserved if
        str.append("R_if_").append(guid).append("[label=\"IF\"];\n");
        
        // root to reserved
        str.append("T_").append(guid).append("->").append("R_if_")
                .append(guid).append(";\n");
        
        // root to expr
        str.append("T_").append(guid).append("->")
                        .append("T_").append(expr.getGuid()).append(";\n");
        
        // expr
        str.append(expr.traverse());
        
        // reserved then
        str.append("R_then_").append(guid).append("[label=\"THEN\"];\n");
        
        // root to reserved
        str.append("T_").append(guid).append("->").append("R_then_")
                .append(guid).append(";\n");
        
        // statements
        for (Statement statement : statements) {
            if (statement != null){
                // root to statement
                str.append("T_").append(guid).append("->")
                        .append("T_").append(statement.getGuid()).append(";\n");

                // statement
                str.append(statement.traverse());
            }
        }

        if (elifs != null){
            for (Elif elif : elifs) {
                // root to elif
                str.append("T_").append(guid).append("->")
                        .append("T_").append(elif.getGuid()).append(";\n");
                
                // elif
                str.append(elif.traverse());
            }
        }
        
        if (else_statements != null){
            // reserved else
            str.append("R_else_").append(guid).append("[label=\"ELSE\"];\n");
        
            // root to reserved
            str.append("T_").append(guid).append("->").append("R_else_")
                .append(guid).append(";\n");
            
            for (Statement else_statement : else_statements) {
                if (else_statement != null){
                    // root to statement
                    str.append("T_").append(guid).append("->")
                            .append("T_").append(else_statement.getGuid()).append(";\n");

                    // statement
                    str.append(else_statement.traverse());
                }
            }
        
        }
        
        // reserved end if
        str.append("R_end_if_").append(guid).append("[label=\"END IF\"];\n");
        
        // root to reserved
        str.append("T_").append(guid).append("->").append("R_end_if_")
                .append(guid).append(";\n");
        
        return str.toString();
    }
    
    @Override
    public String translatePython(){
        StringBuilder str = new StringBuilder();
        
        str.append("if ").append(expr.translatePython()).append(":\n");
        for (Statement statement : statements) {
            if (statement != null)
                str.append(Utils.addTabs(statement.translatePython())).append("\n");
        }
        
        if (elifs != null){
        
            for (Elif elif : elifs) {
                str.append(elif.translatePython()).append("\n");
            }
        
        }
        
        if (else_statements != null){
            str.append("else:\n");
            for (Statement else_statement : else_statements) {
                if (else_statement != null)
                    str.append(Utils.addTabs(else_statement.translatePython())).append("\n");
            }
            
        }
        
        return str.toString();
    }

    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();
        
        str.append("if ").append(expr.translateGolang()).append(" {\n");
        for (Statement statement : statements) {
            if (statement != null)
                str.append(Utils.addTabs(statement.translateGolang())).append("\n");
        }
        
        str.append("}");
        
        if (elifs != null){
        
            for (Elif elif : elifs) {
                str.append(elif.translateGolang());
            }
        
        }
        
        if (else_statements != null){
            str.append("else{\n");
            for (Statement else_statement : else_statements) {
                if (else_statement != null)
                    str.append(Utils.addTabs(else_statement.translateGolang())).append("\n");
            }
            str.append("}");
        }
        
        
        return str.toString();
    }
}
