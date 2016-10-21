package libj.math;


/**
 *
 * @author Magnus C. Hyll <magnus@hyll.no>
 */
public class Matrix {
    
    protected double[] elem;
    protected int rows;
    protected int cols;
    
    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows or cols can't be zero");
        }
        
        this.rows = rows;
        this.cols = cols;
        elem = new double[rows * cols];
    }
    
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
    
    public int rows() {
        return rows;
    }
    
    public int cols() {
        return cols;
    }
    
    public double[] getElements() {
        double[] elems = new double[rows * cols];
        System.arraycopy(elem, 0, elems, 0, elem.length);
        return elems;
    }
    
    public boolean isWithin(int row, int col) {
        return row > 0 && row <= rows
            && col > 0 && col <= cols;
    }
    
    public double getElem(int row, int col) {
        if (!isWithin(row, col)) {
            return 0;
        }
        
        return elem[(row - 1) * cols + (col - 1)];
    }
    
    public void setElem(int row, int col, double value) {
        if (!isWithin(row, col)) {
            return;
        }
        
        elem[(row - 1) * cols + (col - 1)] = value;
    }
    
    public void setAll(double[] elems) {
        if (elems.length < elem.length) {
            return;
        }
        
        System.arraycopy(elems, 0, elem, 0, elem.length);
    }
    
    public void mul(double c) {
        for (int i = 0; i < elem.length; i++) {
            elem[i] *= c;
        }
    }
    
    public void add(Matrix other) {
        if (other.rows != rows || other.cols != cols) {
            return;
        }
        
        for (int i = 0; i < elem.length; i++) {
            elem[i] += other.elem[i];
        }
    }
    
    public void sub(Matrix other) {
        if (other.rows != rows || other.cols != cols) {
            return;
        }
        
        for (int i = 0; i < elem.length; i++) {
            elem[i] -= other.elem[i];
        }
    }
    
    public Matrix mul(Matrix other) {
        if (cols != other.rows) {
            return null;
        }
        
        Matrix res = new Matrix(rows, other.cols);
        for (int r = 1; r <= res.rows; r++) {
            for (int c = 1; c <= res.cols; c++) {
                double e = 0;
                for (int i = 1; i <= cols; i++) {
                    e += getElem(r, i) * other.getElem(i, c);
                }
                res.setElem(r, c, e);
            }
        }
        return res;
    }
    
    public double det() {
        if (rows != cols) {
            throw new IllegalStateException("Non-square matrix");
        }
        
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
    
    public Matrix transpose() {
        Matrix res = new Matrix(cols, rows);
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                res.setElem(j, i, getElem(i, j));
            }
        }
        return res;
    }
    
    public double cof(int r, int c) {
        return ((c + r) % 2 == 0 ? 1 : -1) * subMat(r, c).det();
    }
    
    public Matrix subMat(int row, int col) {
        if (rows - 1 <= 0 || cols - 1 <= 0) {
            return null;
        }
        
        Matrix res = new Matrix(rows - 1, cols - 1);
        
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                int sr = r < row ? r : r + 1;
                int sc = c < col ? c : c + 1;
                res.setElem(r, c, getElem(sr, sc));
            }
        }
        
        return res;
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
        if (rows != cols) {
            throw new IllegalStateException("Non-square matrix");
        }
        
        elem = new double[rows * rows];
        for (int i = 0; i < rows * rows; i += rows + 1) {
            elem[i] = 1;
        }
    }
    
    public static Matrix identity(int dim) {
        Matrix res = new Matrix(dim, dim);
        for (int i = 0; i < dim * dim; i += dim + 1) {
            res.elem[i] = 1;
        }
        return res;
    }
}
