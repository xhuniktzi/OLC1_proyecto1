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
public class While implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    Operation expr;
    LinkedList<Statement> statements;
    
    public While(Operation expr, LinkedList<Statement> statements){
        this.expr = expr;
        this.statements = statements;
    }
    
    public While(Operation expr){
        this.expr = expr;
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expresion
        str.append("T_").append(guid).append("[label=\"T_While\"];\n");
        
        // reserved word while
        str.append("R_while_").append(guid).append("[label=\"WHILE\"];\n");
        
        // root to reserved while
        str.append("T_").append(guid).append("->").append("R_while_")
                        .append(guid).append(";\n");
        
        // root to expresion
        str.append("T_").append(guid).append("->").append("T_").append(expr.getGuid())
                .append(";\n");
        
        // expresion
        str.append(expr.traverse());
        
        // reserved word do
        str.append("R_do_").append(guid).append("[label=\"DO\"];\n");
        
        // root to reserved do
        str.append("T_").append(guid).append("->").append("R_do_")
                        .append(guid).append(";\n");
        
        // statements
        for (Statement statement : statements) {
            if (statement != null){
                str.append("T_").append(guid).append("->").append("T_").append(statement.getGuid()).append(";\n");
                str.append(statement.traverse());
            }

        }
        
        // reserved word end while
        str.append("R_end_while_").append(guid).append("[label=\"END WHILE\"];\n");
        
        // root to reserved end while
        str.append("T_").append(guid).append("->").append("R_end_while_")
                        .append(guid).append(";\n");
        
        return str.toString();
    }
    
    @Override
    public String translatePython(){
        StringBuilder str = new StringBuilder();
        
        str.append("while ").append(expr.translatePython()).append(":\n");
        for (Statement statement : statements) {
            if (statement != null)
                str.append(Utils.addTabs(statement.translatePython())).append("\n");
        }
        
        return str.toString();
    }

    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();
        str.append("for ").append(expr.translateGolang()).append(" {\n");
        for (Statement statement : statements) {
            if (statement != null)
                str.append(Utils.addTabs(statement.translateGolang())).append("\n");
        }
        str.append("}\n");
        return str.toString();
    }
}
