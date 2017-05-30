package execution;

import com.microsoft.z3.*;

/**
 * Created by mahsa on 3/15/17.
 */
public class RGTXN {

    Expr R;
    Expr I;
    Expr P;
    Expr Q1;
    Expr Q2;
    application a;


    Expr effect;

    BoolExpr prove(Context ctx, PP P, QQ Q1, QQ Q2, relyRelation R, GG G, consistency I, CC c, Expr effect_set) throws Z3Exception {


        IntExpr j=(IntExpr) ctx.mkConst("j",ctx.mkIntSort());


//        Expr b1=ctx.mkSetSubset(a.effect_set, effect_set);

        IntExpr i=(IntExpr) ctx.mkConst("i",ctx.mkIntSort());


        effect=ctx.mkConst("effect", ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")) );

        isMax ismax=new isMax(effect_set,effect, a.idRelation_set);

        Expr b2=ismax.evaluation(ctx);

        Expr b3=(BoolExpr) Q2.get(ctx, effect_set);



        Expr ee1=ctx.mkSetMembership(effect, a.effect_set);

        Expr[] iArgs2 =  new Expr[2];
        iArgs2[0]=effect;
        iArgs2[1]= j;

        Expr irelation2=  application.idRelation.mkDecl().apply(iArgs2);
        Expr ee2=ctx.mkSetMembership(irelation2, a.idRelation_set);


        Expr obj= ctx.mkConst("null", ctx.mkUninterpretedSort(ctx.mkSymbol("object")));
        Expr[] arg =  new Expr[3];
        arg[0]=effect;
        arg[1]= application.type.getConst(2);
        arg[2]= obj;

        Expr type=  application.typeRelation.mkDecl().apply(arg);

        Expr ee3=ctx.mkSetMembership(type, a.type_set);


        Expr[] pArgs =  new Expr[2];
        pArgs[0]=effect;
        pArgs[1]= ctx.mkConst("T", ctx.mkUninterpretedSort(ctx.mkSymbol("TRANSACTION")));;

        Expr prelation=  application.parent.mkDecl().apply(pArgs);


        Expr ee4=ctx.mkSetMembership(prelation, a.parent_set);

        Expr S=ctx.mkConst("S", ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))));
        Expr b1=ctx.mkSetSubset(S, effect_set);

    //    SX sx=new SX(S,a.ValueRelation_set, a.type_set, obj, a.idRelation_set);
     //   Expr rval= sx.evaluation(ctx);
        //ctx.mkConst("rval", ctx.mkIntSort());
    //    Expr rval=ctx.mkTrue();
     //   Expr[] rArgs =  new Expr[2];
     //   rArgs[0]=effect;
      //  rArgs[1]= rval;

      //  Expr rrelation=  application.ValueRelation.mkDecl().apply(rArgs);

        //Expr ee5=ctx.mkSetMembership(rrelation, a.ValueRelation_set);

        Expr effect_set2= ctx.mkSetAdd(effect_set, effect);

        Expr b4=(BoolExpr) I.get(ctx, effect_set2);

        BoolExpr b=ctx.mkAnd(new BoolExpr[] {(BoolExpr) b1, (BoolExpr) b2,(BoolExpr) b3, (BoolExpr) b4, (BoolExpr) ee1,(BoolExpr) ee2,(BoolExpr) ee3, (BoolExpr) ee4});

        Expr r1=Q1.get(ctx, effect_set2);
        Expr r2=G.get(ctx, effect_set, effect_set2);
       // Expr r3=c.get(ctx,rval);
        BoolExpr r=ctx.mkAnd(new BoolExpr[] {(BoolExpr) r1,(BoolExpr) r2});

        Expr e=ctx.mkImplies(b, r);


        Sort[] args = new Sort[4];
        args[0] =ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")));
        args[1] =ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")));
        args[2] =ctx.mkIntSort();
        args[3] =ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"));

        //setting names
        Symbol[] namess = new Symbol[4];
        namess[0] =  ctx.mkSymbol("E1");
        namess[1] =  ctx.mkSymbol("S");
        namess[2] =  ctx.mkSymbol("j");
        namess[3] =ctx.mkSymbol("effect");

        BoolExpr stable =ctx.mkForall(args, namess, e, 1, null, null,  null, null);

        return  stable;

    }

}
