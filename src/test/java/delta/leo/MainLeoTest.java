package delta.leo;

import java.net.URL;
import java.util.List;

import delta.common.utils.url.URLTools;
import delta.leo.data.ObjectId;
import delta.leo.data.ObjectInstance;
import delta.leo.data.ObjectsRealm;
import delta.leo.data.ObjectsSource;
import delta.leo.metadata.ObjectClass;
import delta.leo.metadata.ObjectsModel;
import delta.leo.model.entity.field.Field;

/**
 * Test class for LEO features.
 * @author DAM
 */
public class MainLeoTest
{
  /**
   * Test code.
   */
  public static void doIt()
  {
    String packageName="delta.leo.examples.genea";
    URL modelUrl=URLTools.getFromClassPath("model.xml",packageName);
    URL bindingsUrl=URLTools.getFromClassPath("bindings.xml",packageName);
    ObjectsModel model=new ObjectsModel(modelUrl,bindingsUrl);
    ObjectsRealm realm=new ObjectsRealm(model);

    realm.start();

    ObjectsSource defaultSource=realm.getDefaultObjectsSource();
    ObjectsSource sqlSource=realm.getObjectsSource("SQL");
    ObjectClass objectClass=model.getClassByName("PERSONNE");
    Field idField=objectClass.getFieldByName("ID");
    Field firstNameField=objectClass.getFieldByName("NOM");
    Field lastNameField=objectClass.getFieldByName("PRENOMS");
    //Field fatherField=objectClass.getFieldByName("PERE");

    if (false)
    {
      ObjectInstance instance;
      for(int i=0;i<100;i++)
      {
        instance=new ObjectInstance(objectClass);
        instance.setValue(idField,Integer.valueOf(i));
        defaultSource.create(instance);
        System.out.println("Created : "+instance);
        ObjectId id=instance.getId();
        ObjectInstance sameInstance=defaultSource.get(id);
        System.out.println("Retrieved : "+sameInstance);
      }
    }

    if (true)
    {
      long now1=System.currentTimeMillis();
      List<ObjectInstance> allInstances=sqlSource.getAll(objectClass);
      long now2=System.currentTimeMillis();
      List<ObjectInstance> allInstances2=sqlSource.getAll(objectClass);
      long now3=System.currentTimeMillis();
      System.out.println("First read : "+(now2-now1)+"ms");
      System.out.println("Second read : "+(now3-now2)+"ms");
      int nbInstances=allInstances2.size();
      for(int i=0;i<nbInstances;i++)
      {
        ObjectInstance instance=allInstances.get(i);
        Object firstName=instance.getValue(firstNameField);
        Object lastName=instance.getValue(lastNameField);
        System.out.println(firstName+" "+lastName);
      }
      //System.out.println(allInstances);
      System.out.println("Number of instances : "+nbInstances);
      // Object instance unicity checking
      ObjectInstance i1=allInstances2.get(0);
      ObjectId oid=i1.getId();
      ObjectInstance i2=sqlSource.get(oid);
      if (i1!=i2)
      {
        System.err.println("No unicity !!");
      }
      // Cannot change ID fields without changing OID
      {
        int value=-1;
        // todo Should not be able to change an ID field after the creation of the object...
        i1.setValue(idField,Integer.valueOf(value));
        Object idValue=i1.getId().getFieldValue(0);
        if (!idValue.equals(Integer.valueOf(value)))
        {
          System.err.println("ID field value inconsistency !!");
        }
      }
    }

    if (true)
    {
      /*List<ObjectInstance> allInstances=*/sqlSource.getAll(objectClass);
      ObjectId oid=new ObjectId(sqlSource,objectClass,new Object[]{Integer.valueOf(76)});
      ObjectInstance object=sqlSource.get(oid);
      if (object!=null)
      {
        Object me=object.getValue(lastNameField);
        System.out.println(me+", "+me.getClass());
      }
      else
      {
        System.err.println("Object ["+oid+"] not found !!");
      }
    }

    if (false)
    {
      ObjectInstance object=objectClass.newInstance();
      object.setValue(lastNameField,"MORCELLET");
      object.setValue(firstNameField,"Damien");
      long now1=System.currentTimeMillis();
      sqlSource.create(object);
      long now2=System.currentTimeMillis();
      System.out.println("Create : "+(now2-now1)+"ms");
    }
    realm.terminate();
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    doIt();
  }
}
