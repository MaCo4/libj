package libj.math.transform.d3;

import libj.math.Const;
import libj.math.SqMatrix;

/**
 *
 * @author Magnus C. Hyll <magnus@hyll.no>
 */
public class RotationZMatrix extends SqMatrix {
    
    public RotationZMatrix(double angle) {
        super(4);
        loadIdentity();
        
        setElem(1, 1, Math.cos(angle * Const.degToRad));
        setElem(1, 2, -Math.sin(angle * Const.degToRad));
        setElem(2, 1, Math.sin(angle * Const.degToRad));
        setElem(2, 2, Math.cos(angle * Const.degToRad));
    }
    
}
