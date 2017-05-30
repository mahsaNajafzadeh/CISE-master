package execution;

import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahsa on 1/24/17.
 */
public class test {

     public static void main(String [] args) {
         FuncDecl session,vis ;
         System.out.println(" Hello");
         incrementT t=new incrementT();
         t.addEffect();
         t.ordering();
         System.out.println("sessions"+ t.session);

         Context ctx = new Context();

         Sort effect = ctx.mkUninterpretedSort(ctx.mkSymbol("effects"));

         SetSort ff=ctx.mkSetSort(effect);
         Expr effect_set = ctx.mkConst("effect_set",ff);


         SetSort ff2=ctx.mkSetSort(effect);
         Expr visibility_set = ctx.mkConst("visibility_set",ff2);

         Expr e1=ctx.mkConst("e1", ctx.mkUninterpretedSort(ctx.mkSymbol("effects")));
         Expr e2=ctx.mkConst("e2", ctx.mkUninterpretedSort(ctx.mkSymbol("effects")));

         effect_set= ctx.mkSetAdd(effect_set, e1);
         effect_set= ctx.mkSetAdd(effect_set, e2);

         Expr[] argorder= new Expr[2];
         argorder[0]=e1;
         argorder[1]=e2;
         TupleSort sessionorder= ctx.mkTupleSort(ctx.mkSymbol("mk_sessionorder_tuple"), // name of
                 new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")   }, // names
                 new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("effects")), ctx.mkUninterpretedSort(ctx.mkSymbol("effects")) } );;



         Expr sessionTuple=sessionorder.mkDecl().apply(argorder);
         SetSort ff1=ctx.mkSetSort(sessionorder);
         Expr session_set = ctx.mkConst("session_set",ff1);
         session_set= ctx.mkSetAdd(session_set, sessionTuple);

         Sort[] nodes = new Sort[2];
         nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("effects"));
         nodes[1] =ctx.mkUninterpretedSort(ctx.mkSymbol("effects"));
         //setting names
         Symbol[] namess = new Symbol[2];
         namess[0] =  ctx.mkSymbol("effects1");
         namess[1] =  ctx.mkSymbol("effects2");

         Expr node1=ctx.mkConst("effects1", ctx.mkUninterpretedSort(ctx.mkSymbol("effects")) );
         Expr node2=ctx.mkConst("effects2", ctx.mkUninterpretedSort(ctx.mkSymbol("effects")) );

         Expr[] existArgs =  new Expr[2];
         existArgs[0]=node1;
         existArgs[1]= node2;

         session = ctx.mkFuncDecl("sessionOrder", new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("effects")) ,
                 ctx.mkUninterpretedSort(ctx.mkSymbol("effects")) }, ctx.mkBoolSort());

         Expr sessionOrder= (BoolExpr) ctx.mkApp(session, existArgs)  ;

         Expr[] visArgs =  new Expr[2];
         visArgs[0]=node1;
         visArgs[1]= node2;

        System.out.println("oo"+session_set);

         vis = ctx.mkFuncDecl("visOrder", new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("effects")) ,ctx.mkUninterpretedSort(ctx.mkSymbol("effects")) }, ctx.mkBoolSort());
         Expr viss= ctx.mkApp(vis, visArgs)  ;

         BoolExpr RMVis =ctx.mkForall(nodes, namess, ctx.mkImplies((BoolExpr) sessionOrder, (BoolExpr) viss), 1, null, null,  null, null);


        System.out.println("RMVis"+RMVis);
     }
}
