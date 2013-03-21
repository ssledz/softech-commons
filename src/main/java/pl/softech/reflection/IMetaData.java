package pl.softech.reflection;

import java.lang.annotation.Annotation;

/**
 *
 * @author Sławomir Śledź
 * @since 1.0
 */
public interface IMetaData<T extends Annotation> {

	public static class IllegalMetaDataUsage extends Exception {

		private static final long serialVersionUID = 1L;

		protected IllegalMetaDataUsage(String s, Exception e) {
			super(s, e);
		}

		protected IllegalMetaDataUsage(String s) {
			super(s);
		}

	}

	public enum ColumnTyp {
		FIELD, METHOD
	};

	public void setValue(Object src, Object value) throws IllegalMetaDataUsage;

	public Object getValue(Object src) throws IllegalMetaDataUsage;

	/**
	 * Returns the parent of this meta-data.
	 * @return The parent or null if meta-data doesn't have parent. 
	 */
	public IMetaData<T> getParent();

	public String getFullPath();

	public T getAnnotation();

	public Class<?> getType();

}
