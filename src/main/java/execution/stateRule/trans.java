package execution.stateRule;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/18/17.
 */
public class trans implements Transaction{
    public LinkedList<Transaction> trans;
    Expr P;
    Expr Q;
    Operation o;
    BoolExpr G;
    LinkedList<Expr> readSet;
    LinkedList<Expr> writeSet;
    public  LinkedList<Operation> Ops;

    List<Expr> localExpr;
    List<Expr> globalExpr;


    public trans(LinkedList<Operation> l) {
      //  Ops=new LinkedList<Operation>();
        Ops=l;
        trans=new LinkedList<Transaction>();
        //Operation o=new o1(ctx);
        //o.putType();
        //Ops.add(o);

    }

    public trans(List<Expr> gexpr, List<Expr> lexpr) {
        localExpr=new ArrayList<>();
        globalExpr=new ArrayList<>();
        localExpr=lexpr;
        globalExpr=gexpr;

    }

    public  String getName() {
        return "txn";
    }



    public  Expr[] getArgs(String name) {
        return null;
    }

    @Override
    public List<Operation> getOps(Context ctx, Expr inv) throws Z3Exception {
        return null;
    }




    public LinkedList<Operation> getOps() throws Z3Exception {

      //  Operation o=new o1(ctx);
      //  Ops.add(o);
        return Ops;
    }

    @Override
    public void putOp(Operation o) throws Z3Exception {
        this.o=o;
    }

    @Override
    public Operation getOp() throws Z3Exception {
        return this.o;
    }

    @Override
    public LinkedList<Expr> writeSet() throws Z3Exception {
        LinkedList<Expr> l=new LinkedList<>();
     //  for(int i=0;i<Ops.size();i++) {
      //      LinkedList ll=Ops.get(i).writeSet();
      //      for(int j=0;j<ll.size();j++)
        //        l.add(Ops.get(i).writeSet().get(j));
        //}
        return this.readSet;
    }

    @Override
    public LinkedList<Expr> readSet() throws Z3Exception {
       // LinkedList<Expr> l=new LinkedList<>();
      //  for(int i=0;i<Ops.size();i++)
        //    for(int j=0;j<Ops.get(j).readSet().size();j++)
            //    l.add(Ops.get(j).readSet().get(j));
        return  readSet;
    }

    @Override
    public void putConcur(Transaction t) throws Z3Exception {
        trans.add(t);
    }

    @Override
    public LinkedList<Transaction> getConcur() throws Z3Exception {
        return this.trans;
    }

    @Override
    public void putQ(Expr qq) throws Z3Exception {
        this.Q=qq;
    }

    @Override
    public Expr getQ(Context ctx) throws Z3Exception {
        return this.Q;
    }

    @Override
    public void putP(Expr pp, Context ctx) throws Z3Exception {
        this.P=pp;
    }

    @Override
    public Expr getP(Context ctx) throws Z3Exception {
        return this.P;
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
        return this.G;

    }

    @Override
    public BoolExpr applyG(Context ctx) throws Z3Exception {
        return null;
    }

    public Operation parse(LinkedList<Operation> Ops) throws Z3Exception {
        if(Ops.size()==1)
            return Ops.get(0);
        Operation o = new seqOp("SEQ");
        o.puto1(Ops.get(0));
        Ops.removeFirst();
        o.puto2(parse(Ops));
        return o;
    }
}
