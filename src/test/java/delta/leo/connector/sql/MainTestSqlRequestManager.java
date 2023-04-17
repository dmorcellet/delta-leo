package delta.leo.connector.sql;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.url.URLTools;
import delta.leo.binding.ClassAndField;
import delta.leo.binding.ClassBinding;
import delta.leo.binding.Join;
import delta.leo.metadata.ObjectClass;
import delta.leo.metadata.ObjectsModel;
import delta.leo.model.entity.field.Field;

/**
 * Test class for the SQL requests manager.
 * @author DAM
 */
public class MainTestSqlRequestManager
{
  /**
   * Constructor.
   */
  public MainTestSqlRequestManager()
  {
    long now1=System.currentTimeMillis();

    String packageName="delta.leo.examples.genea";
    URL modelUrl=URLTools.getFromClassPath("model.xml",packageName);
    ObjectsModel model=new ObjectsModel(modelUrl,null);

    String table1="table1";
    String table2="table2";
    String fieldName1="NOM";
    String fieldName2="PRENOMS";
    ObjectClass clazz=model.getClassByName("PERSONNE");
    Field field1=clazz.getFieldByName(fieldName1);
    Field field2=clazz.getFieldByName(fieldName2);

    ClassBinding binding=new ClassBinding(clazz);
    binding.addFieldBinding(table1,"LASTNAME",field1,null);
    binding.addFieldBinding(table2, "FIRSTNAME",field2,null);
    Join join=new Join(new ClassAndField(table1,"TABLE2_FK"),new ClassAndField(table2, "ID"));
    binding.addJoin(join);

    List<Field> fieldsToGet=new ArrayList<Field>();
    fieldsToGet.add(field1);
    fieldsToGet.add(field2);
    SqlSelectRequest manager=new SqlSelectRequest(binding,fieldsToGet);
    long now2=System.currentTimeMillis();
    String selectRequest=manager.getSqlRequest();
    long now3=System.currentTimeMillis();
    System.out.println("Select request ["+selectRequest+"]");
    System.out.println("Time : "+(now2-now1)+"ms");
    System.out.println("Time : "+(now3-now1)+"ms");
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestSqlRequestManager();
  }
}
