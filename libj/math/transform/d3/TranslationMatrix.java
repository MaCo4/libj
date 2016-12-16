package libj.math.transform.d3;

import libj.math.SqMatrix;

/**
 * Represents a translation matrix in 3-dimension space.
 * @author Magnus C. Hyll <magnus@hyll.no>
 */
public class TranslationMatrix extends SqMatrix {

    public TranslationMatrix(double x, double y, double z) {
        super(4);
        loadIdentity();
        
        setElem(1, 4, x);
        setElem(2, 4, y);
        setElem(3, 4, z);
    }
    
}
