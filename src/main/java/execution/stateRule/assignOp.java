package execution.stateRule;

import com.microsoft.z3.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/18/17.
 */
public class assignOp implements Operation {

    public static  Expr  vid;

    public static Expr lstate;

    public static Sort ff;
    public  Expr v;
    public  Expr e;
    public  Transaction t;

    public LinkedList<Expr> readl;
    public LinkedList<Expr> writel;
    public String type;

    public  Expr P;
    public  Expr Q;

    public assignOp(Context ctx) {
        vid =  ctx.mkConst("v", ctx.mkUninterpretedSort(ctx.mkSymbol("Object")));
        ff=ctx.mkSetSort(counterapp.counter);
        lstate=ctx.mkConst("local_state",ff);
        Expr[] index = new Expr[3];
        index[0] = vid;
        index[1] =  ctx.mkConst("v", ctx.mkIntSort());
        index[2] = ctx.mkInt("0");

        v = counterapp.counter.mkDecl().apply(index);
        this.e=counterapp.Tuple;
        readl=new LinkedList<Expr>();
        writel=new LinkedList<Expr>();
    }

    public assignOp(Context ctx, Expr v, Expr e, String str) {
//        ff=ctx.mkSetSort(counterapp.counter);
  //      lstate=ctx.mkConst("local_state",ff);
        this.v=v;
        this.e=e;
        type=str;


    }

    public  String getName() {
        return "assignment";
    }


    public BoolExpr getP(Context ctx) throws Z3Exception {
        System.out.println("ppppp"+this.P);
        return (BoolExpr) this.P;
    }


    public Expr getQ(Context ctx) throws Z3Exception {
        return  Q;
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

//        lstate=ctx.mkSetAdd(lstate, v);
        BoolExpr b1=ctx.mkEq(v, e);
        //Tuple.getArgs()[1]= counterapp.Tuple.getArgs()[1];
        return b1;
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
        System.out.println("hhhh"+p);
    }

    @Override
    public void putQ(Expr q) throws Z3Exception {
        this.Q=q;
    }

    @Override
    public Operation geto1() throws Z3Exception {
        return null;
    }

    @Override
    public Operation geto2() throws Z3Exception {
        return null;
    }

    @Override
    public Expr getCond() throws Z3Exception {
        return null;
    }

    @Override
    public void puto1(Operation o1) throws Z3Exception {

    }

    @Override
    public void puto2(Operation o2) throws Z3Exception {

    }

}
