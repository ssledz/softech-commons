package pl.softech.reflection;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

/**
 * 
 * @author Sławomir Śledź
 * @since 1.0
 */
public interface IMetaDataFactory<T extends Annotation> {

	public Collection<IMetaData<T>> class2MetaData(Class<?> clazz);

	public Map<String, IMetaData<T>> class2MetaDataByFullPath(Class<?> clazz);

	public String getAccessSeparator();

}
