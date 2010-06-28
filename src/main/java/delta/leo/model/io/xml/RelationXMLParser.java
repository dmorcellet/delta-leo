package delta.leo.model.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import delta.common.utils.xml.DOMParsingTools;
import delta.leo.model.entity.Entity;
import delta.leo.model.impl.EditableModel;
import delta.leo.model.relation.Relation;
import delta.leo.model.relation.RelationLink;
import delta.leo.model.relation.impl.EditableRelation;
import delta.leo.model.relation.impl.EditableRelationLink;
import delta.leo.model.relation.impl.EditableRelationTier;

public class RelationXMLParser
{
  // Tags
  private static final String BA_TAG="BINARY_ASSOCIATION";
  private static final String A_TAG="A";
  private static final String B_TAG="B";
  private static final String AtoB_TAG="AtoB";
  private static final String BtoA_TAG="BtoA";
  // Attributes
  private static final String NAME_ATTR="NAME";
  private static final String ENTITY_NAME_ATTR="ENTITY_NAME";
  private static final String ROLE_ATTR="ROLE";
  private static final String MIN_ATTR="MIN";
  private static final String MAX_ATTR="MAX";
  private static final String ORDERED_ATTR="ORDERED";
  private static final String TRAVERSABLE_ATTR="TRAVERSABLE";
  private static final String MEANING_ATTR="MEANING";

  public void parseRelations(EditableModel model, Element root)
  {
    parseBinaryAssociations(model,root);
  }

  private void parseBinaryAssociations(EditableModel model, Element root)
  {
    NodeList nl=root.getElementsByTagName(BA_TAG);
    int nbNodes=nl.getLength();
    Element e;
    for(int i=0;i<nbNodes;i++)
    {
      e=(Element)nl.item(i);
      parseBinaryAssociation(model,e);
    }
  }

  private Relation parseBinaryAssociation(EditableModel model, Element e)
  {
    String name=DOMParsingTools.getStringAttribute(e.getAttributes(),NAME_ATTR,"");

    // Entity A
    Element elementA=DOMParsingTools.getChildTagByName(e,A_TAG);
    NamedNodeMap aAttrs=elementA.getAttributes();
    String nameA=DOMParsingTools.getStringAttribute(aAttrs,ENTITY_NAME_ATTR,"");
    Entity entityA=model.getEntity(nameA);
    if (entityA==null)
    {
      throw new IllegalArgumentException("Entity "+nameA+" referenced by relation "+name+" does not exists !");
    }
    // Entity B
    Element elementB=DOMParsingTools.getChildTagByName(e,B_TAG);
    NamedNodeMap bAttrs=elementB.getAttributes();
    String nameB=DOMParsingTools.getStringAttribute(bAttrs,ENTITY_NAME_ATTR,"");
    Entity entityB=model.getEntity(nameB);
    if (entityB==null)
    {
      throw new IllegalArgumentException("Entity "+nameB+" referenced by relation "+name+" does not exists !");
    }

    // Build relation
    EditableRelation relation=model.addBinaryAssociation(entityA,entityB);
    relation.setName(name);
    EditableRelationTier tierA=relation.getTier(0);
    String aRole=DOMParsingTools.getStringAttribute(aAttrs,ROLE_ATTR,name);
    tierA.setRole(aRole);

    EditableRelationTier tierB=relation.getTier(1);
    String bRole=DOMParsingTools.getStringAttribute(bAttrs,ROLE_ATTR,name);
    tierB.setRole(bRole);

    // Links
    EditableRelationLink AtoB=relation.getEditableLink(tierA,tierB);
    Element elementAtoB=DOMParsingTools.getChildTagByName(e,AtoB_TAG);
    parseLink(AtoB,elementAtoB);

    EditableRelationLink BtoA=relation.getEditableLink(tierB,tierA);
    Element elementBtoA=DOMParsingTools.getChildTagByName(e,BtoA_TAG);
    parseLink(BtoA,elementBtoA);
    return relation;
  }

  private RelationLink parseLink(EditableRelationLink link, Element e)
  {
    NamedNodeMap attrs=e.getAttributes();
    String meaning=DOMParsingTools.getStringAttribute(attrs,MEANING_ATTR,"");
    link.setLabel(meaning);
    int min=DOMParsingTools.getIntAttribute(attrs,MIN_ATTR,0);
    link.setMinOccurrences(min);
    int max=DOMParsingTools.getIntAttribute(attrs,MAX_ATTR,Integer.MAX_VALUE);
    link.setMaxOccurrences(max);
    boolean ordered=DOMParsingTools.getBooleanAttribute(attrs,ORDERED_ATTR,false);
    link.setOrdered(ordered);
    boolean traversable=DOMParsingTools.getBooleanAttribute(attrs,TRAVERSABLE_ATTR,true);
    link.setTraversable(traversable);
    return link;
  }
}
