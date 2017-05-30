package execution;

import analyzer.SequentialCheck;
import com.microsoft.z3.*;

import isolations.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahsa on 1/25/17.
 */
public class stableRI {

    public BoolExpr prove(Context ctx, relyRelation R, consistency I,  Expr effect_set1, Expr effect_set2) throws Z3Exception {




     //   Expr effect_set1 = ctx.mkConst("effect_set1",a.ff);
     //   Expr effect_set2 = ctx.mkConst("effect_set2",a.ff);

        effect_set1= ctx.mkConst("E1",  ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))));
        effect_set2= ctx.mkConst("E2", ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))));

        BoolExpr b1=(BoolExpr) I.get(ctx, effect_set1);

        BoolExpr b2=(BoolExpr) R.get(ctx, effect_set1,effect_set2 );

        BoolExpr b=ctx.mkAnd(new BoolExpr[]{b1,b2});

        BoolExpr result=(BoolExpr) I.get(ctx, effect_set2);

      //  BoolExpr stable =ctx.mkForall(args, names, ctx.mkImplies( b,
              //  result) , 1, null, null,  null, null);
        BoolExpr stable1=ctx.mkImplies( b,result);


        Sort[] args = new Sort[2];
        args[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("effect_set"));
        args[1] =ctx.mkUninterpretedSort(ctx.mkSymbol("effect_set"));
        //setting names
        Symbol[] namess = new Symbol[2];
        namess[0] =  ctx.mkSymbol("E1");
        namess[1] =  ctx.mkSymbol("E2");

        BoolExpr stable =ctx.mkForall(args, namess, stable1, 1, null, null,  null, null);

        return  stable;
    }


}
