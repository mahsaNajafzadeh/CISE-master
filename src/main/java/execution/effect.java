package execution;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Z3Exception;
import com.microsoft.z3.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahsa on 1/24/17.
 */
public interface effect  {

    public abstract BoolExpr preconidtion(Context ctx, application a) throws Z3Exception;
    public abstract BoolExpr postcondition(Context ctx, application a) throws Z3Exception;
    public abstract IntExpr getValue(Context ctx);

    public abstract Expr getTransaction(Context ctx);

}
