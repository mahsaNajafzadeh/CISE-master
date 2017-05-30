package execution;

import com.microsoft.z3.*;
import isolations.MonotonicVis;
import isolations.RMWVis;
import isolations.AtomicVis;
import isolations.isolation;
import isolations.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mahsa on 1/30/17.
 */
public class application {



    public static Sort effect;
    public static Expr effect_set;
    public static Sort relation;
    public static Expr relation_set;
    public static Sort transation;
    public static Sort object;
    public static Expr transation_set;
    public static Expr object_set;
    public static BoolExpr preconidtion;
    public static Expr balanceObj;
    public static Sort ff;
    public static Expr id_set;
    public static Expr idRelation_set;

    public static TupleSort session;
    public static TupleSort visibility;
    public static TupleSort visibility_trans;
    public static TupleSort visibility_gtrans;
    public static TupleSort pre;
    public static TupleSort effectObj;
    public static TupleSort parent;
    public static EnumSort type;
    public static TupleSort typeRelation;
    public static TupleSort idRelation;
    public static TupleSort ValueRelation;
    public static TupleSort commitRelation;
    public static TupleSort typetransRelation;

    public static TupleSort parentUpdate;

   // public static List<isolation> inv=new ArrayList<isolation>();
  //  RMWVis test=new RMWVis("isolation");


    public static Expr session_set;
    public static Expr visibility_set;
    public static Expr visibility_trans_set;
    public static Expr visibility_gtrans_set;
    public static Expr effectObj_set;
    public static Expr parent_set;
    public static Expr type_set;
    public static Expr typetrans_set;
    public static Expr ValueRelation_set;

    public static Expr commitRelation_set;
    public static Expr parentUpdate_set;

    public static FuncDecl finv;

    public static  IntExpr balance;
    public static  IntExpr value;
    public List<transaction> transactions=new ArrayList<transaction>();
    public List<effect> effects=new ArrayList();

    public static  transaction  T1;
    public static transaction  T2;

    public static Sort update;
    public static Expr update_set;
    public static Expr visibileUpdate_set;
    public static TupleSort visibileUpdate;

