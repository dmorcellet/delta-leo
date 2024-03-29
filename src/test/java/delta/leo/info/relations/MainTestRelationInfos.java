package delta.leo.info.relations;

import delta.leo.model.entity.Entity;
import delta.leo.model.impl.EditableEntity;

/**
 * Test class for relation infos.
 * @author DAM
 */
public class MainTestRelationInfos
{
  /**
   * Constructor.
   */
  public MainTestRelationInfos()
  {
    Entity a=new EditableEntity("A");
    Entity b=new EditableEntity("B");
    // Binary association
    BinaryAssociationRelationInfo bri=new BinaryAssociationRelationInfo("aime",a,b,0,1,true,0,RelationInfo.INFINITE_CARDINALITY,true);
    System.out.println(bri);
    BinaryRelationInfo direct=bri.getRelation(true);
    System.out.println(direct.getDisplayableLink());
    BinaryRelationInfo reverse=bri.getRelation(false);
    System.out.println(reverse.getDisplayableLink());
    // Composition
    CompositionRelationInfo cri=new CompositionRelationInfo("aime",a,b,3,5,false);
    System.out.println(cri);
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestRelationInfos();
  }
}
