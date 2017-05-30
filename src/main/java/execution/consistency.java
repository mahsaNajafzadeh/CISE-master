package execution;

import com.microsoft.z3.*;
import isolations.AtomicVis;
import isolations.isolation;

/**
 * Created by mahsa on 3/5/17.
 */
public class consistency {

    Expr effect_set;

    public Expr get(Context ctx, Expr effect_set) {
        //BoolExpr relation= (BoolExpr) ctx.mkConst("rely", ctx.mkBoolSort());

        FuncDecl consistency = ctx.mkFuncDecl("I", ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))), ctx.mkBoolSort());

        Expr relation =  ctx.mkApp(consistency, effect_set);

        return relation;
    }

    public Expr getapp(Context ctx, Expr effect_set, application app) {

      //  isolation i1=new AtomicVis();
      //  isolation i2=new AtomicVis();

        Expr t1=app.transactions.get(0).gettrans();
        Expr t2=app.transactions.get(1).gettrans();
     //   i1.setTransaction(app.transactions.get(0));
    //    i2.setTransaction(app.transactions.get(1));

     //   i1.getIsolation(ctx,effect_set);

     //   Expr relation =  ctx.mkApp(consistency, effect_set);

        return null;
    }

}
