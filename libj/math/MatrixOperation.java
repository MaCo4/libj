package libj.math;

import libj.Util;

/**
 *
 * @author Magnus C. Hyll <magnus@hyll.no>
 */
public class MatrixOperation {
    
    private Matrix m;
    
    public MatrixOperation(Matrix input) {
        m = input;
    }    
    
    public Matrix gauss() {
        Matrix ret = m.copy();
        while (!isRowEchelon() && false) {
            
        }
        return ret;
    }
    
    private boolean isRowEchelon() {
        int prevPos = -1;
        for (int r = 1; r <= m.rows(); r++) {
            int thisPos = getPivotElemColPos(r);
            if (thisPos < 0) {
                continue;
            }
            if (thisPos <= prevPos) {
                return false;
            }
            prevPos = thisPos;
        }
        return true;
    }
    
    private int getPivotElemColPos(int row) {
        int pos = -1;
        for (int c = 1; c <= m.cols(); c++) {
            if (m.getElem(row, c) != 0) {
                pos = c;
                break;
            }
        }
        return pos;
    }
    
    public static void main(String[] args) {
        Matrix m = new Matrix(new double[][]{
            {3,6,8,4},
            {0,0,0,0},
            {0,7,0,8},
        });
        Util.printMatrix(m);
        MatrixOperation op = new MatrixOperation(m);
        Matrix g = op.gauss();
        System.out.println();
        Util.printMatrix(g);
        System.out.println();
        System.out.println(op.isRowEchelon());
    }
}
