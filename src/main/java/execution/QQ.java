package execution;

import com.microsoft.z3.*;
import java.util.Random;
/**
 * Created by mahsa on 3/3/17.
 */
public class QQ {

    FuncDecl consistency ;

    public QQ(int index, Context ctx) {

        consistency = ctx.mkFuncDecl("Q"+index, ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))), ctx.mkBoolSort());
    }
    public Expr get(Context ctx, Expr effect_set) throws Z3Exception {
        //BoolExpr relation= (BoolExpr) ctx.mkConst("rely", ctx.mkBoolSort());

        Expr relation =  ctx.mkApp(consistency, effect_set);

        return relation;
    }

    public Expr getapp(Context ctx, Expr effect_set, application a) throws Z3Exception {
        //BoolExpr relation= (BoolExpr) ctx.mkConst("rely", ctx.mkBoolSort());

        Expr e1=a.transactions.get(0).gettrans();
        Expr e2=a.transactions.get(1).gettrans();
        Expr e3=ctx.mkEq(a.balance, ctx.mkSub(a.balance, a.transactions.get(0).getValue(ctx), a.transactions.get(1).getValue(ctx)));

        Expr[] arg =  new Expr[2];
        arg[0]=effect_set;
        arg[1]=e1 ;

        Expr tt=  application.commitRelation.mkDecl().apply(arg);

        Expr ee3=ctx.mkSetMembership( tt, a.commitRelation_set);

        Expr t2=a.transactions.get(1).gettrans();

        Expr[] arg1 =  new Expr[2];
        arg1[0]=effect_set;
        arg1[1]=e2 ;

        Expr tt1=  application.commitRelation.mkDecl().apply(arg1);

        Expr ee4=ctx.mkSetMembership( tt1, a.commitRelation_set);


        Expr relation =  ctx.mkAnd(new BoolExpr[]{(BoolExpr) ee3,(BoolExpr) ee4, (BoolExpr)e3});


        return relation;
    }

}
