package execution.stateRule;

import com.microsoft.z3.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/22/17.
 */
public class rgAssign {


    public void prove(List<Transaction> tList, Context ctx, String str, application a) {
        for (int j = 0; j < tList.size(); j++) {
            Transaction t = tList.get(j);
            System.out.println("size" + t.getOps(ctx,a.invariant(ctx)).size());
            for (int i = 0; i < t.getOps(ctx,a.invariant(ctx)).size(); i++) {
                Operation o = t.getOps(ctx,a.invariant(ctx)).get(i);
                if (o.getType() == "assignment") {

                    Expr e1 = o.getP(ctx);
                    Expr e2 = o.applyEffect(ctx);
                    Expr e3 = o.getQ(ctx);

                    BoolExpr stable1 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) e1, (BoolExpr) e2}), (BoolExpr) e3);
                    System.out.println("stable1:" + stable1);
                    Solver s = ctx.mkSolver();

                    StableRQ rs = new StableRQ(o, str);
                    BoolExpr stable2 = rs.prove(ctx);
                    System.out.println("stable2:" + stable2);

                    StableRP rp = new StableRP(o, str);
                    BoolExpr stable3 = rp.prove(ctx);
                    System.out.println("stable3:" + stable3);

                    BoolExpr st = ctx.mkAnd(new BoolExpr[]{stable1, stable2, stable3});
                    System.out.println("st:" + st);
                    s.add(ctx.mkNot(st));
                    Status status = s.check();
                    if (status == Status.SATISFIABLE)
                        System.out.println("model" + s.getModel());
                    System.out.println("status:" + status);
                }
            }

        }
    }

    public static void prove(Operation o, Context ctx, String str) {

        Expr e1 = o.getP(ctx);
        Expr e2 = o.applyEffect(ctx);
        Expr e3 = o.getQ(ctx);

        BoolExpr stable1 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) e1, (BoolExpr) e2}), (BoolExpr) e3);
        System.out.println("stable1:" + stable1);
        Solver s = ctx.mkSolver();

        StableRQ rs = new StableRQ(o, str);
        BoolExpr stable2 = rs.prove(ctx);
        System.out.println("stable2:" + stable2);

        StableRP rp = new StableRP(o, "SER");
        BoolExpr stable3 = rp.prove(ctx);
        System.out.println("stable3:" + stable3);

        BoolExpr st = ctx.mkAnd(new BoolExpr[]{stable1, stable2, stable3});
        System.out.println("st:" + st);
        s.add(ctx.mkNot(st));
        Status status = s.check();
        if (status == Status.SATISFIABLE)
            System.out.println("model" + s.getModel());
        System.out.println("status:" + status);
    }

    public static BoolExpr getprove(Operation o, Context ctx, String str) {
        System.out.println("prove for Operation:   "+o);
        Expr e1 = o.getP(ctx);
        System.out.println("e111"+e1);
        Expr e2 = o.applyEffect(ctx);
        System.out.println("e222"+e2);
        Expr e3 = o.getQ(ctx);
        System.out.println("e333"+e3);

        BoolExpr stable1 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) e1, (BoolExpr) e2}), (BoolExpr) e3);
        System.out.println("stable1:" + stable1);
        Solver s = ctx.mkSolver();

        StableRQ rs = new StableRQ(o, str);
        BoolExpr stable2 = rs.prove(ctx);
        System.out.println("stable2:" + stable2);

        StableRP rp = new StableRP(o, str);
        BoolExpr stable3 = rp.prove(ctx);
        System.out.println("stable3:" + stable3);

        BoolExpr st = ctx.mkAnd(new BoolExpr[]{stable1, stable2, stable3});
        System.out.println("st:" + st);
        return st;
    }


}
