package execution;

import com.microsoft.z3.*;

/**
 * Created by mahsa on 3/6/17.
 */
public class SX {

    Expr S;
    Expr effect;
    Expr relation;
    Expr type_set;
    Expr obj;
    Expr id_set;

    public SX(Expr set, Expr relation, Expr type_set, Expr obj, Expr id_set){
        this.S=set;
        this.relation=relation;
        this.type_set=type_set;
        this.obj=obj;
        this.id_set=id_set;
    }

    public Expr evaluation(Context ctx)  throws Z3Exception {

        Sort[] args = new Sort[1];
        args[0] = ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"));

        Symbol[] namess = new Symbol[1];
        namess[0] = ctx.mkSymbol("effect11");


        Expr effect1 = ctx.mkConst("effect11", ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")));


        Expr rval=ctx.mkConst("v", ctx.mkIntSort());
        Expr[] rArgs =  new Expr[2];
        rArgs[0]=effect1;
        rArgs[1]= rval;

        Expr rrelation=  application.ValueRelation.mkDecl().apply(rArgs);

        Expr ee5=ctx.mkSetMembership(rrelation, relation);

      //  Expr obj= ctx.mkConst("x", ctx.mkUninterpretedSort(ctx.mkSymbol("object")));

        Expr[] arg =  new Expr[3];
        arg[0]=effect1;
        arg[1]= application.type.getConst(0);
        arg[2]= obj;

        Expr type=  application.typeRelation.mkDecl().apply(arg);

        Expr ee3=ctx.mkSetMembership(type,type_set);

        IntExpr i = (IntExpr) ctx.mkConst("i", ctx.mkIntSort());
        IntExpr j = (IntExpr) ctx.mkConst("j", ctx.mkIntSort());

        Expr[] iArgs1 = new Expr[2];
        iArgs1[0] = effect1;
        iArgs1[1] = j;

        Expr irelation = application.idRelation.mkDecl().apply(iArgs1);

        BoolExpr e1=ctx.mkExists(args, namess, ctx.mkAnd(new BoolExpr[]{(BoolExpr) ctx.mkSetMembership(effect1,S ), (BoolExpr) ee5, (BoolExpr) ee3
       , (BoolExpr) ctx.mkSetMembership(irelation, id_set) }), 1, null, null, null, null);


        Expr effect2 = ctx.mkConst("effect22", ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")));

        Sort[] args1 = new Sort[1];
        args1[0] = ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"));

        Symbol[] namess1 = new Symbol[1];
        namess1[0] = ctx.mkSymbol("effect22");


        Expr[] arg1 =  new Expr[3];
        arg1[0]=effect2;
        arg1[1]= application.type.getConst(1);
        arg1[2]= obj;

        Expr type1=  application.typeRelation.mkDecl().apply(arg);

        Expr ee4=ctx.mkSetMembership(type1,type_set);




        ;
        Expr[] iArgs2 = new Expr[2];
        iArgs2[0] = effect2;
        iArgs2[1] = i;

        Expr irelation2 = application.idRelation.mkDecl().apply(iArgs2);


        Expr[] arg11 =  new Expr[3];
        arg11[0]=effect2;
        arg11[1]= application.type.getConst(0);
        arg11[2]= obj;

        Expr type11=  application.typeRelation.mkDecl().apply(arg11);



        BoolExpr e2 = ctx.mkForall(args1, namess1, ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr)
                        ctx.mkSetMembership(effect2, S), (BoolExpr) ctx.mkSetMembership(type11, type_set), (BoolExpr) ctx.mkSetMembership(irelation2, id_set)}),
                        (BoolExpr)ctx.mkGt(j, i)), 1, null, null, null, null);

        BoolExpr e=ctx.mkAnd(new BoolExpr[] {e1,e2});


        Expr result=ctx.mkITE(e,rval, ctx.mkInt(0));

        return result;


    }
}
