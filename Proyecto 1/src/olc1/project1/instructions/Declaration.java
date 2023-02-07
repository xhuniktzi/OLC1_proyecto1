/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import olc1.project1.enums.EnumTypes;
import java.util.Iterator;
import java.util.LinkedList;
import olc1.project1.utils.Utils;

/**
 *
 * @author Xhunik
 */
public class Declaration implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    LinkedList<String> name_list;
    EnumTypes type;
    Operation expr;
    
    public Declaration(LinkedList<String> name_list, String type, Operation expr){
        this.name_list = name_list;
        this.type = Utils.checkTypes(type);
        this.expr = expr;
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expresion
        str.append("T_").append(guid).append("[label=\"T_Declaration\"];\n");
        
        // reserved word enter
        str.append("R_enter_").append(guid).append("[label=\"ENTER\"];\n");
        
        // root to reserved enter
        str.append("T_").append(guid).append("->").append("R_enter_")
                        .append(guid).append(";\n");
        
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
        
        // as
        str.append("As_").append(guid).append("[label=\"AS\"];\n");
        
        // root to as
        str.append("T_").append(guid).append("->").append("As_").append(guid)
                .append(";\n");
        
        // datatype
        str.append("Datatype_").append(guid).append("[label=\"")
                .append(Utils.viewTypes(type)).append("\"];\n");
        
        // root to datatype
        str.append("T_").append(guid).append("->").append("Datatype_")
                .append(guid).append(";\n");
        
        // with
        str.append("With_").append(guid).append("[label=\"WITH VALUE\"];\n");
        
        // root to with
        str.append("T_").append(guid).append("->").append("With_").append(guid)
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
         
        return str.toString();
    }

    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();
        
        Iterator<String> iterator = name_list.iterator();
        str.append("var ");
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
            str.append(expr.translateGolang());
            if (iterator.hasNext()){
                str.append(",");
            }
        }
         
        return str.toString();
    }
}
