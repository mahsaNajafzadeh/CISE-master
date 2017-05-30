package execution.stateRule;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/19/17.
 */
public class Relyl {
    Operation o1;
    List<Transaction> trans;
    String isolation;

    Relyl(Operation o1, List<Transaction>  trans, String isolation) {
        this.o1=o1;
        this.trans=trans;
        this.isolation=isolation;
    }

    public Expr relyEffect(Context ctx) {
        if(isolation=="SER")
            return ctx.mkTrue();
        else if(isolation=="SI")
        {
            return ctx.mkTrue();
          //  for(int i=0;i<trans.size(); i++)
           // {
               //System.out.println("trans.get(i).writeSet()"+trans.get(i).writeSet());
                //System.out.println("o1.writeSet()"+o1.writeSet());
               // if(conflict(trans.get(i).writeSet(), o1.writeSet()) ) {
                 //   continue;}
                //else
                   // return  trans.get(i).Q(ctx);
           // }
        }

        else if(isolation=="RR")
            return ctx.mkTrue();
           /* for(int i=0;i<trans.size(); i++)
            {
                System.out.println("trans.get(i).writeSet()"+trans.get(i).writeSet());
                System.out.println("o1.readSet()"+o1.readSet());
                if(conflict(trans.get(i).writeSet(), o1.readSet()) ) {
                    continue;}
                else
                    return  trans.get(i).Q(ctx);
            } */
        else if(isolation=="RC") {
            Expr e=ctx.mkTrue();
            for (int i = 0; i < trans.size(); i++)
                e=ctx.mkAnd(new BoolExpr[]{(BoolExpr) e,(BoolExpr)trans.get(i).getQ(ctx)}); /// modify that
            return e;

        }
        return ctx.mkTrue();

    }


    public boolean conflict(List<Expr> l1, List<Expr> l2) {
        System.out.println("here0000");
        if(!l1.retainAll(l2))
            return true;
        return false;
    }

}
