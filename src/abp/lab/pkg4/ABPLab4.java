/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abp.lab.pkg4;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author pmele
 */
public class ABPLab4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
         List<FastaSequence> fastaList = 
            FastaSequence.readFastaFile(
                    "C:\\Users\\pmele\\Documents\\NetBeansProjects\\ABP Lab 4\\sample.fasta");

         for( FastaSequence fs : fastaList)
         {
             System.out.println(fs.getHeader());
             System.out.println(fs.getSequence());
             System.out.println(fs.getGCRatio());
          }

         File myFile = new File("C:\\Users\\pmele\\Documents\\NetBeansProjects\\ABP Lab 4\\sampleOut.txt");

         writeTableSummary( fastaList,  myFile);
    }
    
    public static void writeTableSummary( List<FastaSequence> list, File outputFile) throws Exception {
        FileWriter fw = new FileWriter(outputFile);
        PrintWriter pw = new PrintWriter(fw);
        pw.println("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence");
        StringBuilder builder;
        for (FastaSequence fSeq : list) {
            builder = new StringBuilder();
            for (int count : fSeq.getNucCounts()) {
                builder.append(count+"\t");
            }
            pw.println(fSeq.getHeader() + "\t" + builder.toString() + "\t" + fSeq.getSequence());
        }
        pw.close();
        fw.close();
    }

    
}
