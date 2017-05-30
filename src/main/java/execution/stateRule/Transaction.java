package execution.stateRule;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/18/17.
 */
public interface Transaction {


    /**
     * @return the name of operation
     */
    public abstract String getName();


    /**
     * @param name
     * @return an expression in Z3 context which represents the arguments of operation
     */
    public abstract Expr[] getArgs(String name);


    public abstract List<Operation> getOps(Context ctx, Expr inv) throws Z3Exception;
    public abstract void putOp(Operation o) throws Z3Exception;
    public abstract Operation getOp() throws Z3Exception;

    public abstract LinkedList<Expr> writeSet() throws Z3Exception;

    public abstract LinkedList<Expr> readSet() throws Z3Exception;
    public abstract void putConcur(Transaction t) throws Z3Exception;

    public abstract LinkedList<Transaction> getConcur() throws Z3Exception;


    public abstract void putQ(Expr qq) throws Z3Exception;
    public abstract Expr getQ(Context ctx) throws Z3Exception;


    public abstract void putP(Expr pp, Context ctx) throws Z3Exception;
    public abstract Expr getP(Context ctx) throws Z3Exception;


    public abstract void putReadSet(LinkedList<Expr> l) throws Z3Exception;

    public abstract void putWriteSet(LinkedList<Expr> l) throws Z3Exception;

    public abstract void putG(BoolExpr g) throws Z3Exception;
    public abstract BoolExpr getG() throws Z3Exception;

    public abstract BoolExpr applyG(Context ctx) throws Z3Exception;

    public abstract Operation parse(LinkedList<Operation> l) throws Z3Exception;
}
