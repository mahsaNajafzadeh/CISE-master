package execution;

import com.microsoft.z3.*;
import isolations.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahsa on 1/24/17.
 */
public interface transaction {

    public Expr effect_set=null;
    public abstract BoolExpr preconidtion(Context ctx, application a) throws Z3Exception;
    public abstract BoolExpr postcondition(Context ctx, application a) throws Z3Exception;
    public abstract List<transaction> concurrentTrans(Context ctx);
    public abstract BoolExpr getResult(Context ctx);
    public abstract IntExpr getValue(Context ctx);
    public abstract Expr  setEffect(Context ctx, application a);
    public abstract Expr  getEffect();
    public abstract void settrans(Context ctx, application a);
    public abstract Expr gettrans();
}
