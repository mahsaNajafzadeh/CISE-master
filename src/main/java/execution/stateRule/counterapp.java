package execution.stateRule;

import com.microsoft.z3.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahsa on 5/18/17.
 */
public class counterapp implements application {


    public static TupleSort counter;
    public static Sort Object;
    public static Expr Tuple;
    public static Expr id;
    public static List<Transaction> tList;
    public static Transaction t1;
    public static Transaction t2;
    public static  Operation o14;

    public void initialize_state(Context ctx) {

        Object = ctx.mkUninterpretedSort(ctx.mkSymbol("Object"));
        counter = ctx.mkTupleSort(ctx.mkSymbol("mk_Parent_tuple"), // name of
                new Symbol[]{ctx.mkSymbol("object"), ctx.mkSymbol("value"), ctx.mkSymbol("version")}, // names
                new Sort[]{ctx.mkUninterpretedSort(ctx.mkSymbol("Object")), ctx.mkIntSort(), ctx.mkIntSort()});
        id = ctx.mkConst("c", ctx.mkUninterpretedSort(ctx.mkSymbol("Object")));

        Expr[] index = new Expr[3];
        index[0] = id;
        index[1] = ctx.mkConst("c", ctx.mkIntSort());
        index[2] = ctx.mkInt("0");

        Expr vid =  ctx.mkConst("v", ctx.mkUninterpretedSort(ctx.mkSymbol("Object")));
        Tuple = counter.mkDecl().apply(index);
        Expr[] index1 = new Expr[3];
        index1[0] = vid;
        index1[1] =  ctx.mkConst("v", ctx.mkIntSort());
        index1[2] = ctx.mkInt("0");

        Expr v = counterapp.counter.mkDecl().apply(index1);

        Expr gid =  ctx.mkConst("g", ctx.mkUninterpretedSort(ctx.mkSymbol("Object")));
        Expr[] index2 = new Expr[3];
        index2[0] = gid;
        index2[1] =  ctx.mkConst("g", ctx.mkIntSort());
        index2[2] = ctx.mkInt("0");

        Expr g = counterapp.counter.mkDecl().apply(index2);

        Operation o11=new assignOp(ctx,g,Tuple,"assignment");
        LinkedList<Expr> rl1=new LinkedList<>();
        LinkedList<Expr> wl1=new LinkedList<>();
        rl1.add(Tuple);
        wl1.add(v);

        LinkedList<Expr> rl2=new LinkedList<>();
        LinkedList<Expr> wl2=new LinkedList<>();
        rl2.add(Tuple);
        wl2.add(g);

        o11.putReadSet(rl1);
        o11.putWriteSet(wl1);

        BoolExpr b1=ctx.mkEq(g.getArgs()[1], Tuple.getArgs()[1]);

        o11.putP(invariant(ctx));
        o11.putQ(ctx.mkAnd(new BoolExpr[]{invariant(ctx) , b1 }));


        Operation o12=new assignOp(ctx,g,Tuple, "assignment");
        o12.putReadSet(rl2);
        o12.putWriteSet(wl2);

        BoolExpr b2=ctx.mkEq(g.getArgs()[1], g.getArgs()[1]);

        o12.putP(invariant(ctx));
        o12.putQ(ctx.mkAnd(new BoolExpr[]{invariant(ctx) , b2 }));

        LinkedList<Operation> l1=new LinkedList<>();
        LinkedList<Operation> l2=new LinkedList<>();

        l1.add(o11);

        l2.add(o12);


        Expr e=ctx.mkGe((IntExpr)g.getArgs()[1], ctx.mkInt("1"));

        Operation o1=new assignOp(ctx,g,Tuple, "assignment");
        Expr b= ctx.mkEq(g.getArgs()[1], ctx.mkSub((ArithExpr) g.getArgs()[1], ctx.mkInt("1")));
        o1.putQ(ctx.mkAnd(new BoolExpr[]{invariant(ctx) , (BoolExpr) b }));
        o1.putP(ctx.mkTrue());

        Operation o13=new iteOp(ctx,e,o1, o12, "ITE");

        BoolExpr b23=ctx.mkOr((BoolExpr) o13.geto1().getQ(ctx), (BoolExpr)o13.geto2().getQ(ctx));

        o13.putP(invariant(ctx));
        o13.putQ(ctx.mkAnd(new BoolExpr[]{invariant(ctx) , b23 }));



        o14=new seqOp(ctx, o11, o12, "SEQ");
        o14.putP(invariant(ctx));
        o14.puto2(o13);
        o14.puto1(o12);

        Operation o2=new assignOp(ctx,g,Tuple, "assignment");


        BoolExpr b3=ctx.mkEq(Tuple.getArgs()[1], g.getArgs()[1]);

        o2.putP(invariant(ctx));
        o2.putQ(ctx.mkAnd(new BoolExpr[]{invariant(ctx) , b3 }));

   //     l1.add(o13);
   //     l1.add(o2);

        t2 = new trans(l2);
        t1 = new trans(l1);
        t1.putP(invariant(ctx), ctx);
        Operation o=t1.parse(l1);

        LinkedList<Expr> lt1=new LinkedList<>();
        LinkedList<Expr> lt2=new LinkedList<>();
        lt1.add(Tuple);
        lt2.add(g);

        t1.putReadSet(lt1);
        t1.putWriteSet(lt2);

        t1.putOp(o);
        t1.putG(invariant(ctx));

        t2.putOp(o14);


        o11.putTransaction(t1);
        o12.putTransaction(t2);
        o13.putTransaction(t1);

        t1.putConcur(t2);
        t2.putConcur(t1);

        BoolExpr inv=invariant(ctx);
        BoolExpr e1= ctx.mkEq(v.getArgs()[1], counterapp.Tuple.getArgs()[1]);
        BoolExpr e2= ctx.mkEq(g.getArgs()[1], counterapp.Tuple.getArgs()[1]);

        t1.putQ(ctx.mkAnd(new BoolExpr[] {inv, e1}));
        t2.putQ(ctx.mkAnd(new BoolExpr[] {inv, e2}));

        System.out.println("ttt:"+ t1.getConcur());
        tList=new ArrayList<Transaction>();
        tList.add(t1);
        tList.add(t2);
        o1.putTransaction(t1);
        o2.putTransaction(t1);



    }

    public  BoolExpr invariant(Context ctx) {

        return ctx.mkGe((IntExpr) Tuple.getArgs()[1], ctx.mkInt("0"));
    }

    public static void main(String[] args) {

        Context ctx=new Context();
        counterapp c=new counterapp();
        c.initialize_state(ctx);
     //   BoolExpr P = t2.P(ctx);
     //   Expr Q = t2.Q(ctx);

        rgAssign rg=new rgAssign();
        //rg.prove(c.tList,ctx);

      //  rgITE ri=new rgITE();
      //  ri.prove(c.tList,ctx);

        rgTXN ri=new rgTXN();
        ri.prove(c.t1,ctx,c, "SER");
    }
}



