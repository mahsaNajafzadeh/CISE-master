package execution;

import com.microsoft.z3.*;
import evaluation.auction.operations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahsa on 1/24/17.
 */
public class decremenetT1 implements transaction {

    public static BoolExpr  Committed;
    public static IntExpr a1;
    public  Expr effect_set;
    public static TupleSort effectObj;
    public static Expr effectObj_set;
    public  Expr transaction;

    List<transaction> concurrentTrans;
    static decremenetT2 t1;
    List<effect> effects=new ArrayList();;
    decremenetT1(Context ctx, IntExpr a)  {
        Committed=ctx.mkBoolConst("committed1");
        a1= (IntExpr) ctx.mkConst("value1",ctx.mkIntSort());
        effectObj= ctx.mkTupleSort(ctx.mkSymbol("mk_session_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")  }, // names
                new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")), ctx.mkUninterpretedSort(ctx.mkSymbol("object"))});
        Sort eo=ctx.mkSetSort(effectObj);
        effectObj_set = ctx.mkConst("effectObj_setT1",eo);
        transaction=ctx.mkConst("T1", ctx.mkUninterpretedSort(ctx.mkSymbol("TRANSACTION")) );
    }

    public  Expr  setEffect(Context ctx, application app){

        effect read=new read(ctx, app.balanceObj);
        effect write=new write(ctx, app.balanceObj);
        effect commit=new commit();

        Expr e1=ctx.mkConst("read", ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")));
        Expr e2=ctx.mkConst("write", ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")));
        Expr e3=ctx.mkConst("commit", ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")));

        app.effect_set=ctx.mkSetAdd(app.effect_set,e1);
        app.effect_set=ctx.mkSetAdd(app.effect_set,e2);
        app.effect_set=ctx.mkSetAdd(app.effect_set,e3);

        Sort ff=ctx.mkSetSort(app.effect);
        effect_set = ctx.mkConst("effect_setT1",ff);
        effect_set=ctx.mkSetAdd(effect_set,e1);
        effect_set=ctx.mkSetAdd(effect_set,e2);
        effect_set=ctx.mkSetAdd(effect_set,e3);

        Expr[] objArgs =  new Expr[2];
        objArgs[0]=e1;
        objArgs[1]= app.balanceObj;

        Expr objj=  effectObj.mkDecl().apply(objArgs);

        effectObj_set=ctx.mkSetAdd(effectObj_set,objj);

        Expr[] objArgs1 =  new Expr[2];
        objArgs1[0]=e2;
        objArgs1[1]= app.balanceObj;

        Expr objj1=  effectObj.mkDecl().apply(objArgs1);
        effectObj_set=ctx.mkSetAdd(effectObj_set,objj1);


        List<effect> list=new ArrayList();
        effects.add(read);
        effects.add(write);
        effects.add(commit);


        Expr[] parArgs1 =  new Expr[2];
        parArgs1[0]=e3;
        parArgs1[1]= this.transaction;

        Expr par1=  application.parent.mkDecl().apply(parArgs1);

        Expr[] parArgs2 =  new Expr[2];
        parArgs2[0]=e1;
        parArgs2[1]= this.transaction;

        Expr par2=  application.parent.mkDecl().apply(parArgs2);

        Expr[] parArgs3 =  new Expr[2];
        parArgs3[0]=e2;
        parArgs3[1]= this.transaction;

        Expr par3=  application.parent.mkDecl().apply(parArgs3);


        application.parent_set=ctx.mkSetAdd(  application.parent_set,par1);
        application.parent_set=ctx.mkSetAdd(  application.parent_set,par2);
        application.parent_set=ctx.mkSetAdd(  application.parent_set,par3);

        settrans( ctx,  app);
        return effect_set;
    }

    public  Expr  getEffect(){
        return this.effect_set;
    }

    public BoolExpr preconidtion(Context ctx, application app)  throws Z3Exception {


        IntExpr a2= (IntExpr) ctx.mkConst("value",ctx.mkIntSort());
        transaction t1=new decremenetT2(ctx,a2);

        BoolExpr expr1=ctx.mkImplies(ctx.mkEq(t1.getResult(ctx), ctx.mkFalse()),ctx.mkEq(app.balance,app.balance)  );
        BoolExpr expr2=ctx.mkImplies(ctx.mkEq(t1.getResult(ctx), ctx.mkTrue()),ctx.mkEq(app.balance, ctx.mkSub(app.balance,t1.getValue(ctx) ) ) );

        return ctx.mkAnd(new BoolExpr[ ] { ctx.mkNot(this.getResult(ctx)), ctx.mkOr(new BoolExpr[]{expr1, expr2 })}) ;
    }

    public BoolExpr postcondition(Context ctx, application app)  throws Z3Exception {

        IntExpr a2= (IntExpr) ctx.mkConst("value",ctx.mkIntSort());
        transaction t1=new decremenetT2(ctx,a2);

       BoolExpr expr1=ctx.mkImplies(ctx.mkEq(t1.getResult(ctx), ctx.mkFalse()), ctx.mkEq(app.balance, ctx.mkSub(app.balance, a1) )  ) ;
       BoolExpr expr2=ctx.mkImplies(ctx.mkEq(t1.getResult(ctx), ctx.mkTrue()), ctx.mkEq( app.balance, ctx.mkSub(app.balance, ctx.mkSub(a1, t1.getValue(ctx)))  ));

        return (BoolExpr) ctx.mkITE((BoolExpr) ctx.mkEq(t1.getResult(ctx),ctx.mkFalse()),
                ctx.mkEq(app.balance, ctx.mkSub(app.balance, a1)) ,
                (ctx.mkEq(app.balance, ctx.mkSub(app.balance, ctx.mkSub(a1, t1.getValue(ctx))))) );



       // return ctx.mkOr(new BoolExpr[ ] { expr1, expr2 }) ;

    }

    public List<transaction> concurrentTrans(Context ctx) {

        IntExpr a2= (IntExpr) ctx.mkConst("value",ctx.mkIntSort());
        t1=new decremenetT2(ctx,a2);
        List<transaction> trans=new ArrayList<transaction>();
        trans.add(t1);

        return trans;
    }

    public BoolExpr getResult(Context ctx) {
       // Committed=ctx.mkBoolConst("committed1");
        this.Committed=(BoolExpr) ctx.mkConst("committed1",ctx.mkBoolSort());;
        return  Committed;
    }

    public IntExpr getValue(Context ctx) {
        a1= (IntExpr) ctx.mkConst("value1",ctx.mkIntSort());
        return  a1;
    }

    public void settrans(Context ctx, application a) {
       a.transation_set=ctx.mkSetAdd(a.transation_set, transaction);
    }

    public Expr gettrans() {
        return  this.transaction;
    }

    public Expr getarg() {
        return  this.a1;
    }

}
