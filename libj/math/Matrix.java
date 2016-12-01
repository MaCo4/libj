package libj.math;

import java.util.Arrays;


/**
 * A generalized n x m-matrix holding real numbers.
 * @author Magnus C. Hyll <magnus@hyll.no>
 * @see SqMatrix
 */
public class Matrix {
    
    /**
     * Array holding the elements in this matrix.
     */
    protected double[] elem;
    
    /**
     * Number of rows in this matrix.
     */
    protected int rows;
    
    /**
     * Number of columns in this matrix.
     */
    protected int cols;
    
    /**
     * Creates a new Matrix with the specified number of rows and columns. All
     * elements are set to zero.
     * @param rows
     * @param cols 
     */
    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows or cols can't be zero or negative");
        }
        
        this.rows = rows;
        this.cols = cols;
        elem = new double[rows * cols];
    }
    
    /**
     * Creates a new Matrix and sets elements according to the given
     * two-dimensional array of values. The number of rows are set to the size
     * of the suppplied array, and the number of columns are set to the row in
     * the suppplied array with the largest number of elements. Elements in 
     * positions which are not supplied in the array are set to zero.
     * @param elements 
     */
    public Matrix(double[][] elements) {
        int r = elements.length;
        int c = 0;
        
        for (double[] row : elements) {
            c = Math.max(c, row.length);
        }
        
        rows = r;
        cols = c;
        elem = new double[rows * cols];
        
        for (r = 0; r < rows; r++) {
            for (c = 0; c < cols; c++) {
                if (c >= elements[r].length) {
                    setElem(r + 1, c + 1, 0);
                }
                else {
                    setElem(r + 1, c + 1, elements[r][c]);
                }
            }
        }
    }
    
    /**
     * Returns the number of rows in this matrix.
     * @return 
     */
    public int rows() {
        return rows;
    }
    
    /**
     * Returns the number of columns in this matrix.
     * @return 
     */
    public int cols() {
        return cols;
    }
    
    /**
     * Returns an array containing a copy of this matrix's elements.
     * @return 
     */
    public double[] getElements() {
        double[] elems = new double[rows * cols];
        System.arraycopy(elem, 0, elems, 0, elem.length);
        return elems;
    }
    
    /**
     * Lambda function interface for iterating though each element of the
     * matrix.
     * @see Matrix#forEach(libj.math.Matrix.EachElementAction)
     */
    @FunctionalInterface
    public interface EachElementAction {
        /**
         * Performs an action for an element in the matrix.
         * @param r
         * @param c
         * @param e 
         * @see Matrix#forEach(libj.math.Matrix.EachElementAction) 
         */
        public void perform(int r, int c, double e);
    }
    
    /**
     * Iterates through every element in this matrix, supplying row number,
     * column number and value for each element. Note that the action performed
     * for each element cannot change the element's value.
     * @param action 
     * @see EachElementAction
     * @see EachElementAction#perform(int, int, double) 
     */
    public void forEach(EachElementAction action) {
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                action.perform(r, c, getElem(r, c));
            }
        }
    }
    
    /**
     * Checks if an element in the given row and column exists in this matrix.
     * @param row
     * @param col
     * @return 
     */
    public boolean isWithin(int row, int col) {
        return row > 0 && row <= rows
            && col > 0 && col <= cols;
    }
    
    /**
     * Returns the value of the element in the given row and column.
     * @param row
     * @param col
     * @return 
     */
    public double getElem(int row, int col) {
        if (!isWithin(row, col)) {
            return 0;
        }
        
        return elem[(row - 1) * cols + (col - 1)];
    }
    
    /**
     * Sets the value of the element in the given row and column.
     * @param row
     * @param col
     * @param value 
     */
    public void setElem(int row, int col, double value) {
        if (!isWithin(row, col)) {
            return;
        }
        
        elem[(row - 1) * cols + (col - 1)] = value;
    }
    
    /**
     * Sets all elements to the values in the given array by deep-copying.
     * The elements must be ordered row-first.
     * @param elems 
     */
    public void setAll(double[] elems) {
        if (elems.length < elem.length) {
            return;
        }
        
        System.arraycopy(elems, 0, elem, 0, elem.length);
    }
    
    /**
     * Multiplies this matrix by a scalar, changing this matrix's elements.
     * @param c 
     */
    public void mul(double c) {
        for (int i = 0; i < elem.length; i++) {
            elem[i] *= c;
        }
    }
    
    /**
     * Adds another matrix to this matrix, changing this matrix's elements.
     * @param other 
     */
    public void add(Matrix other) {
        if (other.rows != rows || other.cols != cols) {
            return;
        }
        
        for (int i = 0; i < elem.length; i++) {
            elem[i] += other.elem[i];
        }
    }
    
    /**
     * Subtracts this matrix by another matrix, changing this matrix's elements.
     * Subtraction order is: [this] = [this] - [other]
     * @param other 
     */
    public void sub(Matrix other) {
        if (other.rows != rows || other.cols != cols) {
            return;
        }
        
        for (int i = 0; i < elem.length; i++) {
            elem[i] -= other.elem[i];
        }
    }
    
    /**
     * Multiplies this matrix by another matrix, changing this matrix's
     * elements.
     * Multiplication order is: [this] = [this] x [other]
     * @param other 
     */
    public void mul(Matrix other) {
        Matrix res = mul(this, other);
        rows = res.rows();
        cols = res.cols();
        elem = res.elem;
    }
    
    /**
     * Multiplies two matrices and returns the resulting matrix. Multiplication
     * order is: [first] x [second]
     * @param first
     * @param second
     * @return 
     */
    public static Matrix mul(Matrix first, Matrix second) {
        if (first.cols != second.rows) {
            throw new IllegalArgumentException("Incompatible matrix dimensions");
        }
        
        Matrix res = new Matrix(first.rows, second.cols);
        for (int r = 1; r <= res.rows; r++) {
            for (int c = 1; c <= res.cols; c++) {
                double e = 0;
                for (int i = 1; i <= first.cols; i++) {
                    e += first.getElem(r, i) * second.getElem(i, c);
                }
                res.setElem(r, c, e);
            }
        }
        return res;
    }
    
    /**
     * Returns a transposed version of this matrix.
     * @return 
     */
    public Matrix transpose() {
        Matrix res = new Matrix(cols, rows);
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                res.setElem(j, i, getElem(i, j));
            }
        }
        return res;
    }
    
    /**
     * Returns a sub-matrix of this matrix, removing one row and one column.
     * @param row The row to remove.
     * @param col The column to remove.
     * @return the sub-matrix, or null if the sub-matrix is empty.
     */
    public Matrix subMat(int row, int col) {
        if (!isWithin(row, col)) {
            throw new IllegalArgumentException("Row or column is not within matrix");
        }
        
        if (rows - 1 <= 0 || cols - 1 <= 0) {
            return null;
        }
        
        Matrix res = new Matrix(rows - 1, cols - 1);
        if (res.rows == res.cols) {
            res = new SqMatrix(res.rows);
        }
        
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                int sr = r < row ? r : r + 1;
                int sc = c < col ? c : c + 1;
                res.setElem(r, c, getElem(sr, sc));
            }
        }
        
        return res;
    }
    
    /**
     * Sums all elements in a row.
     * @param row The number of the row to sum.
     * @return 
     */
    public double sumRow(int row) {
        if (!isWithin(row, 1)) {
            throw new IllegalArgumentException("Row is not within matrix");
        }
        
        double sum = 0;
        for (int col = 1; col <= cols; col++) {
            sum += getElem(row, col);
        }
        
        return sum;
    }
    
    /**
     * Sums all elements in a column.
     * @param col The number of the column to sum.
     * @return 
     */
    public double sumCol(int col) {
        if (!isWithin(1, col)) {
            throw new IllegalArgumentException("Column is not within matrix");
        }
        
        double sum = 0;
        for (int row = 1; row <= rows; row++) {
            sum += getElem(row, col);
        }
        
        return sum;
    }
    
    /**
     * Checks if this matrix is equal to another matrix. They are equal if and
     * only if their row and column count match, and each elements in this
     * matrix matches exactly the element in the same position in the other
     * matrix.
     * @param other
     * @return 
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Matrix)) {
            return false;
        }
        
        Matrix mat = (Matrix) other;
        
        if (mat.cols != cols || mat.rows != rows) {
            return false;
        }
        
        int i = 0;
        for (double e : elem) {
            if (Double.doubleToLongBits(e) != Double.doubleToLongBits(mat.elem[i++])) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Generates a hash code for this matrix object.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Arrays.hashCode(elem);
        hash = 37 * hash + rows;
        hash = 37 * hash + cols;
        return hash;
    }
    
    /**
     * Returns this matrix object as a string, including row- and column count
     * and hash code. <br>
     * Note: Does not include element values. To pretty-print a Matrix,
     * see libj.Util.printMatrix(Matrix m).
     * @return 
     * @see libj.Util#printMatrix(libj.math.Matrix) 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix[").append(rows).append("x").append(cols);
        sb.append("]@").append(Integer.toHexString(hashCode()));
        return sb.toString();
    }
}
