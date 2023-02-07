/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import java.util.Iterator;
import java.util.LinkedList;
import olc1.project1.utils.Utils;
import olc1.project1.utils.PythonUtils;

/**
 *
 * @author Xhunik
 */
public class Procedure implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    String funcId;
    LinkedList<Param> params_list;
    LinkedList<Statement> statements;
    
    public Procedure(String funcId, LinkedList<Statement> statements){
        this.funcId = funcId;
        this.statements = statements;
    }
    
    public Procedure(String funcId, LinkedList<Param> params_list, LinkedList<Statement> statements){
        this.funcId = funcId;
        this.params_list = params_list;
        this.statements = statements;
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expresion
        str.append("T_").append(guid).append("[label=\"T_Procedure\"];\n");
        
        // reserved word procedure
        str.append("R_procedure_").append(guid).append("[label=\"PROCEDURE\"];\n");
        
        // root to reserved procedure
        str.append("T_").append(guid).append("->").append("R_procedure_")
                .append(guid).append(";\n");
        
        // id
        str.append("P_id_").append(guid).append("[label=\"")
                .append(funcId).append("\"];\n");
        
        // root to id
        str.append("T_").append(guid).append("->").append("P_id_").append(guid).append(";\n");
        
        
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
        
        // reserved word end procedure
        str.append("R_end_procedure_").append(guid)
                .append("[label=\"END PROCEDURE\"];\n");
        
        // root to reserved end procedure
        str.append("T_").append(guid).append("->").append("R_end_procedure_")
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
        
        str.append(") ").append("{\n");
        
        for (Statement statement : statements) {
            if (statement != null)
                str.append(Utils.addTabs(statement.translateGolang())).append("\n");
        }
        str.append("}\n");
        
        return str.toString();
    }
}
