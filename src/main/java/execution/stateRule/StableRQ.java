package execution.stateRule;

import com.microsoft.z3.*;

/**
 * Created by mahsa on 5/18/17.
 */
public class StableRQ {

    Operation o;
    String iso;

    public StableRQ( Operation o,  String iso) {
        this.o=o;
        this.iso=iso;

    }
    public BoolExpr prove(Context ctx) {

        Relyl r=new Relyl(o,o.concurTrans(),iso);

        BoolExpr e=(BoolExpr)r.relyEffect(ctx);
        System.out.println("rely"+e);
        BoolExpr stable = ctx.mkImplies(ctx.mkAnd(new BoolExpr[] { (BoolExpr) o.getQ(ctx),e }),(BoolExpr) o.getQ(ctx));

        return stable;

    }


}
