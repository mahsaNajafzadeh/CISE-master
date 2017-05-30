package execution;

import com.microsoft.z3.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by mahsa on 3/5/17.
 *
 */
public class relyRelation {
    Expr effect_set1;
    Expr effect_set2;

    public relyRelation(Expr E1, Expr E2){
        this.effect_set1=E1;
        this.effect_set2=E2;
    }

    public Expr get(Context ctx, Expr effect_set1, Expr effect_set2) throws Z3Exception {

        FuncDecl rely = ctx.mkFuncDecl("R", new Sort []{ ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))),
                        ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")))},
                ctx.mkBoolSort());

        Expr relation =  ctx.mkApp(rely, effect_set1, effect_set2);
        return relation;
    }


    public Expr getapp(Context ctx, Expr effect_set1, Expr effect_set2, application a) throws Z3Exception {

        Expr t1=a.transactions.get(0).gettrans();

        Expr[] arg =  new Expr[2];
        arg[0]=effect_set1;
        arg[1]=t1 ;

        Expr tt=  application.commitRelation.mkDecl().apply(arg);

        Expr ee3=ctx.mkNot((BoolExpr) ctx.mkSetMembership(tt, a.commitRelation_set));

        Expr t2=a.transactions.get(1).gettrans();

        Expr[] arg1 =  new Expr[2];
        arg1[0]=effect_set2;
        arg1[1]=t2 ;

        Expr tt1=  application.commitRelation.mkDecl().apply(arg1);

        Expr ee4=ctx.mkSetMembership(tt1, a.commitRelation_set);

        Expr e3=ctx.mkEq(a.balance, ctx.mkSub(a.balance, a.transactions.get(1).getValue(ctx)));

        Expr[] arg2 =  new Expr[2];
        arg2[0]=t2;
        arg2[1]= a.balance;

        Expr writet2=  application.typetransRelation.mkDecl().apply(arg2);

        Expr e4=ctx.mkSetMembership(writet2, a.typetrans_set);


        Expr r1=ctx.mkImplies(ctx.mkAnd(new BoolExpr[] {(BoolExpr) ee3,(BoolExpr)ee4}), ctx.mkAnd(new BoolExpr[]{(BoolExpr) e4,  ctx.mkEq(a.balance, ctx.mkSub(a.balance,a.transactions.get(1).getValue(ctx)))}));

        Expr bb1= ctx.mkSetMembership(tt, a.commitRelation_set);

        Expr r2=ctx.mkImplies(ctx.mkAnd(new BoolExpr[] {(BoolExpr) bb1,(BoolExpr)ee4}), ctx.mkAnd(new BoolExpr[]{(BoolExpr) e4,  ctx.mkEq(a.balance, ctx.mkSub(a.balance,a.transactions.get(1).getValue(ctx),
                a.transactions.get(0).getValue(ctx) ))}));


        return ctx.mkAnd(new BoolExpr[] {(BoolExpr) r2, (BoolExpr)r1});

    }

}
