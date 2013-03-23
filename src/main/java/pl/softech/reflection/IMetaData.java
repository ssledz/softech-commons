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

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
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
