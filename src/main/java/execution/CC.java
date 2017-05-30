package execution;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Z3Exception;

/**
 * Created by mahsa on 3/6/17.
 */
public class CC {
    public Expr get(Context ctx, Expr rval) throws Z3Exception {
        //BoolExpr relation= (BoolExpr) ctx.mkConst("rely", ctx.mkBoolSort());

        FuncDecl assertion = ctx.mkFuncDecl("C", ctx.mkIntSort(), ctx.mkBoolSort());

        Expr relation =  ctx.mkApp(assertion, rval);

        return relation;
    }
}
