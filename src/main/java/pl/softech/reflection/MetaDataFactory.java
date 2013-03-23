/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class MetaDataFactory<T extends Annotation> implements IMetaDataFactory<T> {

    protected static final int DEFAULT_DEPTH = 5;

    protected static final String DEFAULT_ACCESS_SEPARATOR = "|";
    protected static final String DEFAULT_INVOKE_SEPARATOR = "&";

    protected String accessSeparator = DEFAULT_ACCESS_SEPARATOR;
    protected String invokeSeparator = DEFAULT_INVOKE_SEPARATOR;

	@SuppressWarnings("hiding")
	protected class MetaData<T extends Annotation> implements IMetaData<T> {

		protected String name;
		protected Class<?> type;
		protected Class<?> clazz;
		protected MetaData<T> parentColumnMetaData;
		protected AccessibleObject ao;
		protected Class<?>[] methodParameters;
		protected ColumnTyp columnTyp;
		protected T annotation;
		protected String setterName;

		protected MetaData(Class<?> clazz, Class<?> type, AccessibleObject ao,
				T annotation, MetaData<T> parentColumnMetaData) {

			this.annotation = annotation;
			this.clazz = clazz;
			this.type = type;
			this.ao = ao;
			this.parentColumnMetaData = parentColumnMetaData;

		}

		protected MetaData(Class<?> clazz, Field field, T annotation,
				MetaData<T> parentColumnMetaData) {

			this(clazz, field.getType(), field, annotation,
					parentColumnMetaData);
			columnTyp = ColumnTyp.FIELD;
			name = field.getName();

		}

		protected MetaData(Class<?> clazz, Method method, T annotation,
				MetaData<T> parentColumnMetaData) {

			this(clazz, method.getReturnType(), method, annotation,
					parentColumnMetaData);
			columnTyp = ColumnTyp.METHOD;
			methodParameters = new Class[] { type };
			setterName = "s" + method.getName().substring(1);
			name = method.getName().replaceFirst("^get", "");
			name = name.substring(0, 1).toLowerCase() + name.substring(1);

		}

		@Override
		public String getFullPath() {
			String ret = null;
			if(parentColumnMetaData == null)
				ret = clazz.getName() + accessSeparator + name;
			else ret = parentColumnMetaData.getFullPath() + accessSeparator + name; 
			return ret;
		}

		@Override
		public T getAnnotation() {
			return annotation;
		}

		public ColumnTyp getColumnTyp() {
			return columnTyp;
		}

		@Override
		public Class<?> getType() {
			return type;
		}

		@Override
		public String toString() {
			String ret = "class: " + clazz.getName();
			ret += "\nfull path: " + name;
			ret += "\nname: " + name;
			ret += "\nao: " + (ao instanceof Field ? "field" : "method");
			ret += "\ntype: " + type.getName();
			if (ao instanceof Method) {
				ret += "\nmethod parameters : ";
				String tmp = "";
				for (Class<?> p : methodParameters)
					tmp += "," + p.getName();
				if (tmp.length() > 0)
					ret += tmp.substring(1);
				else
					ret += "lack";
			}
			return ret;
		}

		private final Object processValueFrom(Object object)
				throws IllegalArgumentException, IllegalAccessException,
				IllegalMetaDataUsage {

			if (object.getClass() != clazz) {

				if (parentColumnMetaData == null)
					throw new IllegalArgumentException(
							"parentColumn can't be null.");

				object = parentColumnMetaData.getValue(object);

			}

			return object;

		}

		private final Object getValueFromMethod(Object object)
				throws IllegalArgumentException, IllegalAccessException,
				IllegalMetaDataUsage, InvocationTargetException {

			Method m = (Method) ao;

			object = processValueFrom(object);

			return m.invoke(object, new Object[0]);
		}

		private final Object getValueFromField(Object object)
				throws IllegalArgumentException, IllegalAccessException,
				IllegalMetaDataUsage {

			Field f = (Field) ao;

			object = processValueFrom(object);

			return f.get(object);

		}

		private final boolean processValueSet(Object src, Object value)
				throws IllegalArgumentException, IllegalMetaDataUsage {

			if (src.getClass() != clazz) {

				if (parentColumnMetaData == null)
					throw new IllegalArgumentException(
							"parentColumn can't be null.");

				setValue(parentColumnMetaData.getValue(src), value);
				return true;

			}

			return false;

		}

		private final void setFieldValue(Object src, Object value)
				throws IllegalMetaDataUsage, IllegalArgumentException,
				IllegalAccessException {

			Field f = (Field) ao;

			if (processValueSet(src, value))
				return;

			f.set(src, value);

		}

		private final void setMethodValue(Object src, Object value)
				throws IllegalMetaDataUsage, IllegalArgumentException,
				IllegalAccessException, InvocationTargetException,
				NoSuchMethodException {

			if (processValueSet(src, value))
				return;

			Method m = (Method) clazz.getMethod(setterName, methodParameters);

			m.invoke(src, value);

		}

		@Override
		public void setValue(Object src, Object value)
				throws IllegalMetaDataUsage {

			if (src == null)
				throw new IllegalMetaDataUsage("src parameter can't be null");

			try {

				if (columnTyp == ColumnTyp.FIELD)
					setFieldValue(src, value);
				else
					setMethodValue(src, value);

			} catch (Exception e) {
				throw new IllegalMetaDataUsage("Illegal Argument.", e);
			}

		}

		@Override
		public Object getValue(Object src) throws IllegalMetaDataUsage {

			if (src == null)
				return null;

			try {

				if (columnTyp == ColumnTyp.FIELD)
					return getValueFromField(src);
				return getValueFromMethod(src);

			} catch (Exception e) {
				throw new IllegalMetaDataUsage("Illegal Argument.", e);
			}

		}

		/**
		 * @see jscl.reflection.IMetaData#getParent()
		 */
		@Override
		public IMetaData<T> getParent() { return parentColumnMetaData; }
	}

    private Class<T> annotationClass;

    public MetaDataFactory(Class<T> annotationClass) {
        this.annotationClass = annotationClass;
    }
	
    protected Collection<IMetaData<T>> processColumnData(Class<?> clazz,
            AccessibleObject ao, MetaData<T> parentColumn, int depth) {
            T ann = ao.getAnnotation(annotationClass);
            if(ann == null) return null;
            MetaData<T> metaData = null;
            if(ao instanceof Field)
                metaData = new MetaData<T>(clazz, (Field)ao, ann, parentColumn);
            else metaData = new MetaData<T>(clazz, (Method)ao, ann, parentColumn);

            Collection<IMetaData<T>> ret = class2ColumnMetaData(metaData.type,
                    metaData, depth - 1);

            if(ret == null)
                ret = new LinkedList<IMetaData<T>>();

            ret.add(metaData);

            return ret;


    }

    @Override
    public String getAccessSeparator() { return accessSeparator; }

    @Override
    public Map<String, IMetaData<T>> class2MetaDataByFullPath(Class<?> clazz) {

        IMetaDataComparator<String> comparator = new IMetaDataComparator<String>() {

            @SuppressWarnings("hiding")
			public <T extends Annotation> String getName(IMetaData<T> m) {
                return m.getFullPath();
            }

            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };

        return class2ColumnMetaDataBySring(clazz,comparator);
    }

    protected interface IMetaDataComparator<E> extends Comparator<E> {

        public <T extends Annotation> E getName(IMetaData<T> m);

    }

    protected Map<String, IMetaData<T>> class2ColumnMetaDataBySring(Class<?> clazz,
            IMetaDataComparator<String> comparator) {

        Collection<IMetaData<T>> metaData =
                class2ColumnMetaData(clazz, null, DEFAULT_DEPTH);

        Map<String, IMetaData<T>> name2MetaData = 
                new TreeMap<String, IMetaData<T>>(comparator);

        for(IMetaData<T> m : metaData)
            name2MetaData.put(comparator.getName(m), m);

        return name2MetaData;

    }

    @Override
    public Collection<IMetaData<T>> class2MetaData(Class<?> clazz) {
        return class2ColumnMetaData(clazz, null, DEFAULT_DEPTH);
    }

    protected Collection<IMetaData<T>> class2ColumnMetaData(Class<?> clazz,
            MetaData<T> parentColumn, int depth) {

        if(depth <= 0) return null;

        Collection<IMetaData<T>> ret = new LinkedList<IMetaData<T>>();

        Collection<AccessibleObject> accessibleObjects =
                new LinkedList<AccessibleObject>();

        accessibleObjects.addAll(Arrays.asList(clazz.getFields()));
        accessibleObjects.addAll(Arrays.asList(clazz.getMethods()));

        for(AccessibleObject ao : accessibleObjects) {

            Collection<IMetaData<T>> metaDataCollection = processColumnData(
                    clazz, ao, parentColumn, depth);
            if(metaDataCollection != null && metaDataCollection.size() > 0)
                ret.addAll(metaDataCollection);

        }

        return ret;
    }

}
