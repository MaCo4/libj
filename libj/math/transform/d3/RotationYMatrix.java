package libj.math.transform.d3;

import libj.math.Const;
import libj.math.SqMatrix;

/**
 *
 * @author Magnus C. Hyll <magnus@hyll.no>
 */
public class RotationYMatrix extends SqMatrix {

    public RotationYMatrix(double angle) {
        super(4);
        loadIdentity();
        
        setElem(1, 1, Math.cos(angle * Const.degToRad));
        setElem(1, 3, Math.sin(angle * Const.degToRad));
        setElem(3, 1, -Math.sin(angle * Const.degToRad));
        setElem(3, 3, Math.cos(angle * Const.degToRad));
    }
    
}
