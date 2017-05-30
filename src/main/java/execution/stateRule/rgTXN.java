package execution.stateRule;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

/**
 * Created by mahsa on 5/25/17.
 */
public class rgTXN {

    public static void prove(Transaction t, Context ctx, application a, String str) {

        Operation o = t.getOp();
        System.out.println("ooooo:" + o);
        o.putP(t.getP(ctx));

        BoolExpr e1=null;


        if(o.getType()=="assignment") {
            e1=rgAssign.getprove(o, ctx, str);
            t.putQ(o.getQ(ctx));
        }
        else if (o.getType()=="ITE") {
            e1 = rgITE.getprove(o, ctx,str);
            t.putQ(o.getQ(ctx));
        }
        else if (o.getType()=="SEQ") {
            System.out.println("hereeee");
            e1 = rgSEQ.getprove(o, ctx, str);
            t.putQ(o.getQ(ctx));
        }

        System.out.println("eee111:" + e1);

        StableTRQ trq=new StableTRQ(t, str);
        BoolExpr  e2= trq.prove(ctx);
        System.out.println("eee222:" + e2);


        BoolExpr e3=ctx.mkImplies((BoolExpr) t.getQ(ctx), t.getG());
        System.out.println("eee333:" + e3);

        BoolExpr i1=a.invariant(ctx);
        BoolExpr gg=(BoolExpr) t.getQ(ctx);
        BoolExpr i2=a.invariant(ctx);

        BoolExpr e4=ctx.mkImplies(ctx.mkAnd(i1,gg),i2);
        System.out.println("eee444:" + e4);

        BoolExpr e=ctx.mkAnd(new BoolExpr[]{e1,e2,e3,e4});

        Solver s=ctx.mkSolver();
        s.add(ctx.mkNot(e));
        Status status = s.check();
        if (status == Status.SATISFIABLE)
            System.out.println("model" + s.getModel());
        System.out.println("status111:" + status);

    }
}
