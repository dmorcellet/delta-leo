package delta.leo.data.filters;

import java.util.ArrayList;

import delta.leo.data.ObjectInstance;
import delta.leo.info.LogicalOperator;

public class CompoundFilterElement extends FilterElement
{
  private LogicalOperator _logicalOperator;
  private ArrayList<FilterElement> _elements;

  public CompoundFilterElement(LogicalOperator logicalOperator)
  {
    _logicalOperator=logicalOperator;
  }

  public void addFilterElement(FilterElement element)
  {
    if (_elements==null) _elements=new ArrayList<FilterElement>();
    _elements.add(element);
  }

  public int getNumberOfElements()
  {
    if (_elements==null) return 0;
    return _elements.size();
  }

  public FilterElement getElement(int index)
  {
    if (_elements==null) return null;
    if ((index<0) || (index>=_elements.size())) return null;
    return _elements.get(index);
  }

  @Override
  public boolean match(ObjectInstance o)
  {
    int nb=getNumberOfElements();
    FilterElement element;
    for(int i=0;i<nb;i++)
    {
      element=getElement(i);
      boolean match=element.match(o);
      if ((!match) && (_logicalOperator==LogicalOperator.AND))
        return false;
      if ((match) && (_logicalOperator==LogicalOperator.OR))
        return true;
    }
    return (_logicalOperator==LogicalOperator.AND);
  }

  public LogicalOperator getLogicalOperator()
  {
    return _logicalOperator;
  }
}
