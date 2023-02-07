/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import olc1.project1.enums.EnumTypes;
import olc1.project1.utils.GolangUtils;
import olc1.project1.utils.Utils;

/**
 *
 * @author Xhunik
 */
public class Param implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    String id;
    EnumTypes type;
    public Param(String id, String type){
        this.id = id;
        this.type = Utils.checkTypes(type);
    }
    
    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expresion
        str.append("T_").append(guid).append("[label=\"T_Param\"];\n");
        
        // id
        str.append("Id_").append(guid).append("[label=\"").append(id).append("\"];\n");
        
        // root to id
        str.append("T_").append(guid).append("->").append("Id_").append(guid).append(";\n");

        // datatype
        str.append("Datatype_").append(guid).append("[label=\"").append(Utils.viewTypes(type)).append("\"];\n");
        
        // root to datatype
        str.append("T_").append(guid).append("->").append("Datatype_").append(guid).append(";\n");
        
        return str.toString();
    }
    
    @Override
    public String translatePython(){
        StringBuilder str = new StringBuilder();
        
        str.append(id);
        
        return str.toString();
    }

    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();
        
        str.append(id).append(" ").append(GolangUtils.golangViewTypes(type));
        
        return str.toString();
    }
}


