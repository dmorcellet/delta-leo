package delta.leo.model.relation;

public interface RelationLink
{
  public RelationTier getSource();

  public RelationTier getTarget();

  public int getMinOccurrences();

  public int getMaxOccurrences();

  public boolean isTraversable();

  public boolean isOrdered();

  public String getLabel();
}
