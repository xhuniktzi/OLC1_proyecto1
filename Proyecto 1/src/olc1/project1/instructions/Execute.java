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
public class Execute implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    String funcId;
    LinkedList<Operation> args_list;
    
    public Execute(String funcId){
        this.funcId = funcId;
    }
    
    public Execute(String funcId, LinkedList<Operation> args_list){
        this.funcId = funcId;
        this.args_list = args_list;
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expresion
        str.append("T_").append(guid).append("[label=\"T_Execute\"];\n");
        
        // reserved word execute
        str.append("R_exec_").append(guid).append("[label=\"EXEC\"];\n");
        
        // root to reserved
        str.append("T_").append(guid).append("->").append("R_exec_")
                        .append(guid).append(";\n");
        
        // function id
        str.append("ID_func_").append(guid).append("[label=\"")
                        .append(funcId).append("\"];\n");
        
        // root to reserved
        str.append("T_").append(guid).append("->").append("ID_func_")
                        .append(guid).append(";\n");
        
        // start token
        str.append("SP_").append(guid).append("[label=\"(\"];\n");
        
        // root to start
        str.append("T_").append(guid).append("->").append("SP_")
                        .append(guid).append(";\n");
        
        // args list
        if (args_list != null){
            for (Operation operation : args_list) {
                // root of expr to root of argument
                str.append("T_").append(guid).append("->").append("T_")
                        .append(operation.getGuid()).append(";\n");
                
                // argument
                str.append(operation.traverse());
            }
        }
        
        // end token
        str.append("EP_").append(guid).append("[label=\")\"];\n");
        
        // root to start
        str.append("T_").append(guid).append("->").append("EP_")
                        .append(guid).append(";\n");
        
        return str.toString();
    }
    
    @Override
    public String translatePython() {
        StringBuilder str = new StringBuilder();
        
        str.append(funcId);
        str.append("(");
        if (args_list != null){
            Iterator<Operation> iterator = args_list.iterator();

            while (iterator.hasNext()){
                String args = iterator.next().translatePython();
                str.append(args);
                if (iterator.hasNext()){
                    str.append(",");
                }
            }
        }
        
        str.append(")");
        
        return str.toString();
    }

    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();
        
        str.append(funcId);
        str.append("(");
        if (args_list != null){
            Iterator<Operation> iterator = args_list.iterator();

            while (iterator.hasNext()){
                String args = iterator.next().translateGolang();
                str.append(args);
                if (iterator.hasNext()){
                    str.append(",");
                }
            }
        }
        
        str.append(")");
        
        return str.toString();
    }
}
