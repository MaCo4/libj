package libj.math.transform.d3;

import libj.math.Const;
import libj.math.SqMatrix;

/**
 *
 * @author Magnus C. Hyll <magnus@hyll.no>
 */
public class RotationXMatrix extends SqMatrix {

    public RotationXMatrix(double angle) {
        super(4);
        loadIdentity();
        
        setElem(2, 2, Math.cos(angle * Const.degToRad));
        setElem(2, 3, -Math.sin(angle * Const.degToRad));
        setElem(3, 2, Math.sin(angle * Const.degToRad));
        setElem(3, 3, Math.cos(angle * Const.degToRad));
    }
    
}
