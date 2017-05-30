package execution;

import com.microsoft.z3.*;

/**
 * Created by mahsa on 3/3/17.
 */
public class isMax {

    Expr S;
    Expr effect;
    Expr relation;

    public isMax(Expr set, Expr effect, Expr relation){
        this.S=set;
        this.effect=effect;
        this.relation=relation;
    }



    public BoolExpr evaluation(Context ctx)  throws Z3Exception {

        IntExpr i = (IntExpr) ctx.mkConst("i", ctx.mkIntSort());

        Sort[] args = new Sort[1];
        args[0] = ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"));

        Symbol[] namess = new Symbol[1];
        namess[0] = ctx.mkSymbol("effect1");
        Expr effect1 = ctx.mkConst("effect1", ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")));

        Expr[] iArgs1 = new Expr[2];
        iArgs1[0] = effect1;
        iArgs1[1] = i;

        Expr irelation = application.idRelation.mkDecl().apply(iArgs1);

        IntExpr j = (IntExpr) ctx.mkConst("j", ctx.mkIntSort());
        ;
        Expr[] iArgs2 = new Expr[2];
        iArgs2[0] = effect;
        iArgs2[1] = j;

        Expr irelation2 = application.idRelation.mkDecl().apply(iArgs2);


        BoolExpr ismax = ctx.mkForall(args, namess, ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr)
                        ctx.mkSetMembership(effect, S), (BoolExpr) ctx.mkSetMembership(irelation2, relation), (BoolExpr) ctx.mkSetMembership(irelation, relation),
                        (BoolExpr) ctx.mkSetMembership(effect1, S)}),
             ctx.mkOr( new BoolExpr[]{(BoolExpr) ctx.mkEq(effect1, effect)  ,
                        (BoolExpr)ctx.mkGt(j, i)})), 1, null, null, null, null);
        return ismax;


    }

}
