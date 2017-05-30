package execution;

import com.microsoft.z3.*;

/**
 * Created by mahsa on 2/13/17.
 */
public class commit implements effect{
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
    public Expr getTransaction(Context ctx) {
        return null;
    }
}

