package libj.math;

/**
 * A n x n square matrix holding real values. Extends Matrix with methods only
 * applicable to square matrices.
 * @author Magnus C. Hyll <magnus@hyll.no>
 * @see Matrix
 */
public class SqMatrix extends Matrix {

    /**
     * Creates a new matrix with the given dimension, that is, the number of
     * rows and columns.
     * @param dim 
     */
    public SqMatrix(int dim) {
        super(dim, dim);
    }
    
    /**
     * Calculates the coefficient for the element in the given position.
     * @param r
     * @param c
     * @return 
     */
    public double cof(int r, int c) {
        return ((c + r) % 2 == 0 ? 1 : -1) * ((SqMatrix) subMat(r, c)).det();
    }
    
    /**
     * Calculates the determinant of this matrix.
     * @return 
     */
    public double det() {
        if (rows == 1) {
            return getElem(1, 1);
        }
        
        else if (rows == 2) {
            return getElem(1, 1) * getElem(2, 2)
                 - getElem(2, 1) * getElem(1, 2);
        }
        
        double det = 0;
        for (int c = 1; c <= cols; c++) {
            det += getElem(1, c) * cof(1, c);
        }
        
        return det;
    }
    
    /**
     * Generates an inverse matrix of this matrix.
     * @return 
     */
    public Matrix inv() {
        if (det() == 0) {
            return null;
        }
        
        Matrix res = new Matrix(rows, cols);
        double detInv = 1 / det();
        
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                res.setElem(r, c, cof(r, c) * detInv);
            }
        }
        return res;
    }
    
    /**
     * Sets this matrix to the identity matrix of the same dimension.
     */
    public void loadIdentity() {
        elem = new double[rows * rows];
        for (int i = 0; i < rows * rows; i += rows + 1) {
            elem[i] = 1;
        }
    }
    
    /**
     * Creates and returns a new identity matrix of the given dimension.
     * @param dim
     * @return 
     */
    public static SqMatrix identity(int dim) {
        SqMatrix res = new SqMatrix(dim);
        res.loadIdentity();
        return res;
    }
}
