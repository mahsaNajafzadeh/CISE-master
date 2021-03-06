package execution.stateRule;

import com.microsoft.z3.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/18/17.
 */
public class seqOp implements Operation {


    public  Transaction t;

    public LinkedList<Expr> readl;
    public LinkedList<Expr> writel;
    public String type;

    public  Expr P;
    public  Expr Q;

    public Operation o1;
    public Operation o2;


    public seqOp(String str) {
        this.type=str;
    }

    public seqOp(Context ctx, Operation o1,  Operation o2, String str) {
        this.o1=o1;
        this.o2=o2;
        type=str;
    }

    public  String getName() {
        return "SEQ";
    }


    public BoolExpr getP(Context ctx) throws Z3Exception {
        return (BoolExpr) P;
    }


    public Expr getQ(Context ctx) throws Z3Exception {
        return  o2.getQ(ctx);
    }


    public  Expr[] getArgs(String name) {
        return null;
    }



    public  String getType() throws Z3Exception {
        return type;
    }

    public  void putType(String str) throws Z3Exception{
        type=str;

    }

    public BoolExpr applyEffect(Context ctx)throws Z3Exception {

        //Tuple.getArgs()[1]= counterapp.Tuple.getArgs()[1];
        return ctx.mkTrue();
        //   ctx.mkEq(v,counterapp.id);
    }

    @Override
    public LinkedList<Expr> writeSet() throws Z3Exception {

        return writel;
    }

    @Override
    public LinkedList<Expr>  readSet() throws Z3Exception {
        return readl;
    }

    @Override
    public void putReadSet(LinkedList<Expr> l) throws Z3Exception {
        readl=l;
    }

    @Override
    public void putWriteSet(LinkedList<Expr> l) throws Z3Exception {
        writel=l;
    }

    @Override
    public List<Transaction> concurTrans() throws Z3Exception {
        return t.getConcur();
    }

    @Override
    public Transaction getTransaction() throws Z3Exception {
        return t;
    }

    @Override
    public void putTransaction(Transaction t) throws Z3Exception {
        this.t=t;
    }

    @Override
    public void putP(Expr p) throws Z3Exception {
        this.P=p;
    }

    @Override
    public void putQ(Expr q) throws Z3Exception {
        this.Q=q;
    }

    @Override
    public Operation geto1() throws Z3Exception {
        return this.o1;
    }

    @Override
    public Operation geto2() throws Z3Exception {
        return this.o2;
    }

    @Override
    public Expr getCond() throws Z3Exception {
        return null;
    }

    @Override
    public void puto1(Operation o1) throws Z3Exception {
        this.o1=o1;
    }

    @Override
    public void puto2(Operation o2) throws Z3Exception {
        this.o2=o2;
    }

}
