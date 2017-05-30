package execution.stateRule;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/25/17.
 */
public class Relyc {
    LinkedList<Expr> rstate;
    LinkedList<Expr> wstate;
    List<Transaction> trans;
    String isolation;

    Relyc(LinkedList<Expr> rstate,LinkedList<Expr> wstate, List<Transaction>  trans, String isolation) {
        this.rstate=rstate;
        this.wstate=wstate;
        this.trans=trans;
        this.isolation=isolation;
    }

    public Expr relyEffect(Context ctx) {
        BoolExpr e=ctx.mkTrue();
        if(isolation=="SER")
            return ctx.mkTrue();
        else if(isolation=="SI")
        {
            //return ctx.mkTrue();
              for(int i=0;i<trans.size(); i++){
            //System.out.println("trans.get(i).writeSet()"+trans.get(i).writeSet());
            //System.out.println("o1.writeSet()"+o1.writeSet());
                if(conflict(trans.get(i).writeSet(), wstate) )
                    continue;
                else
                    e=ctx.mkAnd(new BoolExpr[]{ (BoolExpr)trans.get(i).getQ(ctx),e});
             }
             return e;
        }

        else if(isolation=="RR")
            for (int i = 0; i < trans.size(); i++)
                e=ctx.mkAnd(new BoolExpr[]{(BoolExpr) e,(BoolExpr)trans.get(i).getQ(ctx)});
        else if(isolation=="RC") {
            for (int i = 0; i < trans.size(); i++)
                e=ctx.mkAnd(new BoolExpr[]{(BoolExpr) e,(BoolExpr)trans.get(i).getQ(ctx)});
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
