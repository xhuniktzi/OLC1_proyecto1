/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import java.util.Iterator;
import java.util.LinkedList;
import olc1.project1.utils.Utils;

/**
 *
 * @author Xhunik
 */
public class Assignment implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    
    LinkedList<String> name_list;
    Operation expr;
    
    public Assignment(LinkedList<String> name_list, Operation expr){
        this.name_list = name_list;
        this.expr = expr;
    }


    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expresion
        str.append("T_").append(guid).append("[label=\"T_Assignment\"];\n");
        
        // name list
        int i = 0;
        for (String name : name_list) {
            // name
            str.append("Name_").append(guid).append("_").append(i)
                    .append("[label=\"").append(name).append("\"];\n");
            
            // root to name
            str.append("T_").append(guid).append("->").append("Name_")
                    .append(guid).append("_").append(i).append(";\n");
            i++;
        }
        
        // arrow
        str.append("Arrow_").append(guid).append("[label=\"ARROW\"];\n");
        
        // root to arrow
        str.append("T_").append(guid).append("->").append("Arrow_").append(guid)
                .append(";\n");
        
        // root to expresion
        str.append("T_").append(guid).append("->").append("T_").append(expr.getGuid())
                .append(";\n");
        
        // expresion
        str.append(expr.traverse());
        
        return str.toString();
    }

    @Override
    public String translatePython() {
        StringBuilder str = new StringBuilder();
        
        Iterator<String> iterator = name_list.iterator();
        
        while (iterator.hasNext()){
            String name = iterator.next();
            str.append(name);
            if (iterator.hasNext()){
                str.append(",");
            }
        }
        
        str.append(" = ");
        
        iterator = name_list.iterator();
        
        while (iterator.hasNext()){
            iterator.next();
            str.append(expr.translatePython());
            if (iterator.hasNext()){
                str.append(",");
            }
        }
        
        str.append("\n");

        
        return str.toString();
    }

    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();

        for (String name : name_list) {
            str.append(name).append(" = ").append(expr.translateGolang()).append("\n");
        }
        
        str.append("\n");

        return str.toString();
    }
    
}
