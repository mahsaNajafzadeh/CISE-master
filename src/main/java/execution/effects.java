package execution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.microsoft.z3.*;


/**
 * Created by mahsa on 1/24/17.
 */
public class effects extends Expr {

    transaction trans;
    String type;
    Object arg;

    effects(Context ctx, long obj)
    {
        super(ctx, obj);
    }



}
