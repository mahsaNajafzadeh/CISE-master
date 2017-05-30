package execution;

import com.microsoft.z3.*;

/**
 * Created by mahsa on 3/3/17.
 */
public class GG {
    Expr effect_set1;
    Expr effect_set2;

    public GG(Expr E1, Expr E2){
        this.effect_set1=E1;
        this.effect_set2=E2;
    }

    public Expr get(Context ctx, Expr effect_set1, Expr effect_set2) throws Z3Exception {

        FuncDecl gaurantee = ctx.mkFuncDecl("GG", new Sort []{ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))),
                        ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")))},
                ctx.mkBoolSort());

        Expr relation =  ctx.mkApp(gaurantee, effect_set1, effect_set2);
        return relation;
    }

}
