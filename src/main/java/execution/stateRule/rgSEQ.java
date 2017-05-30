package execution.stateRule;

import com.microsoft.z3.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/22/17.
 */
public class rgSEQ {


    public void prove(List<Transaction> tList, Context ctx, String str, Expr inv) {
        for (int j = 0; j < tList.size(); j++) {
            Transaction t = tList.get(j);
            System.out.println("size" + t.getOps(ctx,inv).size());
            for (int i = 0; i < t.getOps(ctx,inv).size(); i++) {
                Operation o = t.getOps(ctx,inv).get(i);
                if (o.getType() == "RG-SEQ") {

                    Operation o1 = o.geto1();
                    Operation o2 = o.geto2();

                    o1.putP(o.getP(ctx));
                    o2.putP(o1.getQ(ctx));
                    o2.putQ(o.getQ(ctx));

                    if(o1.getType()=="assigment") {
                        rgAssign.prove(o1, ctx, str);
                    }
                    else if (o1.getType()=="ITE")
                        rgITE.prove(o1,ctx, str);
                    else if (o1.getType()=="ITE")
                        rgSEQ.prove(o1,ctx, str);
                    if(o2.getType()=="assigment") {
                        rgAssign.prove(o2, ctx, str);
                    }
                    else if (o2.getType()=="ITE")
                        rgITE.prove(o2,ctx,str);
                    else if (o2.getType()=="SEQ")
                        rgSEQ.prove(o2,ctx , str);
            }

                }
            }

        }
    public static void prove(Operation o, Context ctx, String str) {
        Operation o1 = o.geto1();
        Operation o2 = o.geto2();

        o1.putP(o.getP(ctx));
        o2.putP(o1.getQ(ctx));
    //    o2.putQ(o.getQ(ctx));
        BoolExpr e1=null;
        BoolExpr e2=null;

        if(o1.getType()=="assignment") {
            e1=rgAssign.getprove(o1, ctx, str);
        }
        else if (o1.getType()=="ITE")
            e1=rgITE.getprove(o1,ctx, str);
        else if (o1.getType()=="SEQ")
            e1=rgSEQ.getprove(o1,ctx, str);

        if(o2.getType()=="assignment") {
            e2=rgAssign.getprove(o2, ctx, str);
        }
        else if (o2.getType()=="ITE")
            e2=rgITE.getprove(o2,ctx, str);
        else if (o2.getType()=="SEQ")
            e2=rgSEQ.getprove(o2,ctx, str);

        System.out.println("eee111:" + e1);
        System.out.println("eee222:" + e2);

        BoolExpr e=ctx.mkAnd(new BoolExpr[] {e1,e2});
        System.out.println("eee:" + e);
        Solver s=ctx.mkSolver();
        s.add(ctx.mkNot(e));
        Status status = s.check();
        if (status == Status.SATISFIABLE)
            System.out.println("model" + s.getModel());
        System.out.println("status111:" + status);

    }
    public static BoolExpr getprove(Operation o, Context ctx, String str) {
        System.out.println("prove for Operation:   "+o);
        Operation o1 = o.geto1();
        Operation o2 = o.geto2();

        o1.putP(o.getP(ctx));
        o2.putP(o1.getQ(ctx));
        //    o2.putQ(o.getQ(ctx));

        o.putQ(o2.getQ(ctx));

        BoolExpr e1=null;
        BoolExpr e2=null;

        if(o1.getType()=="assignment") {
            e1=rgAssign.getprove(o1, ctx, str);
            System.out.println("prove for Operation finished   "+o1);
        }
        else if (o1.getType()=="ITE") {
            e1=rgITE.getprove(o1, ctx, str);
            System.out.println("prove for Operation finished   "+o1);
        }
        else if (o1.getType()=="SEQ") {
            e1=rgSEQ.getprove(o1, ctx, str);
            System.out.println("prove for Operation finished   "+o1);
        }

        if(o2.getType()=="assignment") {
            o2.putP(o1.getQ(ctx));
            e2=rgAssign.getprove(o2, ctx, str);
            System.out.println("prove for Operation finished   "+o2);
        }
        else if (o2.getType()=="ITE") {
            o2.putP(o1.getQ(ctx));
            e2=rgITE.getprove(o2, ctx, str);
            System.out.println("prove for Operation finished   "+o2);
        }
        else if (o2.getType()=="SEQ") {
            o2.putP(o1.getQ(ctx));
            e2=rgSEQ.getprove(o2, ctx, str);
            System.out.println("prove for Operation finished   "+o2);
        }

        System.out.println("eee111:" + e1);
        System.out.println("eee222:" + e2);

        BoolExpr e=ctx.mkAnd(new BoolExpr[] {e1,e2});
        System.out.println("eee:" + e);
        return e;

    }
}
