package libj;

import java.io.PrintStream;
import libj.math.Matrix;

/**
 * Utility functions.
 * @author Magnus C. Hyll <magnus@hyll.no>
 */
public class Util {
    
    /**
     * Pretty-prints a matrix to System.out.
     * @param m 
     * @see #printMatrix(libj.math.Matrix, java.io.PrintStream) 
     */
    public static void printMatrix(Matrix m) {
        printMatrix(m, System.out);
    }
    
    /**
     * Pretty-prints a matrix to the given PrintStream.
     * @param m
     * @param out 
     * @see #printMatrix(libj.math.Matrix) 
     */
    public static void printMatrix(Matrix m, PrintStream out) {
        int cellWidth = 0;
        for (double e : m.getElements()) {
            int len = Double.toString(round3dec(e)).length();
            if (len > cellWidth) {
                cellWidth = len;
            }
        }
        
        cellWidth += 2;
        
        for (int r = 1; r <= m.rows(); r++) {
            out.print("| ");
            
            if (m.cols() == 1) {
                out.print(padCenter(Double.toString(round3dec(m.getElem(r, 1))), cellWidth, ' '));
            }
            
            else {
                for (int c = 1; c <= m.cols(); c++) {
                    if (c == 1) {
                        out.print(padRight(Double.toString(round3dec(m.getElem(r, c))), cellWidth, ' '));
                    }
                    else if (c == m.cols()) {
                        out.print(padLeft(Double.toString(round3dec(m.getElem(r, c))), cellWidth, ' '));
                    }
                    else {
                        out.print(padCenter(Double.toString(round3dec(m.getElem(r, c))), cellWidth, ' '));
                    }
                }
            }
            
            out.print(" |\n");
            
            if (r != m.rows()) {
                out.print("|" + padCenter("", cellWidth * m.cols() + 2, ' ') + "|\n");
            }
        }
    }
    
    /**
     * Pads a string to a specified length by adding a char on the right-hand
     * side of the string.
     * @param s
     * @param size
     * @param pad
     * @return The padded string.
     * @see #padLeft(java.lang.String, int, char) 
     * @see #padCenter(java.lang.String, int, char) 
     */
    public static String padRight(String s, int size, char pad) {
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder(size);
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }
    
    /**
     * Pads a string to a specified length by adding a char on the left-hand
     * side of the string.
     * @param s
     * @param size
     * @param pad
     * @return The padded string.
     * @see #padRight(java.lang.String, int, char) 
     * @see #padCenter(java.lang.String, int, char) 
     */
    public static String padLeft(String s, int size, char pad) {
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder(size);
        while (sb.length() < size - s.length()) {
            sb.append(pad);
        }
        sb.append(s);
        return sb.toString();
    }
    
    /**
     * Pads a string to a specified length by adding a char on both sides
     * of the string.
     * @param s
     * @param size
     * @param pad
     * @return The padded string.
     * @see #padLeft(java.lang.String, int, char) 
     * @see #padRight(java.lang.String, int, char) 
     */
    public static String padCenter(String s, int size, char pad) {
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }
    
    /**
     * Rounds a double to maximum three decimal points.
     * @param num
     * @return 
     */
    public static double round3dec(double num) {
        return Math.round(num * 1000.0) / 1000.0;
    }
}
