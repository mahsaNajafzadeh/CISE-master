package execution;

import com.microsoft.z3.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahsa on 1/24/17.
 */
public class decremenetT2  implements transaction {

    public  static BoolExpr Committed;
    public  static IntExpr a2;
    public static decremenetT1 t1;
    List<effect> effects=new ArrayList<>();
    public Expr transaction;

    decremenetT2(Context ctx, IntExpr a)  {

        IntExpr a1= (IntExpr) ctx.mkConst("value",ctx.mkIntSort());
        Committed= ctx.mkBoolConst("committed2");
        t1=new decremenetT1(ctx,a1);
        transaction=ctx.mkConst("T2", ctx.mkUninterpretedSort(ctx.mkSymbol("TRANSACTION")) );
    }

    public  Expr  setEffect(Context ctx, application app){

        effect read=new read(ctx, app.balanceObj);
        effect write=new write(ctx, app.balanceObj);
        effect commit=new commit();

        List<effect> list=new ArrayList();
        effects.add(read);
        effects.add(write);
        effects.add(commit);

        return null;
    }

    public  Expr  getEffect(){
        return null;

    }

    public BoolExpr preconidtion(Context ctx, application app)  throws Z3Exception {

        BoolExpr expr1=ctx.mkEq(app.balance,app.value)  ;
        BoolExpr expr2=ctx.mkEq(app.balance, ctx.mkSub(app.balance, decremenetT1.a1 ) ) ;

        return ctx.mkAnd(new BoolExpr[ ] { getResult(ctx), expr1, expr2 }) ;

    }

    public BoolExpr postcondition(Context ctx, application app)  throws Z3Exception {

        BoolExpr expr1=ctx.mkImplies(ctx.mkEq(t1.getResult(ctx), ctx.mkFalse()), ctx.mkEq(app.balance, ctx.mkSub(app.balance, a2) )  ) ;
        BoolExpr expr2=ctx.mkImplies(ctx.mkEq(t1.getResult(ctx), ctx.mkTrue()), ctx.mkEq(app.balance, ctx.mkSub(app.balance, ctx.mkSub(a2, t1.getValue(ctx)))) ) ;

       // return   ctx.mkEq(app.balance, ctx.mkSub(app.balance, a2)) ;

        return (BoolExpr) ctx.mkITE((BoolExpr) ctx.mkEq(t1.getResult(ctx),ctx.mkFalse()),
                ctx.mkEq(app.balance, ctx.mkSub(app.balance, a2)) ,
                 (ctx.mkEq(app.balance, ctx.mkSub(app.balance, ctx.mkSub(a2, t1.getValue(ctx))))) );

      //  return  ctx.mkOr(new BoolExpr[]{ expr1, expr2}) ;

    }

    public List<transaction> concurrentTrans(Context ctx) {

        IntExpr a1= (IntExpr) ctx.mkConst("value",ctx.mkIntSort());
        t1=new decremenetT1(ctx,a1);
        List<transaction> trans=new ArrayList<transaction>();
        trans.add(t1);

        return trans;
    }

    @Override
    public BoolExpr getResult(Context ctx) {
        this.Committed=(BoolExpr) ctx.mkConst("committed2",ctx.mkBoolSort());;
        return  Committed;
    }

    public IntExpr getValue(Context ctx) {
        a2= (IntExpr) ctx.mkConst("value2",ctx.mkIntSort());
        return  a2;
    }
    public void settrans(Context ctx, application a) {
        a.transation_set=ctx.mkSetAdd(a.transation_set, transaction);
    }

    public Expr gettrans() {
        return  this.transaction;
    }
}
