package execution.stateRule.Bank;

import com.microsoft.z3.*;
import execution.stateRule.*;

import java.util.List;

/**
 * Created by mahsa on 5/26/17.
 */
public class bank implements application {

    public static TupleSort counter;
    public static Sort Object;
    public static Expr balance;
    public static Expr id;
    public static List<Transaction> tList;
    static Transaction debit;

    @Override
    public void initialize_state(Context ctx) {
        Object = ctx.mkUninterpretedSort(ctx.mkSymbol("Object"));
        counter = ctx.mkTupleSort(ctx.mkSymbol("mk_Parent_tuple"), // name of
                new Symbol[]{ctx.mkSymbol("object"), ctx.mkSymbol("value"), ctx.mkSymbol("version")}, // names
                new Sort[]{ctx.mkUninterpretedSort(ctx.mkSymbol("Object")), ctx.mkIntSort(), ctx.mkIntSort()});
        id = ctx.mkConst("c", ctx.mkUninterpretedSort(ctx.mkSymbol("Object")));

        Expr[] index = new Expr[3];
        index[0] = id;
        index[1] = ctx.mkConst("c", ctx.mkIntSort());
        index[2] = ctx.mkInt("0");

        balance = counter.mkDecl().apply(index);

        Expr value=ctx.mkConst("value", ctx.mkIntSort());
        Expr inv=invariant(ctx);

        debit=new Debit(value, balance.getArgs()[1], ctx, inv);



    }

    @Override
    public BoolExpr invariant(Context ctx) {
        return ctx.mkGe((ArithExpr) balance.getArgs()[1],ctx.mkInt("0"));
    }

    public static void main(String[] args) {

        Context ctx=new Context();
        bank app=new bank();
        app.initialize_state(ctx);

        rgTXN ri=new rgTXN();
        ri.prove(app.debit,ctx,app, "SER");
    }
}
