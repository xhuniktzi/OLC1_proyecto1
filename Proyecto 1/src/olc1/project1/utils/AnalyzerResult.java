/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package olc1.project1.utils;

import java.util.LinkedList;
import olc1.project1.errors.LexicalError;
import olc1.project1.errors.SintaxError;
import olc1.project1.instructions.Statement;

/**
 *
 * @author Xhunik
 */
public class AnalyzerResult {
    public LinkedList<Statement> ast;
    public LinkedList<LexicalError> lexErrors;
    public LinkedList<SintaxError> sintaxErrors;
    
    public AnalyzerResult(LinkedList<Statement> ast, LinkedList<LexicalError> lexErrors,
            LinkedList<SintaxError> sintaxErrors){
        this.ast = ast;
        this.lexErrors = lexErrors;
        this.sintaxErrors = sintaxErrors;
    }
}