    public void initializeState(Context ctx) throws Z3Exception {
        effect = ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"));

        update = ctx.mkUninterpretedSort(ctx.mkSymbol("update"));

        ff=ctx.mkSetSort(effect);
        effect_set = ctx.mkConst("effect_set",ff);


        id_set = ctx.mkConst("id_set",ctx.mkIntSort());

        relation = ctx.mkUninterpretedSort(ctx.mkSymbol("RELATION"));

        SetSort dd=ctx.mkSetSort(relation);
        relation_set = ctx.mkConst("relation_set",dd);

        transation = ctx.mkUninterpretedSort("TRANSACTION");

        SetSort fl=ctx.mkSetSort(transation);
        transation_set = ctx.mkConst("transation_set",fl);

        object = ctx.mkUninterpretedSort(ctx.mkSymbol("object"));
        SetSort os=ctx.mkSetSort(object);
        object_set= ctx.mkConst("object_set",os);


        session= ctx.mkTupleSort(ctx.mkSymbol("mk_session_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")  }, // names
                new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")), ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))});

        SetSort ps=ctx.mkSetSort(session);
        session_set = ctx.mkConst("session_set",ps);

        visibility= ctx.mkTupleSort(ctx.mkSymbol("mk_visibility_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
                new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")), ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))  } );

        Sort re=ctx.mkSetSort(visibility);
        visibility_set = ctx.mkConst("visibility_set",re);

        visibileUpdate= ctx.mkTupleSort(ctx.mkSymbol("mk_visibility_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
                new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("update")), ctx.mkUninterpretedSort(ctx.mkSymbol("update"))  } );

        Sort vu=ctx.mkSetSort(visibileUpdate);
        visibileUpdate_set = ctx.mkConst("visibileUpdate_set",vu);


        visibility_trans= ctx.mkTupleSort(ctx.mkSymbol("mk_visibility_trans_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
                new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("TRANSACTION")), ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))  } );

        Sort vt=ctx.mkSetSort(visibility_trans);
        visibility_trans_set = ctx.mkConst("visibility_trans__set",vt);

        visibility_gtrans= ctx.mkTupleSort(ctx.mkSymbol("mk_visibility_gtrans_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
                new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("TRANSACTION")), ctx.mkUninterpretedSort(ctx.mkSymbol("TRANSACTION"))  } );

        Sort gt=ctx.mkSetSort(visibility_gtrans);
        visibility_gtrans_set = ctx.mkConst("visibility_gtrans__set",gt);


        effectObj= ctx.mkTupleSort(ctx.mkSymbol("mk_session_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")  }, // names
                new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")), ctx.mkUninterpretedSort(ctx.mkSymbol("object"))});

        Sort eo=ctx.mkSetSort(effectObj);
        effectObj_set = ctx.mkConst("effectObj_set",eo);


        parent= ctx.mkTupleSort(ctx.mkSymbol("mk_parent_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")  }, // names
                new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")), ctx.mkUninterpretedSort(ctx.mkSymbol("TRANSACTION"))});

        Sort pa=ctx.mkSetSort(parent);
        parent_set = ctx.mkConst("parent_set",pa);

        parentUpdate= ctx.mkTupleSort(ctx.mkSymbol("mk_parent_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")  }, // names
                new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("update")), ctx.mkUninterpretedSort(ctx.mkSymbol("update"))});

        Sort pu=ctx.mkSetSort(parentUpdate);
        parentUpdate_set = ctx.mkConst("parent_set",pu);


        ValueRelation= ctx.mkTupleSort(ctx.mkSymbol("make_rvalue"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")  }, // names
                new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")), ctx.mkIntSort()});

        Sort vr=ctx.mkSetSort(ValueRelation);
        ValueRelation_set = ctx.mkConst("valueRelation_set",vr);


        commitRelation= ctx.mkTupleSort(ctx.mkSymbol("make_commit"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")  }, // names
                new Sort[] { 	ff, ctx.mkUninterpretedSort(ctx.mkSymbol("TRANSACTION"))});

        Sort cr=ctx.mkSetSort(commitRelation);
        commitRelation_set = ctx.mkConst("committedTrans_set",cr);


        Symbol name = ctx.mkSymbol("type");
        Symbol[] args={ ctx.mkSymbol("write"), ctx.mkSymbol("read"), ctx.mkSymbol("commit")} 	;
        type= ctx.mkEnumSort(name, args);



        typeRelation= ctx.mkTupleSort(ctx.mkSymbol("mk_type_order"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second"),  ctx.mkSymbol("third")  }, // names
                new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")), type, ctx.mkUninterpretedSort(ctx.mkSymbol("object"))});

        Sort ty=ctx.mkSetSort(typeRelation);
        type_set = ctx.mkConst("type_set",ty);


        typetransRelation= ctx.mkTupleSort(ctx.mkSymbol("writingObjects"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
                new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("TRANSACTION")), ctx.mkIntSort()});

        Sort tr=ctx.mkSetSort(typetransRelation);
        typetrans_set = ctx.mkConst("writingObjects_set",tr);



        idRelation= ctx.mkTupleSort(ctx.mkSymbol("mk_id"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
                new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT")), ctx.mkIntSort()});

        Sort id=ctx.mkSetSort(idRelation);
        idRelation_set = ctx.mkConst("idRelation_set",id);

        pre= ctx.mkTupleSort(ctx.mkSymbol("mk_precondition"), // name of
                new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
                new Sort[] { ff, re  } );

        pre.mkDecl().apply(effect_set,visibility_set);
       // inv.add(test);
        this.balance=(IntExpr) ctx.mkConst("balance",ctx.mkIntSort());;
        this.value=(IntExpr) ctx.mkConst("value",ctx.mkIntSort());;

        balanceObj= ctx.mkConst("balance", ctx.mkUninterpretedSort(ctx.mkSymbol("object")));
        object_set=ctx.mkSetAdd(object_set, balanceObj);

        IntExpr a1= (IntExpr) ctx.mkConst("value1",ctx.mkIntSort());
        IntExpr a2= (IntExpr) ctx.mkConst("value2",ctx.mkIntSort());
        T1=new decremenetT1(ctx, a1);
        T2=new decremenetT2(ctx,a2);
        transactions.add(T1);
        transactions.add(T2);
      //  T1.getEffect(ctx,this);
      //  T2.getEffect(ctx,this);


    }
    public BoolExpr preconidtion(Context ctx)  throws Z3Exception {

        this.balance=(IntExpr) ctx.mkConst("balance",ctx.mkIntSort());;
        this.value=(IntExpr) ctx.mkConst("value",ctx.mkIntSort());;

        BoolExpr expr2=ctx.mkEq(balance, value );

        return expr2;

    }

   /* public BoolExpr preconidtion(Context ctx)  throws Z3Exception {

        this.balance=(IntExpr) ctx.mkConst("balance",ctx.mkIntSort());;
        this.value=(IntExpr) ctx.mkConst("value",ctx.mkIntSort());;

        BoolExpr expr2=ctx.mkEq(balance, value );

        return ctx.mkAnd(new BoolExpr[ ] { ctx.mkEq(decremenetT2.Committed,ctx.mkFalse()), ctx.mkEq(decremenetT1.Committed,ctx.mkFalse()), expr2 }) ;

    }*/
    
    public static void main (String [] args){

        application a=new application();
        Context ctx=new Context();
        a.initializeState(ctx);

        Random rand=new Random();
        int index = rand.nextInt((20 - 6) + 1) + 6;
       // stableRI r=new stableRI();
       // r.getRule(ctx,a);
      //  T1.setEffect(ctx,a);
     //   System.out.println("effects"+T1.getEffect());
      //  RMWVis v1=new RMWVis("RMWVis",T1);
        MonotonicVis v2=new MonotonicVis("monotonicVis", T1);
    ///    AtomicVis v3=new AtomicVis("atomicVis", T1);
        CommitVis v4=new CommitVis("CommitVis", T1);
        RC v5=new RC("RC", T1);
    //    MAV v6=new MAV("RC", T1);
        SnapshotVis v7=new SnapshotVis("SnapshotVis", T1, T2);
        RR v8=new RR("RR", T1);
        SnapshotSER v9= new SnapshotSER("SnapshotSER", T1,T2, balanceObj);
        SI v10= new SI("SI", T1);
        SER v11= new SER("SnapshotSER", T1);

     //   v1.getIsolation(ctx);
      //  v2.getIsolation(ctx);
       // v3.getIsolation(ctx);
      //  v10.getIsolation(ctx);
        stableRI s=new stableRI();

        Expr effect_set1=ctx.mkConst("E1", ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))));
        Expr effect_set2=ctx.mkConst("E2", ctx.mkSetSort(ctx.mkUninterpretedSort(ctx.mkSymbol("EFFECT"))));
        relyRelation R=new relyRelation(effect_set1, effect_set2);
        consistency I=new consistency();

        PP p=new  PP();
        QQ q=new  QQ(1,  ctx);;
        Expr x1=s.prove(ctx, R, I,effect_set1,effect_set2) ;
        System.out.println("stableRI:  "+x1);

        stableRP s2=new stableRP();

     //   Expr x2=s2.prove(ctx, p, R, I,effect_set1,effect_set2) ;
      // System.out.println("stableRP:  "+x2);


        stableRQ s3=new stableRQ();


        Expr x3=s3.prove(ctx, q, R, I,effect_set1,effect_set2,a) ;
        System.out.println("stableRQ1:  "+x3);


        stableRQ s4=new stableRQ();

        QQ q2=new  QQ(2,  ctx);
      //  Expr x44=s4.prove(ctx, q2, R, I,effect_set1,effect_set2) ;
      //  System.out.println("stableRQ2':  "+x44);


        GG g=new  GG(effect_set1,effect_set2);
        CC c=new  CC();
        RGVAR rg=new RGVAR();
       // Expr x4=rg.prove(ctx, p,q,R,g, I, c, effect_set1);
     //   System.out.println("\n\n\n");
     //   System.out.println("RGVAR:  "+x4);
        //stableRQ r2=new stableRQ();
       // r2.getRule(ctx,a);

        RGTXN rg2=new RGTXN();
        //Expr x4=rg2.prove(ctx, p,q,q2,R,g, I, c, effect_set1);
      //  System.out.println("RGTXN:  "+x4);

    }
}
