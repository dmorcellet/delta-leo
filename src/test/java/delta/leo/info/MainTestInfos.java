package delta.leo.info;


public class MainTestInfos
{
  private static void go()
  {
/*
    ProjectInfo p=new ProjectInfo("genea");

    // Create 'signature' Class
    Entity signatureCls=new Entity("signatureCls");
    {
      Field id=new StringField("id",1);
      id.setMark(FieldProperties.ID,true);
      signatureCls.addField(id);
    }
    p.addClass(signatureCls);
    // Build instances of the 'signature' class
    {
      StringField idField=(StringField)signatureCls.getFieldInfo("id");
      DataObject o=new DataObject(signatureCls);
      o.setValue(new StringValue(idField,"S"));
      o.create();
      o=new DataObject(signatureCls);
      o.setValue(new StringValue(idField,"M"));
      o.create();
      o=new DataObject(signatureCls);
      o.setValue(new StringValue(idField,"N"));
      o.create();
    }

    // Create 'person' class
    Entity personCls=new Entity("personCls");
    {
      Field id=new IntegerField("id",1,Integer.MAX_VALUE);
      personCls.addField(id);
      id.setMark(FieldProperties.ID,true);
      Field surName=new StringField("surName",32);
      personCls.addField(surName);
      Field firstName=new StringField("firstName",64);
      personCls.addField(firstName);
      new RelationFieldInfo("signature");
    }
    p.addClass(personCls);
*/
  }

  public static void main(String[] args)
  {
    go();
  }
}
