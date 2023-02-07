/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.instructions;

import olc1.project1.enums.TypeOperation;
import olc1.project1.enums.EnumUnitaryOperations;
import olc1.project1.enums.EnumOperations;
import olc1.project1.enums.EnumTerminals;
import olc1.project1.utils.GolangUtils;
import olc1.project1.utils.Utils;
import olc1.project1.utils.PythonUtils;

/**
 *
 * @author Xhunik
 */
public class Operation implements Statement {
    private final String guid = Utils.generateGuid();
    @Override
    public String getGuid() { return this.guid; }
    
    // Binary operations
    Operation left;
    Operation right;
    EnumOperations type;
        
    // Unitary operations;
    Operation op;
    EnumUnitaryOperations typeUnitary;
    
    // Terminal expresions
    String value;
    EnumTerminals typeTerminal;
    
    // Group
    Operation opGroup;
    
    // Flag Type
    private final TypeOperation typeOp;

    public Operation(Operation left, EnumOperations type, Operation right){
        this.left = left;
        this.right = right;
        this.type = type;
        this.typeOp = TypeOperation.BINARY;
    }
    
    public Operation(Operation op, EnumUnitaryOperations typeUnitary){
        this.op = op;
        this.typeUnitary = typeUnitary;
        this.typeOp = TypeOperation.UNITARY;
    }
    
    public Operation(String value, EnumTerminals typeTerminal){
        this.typeTerminal = typeTerminal;
        this.typeOp = TypeOperation.TERMINAL;
        
        if (typeTerminal == EnumTerminals.CHAR){
            if (value.length() > 1){
                this.value = Character.toString((char) Integer.parseInt(value.replace("${", "").replace("}", "")));
            } else {
                this.value = value;
            }
            return;
        }
        this.value = value;

    }
    
    public Operation(Operation value){
        this.opGroup = value;
        this.typeOp = TypeOperation.GROUP;
    }
    

    @Override
    public String traverse() {
        StringBuilder str = new StringBuilder();
        
        // root of expresion
        str.append("T_").append(guid).append("[label=\"T_Operation\"];\n");
        
        switch (typeOp) {
            case BINARY -> {
                // root of this to root of left
                str.append("T_").append(guid).append("->").append("T_").append(left.guid)
                         .append(";\n");
                
                // left expresion
                str.append(left.traverse());
                
                // operator
               str.append("Op_").append(guid).append("[label=\"")
                        .append(PythonUtils.pythonSymbolBinaryOperators(type)).append("\"];\n");
               
               // root to operator
                str.append("T_").append(guid).append("->").append("Op_").append(guid)
                         .append(";\n");
                
                // root of this to root of right
                str.append("T_").append(guid).append("->").append("T_").append(right.guid)
                         .append(";\n");
                
                // right expresion
                str.append(right.traverse());
            }
            case UNITARY -> {
                // operator
                str.append("Op_").append(guid).append("[label=\"")
                        .append(PythonUtils.pythonSymbolUnitaryOperators(typeUnitary)).append("\"];\n");
                
                // root to operator
                str.append("T_").append(guid).append("->").append("Op_").append(guid)
                         .append(";\n");
                
                // root of this to root of expresion
                str.append("T_").append(guid).append("->").append("T_").append(op.guid)
                         .append(";\n");
                
                //expresion
                str.append(op.traverse());
            }
            case TERMINAL -> {
                // value of expresion
                str.append("Val_").append(guid).append("[label=\"")
                        .append(value).append("\"];\n");
                
                // root of this to value
                str.append("T_").append(guid).append("->").append("Val_").append(guid)
                         .append(";\n");
            }
            case GROUP -> {
                // start token
                str.append("SP_").append(guid).append("[label=\"(\"];\n");
                
                // root to start
                str.append("T_").append(guid).append("->").append("SP_").append(guid)
                         .append(";\n");
                
                // root to group
                str.append("T_").append(guid).append("->").append("T_")
                        .append(opGroup.guid).append(";\n");
                
                // group
                str.append(opGroup.traverse());

                // end token
                str.append("EP_").append(guid).append("[label=\")\"];\n");
                
                // root to end
                str.append("T_").append(guid).append("->").append("EP_").append(guid)
                         .append(";\n");
            }

            default -> throw new AssertionError();
        }
        
        return str.toString();
    }
    
    @Override
    public String translatePython() {
        StringBuilder str = new StringBuilder();
        
        switch (typeOp) {
            case BINARY -> {
                str.append(left.translatePython()).append(" ")
                        .append(PythonUtils.pythonSymbolBinaryOperators(type)).append(" ")
                        .append(right.translatePython());
            }
            case UNITARY -> {
                str.append(PythonUtils.pythonSymbolUnitaryOperators(typeUnitary)).append(" ").append(op.translatePython());
            }
            case TERMINAL -> {
                str.append(PythonUtils.pythonTerminals(value, typeTerminal));
            }
            case GROUP -> {
                str.append("(").append(opGroup.translatePython()).append(")");
            }

            default -> throw new AssertionError();
        }
        
        return str.toString();
    }

    @Override
    public String translateGolang() {
        StringBuilder str = new StringBuilder();
    
        switch (typeOp) {
            case BINARY -> {
                
                if (type == EnumOperations.POW){
                    str.append("math.Pow(float64(").append(left.translateGolang())
                            .append("), float64(").append(right.translateGolang())
                            .append("))");
                } else {
                    str.append(left.translateGolang()).append(" ");
                    str.append(GolangUtils.golangSymbolBinaryOperators(type));
                    str.append(" ").append(right.translateGolang());
                }
            }
            case UNITARY -> {
                str.append(GolangUtils.golangSymbolUnitaryOperators(typeUnitary))
                        .append(" ").append(op.translateGolang());
            }
            case TERMINAL -> {
                str.append(GolangUtils.golangTerminals(value, typeTerminal));
            }
            case GROUP -> {
                str.append("(").append(opGroup.translateGolang()).append(")");
            }

            default -> throw new AssertionError();
        }
        
        return str.toString();
    }
}
