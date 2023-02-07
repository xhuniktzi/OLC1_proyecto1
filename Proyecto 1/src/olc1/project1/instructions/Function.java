/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import olc1.project1.enums.EnumTypes;
import java.util.Iterator;
import java.util.LinkedList;
import olc1.project1.utils.GolangUtils;
import olc1.project1.utils.Utils;

/**
 *
 * @author Xhunik
 */
public class Function implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    String funcId;
    EnumTypes type;
    LinkedList<Param> params_list;
    LinkedList<Statement> statements;
    
    public Function(String funcId, String type, LinkedList<Statement> statements){
        this.funcId = funcId;
        this.type = Utils.checkTypes(type);
        this.statements = statements;
    }
    
    public Function(String funcId, String type, LinkedList<Param> params_list, LinkedList<Statement> statements){
        this.funcId = funcId;
        this.type = Utils.checkTypes(type);
        this.params_list = params_list;
        this.statements = statements;
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expresion
        str.append("T_").append(guid).append("[label=\"T_Function\"];\n");
        
        // reserved word function
        str.append("R_function_").append(guid).append("[label=\"FUNCTION\"];\n");
        
        // root to reserved function
        str.append("T_").append(guid).append("->").append("R_function_")
                .append(guid).append(";\n");
        
        // id
        str.append("P_id_").append(guid).append("[label=\"")
                .append(funcId).append("\"];\n");
        
        // root to id
        str.append("T_").append(guid).append("->").append("P_id_").append(guid).append(";\n");
        
        // datatype
        str.append("Datatype_").append(guid).append("[label=\"").append(Utils.viewTypes(type)).append("\"];\n");
        
        // root to datatype
        str.append("T_").append(guid).append("->").append("Datatype_").append(guid).append(";\n");
        
         // start token
        str.append("SP_").append(guid).append("[label=\"(\"];\n");
                
        // root to start
        str.append("T_").append(guid).append("->").append("SP_").append(guid)
                .append(";\n");
        
        if (params_list != null){
            for (Param param : params_list) {
                str.append("T_").append(guid).append("->")
                        .append("T_").append(param.getGuid()).append(";\n");
                
                str.append(param.traverse());
            }
        }
        
        // end token
        str.append("EP_").append(guid).append("[label=\")\"];\n");
        
        // root to end
        str.append("T_").append(guid).append("->").append("EP_").append(guid)
                .append(";\n");
        
        for (Statement statement : statements) { 
            if (statement != null){
                str.append("T_").append(guid).append("->")
                        .append("T_").append(statement.getGuid()).append(";\n");
                str.append(statement.traverse());           
            }
        }
        
        // reserved word end function
        str.append("R_end_function_").append(guid)
                .append("[label=\"END FUNCTION\"];\n");
        
        // root to reserved end function
        str.append("T_").append(guid).append("->").append("R_end_function_")
                .append(guid).append(";\n");
        
        return str.toString();
    }
    
    @Override
    public String translatePython(){
        StringBuilder str = new StringBuilder();
        
        str.append("def ").append(funcId).append("(");
        
        if (params_list != null){
            Iterator<Param> iterator = params_list.iterator();

            while (iterator.hasNext()){
                String args = iterator.next().translatePython();
                str.append(args);
                if (iterator.hasNext()){
                    str.append(",");
                }
            }
        }
        
        str.append("):\n");
        
        for (Statement statement : statements) {
            if (statement != null)
                str.append(Utils.addTabs(statement.translatePython())).append("\n");
        }
        
        return str.toString();
    }
    
    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();
        
        str.append("func ").append(funcId).append("(");
        
        if (params_list != null){
            Iterator<Param> iterator = params_list.iterator();

            while (iterator.hasNext()){
                String args = iterator.next().translateGolang();
                str.append(args);
                if (iterator.hasNext()){
                    str.append(",");
                }
            }
        }
        
        str.append(") ").append(GolangUtils.golangViewTypes(type)).append(" {\n");
        
        for (Statement statement : statements) {
            if (statement != null)
                str.append(Utils.addTabs(statement.translateGolang())).append("\n");
        }
        str.append("}\n");
        
        return str.toString();
    }
}
