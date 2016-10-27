package libj.math;

/**
 *
 * @author Magnus C. Hyll <magnus@hyll.no>
 */
public class SqMatrix extends Matrix {

    public SqMatrix(int dim) {
        super(dim, dim);
    }
    
    public double cof(int r, int c) {
        return ((c + r) % 2 == 0 ? 1 : -1) * ((SqMatrix) subMat(r, c)).det();
    }
    
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
    
    public void loadIdentity() {
        elem = new double[rows * rows];
        for (int i = 0; i < rows * rows; i += rows + 1) {
            elem[i] = 1;
        }
    }
    
    public static SqMatrix identity(int dim) {
        SqMatrix res = new SqMatrix(dim);
        for (int i = 0; i < dim * dim; i += dim + 1) {
            res.elem[i] = 1;
        }
        return res;
    }
}
