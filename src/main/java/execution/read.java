package execution;

import com.microsoft.z3.*;

/**
 * Created by mahsa on 2/13/17.
 */
public class read implements  effect{

    public  Expr object;
    public  Expr    transaction;

    public read( Context ctx, Expr obj) {
        this.object=obj;

    }
    @Override
    public BoolExpr preconidtion(Context ctx, application a) throws Z3Exception {
        return null;
    }

    @Override
    public BoolExpr postcondition(Context ctx, application a) throws Z3Exception {
        return null;
    }

    @Override
    public IntExpr getValue(Context ctx) {
        return null;
    }

    @Override
    public  Expr getTransaction(Context ctx) {return this.transaction; }

}
