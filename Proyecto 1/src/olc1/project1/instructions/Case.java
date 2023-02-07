/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import java.util.LinkedList;
import olc1.project1.utils.PythonUtils;
import olc1.project1.utils.Utils;

/**
 *
 * @author Xhunik
 */
public class Case implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    Operation expr;
    LinkedList<Statement> statements;
    
    public Case(Operation expr, LinkedList<Statement> statements){
        this.expr = expr;
        this.statements = statements;
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expr
        str.append("T_").append(guid).append("[label=\"T_Case\"];\n");
        
        // open question
        str.append("OQ_").append(guid).append("[label=\"¿\"];\n");
        
        // root to open
        str.append("T_").append(guid).append("->").append("OQ_").append(guid).append(";\n");
        
        // root to expresion
        str.append("T_").append(guid).append("->").append("T_").append(expr.getGuid())
                .append(";\n");
        
        // expresion
        str.append(expr.traverse());
        
        // root to close question
        str.append("CQ_").append(guid).append("[label=\"?\"];\n");
        
        // root to close
        str.append("T_").append(guid).append("->").append("CQ_").append(guid).append(";\n");
        
        // reserved then
        str.append("R_then_").append(guid).append("[label=\"THEN\"];\n");
        
        // root to reserved
        str.append("T_").append(guid).append("->").append("R_then_")
                .append(guid).append(";\n");
        
        // statements
        for (Statement statement : statements) {
            // root to statement
            if (statement != null){
                str.append("T_").append(guid).append("->")
                        .append("T_").append(statement.getGuid()).append(";\n");

                // statement
                str.append(statement.traverse());
            }
        }

        return str.toString();
    }
    
    @Override
    public String translatePython(){
        return null;
    }

    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();
        str.append("case ").append(expr.translateGolang()).append(":\n");
        
        for (Statement statement : statements) {
            if (statement != null)
                str.append(Utils.addTabs(statement.translateGolang())).append("\n");
        }
        
        
        return str.toString();
    }
}
