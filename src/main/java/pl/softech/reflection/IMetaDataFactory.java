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
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public interface IMetaDataFactory<T extends Annotation> {

	public Collection<IMetaData<T>> class2MetaData(Class<?> clazz);

	public Map<String, IMetaData<T>> class2MetaDataByFullPath(Class<?> clazz);

	public String getAccessSeparator();

}
