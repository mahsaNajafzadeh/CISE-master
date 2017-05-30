package execution;

import com.microsoft.z3.*;

/**
 * Created by mahsa on 3/3/17.
 */
public class PP {

    public Expr get(Context ctx, Expr effect_set) throws Z3Exception {
        //BoolExpr relation= (BoolExpr) ctx.mkConst("rely", ctx.mkBoolSort());

        FuncDecl consistency = ctx.mkFuncDecl("P", ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))), ctx.mkBoolSort());

        Expr relation =  ctx.mkApp(consistency, effect_set);

        return relation;
    }
}
