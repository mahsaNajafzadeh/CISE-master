package execution.stateRule;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;

/**
 * Created by mahsa on 5/22/17.
 */
public interface application {
    public abstract void initialize_state(Context ctx);
    public abstract  BoolExpr invariant(Context ctx);

}
