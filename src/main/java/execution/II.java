package execution;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;

/**
 * Created by mahsa on 3/3/17.
 */
public class II {

    public BoolExpr prove(Context ctx, Expr E) throws Z3Exception {
        return  ctx.mkTrue();
    }
}
