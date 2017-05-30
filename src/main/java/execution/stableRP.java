package execution;

import com.microsoft.z3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mahsa on 2/2/17.
 */
public class stableRP {

    public BoolExpr prove(Context ctx, PP P, relyRelation R, consistency I,  Expr effect_set1, Expr effect_set2) throws Z3Exception {


     //   effect_set1= ctx.mkConst("E1", ctx.mkUninterpretedSort(ctx.mkSymbol("effect_set")));
     //   effect_set2= ctx.mkConst("E2", ctx.mkUninterpretedSort(ctx.mkSymbol("effect_set")));

        BoolExpr b1=(BoolExpr) I.get(ctx, effect_set1);

        BoolExpr b3=(BoolExpr) P.get(ctx, effect_set1);

        BoolExpr b2=(BoolExpr) R.get(ctx, effect_set1,effect_set2 );

        BoolExpr b4=(BoolExpr) I.get(ctx, effect_set2 );

        BoolExpr b=ctx.mkAnd(new BoolExpr[]{b1,b2,b3,b4});

        BoolExpr result=(BoolExpr) P.get(ctx, effect_set2);

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
