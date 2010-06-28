package delta.leo.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import delta.leo.model.Model;
import delta.leo.model.entity.Entity;
import delta.leo.model.relation.RelationLink;

public class Model2Classes
{
	private TreeMap<String,ObjectClass> _builtClasses;

	public List<ObjectClass> build(Model model)
	{
		List<Entity> entities=model.getEntities();
		_builtClasses=new TreeMap<String,ObjectClass>();
		Entity e;
		for(Iterator<Entity> it=entities.iterator();it.hasNext();)
		{
			e=it.next();
			getClassForEntity(model,e);
		}
		Set<String> classNames=_builtClasses.keySet();
    String className;
    ObjectClass clazz;
    List<ObjectClass> classes=new ArrayList<ObjectClass>();
    for(Iterator<String> it=classNames.iterator();it.hasNext();)
    {
      className=it.next();
      clazz=_builtClasses.get(className);
      classes.add(clazz);
    }
    return classes;
	}

	private ObjectClass getClassForEntity(Model model, Entity entity)
	{
		ObjectClass clazz=_builtClasses.get(entity.getName());
		if (clazz==null)
		{
			Entity superEntity=entity.getSuperEntity();
			ObjectClass parentClass=null;
			if (superEntity!=null)
			{
				parentClass=getClassForEntity(model,entity);
			}
      List<RelationLink> relations=model.getLinksFrom(entity,true);
			clazz=ObjectClass.build(entity,relations,parentClass);
      _builtClasses.put(clazz.getName(),clazz);
		}
		return clazz;
	}
}
