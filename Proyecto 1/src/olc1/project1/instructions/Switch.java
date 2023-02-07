/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import java.util.LinkedList;
import olc1.project1.utils.Utils;
import olc1.project1.utils.PythonUtils;

/**
 *
 * @author Xhunik
 */
public class Switch implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    Operation expr;
    LinkedList<Case> cases;
    LinkedList<Statement> else_statements;
    
    public Switch(Operation expr, LinkedList<Case> cases){
        this.expr = expr;
        this.cases = cases;
    }
    
    public Switch(Operation expr, LinkedList<Case> cases, LinkedList<Statement> else_statements){
        this.expr = expr;
        this.cases = cases;
        this.else_statements = else_statements;
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expr
        str.append("T_").append(guid).append("[label=\"T_Switch\"];\n");
        
        // reserved switch
        str.append("R_switch_").append(guid).append("[label=\"SWITCH\"];\n");
        
        // root to reserved
        str.append("T_").append(guid).append("->").append("R_switch_")
                .append(guid).append(";\n");
        
        // root to expresion
        str.append("T_").append(guid).append("->").append("T_").append(expr.getGuid())
                .append(";\n");
        
        // expresion
        str.append(expr.traverse());
        
        for (Case aCase : cases) {
            // root to case
            str.append("T_").append(guid).append("->").append("T_")
                    .append(aCase.getGuid()).append(";\n");
            
            // case
            str.append(aCase.traverse());
        }
        
        
        // else statement
        if (else_statements != null){
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
        
        // reserved switch
        str.append("R_end_switch_").append(guid).append("[label=\"END SWITCH\"];\n");
        
        // root to reserved
        str.append("T_").append(guid).append("->").append("R_end_switch_")
                .append(guid).append(";\n");
        
        return str.toString();
    }
    
    @Override
    public String translatePython(){
        StringBuilder str = new StringBuilder();
        
        int i = 0;
        for (Case aCase : cases) {
            if (i == 0){
                str.append("if ").append(expr.translatePython()).append("==").append(aCase.expr.translatePython()).append(":\n");
                for (Statement statement: aCase.statements) {
                    if (statement != null)
                        str.append(Utils.addTabs(statement.translatePython())).append("\n");
                }

            } else {
                str.append("elif ").append(expr.translatePython()).append("==").append(aCase.expr.translatePython()).append(":\n");
                for (Statement statement: aCase.statements) {
                    if (statement != null)
                        str.append(Utils.addTabs(statement.translatePython())).append("\n");
                }
            }
            i++;
        }
        if(else_statements != null){
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
        str.append("switch ").append("key := ").append(expr.translateGolang()).append("; key {");
        for (Case aCase : cases) {
            str.append(aCase.translateGolang()).append("\n");
        }
        
        if (else_statements != null){
            str.append("default:\n");
            for (Statement else_statement : else_statements) {
                if (else_statement != null)
                    str.append(Utils.addTabs(else_statement.translateGolang()));
            }
        }
        str.append("}");
        
        return str.toString();
    }
}
