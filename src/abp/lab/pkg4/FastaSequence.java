/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abp.lab.pkg4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pmele
 */
public class FastaSequence {
    private String header;
    private String sequence;
    private int[] nucCount = new int[6]; //A C G T Gaps Undetermined

    public FastaSequence(String header, String sequence, int[] nucCount) {
        this.header = header;
        this.sequence = sequence;
        this.nucCount = nucCount;
    }
    
    
    
    public static List<FastaSequence> readFastaFile(String filepath) throws Exception {
        Scanner input = new Scanner(new File(filepath));
        String line = null;
        String header = null;
        StringBuilder sequence = new StringBuilder();
        char posChar;
        int[] nucCount = new int[]{0, 0, 0, 0, 0 ,0};
        
        List<FastaSequence> output = new ArrayList<>();
        while (input.hasNextLine()) {
            line = input.nextLine().trim();
            if (line.startsWith(">")) {
                if (header != null) {
                    output.add(new FastaSequence(header, sequence.toString(), nucCount)); //Change to actually add a FastaSequence object!
                }
                header = line.substring(1).replace("\t", " "); //Prevents misreading in TSVs
                sequence = new StringBuilder();
                nucCount = new int[4];
                continue;
            }
            line = line.toUpperCase();
            for (int i = 0; i < line.length(); i++) {
                posChar = line.charAt(i);
                switch(posChar) {
                    case ('A'):
                        nucCount[0]++;
                        break;
                    case ('C'):
                        nucCount[1]++;
                        break;
                    case ('G'):
                        nucCount[2]++;
                        break;
                    case ('T'):
                        nucCount[3]++;
                        break;
                    case ('-'):
                        nucCount[4]++;
                        break;
                    default:
                        nucCount[5]++;
                        break;
                }
            }
            sequence.append(line);
        }
        if (header != null) {
            output.add(new FastaSequence(header, sequence.toString(), nucCount)); //Change to actually add a FastaSequence object!
        }
        return output;
    }

    // returns the header of this sequence without the “>”
    public String getHeader() {
        return header;
    }

    // returns the Dna sequence of this FastaSequence
    public String getSequence()  {
        return sequence;
    }
    
    // returns the number of G’s and C’s divided by the length of this sequence, including gaps
    public float getGCRatio() {
        return (nucCount[1] + nucCount[2])/(float)(sequence.length());
    }

    // returns the number of G’s and C’s divided by the length of this sequence, not including gaps
    public float getGCRatioNoGaps() {
        return (nucCount[1] + nucCount[2])/(float)(sequence.length() - nucCount[4]);
    }
    
    public int[] getNucCounts() {
        return new int[]{nucCount[0], nucCount[1], nucCount[2], nucCount[3]};
    }
    
    public int[] getAllCounts() {
        return nucCount;
    }
}
