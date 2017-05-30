package execution.stateRule.Bank;

import com.microsoft.z3.*;
import execution.stateRule.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/26/17.
 */
public class Debit implements Transaction {

    public Expr t,b;
    public Expr v;
    public Operation o;
    public LinkedList<Operation> Ops;
    public LinkedList<Transaction> trans;
    public Expr P;
    public Expr Q;
    public BoolExpr G;
    public LinkedList<Expr> readSet;
    public LinkedList<Expr> writeSet;
    public application a;
    public Expr inv;


    Debit( Expr v, Expr b, Context ctx, Expr inv) {
        this.v=v;
        this.b=b;
        this.t=ctx.mkConst("t", ctx.mkIntSort());
        Ops=getOps(ctx, inv);
        this.o=parse(Ops);
        this.inv=inv;

    }

    @Override
    public String getName() {
        return "debit";
    }

    @Override
    public Expr[] getArgs(String name) {
        return new Expr[0];
    }

    @Override
    public LinkedList<Operation> getOps(Context ctx, Expr inv) throws Z3Exception {

        LinkedList<Operation> l=new LinkedList<>();

        Operation o11=new assignOp(ctx,t,b,"assignment");
        o11.putP(inv);
        System.out.println("heree"+inv);
        o11.putQ(ctx.mkAnd(new BoolExpr[]{(BoolExpr) inv , ctx.mkEq(t, b) }));

   /*     Expr c=  ctx.mkSub((IntExpr) t, (IntExpr)v);
        Operation o1=new assignOp(ctx,t,c, "assignment");
        o1.putQ(ctx.mkAnd(new BoolExpr[]{a.invariant(ctx) , (BoolExpr) ctx.mkEq(t, c) }));
        o1.putP(ctx.mkTrue());

        Operation o2=new assignOp(ctx,t,t, "assignment");
        o2.putQ(ctx.mkAnd(new BoolExpr[]{a.invariant(ctx) , (BoolExpr) ctx.mkEq(t, t) }));
        o2.putP(ctx.mkTrue());

        Expr e=ctx.mkGe((IntExpr)t, (IntExpr)v);
        Operation o12=new iteOp(ctx,e,o1, o2, "ITE");

        o12.putP(o11.getQ(ctx));
        o12.putQ(ctx.mkAnd(new BoolExpr[]{a.invariant(ctx) , ctx.mkOr((BoolExpr) o1.getQ(ctx),
                (BoolExpr)o2.getQ(ctx)) }));

        Operation o13=new assignOp(ctx,b,t, "assignment");
        o13.putP(o12.getQ(ctx));
        o13.putQ(ctx.mkAnd(new BoolExpr[]{a.invariant(ctx) , ctx.mkEq(t, b) }));*/

        l.add(o11);
      //  l.add(o12);
      //  l.add(o13);

        return l;
    }

    @Override
    public void putOp(Operation o) throws Z3Exception {

        this.o=parse(Ops);
    }

    @Override
    public Operation getOp() throws Z3Exception {
        return this.o;
    }

    @Override
    public LinkedList<Expr> writeSet() throws Z3Exception {
        return writeSet;
    }

    @Override
    public LinkedList<Expr> readSet() throws Z3Exception {
        return readSet;
    }

    @Override
    public void putConcur(Transaction t) throws Z3Exception {

    }

    @Override
    public LinkedList<Transaction> getConcur() throws Z3Exception {
        return trans;
    }

    @Override
    public void putQ(Expr qq) throws Z3Exception {
        this.Q=qq;
    }

    @Override
    public Expr getQ(Context ctx) throws Z3Exception {
        return Q;
    }

    @Override
    public void putP(Expr pp, Context ctx) throws Z3Exception {
        this.P=inv;
    }

    @Override
    public Expr getP(Context ctx) throws Z3Exception {
        this.P=inv;
        return P;
    }

    @Override
    public void putReadSet(LinkedList<Expr> l) throws Z3Exception {
        this.readSet=l;
    }

    @Override
    public void putWriteSet(LinkedList<Expr> l) throws Z3Exception {
        this.writeSet=l;
    }

    @Override
    public void putG(BoolExpr g) throws Z3Exception {
        this.G=g;
    }

    @Override
    public BoolExpr getG() throws Z3Exception {
        return G;
    }

    @Override
    public BoolExpr applyG(Context ctx) throws Z3Exception {
        return null;
    }

    @Override
    public Operation parse(LinkedList<Operation> l) throws Z3Exception {
        if(l.size()==1)
            return l.get(0);
        Operation o = new seqOp("SEQ");
        o.puto1(l.get(0));
        l.removeFirst();
        o.puto2(parse(l));
        return o;
    }
}
