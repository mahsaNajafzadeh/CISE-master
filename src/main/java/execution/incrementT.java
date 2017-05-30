package execution;

import isolations.isolation;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Created by mahsa on 1/24/17.
 */
public class incrementT {

    public  List<effects> op=new ArrayList<effects>();
    isolation iso;
    int c;
    List<Entry<effects,effects>> session = new ArrayList<>();;


    public void addEffect() {
     //   effects  read=new effects(c,"read",this);
       // read.id=1;
     //   op.add(read);

      //  effects  write=new effects(c,"write");
      //  write.id=2;
      //  op.add(write);
    }



    public void ordering(){
        for(int j=0;j<op.size();j++)
          for(int i=j+1; i < op.size();i++);
       //     if( op.get(j) < op.get(i)  ) {
      //          Entry<effects,effects> relation = new AbstractMap.SimpleEntry(op.get(i),op.get(j));
       //         System.out.println("relation"+relation);
        //        session.add(relation);
        //    }
    }


}
