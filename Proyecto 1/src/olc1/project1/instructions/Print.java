/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import olc1.project1.utils.Utils;

/**
 *
 * @author Xhunik
 */
public class Print implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    Operation expr;
    
    public Print(Operation expr){
        this.expr = expr;
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root
        str.append("T_").append(guid).append("[label=\"T_Print\"];\n");
        
        // reserved word print
        str.append("R_print_").append(guid).append("[label=\"PRINT\"];\n");
        
        // root to print
        str.append("T_").append(guid).append("->").append("R_print_").append(guid)
                .append(";\n");
        
        // root to expr
        str.append("T_").append(guid).append("->").append("T_")
                .append(expr.getGuid()).append(";\n");
        
        // expr
        str.append(expr.traverse());
        
        return str.toString();
    }
    
    @Override
    public String translatePython() {
        StringBuilder str = new StringBuilder();
        
        str.append("print(").append(expr.translatePython()).append(", end='')");
        
        return str.toString();
    }

    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();
        
        str.append("fmt.Print(").append(expr.translateGolang()).append(")");
        
        return str.toString();
    }
}
