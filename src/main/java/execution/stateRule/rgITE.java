package execution.stateRule;

import com.microsoft.z3.*;

import java.util.List;

/**
 * Created by mahsa on 5/22/17.
 */
public class rgITE {

    public static void prove(List<Transaction> tList, Context ctx, Expr inv) {
        for (int j = 0; j < tList.size(); j++) {
            Transaction t = tList.get(j);
            System.out.println("size" + t.getOps(ctx, inv).size());
            for (int i = 0; i < t.getOps(ctx,inv).size(); i++) {
                Operation o = t.getOps(ctx,inv).get(i);
                if (o.getType() == "ITE") {


                    o.geto1().putP(ctx.mkAnd(new BoolExpr[]{o.getP(ctx), (BoolExpr) o.getCond()}));
                    o.geto2().putP(ctx.mkAnd(new BoolExpr[]{o.getP(ctx), ctx.mkNot((BoolExpr) o.getCond())}));

                    Expr e1 = o.geto1().getP(ctx);
                    Expr e2 = o.geto1().applyEffect(ctx);
                    Expr e3 = o.geto1().getQ(ctx);

                 //   BoolExpr stable1 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) e1, (BoolExpr) e2}), (BoolExpr) e3);
                 //   System.out.println("stable1:" + stable1);
                    Solver s = ctx.mkSolver();

                    StableRQ rs = new StableRQ(o,  "SER");
                    BoolExpr stable2 = rs.prove(ctx);
                    System.out.println("stable2:" + stable2);

                    System.out.println("p111"+o.geto1().getP(ctx));
                    StableRP rp1 = new StableRP(o.geto1(), "RR");
                    BoolExpr stable3 = rp1.prove(ctx);
                    System.out.println("stable3:" + stable3);

                    StableRP rp2 = new StableRP(o.geto2(),  "RR");
                    BoolExpr stable4 = rp2.prove(ctx);
                    System.out.println("stable4:" + stable4);

                    BoolExpr stable5 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) o.geto1().getP(ctx), (BoolExpr) o.geto1().applyEffect(ctx)}),
                            (BoolExpr)  o.geto1().getQ(ctx));
                    System.out.println("stable5:" + stable5);

                    BoolExpr stable6 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) o.geto2().getP(ctx), (BoolExpr) o.geto2().applyEffect(ctx)}),
                            (BoolExpr) o.geto2().getQ(ctx));
                    System.out.println("stable6:" + stable6);

                    BoolExpr st = ctx.mkAnd(new BoolExpr[]{stable2, stable3,stable4, stable5, stable6 });
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
        if (o.getType() == "ITE") {
            o.geto1().putP(ctx.mkAnd(new BoolExpr[]{o.getP(ctx), (BoolExpr) o.getCond()}));
            o.geto2().putP(ctx.mkAnd(new BoolExpr[]{o.getP(ctx), ctx.mkNot((BoolExpr) o.getCond())}));

            Expr e1 = o.geto1().getP(ctx);
            Expr e2 = o.geto1().applyEffect(ctx);
            Expr e3 = o.geto1().getQ(ctx);

            //   BoolExpr stable1 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) e1, (BoolExpr) e2}), (BoolExpr) e3);
            //   System.out.println("stable1:" + stable1);
            Solver s = ctx.mkSolver();

            StableRQ rs = new StableRQ(o,  "SER");
            BoolExpr stable2 = rs.prove(ctx);
            System.out.println("stable2:" + stable2);

            System.out.println("p111"+o.geto1().getP(ctx));
            StableRP rp1 = new StableRP(o.geto1(), str);
            BoolExpr stable3 = rp1.prove(ctx);
            System.out.println("stable3:" + stable3);

            StableRP rp2 = new StableRP(o.geto2(),  str);
            BoolExpr stable4 = rp2.prove(ctx);
            System.out.println("stable4:" + stable4);

            BoolExpr stable5 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) o.geto1().getP(ctx), (BoolExpr) o.geto1().applyEffect(ctx)}),
                    (BoolExpr)  o.geto1().getQ(ctx));
            System.out.println("stable5:" + stable5);

            BoolExpr stable6 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) o.geto2().getP(ctx), (BoolExpr) o.geto2().applyEffect(ctx)}),
                    (BoolExpr) o.geto2().getQ(ctx));
            System.out.println("stable6:" + stable6);

            BoolExpr st = ctx.mkAnd(new BoolExpr[]{stable2, stable3,stable4, stable5, stable6 });
            System.out.println("st:" + st);

            s.add(ctx.mkNot(st));
            Status status = s.check();
            if (status == Status.SATISFIABLE)
                System.out.println("model" + s.getModel());
            System.out.println("status:" + status);

        }
    }
    public static BoolExpr getprove(Operation o, Context ctx, String str) {
        System.out.println("prove for Operation:   "+o);
            o.geto1().putP(ctx.mkAnd(new BoolExpr[]{o.getP(ctx), (BoolExpr) o.getCond()}));
            o.geto2().putP(ctx.mkAnd(new BoolExpr[]{o.getP(ctx), ctx.mkNot((BoolExpr) o.getCond())}));

            Expr e1 = o.geto1().getP(ctx);
            Expr e2 = o.geto1().applyEffect(ctx);
            Expr e3 = o.geto1().getQ(ctx);

            o.putQ(ctx.mkOr(new BoolExpr[] {(BoolExpr) o.geto1().getQ(ctx),(BoolExpr) o.geto2().getQ(ctx)}));


            //   BoolExpr stable1 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) e1, (BoolExpr) e2}), (BoolExpr) e3);
            //   System.out.println("stable1:" + stable1);
            Solver s = ctx.mkSolver();

            StableRQ rs = new StableRQ(o,  str);
            BoolExpr stable2 = rs.prove(ctx);
            System.out.println("stable2:" + stable2);

            System.out.println("p111"+o.geto1().getP(ctx));
            StableRP rp1 = new StableRP(o.geto1(), str);
            BoolExpr stable3 = rp1.prove(ctx);
            System.out.println("stable3:" + stable3);

            StableRP rp2 = new StableRP(o.geto2(),  str);
            BoolExpr stable4 = rp2.prove(ctx);
            System.out.println("stable4:" + stable4);

            BoolExpr stable5 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) o.geto1().getP(ctx), (BoolExpr) o.geto1().applyEffect(ctx)}),
                    (BoolExpr)  o.geto1().getQ(ctx));
            System.out.println("stable5:" + stable5);

            BoolExpr stable6 = ctx.mkImplies(ctx.mkAnd(new BoolExpr[]{(BoolExpr) o.geto2().getP(ctx), (BoolExpr) o.geto2().applyEffect(ctx)}),
                    (BoolExpr) o.geto2().getQ(ctx));
            System.out.println("stable6:" + stable6);

            BoolExpr st = ctx.mkAnd(new BoolExpr[]{stable2, stable3,stable4, stable5, stable6 });
            System.out.println("st:" + st);
            return st;

    }

}
