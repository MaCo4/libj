package libj.math.transform.d2;

import libj.math.Const;
import libj.math.SqMatrix;

/**
 *
 * @author Magnus C. Hyll <magnus@hyll.no>
 */
public class RotationMatrix extends SqMatrix {
    
    public RotationMatrix(double angle) {
        
        super(3);
        loadIdentity();
        
        setElem(1, 1, Math.cos(angle * Const.piover180));
        setElem(1, 2, -Math.sin(angle * Const.piover180));
        setElem(2, 1, Math.sin(angle * Const.piover180));
        setElem(2, 2, Math.cos(angle * Const.piover180));
    }
    
}
