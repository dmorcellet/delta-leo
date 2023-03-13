package delta.leo.data.filters;

import java.util.ArrayList;

import delta.leo.data.ObjectInstance;
import delta.leo.info.LogicalOperator;

/**
 * Compound filter element: a filter element that manages:
 * <ul>
 * <li>child filter elements,
 * <li>a logical operator.
 * </ul>
 * @author DAM
 */
public class CompoundFilterElement extends FilterElement
{
  private LogicalOperator _logicalOperator;
  private ArrayList<FilterElement> _elements;

  /**
   * Constructor.
   * @param logicalOperator Logical operator.
   */
  public CompoundFilterElement(LogicalOperator logicalOperator)
  {
    _logicalOperator=logicalOperator;
  }

  /**
   * Add a filter element.
   * @param element Element to add.
   */
  public void addFilterElement(FilterElement element)
  {
    if (_elements==null) _elements=new ArrayList<FilterElement>();
    _elements.add(element);
  }

  /**
   * Get the number of elements.
   * @return an elements count.
   */
  public int getNumberOfElements()
  {
    if (_elements==null) return 0;
    return _elements.size();
  }

  /**
   * Get a child element.
   * @param index Index of the element to get (starting at 0).
   * @return A child filter element (or <code>null</code>).
   */
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

  /**
   * Get the logical operator.
   * @return a logical operator.
   */
  public LogicalOperator getLogicalOperator()
  {
    return _logicalOperator;
  }
}
