package execution.stateRule;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;

import java.util.LinkedList;

/**
 * Created by mahsa on 5/25/17.
 */
public class StableTRQ {
    Transaction t;
    String iso;


    public StableTRQ( Transaction t, String iso) {
        this.iso=iso;
        this.t=t;

    }
    public BoolExpr prove(Context ctx) {

        LinkedList< Expr > rstate=t.readSet();
        LinkedList<Expr> wstate=t.writeSet();
        LinkedList<Transaction> trans=t.getConcur();

        Relyc r=new Relyc(rstate, wstate,  trans, iso);

        BoolExpr e=(BoolExpr)r.relyEffect(ctx);
        System.out.println("rely"+e);
        System.out.println("QQQ"+t.getQ(ctx));
        BoolExpr stable = ctx.mkImplies(ctx.mkAnd(new BoolExpr[] { (BoolExpr) t.getQ(ctx),e }),(BoolExpr) t.getQ(ctx));

        return stable;

    }

}
