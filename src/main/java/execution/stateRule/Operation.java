package execution.stateRule;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/18/17.
 */
public interface Operation {

    /**
     * @return the name of operation
     */
    public abstract String getName();

    /**
     * Compute the precondition of the operation in z3 context.
     * @param ctx Z3 context
     * @return a boolean expression in Z3 context which represents precondition of operation
     * @throws Z3Exception
     */
    public abstract BoolExpr getP(Context ctx) throws Z3Exception ;

    /**
     * Compute the effect of the operation in z3 context.
     * @param ctx Z3 context
     * @return an expression in Z3 context which represents the effect of operation
     * @throws Z3Exception
     */
    public abstract Expr getQ(Context ctx) throws Z3Exception;

    /**
     * @param name
     * @return an expression in Z3 context which represents the arguments of operation
     */
    public abstract Expr[] getArgs(String name);



    public abstract void putType(String str) throws Z3Exception;

    public abstract String getType() throws Z3Exception;

    public abstract BoolExpr applyEffect(Context ctx) throws Z3Exception;

    public abstract LinkedList<Expr> writeSet() throws Z3Exception;

    public abstract LinkedList<Expr> readSet() throws Z3Exception;

    public abstract void putReadSet(LinkedList<Expr> l) throws Z3Exception;

    public abstract void putWriteSet(LinkedList<Expr> l) throws Z3Exception;

    public abstract List<Transaction> concurTrans() throws Z3Exception;

    public abstract Transaction getTransaction() throws Z3Exception;
    public abstract void putTransaction(Transaction t) throws Z3Exception;

    public abstract void putP(Expr p) throws Z3Exception;
    public abstract void putQ(Expr q) throws Z3Exception;

    public abstract Operation geto1() throws Z3Exception;
    public abstract Operation geto2() throws Z3Exception;

    public abstract Expr getCond() throws Z3Exception;

    public abstract void puto1(Operation o1) throws Z3Exception;
    public abstract void puto2(Operation o2) throws Z3Exception;

}
