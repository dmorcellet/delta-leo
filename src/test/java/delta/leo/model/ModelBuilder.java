package delta.leo.model;

import delta.common.utils.types.StringType;
import delta.leo.model.entity.field.FieldPropertyNames;
import delta.leo.model.impl.EditableEntity;
import delta.leo.model.impl.EditableField;
import delta.leo.model.impl.EditableModel;
import delta.leo.model.relation.impl.EditableRelation;
import delta.leo.model.relation.impl.EditableRelationLink;
import delta.leo.model.relation.impl.EditableRelationTier;

/**
 * Builder for the "genea" model.
 * @author DAM
 */
public class ModelBuilder
{
  /**
   * Build the "genea" model.
   * @return A model.
   */
  @SuppressWarnings("unused")
  public Model buildModel()
  {
    EditableModel model=new EditableModel("genea");

    EditableEntity personEntity=model.newEntity("personne",null);
    // Name
    {
      EditableField field=personEntity.newField("nom");
      field.setType(new StringType(0,32,false,null));
      field.addProperty(FieldPropertyNames.NAME);
    }
    // Father/son relationship
    {
      EditableRelation r=model.addBinaryAssociation(personEntity,personEntity);
      r.setName("enfant/père");
      EditableRelationTier child=r.getTier(0);
      child.setRole("enfant");
      EditableRelationTier father=r.getTier(1);
      father.setRole("père");
      EditableRelationLink childToFather=r.getEditableLink(child,father);
      childToFather.setMinOccurrences(0);
      childToFather.setMaxOccurrences(1);
      childToFather.setLabel("est un enfant de");
      childToFather.setTraversable(true);
      EditableRelationLink fatherToChild=r.getEditableLink(father,child);
      fatherToChild.setMinOccurrences(0);
      fatherToChild.setMaxOccurrences(Integer.MAX_VALUE);
      fatherToChild.setLabel("est le père de");
      fatherToChild.setOrdered(true);
      fatherToChild.setTraversable(false);
    }

    EditableEntity actEntity=model.newEntity("acte",null);
    EditableEntity placeEntity=model.newEntity("lieu",null);
    EditableEntity proofEntity=model.newEntity("preuve",null);
    EditableEntity researchEntity=model.newEntity("recherche",null);
    EditableEntity cousinShipEntity=model.newEntity("cousinage",null);
    EditableEntity cousinEntity=model.newEntity("cousin",null);

    return model;
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    Model model=new ModelBuilder().buildModel();
    new ModelPrettyPrinter().dumpModel(model,System.out);
  }
}
